package de.markushimmel.merlinhunt.buggyrelay.generator;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.markushimmel.merlinhunt.buggyrelay.generator.languages.BrainfuckProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.CPlusPlusProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.FullyEscapedJavaProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.HaskellRSAProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.JavaProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.PythonBase64ProgramGenerator;

@ApplicationScoped
public class FullProgramGenerator {

    private static final List<IProgramGenerator> ALL_GENERATORS = List.of( //
            new HaskellRSAProgramGenerator(), //

            new BrainfuckProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //

            new PythonBase64ProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //
            new JavaProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //

            new FullyEscapedJavaProgramGenerator(), //
            new JavaProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //
            new JavaProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //
            new CPlusPlusProgramGenerator() //
    );

    private static final List<String> LEAVES = List.of("I", "n", "c", "o", "m", "p", "r", "e", "h", "e", "n", "s", "i",
            "b", "l", "e");

    public String generateFinalProgram(boolean syntaxErrors) {
        return get(0, syntaxErrors);
    }

    private String get(int index, boolean syntaxErrors) {
        if (index >= ALL_GENERATORS.size()) {
            return LEAVES.get(index - ALL_GENERATORS.size());
        }

        String standardOutput = get(2 * index + 2, syntaxErrors);
        String standardError = get(2 * index + 1, syntaxErrors);
        return ALL_GENERATORS.get(index).generateProgram(standardOutput, standardError, syntaxErrors);
    }

}
