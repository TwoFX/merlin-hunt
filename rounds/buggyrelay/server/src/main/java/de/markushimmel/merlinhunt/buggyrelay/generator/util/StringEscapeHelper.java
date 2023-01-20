package de.markushimmel.merlinhunt.buggyrelay.generator.util;

public class StringEscapeHelper {

    private StringEscapeHelper() {
        // hidden
    }

    public static String escape(String input) {
        return input.replace("\\", "\\\\") //
                .replace("\n", "\\n") //
                .replace("\"", "\\\"");
    }
}
