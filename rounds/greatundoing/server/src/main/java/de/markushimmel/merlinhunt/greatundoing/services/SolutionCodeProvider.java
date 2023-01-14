package de.markushimmel.merlinhunt.greatundoing.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class SolutionCodeProvider {

    private static final String SOLUTION_CODE_FALLBACK = "Oh no, the puzzle is broken!";
    private static final String SOLUTION_CODE_FILE_ENVIRONMENT_VARIABLE = "GREATUNDOING_SOLUTION_CODE_FILE";

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
            return Optional.of(Files.readString(Path.of(solutionFileLocation)));
        } catch (IOException e) {
            Log.errorf("Unable to read solution code file at %s. Falling back to default solution code.",
                    solutionFileLocation);
            return Optional.empty();
        }
    }

    private Optional<String> getSolutionFileLocation() {
        String location = System.getProperty(SOLUTION_CODE_FILE_ENVIRONMENT_VARIABLE);
        if (location != null) {
            return Optional.of(location);
        } else {
            Log.error("No solution code file specified! Falling back to default solution code.");
            return Optional.empty();
        }
    }

    public String getSolutionCode() {
        return solutionCode;
    }
}
