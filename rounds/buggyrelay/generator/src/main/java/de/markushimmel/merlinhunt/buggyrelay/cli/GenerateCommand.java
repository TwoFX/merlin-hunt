package de.markushimmel.merlinhunt.buggyrelay.cli;

import javax.inject.Inject;

import de.markushimmel.merlinhunt.buggyrelay.generator.FullProgramGenerator;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate")
public class GenerateCommand implements Runnable {

    @Option(names = "--syntax-errors", negatable = true)
    boolean syntaxErrors = true;

    @Inject
    private FullProgramGenerator generator;

    @Override
    public void run() {
        System.out.println(generator.generateFinalProgram(syntaxErrors));
    }

}
