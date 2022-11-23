package de.markushimmel.merlinhunt.immortalgame.rest;

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
