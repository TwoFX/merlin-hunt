package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.util.ArrayList;
import java.util.List;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;

public class BrainfuckProgramGenerator implements IProgramGenerator {

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean errors) {
        String output = String.format("out: %s\n\n err: %s\n", standardOutput, standardError);
        String program = generateProgram(output);

        if (errors) {
            program = program.replaceFirst("\\[", "{");
        }

        return program;
    }

    /* Visible for testing */
    String generateProgram(String toPrint) {
        String[] up = new String[256];
        String[] down = new String[256];

        up[0] = "";
        down[0] = "";

        List<Opportunity> upPossibilities = new ArrayList<>();
        List<Opportunity> downPossibilities = new ArrayList<>();

        upPossibilities.add(new Opportunity(1, "+"));
        downPossibilities.add(new Opportunity(1, "-"));

        for (int a = 1; a < 256; a++) {
            for (int b = 1; b < 256; b++) {
                if (a * b > 256) {
                    break;
                }

                upPossibilities.add(new Opportunity(a * b, multiply(a, b, false)));
                downPossibilities.add(new Opportunity(a * b, multiply(a, b, true)));
            }
        }

        for (int i = 0; i < 256; i++) {
            for (Opportunity opp : upPossibilities) {
                if (i + opp.amount() >= 256) {
                    continue;
                }
                String best = up[i + opp.amount()];
                if (best != null && up[i].length() + opp.code().length() >= best.length()) {
                    continue;
                }

                up[i + opp.amount()] = up[i] + opp.code();
            }
            for (Opportunity opp : downPossibilities) {
                if (i + opp.amount() >= 256) {
                    continue;
                }
                String best = down[i + opp.amount()];
                if (best != null && down[i].length() + opp.code().length() >= best.length()) {
                    continue;
                }

                down[i + opp.amount()] = down[i] + opp.code();
            }
        }
        // 1508
        List<Integer> current = new ArrayList<>();
        current.add(0);
        int currentIndex = 0;

        StringBuilder result = new StringBuilder();
        int[] codes = toPrint.codePoints().toArray();
        for (int code : codes) {
            String best = null;
            int bestPosition = -1;
            for (int i = 0; i < current.size(); i++) {
                StringBuilder testCode = new StringBuilder();
                int pos = currentIndex;
                while (pos != i) {
                    if (pos > i) {
                        testCode.append("<<");
                        pos--;
                    }
                    if (pos < i) {
                        testCode.append(">>");
                        pos++;
                    }
                }
                int codeAtPosition = current.get(i);
                testCode.append(codeAtPosition < code ? up[code - codeAtPosition] : down[codeAtPosition - code]);
                testCode.append('.');
                if (best == null || best.length() > testCode.length()) {
                    best = testCode.toString();
                    bestPosition = i;
                }
            }
            result.append(best);
            current.set(bestPosition, code);
            currentIndex = bestPosition;
            if (bestPosition == current.size() - 1) {
                current.add(0);
            }
        }
        return result.toString();
    }

    private record Opportunity(int amount, String code) {
    }

    private String multiply(int a, int b, boolean down) {
        // > b+ [< a+ > -]
        StringBuilder result = new StringBuilder(a + b + 7);
        result.append('>');

        for (int i = 0; i < b; i++) {
            result.append('+');
        }

        result.append("[<");

        for (int i = 0; i < a; i++) {
            result.append(down ? '-' : '+');
        }

        result.append(">-]<");
        return result.toString();
    }

}
