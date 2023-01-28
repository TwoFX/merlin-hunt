package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.nio.charset.StandardCharsets;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

public class JavaProgramGenerator implements IProgramGenerator {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance program(String standardOutputBytes, String standardErrorBytes,
                boolean errors);
    }

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean withSyntaxError) {
        return Templates.program(formatBytes(standardOutput), formatBytes(standardError), withSyntaxError).render();
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
