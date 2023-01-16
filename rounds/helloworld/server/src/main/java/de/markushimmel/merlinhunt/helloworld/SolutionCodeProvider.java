package de.markushimmel.merlinhunt.helloworld;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class SolutionCodeProvider {

    private static final String SOLUTION_CODE_FALLBACK = "Oh no, the puzzle is broken!";

    @ConfigProperty(name = "helloworld.solution.code.file")
    Optional<String> solutionCodeFile;

    private String solutionCode;

    @PostConstruct
    void initializeSecret() {
        solutionCode = getSolutionFileLocation() //
                .flatMap(this::getSolutionCode) //
                .orElse(SOLUTION_CODE_FALLBACK);
        Log.infof("Solution code is '%s'.", solutionCode);
    }

    private Optional<String> getSolutionCode(String solutionFileLocation) {
        try {
            return Optional.of(Files.readAllLines(Path.of(solutionFileLocation)).get(0));
        } catch (IOException | IndexOutOfBoundsException e) {
            Log.errorf("Unable to read solution code file at %s. Falling back to default solution code.",
                    solutionFileLocation);
            return Optional.empty();
        }
    }

    private Optional<String> getSolutionFileLocation() {
        if (!solutionCodeFile.isPresent()) {
            Log.error("No solution code file specified! Falling back to default solution code.");
        }
        return solutionCodeFile;
    }

    public String getSolutionCode() {
        return solutionCode;
    }
}
