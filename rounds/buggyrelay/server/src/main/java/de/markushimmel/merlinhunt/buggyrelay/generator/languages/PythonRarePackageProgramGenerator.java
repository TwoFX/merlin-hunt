package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.util.StringEscapeHelper;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

public class PythonRarePackageProgramGenerator implements IProgramGenerator {

    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final long RANDOM_SEED = 232466665;
    private static final int MAX_DICT_ENTRIES = 5;
    private static final double RECURSION_PROBABILITY = 0.4;
    private static final int FORCE_RECURSION_THRESHOLD = 20;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance program(String standardOutputDict, String standardErrorDict,
                boolean errors);
    }

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean errors) {
        Random random = new Random(RANDOM_SEED);
        String standardOutputDict = asDict(standardOutput, random);
        String standardErrorDict = asDict(standardError, random);
        return Templates.program(standardOutputDict, standardErrorDict, errors).render();
    }

    private String asDict(String input, RandomGenerator random) {
        StringBuilder builder = new StringBuilder();
        asDict(input, 0, input.length(), builder, random);
        return builder.toString();
    }

    private void asDict(String input, int from, int to, StringBuilder builder, RandomGenerator random) {
        builder.append('{');
        int entries = random.nextInt(2, MAX_DICT_ENTRIES);
        List<Integer> splitIndices = new ArrayList<>(entries + 2);
        splitIndices.add(from);
        splitIndices.add(to);
        for (int i = 0; i < entries; i++) {
            splitIndices.add(random.nextInt(from, to));
        }
        splitIndices.sort(Comparator.naturalOrder());
        for (int i = 0; i < entries + 1; i++) {
            builder.append('\'');
            builder.append(randomString(random));
            builder.append("': ");

            int innerFrom = splitIndices.get(i);
            int innerTo = splitIndices.get(i + 1);
            if ((innerFrom < innerTo && random.nextDouble() < RECURSION_PROBABILITY)
                    || (innerTo - innerFrom > FORCE_RECURSION_THRESHOLD)) {
                asDict(input, innerFrom, innerTo, builder, random);
            } else {
                builder.append('\'');
                builder.append(StringEscapeHelper.escape(input.substring(innerFrom, innerTo)));
                builder.append('\'');
            }
            if (i < entries) {
                builder.append(", ");
            }
        }
        builder.append('}');
    }

    private String randomString(RandomGenerator random) {
        int length = random.nextInt(1, 10);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(randomChar(random));
        }
        return result.toString();
    }

    private char randomChar(RandomGenerator random) {
        return ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length()));
    }

}
