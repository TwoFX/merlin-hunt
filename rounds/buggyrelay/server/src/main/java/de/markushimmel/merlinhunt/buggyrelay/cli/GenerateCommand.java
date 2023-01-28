package de.markushimmel.merlinhunt.buggyrelay.cli;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.markushimmel.merlinhunt.buggyrelay.generator.FullProgramGenerator;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate", mixinStandardHelpOptions = true)
public class GenerateCommand implements Runnable {

    @Option(names = "--no-errors", negatable = true)
    boolean errors = true;

    @ConfigProperty(name = "buggyrelay.allow.disabling.errors")
    boolean allowDisablingErrors;

    @Inject
    FullProgramGenerator generator;

    @Override
    public void run() {
        if (!errors && !allowDisablingErrors) {
            System.out.println("Nice try :)");
        } else {
            System.out.println(generator.generateFinalProgram(errors));
        }
    }

}
