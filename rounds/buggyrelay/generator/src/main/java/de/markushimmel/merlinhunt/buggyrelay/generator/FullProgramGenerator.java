package de.markushimmel.merlinhunt.buggyrelay.generator;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.markushimmel.merlinhunt.buggyrelay.generator.languages.CPlusPlusProgramGenerator;

@ApplicationScoped
public class FullProgramGenerator {

    private static final List<IProgramGenerator> ALL_GENERATORS = List.of( //
            new CPlusPlusProgramGenerator(), //

            new CPlusPlusProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //

            new CPlusPlusProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //
            new CPlusPlusProgramGenerator(), //
            new CPlusPlusProgramGenerator() //
    );

    private static final List<String> LEAVES = List.of("a", "b", "c", "d", "e", "f", "g", "h");

    public String generateFinalProgram(boolean syntaxErrors) {
        return get(0, syntaxErrors);
    }

    private String get(int index, boolean syntaxErrors) {
        if (index >= ALL_GENERATORS.size()) {
            return LEAVES.get(index - ALL_GENERATORS.size());
        }

        String leftChild = get(2 * index + 1, syntaxErrors);
        String rightChild = get(2 * index + 2, syntaxErrors);
        return ALL_GENERATORS.get(index).generateProgram(leftChild, rightChild, syntaxErrors);
    }

}
