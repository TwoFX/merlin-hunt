package de.markushimmel.merlinhunt.twistylittlepassages;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import io.quarkus.logging.Log;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate", mixinStandardHelpOptions = true)
public class GenerateBuildScriptCommand implements Runnable {

    private static final String ANIMALS_FILE = "/animals.txt";
    private static final String ADJECTIVES_FILE = "/adjectives.txt";
    private static final long RANDOM_SEED = 443443;

    @Option(names = { "-o", "--output" }, description = "File name to write output to", required = true)
    String outputFileName;

    @Override
    public void run() {
        List<String> solutions = new ArrayList<>(getSolutions());
        List<Identifier> identifiers = new ArrayList<>(getIdentifiers());

        Random random = new Random(RANDOM_SEED);
        Collections.shuffle(solutions, random);
        Collections.shuffle(identifiers, random);

        Deque<Identifier> idQueue = new ArrayDeque<>(identifiers);
        List<Table> tables = new ArrayList<>();
        Table root = new Table(null, idQueue.pop());
        tables.add(root);

        while (!idQueue.isEmpty()) {
            Table parent = tables.get(random.nextInt(tables.size()));
            tables.add(parent.addChild(idQueue.pop(), idQueue.pop()));
        }

        try {
            BufferedWriter outFile = Files.newBufferedWriter(Path.of(outputFileName), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            Log.error("Unable to open output file", e);
            return;
        }

        /*
         * The plan:
         * Generate a list of tables, one of which is clearly marked (or not!).
         * 
         * -> All of them have animal names, only one of them has the name
         * "this one is not like the others".
         * 
         * Generate a random tree on them.
         * 
         * Generate a bunch of rooted subtrees and create entries that match those
         * subtrees
         * 
         * Tables have indices for the column that is incoming
         */

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
        try {
            return Files.readAllLines(Path.of(this.getClass().getResource(name).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Table {

        private final Table parent;
        private final Identifier identifier;
        private final List<Edge> children;

        public Table(Table parent, Identifier identifier) {
            this.parent = parent;
            this.identifier = identifier;
            this.children = new ArrayList<>();
        }

        public Table getParent() {
            return parent;
        }

        public List<Edge> getChildren() {
            return Collections.unmodifiableList(children);
        }

        public Table addChild(Identifier id, Identifier edgeId) {
            Table child = new Table(this, id);
            children.add(new Edge(child, edgeId));
            return child;
        }

        public Identifier getIdentifier() {
            return identifier;
        }

    }

    private record Edge(Table child, Identifier identifier) {

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