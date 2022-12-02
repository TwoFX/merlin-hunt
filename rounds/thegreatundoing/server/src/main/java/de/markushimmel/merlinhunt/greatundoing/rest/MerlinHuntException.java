package de.markushimmel.merlinhunt.greatundoing.rest;

import javax.ws.rs.core.Response.Status;

public class MerlinHuntException extends RuntimeException {

    private final Status status;

    public MerlinHuntException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

}
