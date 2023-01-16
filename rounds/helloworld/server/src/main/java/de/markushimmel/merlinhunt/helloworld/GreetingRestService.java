package de.markushimmel.merlinhunt.helloworld;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class GreetingRestService {

    @Inject
    SolutionCodeProvider solutionCodeProvider;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String greet(GreetingRestRequest request) {
        if (request.getName() == null) {
            return "Please name a person to greet!";
        }

        String result = String.format("Hello, %s!\n", request.getName());

        if (request.getName().toLowerCase().equals("world")) {
            result += String.format("\nThe solution code is: %s\n", solutionCodeProvider.getSolutionCode());
        }

        return result;
    }

}
