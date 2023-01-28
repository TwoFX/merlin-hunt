package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

public class LLVMIRProgramGenerator implements IProgramGenerator {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance program(int[] standardOutputBytes, int[] standardErrorBytes,
                int totalLength, boolean errors);
    }

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean errors) {
        int[] standardOutputBytes = getCodePoints(standardOutput, -1);
        int[] standardErrorBytes = getCodePoints(standardError, -7);
        int totalLength = Math.max(standardOutputBytes.length, standardErrorBytes.length);
        return Templates.program(standardOutputBytes, standardErrorBytes, totalLength, errors).render();
    }

    private int[] getCodePoints(String input, int offset) {
        return input.codePoints().map(codePoint -> {
            if (codePoint >= 127) {
                throw new IllegalArgumentException("Illegal code point!");
            }
            return codePoint + offset;
        }).toArray();
    }

}
