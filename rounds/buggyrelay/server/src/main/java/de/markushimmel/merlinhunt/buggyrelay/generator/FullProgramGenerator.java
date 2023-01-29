package de.markushimmel.merlinhunt.buggyrelay.generator;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.markushimmel.merlinhunt.buggyrelay.generator.languages.BrainfuckProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.CPlusPlusProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.FullyEscapedJavaProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.HaskellRSAProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.JavaProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.LLVMIRProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.PythonBase64ProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.PythonRarePackageProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.languages.RubyHuffmanProgramGenerator;
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

                new RubyHuffmanProgramGenerator(), //
                new LLVMIRProgramGenerator(), //

                new CPlusPlusProgramGenerator(), //
                new BrainfuckProgramGenerator(), //
                new HaskellRSAProgramGenerator(), //
                new PythonRarePackageProgramGenerator(), //

                new PythonBase64ProgramGenerator(), //
                new JavaProgramGenerator(), //
                new FullyEscapedJavaProgramGenerator() //
        );
    }

    private static final List<String> LEAVES = List.of(
            "Solution code is concatenation of all words in the correct order with single space in between",
            "Indispensability", "Noncomprehensive",
            "Procrastinations",
            "Thermoelectrical", "Incomprehensible", "Inconclusiveness", "Internationalize", "Irresponsibility",
            "Journalistically", "Mispronunciation");

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
