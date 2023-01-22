package de.markushimmel.merlinhunt.buggyrelay.cli;

import javax.inject.Inject;

import de.markushimmel.merlinhunt.buggyrelay.generator.FullProgramGenerator;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate", mixinStandardHelpOptions = true)
public class GenerateCommand implements Runnable {

    @Option(names = "--no-syntax-errors", negatable = true)
    boolean syntaxErrors = true;

    @Inject
    FullProgramGenerator generator;

    @Override
    public void run() {
        System.out.println(generator.generateFinalProgram(syntaxErrors));
    }

}
