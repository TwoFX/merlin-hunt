package de.markushimmel.merlinhunt.buggyrelay.generator;

public interface IProgramGenerator {

    String generateProgram(String standardOutput, String standardError, boolean withSyntaxError);

}
