package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.util.Base64;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

public class PythonBase64ProgramGenerator implements IProgramGenerator {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance program(String standardOutput, String standardError,
                boolean withSyntaxError);
    }

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean withSyntaxError) {
        return Templates.program(base64(standardOutput), base64(standardError), withSyntaxError).render();
    }

    private String base64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

}
