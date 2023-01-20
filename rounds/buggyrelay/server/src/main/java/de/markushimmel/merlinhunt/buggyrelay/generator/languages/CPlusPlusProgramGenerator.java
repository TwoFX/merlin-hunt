package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import de.markushimmel.merlinhunt.buggyrelay.generator.util.StringEscapeHelper;

public class CPlusPlusProgramGenerator implements IProgramGenerator {

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean withSyntaxError) {
        return String.format("""
                #include <iostream>

                int main() {
                    std::cout << \"%s\" %s std::endl;
                    std::cerr << \"%s\" << std::endl;
                }
                """, StringEscapeHelper.escape(standardOutput), withSyntaxError ? ">>" : "<<",
                StringEscapeHelper.escape(standardError));
    }

}
