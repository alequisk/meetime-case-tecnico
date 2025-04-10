package dev.alequisk.casetecnicomeetimehubspot.exceptions;

public class RateLimitReachException extends RuntimeException {
    public RateLimitReachException(String message) {
        super(message);
    }
}
