package de.markushimmel.merlinhunt.immortalgame;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public String verify(@PathParam("code") String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        boolean successful = verifier.isValidCode(SECRET, code);

        if (successful) {
            return "Korrekt! Das Passwort für die nächste Runde lautet 'sdfkgjhweifdefjkeshfuierwfaw'";
        } else {
            return "Leider falsch!";
        }
    }

}
