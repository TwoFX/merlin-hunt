package de.markushimmel.merlinhunt.immortalgame.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/undoing")
public class UndoingRestService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(responseCode = "200", description = "Congratulations, you solved the puzzle!")
    @APIResponse(responseCode = "400", description = "Your input was malformed or incorrect")
    public String makeAttempt(UndoingRestRequest request) {
        if (request.getClass() == null || request.getNumbers().size() != 5) {
            throw new MerlinHuntException(Status.BAD_REQUEST, "Request must contain exactly five numbers!");
        }

        return request.toString();
    }
}
