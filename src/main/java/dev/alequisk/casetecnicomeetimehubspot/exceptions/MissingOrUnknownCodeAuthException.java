package dev.alequisk.casetecnicomeetimehubspot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Missing or Unknown Code Auth Token")
public class MissingOrUnknownCodeAuthException extends Exception {
    public MissingOrUnknownCodeAuthException(Exception ex) {
        super("Missing or Unknown Code Auth Token", ex);
    }
}
