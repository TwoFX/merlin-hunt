package de.markushimmel.merlinhunt.twistylittlepassages;

import static java.util.stream.Collectors.toSet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;

import io.quarkus.logging.Log;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate", mixinStandardHelpOptions = true)
public class GenerateBuildScriptCommand implements Runnable {

    private static final String ANIMALS_FILE = "/animals.txt";
    private static final String ADJECTIVES_FILE = "/adjectives.txt";
    private static final long RANDOM_SEED = 443443;

    private final Random random = new Random(RANDOM_SEED);
    private final Set<Long> generatedLongs = new HashSet<>();

    @Option(names = { "-o", "--output" }, description = "File name to write output to", required = true)
    String outputFileName;

    @Option(names = { "-a", "--answer" }, description = "File name to write answer to", required = true)
    String answerFileName;

    @Override
    public void run() {
        List<String> solutions = new ArrayList<>(getSolutions());
        List<Identifier> identifiers = new ArrayList<>(getIdentifiers());

        Collections.shuffle(solutions, random);
        Collections.shuffle(identifiers, random);

        Deque<Identifier> idQueue = new ArrayDeque<>(identifiers);
        List<ITable> tables = new ArrayList<>();
        RootTable root = new RootTable(idQueue.pop(), idQueue.pop());
        tables.add(root);

        while (idQueue.size() >= 2) {
            ITable parent = tables.get(random.nextInt(tables.size()));
            tables.add(parent.addChild(idQueue.pop(), idQueue.pop()));
        }

        Log.infof("Expected number of lines: %d", solutions.size() * tables.size());

        List<ITable> leaves = tables.stream().filter(ITable::isLeaf).toList();

        int fullPosition = random.nextInt(solutions.size());
        for (int index = 0; index < solutions.size(); index++) {
            Set<Identifier> selectedLeaves = index == fullPosition //
                    ? leaves.stream().map(ITable::getIdentifier).collect(toSet()) //
                    : randomSubset(leaves);
            dfsRoot(root, selectedLeaves, solutions.get(index));
        }

        try (BufferedWriter outFile = Files.newBufferedWriter(Path.of(outputFileName), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            outFile.append("BEGIN;\n\n");
            for (ITable table : tables) {
                table.printToFile(outFile);
            }
            outFile.append("COMMIT;\n\n");

            outFile.append("CREATE USER AzureDiamond WITH PASSWORD 'hunter2';\n");
            outFile.append("GRANT SELECT ON ALL TABLES IN SCHEMA public to AzureDiamond;\n");

            Files.writeString(Path.of(answerFileName), solutions.get(fullPosition), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            Log.error("Unable to open output file", e);
            return;
        }

    }

    private void dfsRoot(RootTable root, Set<Identifier> selectedLeaves, String solution) {
        Map<Identifier, Long> entry = new HashMap<>();

        for (Edge edge : root.getChildren()) {
            Optional<Long> inner = dfs(edge.child(), selectedLeaves);
            entry.put(edge.identifier(), inner.orElseGet(this::generateLong));
        }

        root.addEntry(entry, solution);
    }

    private Optional<Long> dfs(InnerTable current, Set<Identifier> selectedLeaves) {
        Map<Identifier, Long> entry = new HashMap<>();
        long ownNumber = generateLong();

        boolean isContained = selectedLeaves.contains(current.getIdentifier());
        for (Edge edge : current.getChildren()) {
            Optional<Long> inner = dfs(edge.child(), selectedLeaves);
            isContained |= inner.isPresent();
            entry.put(edge.identifier(), inner.orElseGet(this::generateLong));
        }

        if (isContained) {
            current.addEntry(entry, ownNumber);
            return Optional.of(ownNumber);
        } else {
            return Optional.empty();
        }
    }

    private Set<Identifier> randomSubset(List<? extends ITable> base) {
        return base.stream().filter(t -> random.nextBoolean()).map(ITable::getIdentifier).collect(toSet());
    }

    private List<Identifier> getIdentifiers() {
        List<String> adjectives = readResource(ADJECTIVES_FILE);
        List<String> animals = readResource(ANIMALS_FILE);
        return adjectives.stream()
                .flatMap(adjective -> animals.stream().map(animal -> new Identifier(adjective, animal))).toList();
    }

    private List<String> getSolutions() {
        return Lists.cartesianProduct( //
                List.of("You're", "You are"), //
                List.of(" in", " inside"), //
                List.of(" a "), //
                List.of("", "twisty little ", "little twisty ", "little ", "twisty "), //
                List.of("maze "), //
                List.of("", "full "), //
                List.of("of "), //
                List.of("", "twisty little ", "little twisty ", "little ", "twisty "), //
                List.of("passages", "pathways"), //
                List.of("; ", ", ", " - ", "---"), //
                List.of("all", "each of them"), //
                List.of(" different", " unequal"), //
                List.of(".", "..", "...", "!", "") //
        ).stream() //
                .map(l -> String.join("", l)) //
                .toList();
    }

    private List<String> readResource(String name) {
        try (InputStream input = this.getClass().getResourceAsStream(name)) {
            String readString = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            return List.of(readString.split("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private interface ITable {
        InnerTable addChild(Identifier id, Identifier edgeId);

        boolean isLeaf();

        Identifier getIdentifier();

        void printToFile(Appendable writer) throws IOException;
    }

    private static abstract class Table<T extends Entry> implements ITable {

        private final Identifier identifier;
        private final List<Edge> children;
        private final List<T> entries;

        public Table(Identifier identifier) {
            this.identifier = identifier;
            this.children = new ArrayList<>();
            this.entries = new ArrayList<>();
        }

        public void printToFile(Appendable writer) throws IOException {
            Log.infof("Table %s has %d entries", identifier.prefixString(), entries.size());

            writer //
                    .append("CREATE TABLE ") //
                    .append(identifier.prefixString()) //
                    .append(" (");

            printAdditionalColumns(writer);

            for (Edge edge : children) {
                writer.append(", ").append(edge.identifier().prefixString()).append(" bigint");
            }

            writer.append(");\n\n");

            for (T entry : entries) {
                writer.append("INSERT INTO ").append(identifier.prefixString()).append(" (");
                printAdditionalColumnNames(writer, entry);
                for (Edge edge : children) {
                    writer.append(", ").append(edge.identifier().prefixString());
                }

                writer.append(") VALUES (");
                printAdditionalColumnValues(writer, entry);
                for (Edge edge : children) {
                    writer.append(", ").append(entry.childValues().get(edge.identifier()).toString());
                }
                writer.append(");\n");
            }
            writer.append("\n");
            printAdditionalStateements(writer);
            writer.append("\n");
        }

        protected abstract void printAdditionalColumns(Appendable writer) throws IOException;

        protected abstract void printAdditionalColumnNames(Appendable writer, T entry) throws IOException;

        protected abstract void printAdditionalColumnValues(Appendable writer, T entry) throws IOException;

        protected abstract void printAdditionalStateements(Appendable writer) throws IOException;

        protected void addEntry(T entry) {
            entries.add(entry);
        }

        public List<Edge> getChildren() {
            return Collections.unmodifiableList(children);
        }

        public boolean isLeaf() {
            return children.isEmpty();
        }

        public InnerTable addChild(Identifier id, Identifier edgeId) {
            InnerTable child = new InnerTable(edgeId, id);
            children.add(new Edge(child, edgeId));
            return child;
        }

        public Identifier getIdentifier() {
            return identifier;
        }

    }

    private long generateLong() {
        long generated = random.nextLong();
        while (!generatedLongs.add(generated)) {
            Log.info("Had a collision...");
            generated = random.nextLong();
        }
        return generated;
    }

    private interface Entry {
        Map<Identifier, Long> childValues();
    }

    private static class RootTable extends Table<RootTableEntry> {

        private final Identifier solutionIdentifier;

        public RootTable(Identifier solutionIdentifier, Identifier identifier) {
            super(identifier);
            this.solutionIdentifier = solutionIdentifier;
        }

        public void addEntry(Map<Identifier, Long> childValues, String solution) {
            super.addEntry(new RootTableEntry(childValues, solution));
        }

        @Override
        protected void printAdditionalColumns(Appendable writer) throws IOException {
            writer.append(solutionIdentifier.prefixString()).append(" text");
        }

        @Override
        protected void printAdditionalColumnNames(Appendable writer, RootTableEntry entry) throws IOException {
            writer.append(solutionIdentifier.prefixString());
        }

        @Override
        protected void printAdditionalColumnValues(Appendable writer, RootTableEntry entry) throws IOException {
            writer.append("'").append(entry.solution().replace("'", "''")).append("'");
        }

        @Override
        protected void printAdditionalStateements(Appendable writer) throws IOException {
            // Do nothing
        }

    }

    private record RootTableEntry(Map<Identifier, Long> childValues, String solution) implements Entry {
    }

    private static class InnerTable extends Table<InnerTableEntry> {

        private final Identifier parentEdgeIdentifier;

        public InnerTable(Identifier parentEdgeIdentifier, Identifier identifier) {
            super(identifier);
            this.parentEdgeIdentifier = parentEdgeIdentifier;
        }

        public void addEntry(Map<Identifier, Long> childValues, long parentValue) {
            super.addEntry(new InnerTableEntry(childValues, parentValue));
        }

        @Override
        protected void printAdditionalColumns(Appendable writer) throws IOException {
            writer.append(parentEdgeIdentifier.postfixString()).append(" bigint");
        }

        @Override
        protected void printAdditionalColumnNames(Appendable writer, InnerTableEntry entry) throws IOException {
            writer.append(parentEdgeIdentifier.postfixString());
        }

        @Override
        protected void printAdditionalColumnValues(Appendable writer, InnerTableEntry entry) throws IOException {
            writer.append(String.valueOf(entry.parentValue()));
        }

        @Override
        protected void printAdditionalStateements(Appendable writer) throws IOException {
            writer //
                    .append("CREATE UNIQUE INDEX ") //
                    .append(getIdentifier().prefixString()) //
                    .append("_idx ON ") //
                    .append(getIdentifier().prefixString()) //
                    .append(" (") //
                    .append(parentEdgeIdentifier.postfixString()) //
                    .append(");\n");
        }

    }

    private record InnerTableEntry(Map<Identifier, Long> childValues, long parentValue) implements Entry {
    }

    private record Edge(InnerTable child, Identifier identifier) {
    }

    private record Identifier(String adjective, String animal) {

        public String prefixString() {
            return adjective() + "_" + animal();
        }

        public String postfixString() {
            return animal() + "_that_is_" + adjective();
        }
    }

}