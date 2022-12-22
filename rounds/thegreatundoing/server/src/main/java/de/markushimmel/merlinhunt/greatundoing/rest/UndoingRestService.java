package de.markushimmel.merlinhunt.greatundoing.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import de.markushimmel.merlinhunt.greatundoing.services.UndoingRestServiceBean;
import de.markushimmel.merlinhunt.greatundoing.util.UndoingConstants;

@Path("/")
public class UndoingRestService {

    @Inject
    private UndoingRestServiceBean service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(responseCode = "200", description = "Congratulations, you solved the puzzle!")
    @APIResponse(responseCode = "400", description = "Your input was malformed or incorrect")
    public String makeAttempt(UndoingRestRequest request) {
        if (request.getNumbers() == null || request.getNumbers().size() != UndoingConstants.SIZE) {
            throw new MerlinHuntException(Status.BAD_REQUEST, String.format("Expected %d numbers, got %d.",
                    UndoingConstants.SIZE, request.getNumbers() == null ? 0 : request.getNumbers().size()));
        }

        if (service.checkNumbers(request.getNumbers())) {

            return String.format("Correct! The solution code is %s",
                    System.getenv().get("MERLIN_HUNT_UNDOING_SECRET"));
        } else {
            throw new MerlinHuntException(Status.BAD_REQUEST, "Those were the wrong numbers");
        }
    }
}
