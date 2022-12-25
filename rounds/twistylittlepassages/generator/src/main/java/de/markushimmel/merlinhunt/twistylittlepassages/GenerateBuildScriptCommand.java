package de.markushimmel.merlinhunt.twistylittlepassages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import io.quarkus.logging.Log;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "generate", mixinStandardHelpOptions = true)
public class GenerateBuildScriptCommand implements Runnable {

    @Parameters(paramLabel = "<animalsFile>", defaultValue = "animals.txt", description = "A list of animal names")
    String animalsFile;

    @Parameters(paramLabel = "<adjectivesFile>", defaultValue = "adjectives.txt", description = "A list of adjectives")
    String adjectivesFile;

    @Override
    public void run() {
        try {
            List<String> animals = Files.readAllLines(Path.of(animalsFile));
            List<String> adjectives = Files.readAllLines(Path.of(adjectivesFile));
        } catch (IOException e) {
            Log.error("Could not read animals and adjectives.", e);
            return;
        }

        /*
         * The plan:
         * Generate a list of tables, one of which is clearly marked.
         * 
         * -> All of them have animal names, only one of them has the name
         * "this one is not like the others".
         * 
         * Generate a random tree on them.
         * 
         * Generate a bunch of rooted subtrees and create entries that match those
         * subtrees
         * 
         * Tables have indices for the column that is incoming
         */

        /*
         * You're vs. you are: 2
         * all vs. each of them: 3
         * different vs unequal: 2
         * ; vs , vs - vs --: 4
         * in vs inside: 2
         * maze prefix: 5
         * passages prefix: 5
         * number of periods + !: 5
         * => 9600 combinations
         * 
         * You’re in a maze of twisty little passages, all different.
         * 
         * You’re in a maze of little twisty passages, all different.
         * 
         * You’re in a twisty maze of little passages, all different.
         * 
         * You’re in a little twisty maze of passages, all different.
         */
    }

}