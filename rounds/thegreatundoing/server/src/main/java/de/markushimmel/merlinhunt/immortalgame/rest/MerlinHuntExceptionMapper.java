package de.markushimmel.merlinhunt.immortalgame.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MerlinHuntExceptionMapper implements ExceptionMapper<MerlinHuntException> {

    @Override
    public Response toResponse(MerlinHuntException exception) {
        return Response.status(exception.getStatus()).entity(exception.getMessage()).build();
    }

}
