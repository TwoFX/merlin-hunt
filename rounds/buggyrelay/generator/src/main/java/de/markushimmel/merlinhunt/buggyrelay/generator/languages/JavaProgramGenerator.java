package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.nio.charset.StandardCharsets;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;

public class JavaProgramGenerator implements IProgramGenerator {

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean withSyntaxError) {
        return String.format("""
                import java.nio.charset.Charset;
                import java.nio.charset.StandardCharsets;

                public class Program {

                    private static final byte[] firstBytes = new byte[] { %s };
                    private static final byte[] secondBytes = new byte[] { %s };

                    public void main(%s) {

                        Charset charset = StandardCharsets.UTF_%s;

                        System.out.println(new String(firstBytes, charset));
                        System.err.println(new String(secondBytes, charset));
                    }
                }""", formatBytes(standardOutput), formatBytes(standardError), withSyntaxError ? "" : "String[] args",
                withSyntaxError ? "16" : "8");

    }

    private String formatBytes(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        StringBuilder result = new StringBuilder();
        result.append(bytes[0]);

        for (int i = 1; i < bytes.length; i++) {
            result.append(", ");
            result.append(bytes[i]);
        }

        return result.toString();
    }

}
