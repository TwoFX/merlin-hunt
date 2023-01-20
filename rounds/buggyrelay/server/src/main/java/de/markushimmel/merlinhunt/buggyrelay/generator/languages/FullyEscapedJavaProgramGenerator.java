package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.nio.charset.StandardCharsets;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;

public class FullyEscapedJavaProgramGenerator implements IProgramGenerator {

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean withSyntaxError) {
        byte[] toEncode = new JavaProgramGenerator().generateProgram(standardOutput, standardError, false)
                .getBytes(StandardCharsets.UTF_16BE);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < toEncode.length / 2; i++) {
            result.append((withSyntaxError && i == 251) ? "/u" : "\\u");
            handleByte(toEncode[2 * i], result);
            handleByte(toEncode[2 * i + 1], result);
        }
        return result.toString();
    }

    private void handleByte(byte b, StringBuilder result) {
        result.append(HEX_ARRAY[b >> 4]);
        result.append(HEX_ARRAY[b & 0x0f]);
    }

}
