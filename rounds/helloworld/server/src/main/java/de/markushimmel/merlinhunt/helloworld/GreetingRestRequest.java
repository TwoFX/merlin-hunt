package de.markushimmel.merlinhunt.helloworld;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class GreetingRestRequest {

    @Schema(required = true, description = "The name of the person to greet")
    private String name;

    public GreetingRestRequest() {
    }

    public GreetingRestRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
