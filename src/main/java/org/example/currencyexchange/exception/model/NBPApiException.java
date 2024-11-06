package org.example.currencyexchange.exception.model;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NBPApiException extends ResponseStatusException {
    public NBPApiException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
