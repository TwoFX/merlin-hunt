package de.markushimmel.merlinhunt.immortalgame;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;

@Path("/immortal")
public class ImmortalRestService {

    private static final String SECRET = "CBZV4QGNZZFXMG5AQU4EKHEJG244CO6JNJESMRF3JMHHQZCU5XSSD7PA6INB2DYF";

    @Path("/{code}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(description = "Prüft einen Authentifizierungscode und gibt bei Erfolg das Lösungswort zurück")
    public String verify(
            @Parameter(description = "Ein sechsstelliger numerischer Authentifizierungscode") @PathParam("code") String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        boolean successful = verifier.isValidCode(SECRET, code);

        if (successful) {
            return "Korrekt! Das Lösungswort lautet 'sdfkgjhweifdefjkeshfuierwfaw'";
        } else {
            return "Leider falsch!";
        }
    }

}
