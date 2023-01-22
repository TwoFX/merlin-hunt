package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

public class CPlusPlusProgramGenerator implements IProgramGenerator {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance program(String standardOutput, String standardError,
                boolean withSyntaxError);
    }

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean withSyntaxError) {
        return Templates.program(standardOutput, standardError, withSyntaxError).render();
    }

}
