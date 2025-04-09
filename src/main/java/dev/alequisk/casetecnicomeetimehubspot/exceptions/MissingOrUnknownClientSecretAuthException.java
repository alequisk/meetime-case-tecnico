package dev.alequisk.casetecnicomeetimehubspot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Missing or Unknown Client Secret Token")
public class MissingOrUnknownClientSecretAuthException extends Exception {
    public MissingOrUnknownClientSecretAuthException(Exception ex) {
        super("Missing or Unknown Client Secret Token", ex);
    }
}
