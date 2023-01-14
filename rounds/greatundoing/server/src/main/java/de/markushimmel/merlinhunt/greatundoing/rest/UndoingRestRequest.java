package de.markushimmel.merlinhunt.greatundoing.rest;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class UndoingRestRequest {

    @Schema(required = true, description = "A list containing exactly 101 numbers")
    private List<Long> numbers;

    public UndoingRestRequest() {
    }

    public UndoingRestRequest(List<Long> numbers) {
        this.numbers = numbers;
    }

    public List<Long> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Long> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "UndoingRestRequest [numbers=" + numbers + "]";
    }

}
