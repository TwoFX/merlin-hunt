package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.util.Map;
import java.util.PriorityQueue;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;

public class RubyHuffmanProgramGenerator implements IProgramGenerator {

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean errors) {
        // TODO Auto-generated method stub
        return null;
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
}
