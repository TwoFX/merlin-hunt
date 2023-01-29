package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Supplier;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.util.StringEscapeHelper;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

public class RubyHuffmanProgramGenerator implements IProgramGenerator {

    private static final long RANDOM_SEED = 33343343;
    private static final double OBFUSCATION_PROBABILITY = 0.05;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance program(String standardOutput, int standardOutputBitLength,
                String standardError, int standardErrorBitLength,
                String decoder);
    }

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean errors) {
        Map<Character, Integer> frequencies = recordFrequencies(standardOutput, standardError);
        HuffmanNode root = createHuffmanTree(frequencies);
        Map<Character, String> code = retrieveCode(root);
        EncodedString encodedStandardOutput = encode(code, standardOutput);
        EncodedString encodedStandardError = encode(code, standardError);
        return Templates
                .program(encodedStandardOutput.data(), encodedStandardOutput.bitLength(), encodedStandardError.data(),
                        encodedStandardError.bitLength(), buildDecoder(root, errors))
                .render();
    }

    private EncodedString encode(Map<Character, String> code, String input) {
        CodeBuilder builder = new CodeBuilder();
        char[] inputArray = input.toCharArray();
        for (char c : inputArray) {
            builder.push(code.get(c));
        }
        return builder.build();
    }

    private Map<Character, Integer> recordFrequencies(String... inputs) {
        Map<Character, Integer> frequencies = new HashMap<>();
        Arrays.stream(inputs).flatMap(s -> s.chars().mapToObj(c -> Character.valueOf((char) c)))
                .forEach(c -> frequencies.compute(c, (k, v) -> v == null ? 1 : v + 1));
        return Collections.unmodifiableMap(frequencies);
    }

    private String buildDecoder(HuffmanNode root, boolean errors) {
        Random random = errors ? new Random(RANDOM_SEED) : null;
        StringBuilder builder = new StringBuilder();
        buildDecoderRecursively(root, 1, builder,
                errors ? () -> random.nextDouble() < OBFUSCATION_PROBABILITY : () -> false);
        return builder.toString();
    }

    private void buildDecoderRecursively(HuffmanNode node, int depth, StringBuilder builder,
            Supplier<Boolean> obfuscate) {
        switch (node) {
            case HuffmanLeaf leaf -> {
                String decodedString = obfuscate.get() ? "?" : StringEscapeHelper.escape(leaf.character());
                appendLine(builder, depth, "return pos, \"", decodedString,
                        "\"");
            }
            case HuffmanInnerNode innerNode -> {
                appendLine(builder, depth, "pos, b = get_bit(unpacked, pos)");
                appendLine(builder, depth, "if !b");
                buildDecoderRecursively(innerNode.leftSubtree(), depth + 1, builder, obfuscate);
                appendLine(builder, depth, "else");
                buildDecoderRecursively(innerNode.rightSubtree(), depth + 1, builder, obfuscate);
                appendLine(builder, depth, "end");
            }
        }
    }

    private void appendLine(StringBuilder builder, int depth, String... components) {
        for (int i = 0; i < depth; i++) {
            builder.append("  ");
        }
        for (String component : components) {
            builder.append(component);
        }
        builder.append("\n");
    }

    private Map<Character, String> retrieveCode(HuffmanNode root) {
        Map<Character, String> code = new HashMap<>();
        retrieveCodeRecursively(root, "", code);
        return Collections.unmodifiableMap(code);
    }

    private void retrieveCodeRecursively(HuffmanNode node, String path, Map<Character, String> code) {
        switch (node) {
            case HuffmanLeaf leaf -> code.put(leaf.character(), path);
            case HuffmanInnerNode innerNode -> {
                retrieveCodeRecursively(innerNode.leftSubtree(), path + "0", code);
                retrieveCodeRecursively(innerNode.rightSubtree(), path + "1", code);
            }
        }
    }

    private HuffmanNode createHuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

        for (var entry : frequencies.entrySet()) {
            queue.add(new HuffmanLeaf(entry.getValue(), entry.getKey()));
        }

        while (queue.size() >= 2) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            int newFrequency = left.frequency() + right.frequency();
            queue.add(new HuffmanInnerNode(newFrequency, left, right));
        }

        return queue.poll();
    }

    private sealed interface HuffmanNode extends Comparable<HuffmanNode> permits HuffmanLeaf, HuffmanInnerNode {
        int frequency();

        default int compareTo(HuffmanNode other) {
            return Integer.compare(frequency(), other.frequency());
        }
    }

    private record HuffmanLeaf(int frequency, char character) implements HuffmanNode {
    }

    private record HuffmanInnerNode(int frequency, HuffmanNode leftSubtree, HuffmanNode rightSubtree)
            implements HuffmanNode {
    }

    private static class CodeBuilder {
        private List<Byte> bytes = new ArrayList<>();
        private int currentBitLength = 0;

        public void push(String code) {
            char[] bits = code.toCharArray();
            for (char c : bits) {
                if (c == '0') {
                    pushBit(false);
                } else if (c == '1') {
                    pushBit(true);
                } else {
                    throw new IllegalArgumentException("Code words must be 01-strings.");
                }
            }
        }

        private void pushBit(boolean b) {
            int indexInByte = currentBitLength % Byte.SIZE;
            if (indexInByte == 0) {
                bytes.add((byte) 0);
            }
            if (b) {
                int byteIndex = bytes.size() - 1;
                byte newValue = (byte) (bytes.get(byteIndex) | (1 << (Byte.SIZE - indexInByte - 1)));
                bytes.set(byteIndex, newValue);
            }
            currentBitLength++;
        }

        public EncodedString build() {
            byte[] result = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                result[i] = bytes.get(i);
            }
            return new EncodedString(base64(result), currentBitLength);
        }

        private String base64(byte[] data) {
            return Base64.getEncoder().encodeToString(data);
        }
    }

    public record EncodedString(String data, int bitLength) {
    }
}
