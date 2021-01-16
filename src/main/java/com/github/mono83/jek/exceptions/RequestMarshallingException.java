package com.github.mono83.jek.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

public class RequestMarshallingException extends GeneralJekException {
    public RequestMarshallingException(@NonNull final JsonProcessingException cause) {
        super("Error writing object to JSON", cause);
    }
}
