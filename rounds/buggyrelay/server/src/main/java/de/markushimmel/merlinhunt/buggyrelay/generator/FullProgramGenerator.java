package de.markushimmel.merlinhunt.buggyrelay.generator;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.markushimmel.merlinhunt.buggyrelay.generator.languages.BrainfuckProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.CPlusPlusProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.FullyEscapedJavaProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.JavaProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.PythonBase64ProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.SQLiteProgramGenerator;

@ApplicationScoped
public class FullProgramGenerator {

    @Inject
    SQLiteProgramGenerator sqliteGenerator;

    private List<IProgramGenerator> allGenerators;

    @PostConstruct
    void setUp() {
        allGenerators = List.of( //
                sqliteGenerator, //

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
    }

    private static final List<String> LEAVES = List.of("I", "n", "c", "o", "m", "p", "r", "e", "h", "e", "n", "s", "i",
            "b", "l", "e");

    public String generateFinalProgram(boolean errors) {
        return get(0, errors);
    }

    private String get(int index, boolean errors) {
        if (index >= allGenerators.size()) {
            return LEAVES.get(index - allGenerators.size());
        }

        String standardOutput = get(2 * index + 2, errors);
        String standardError = get(2 * index + 1, errors);
        return allGenerators.get(index).generateProgram(standardOutput, standardError, errors);
    }

}
