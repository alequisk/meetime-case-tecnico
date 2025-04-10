package dev.alequisk.casetecnicomeetimehubspot.dtos;

import java.time.Instant;


public class DefaultErrorResponse {
    private final Instant timestamp;
    private final String message;
    private final int status;

    public DefaultErrorResponse(int status, String message) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }

}