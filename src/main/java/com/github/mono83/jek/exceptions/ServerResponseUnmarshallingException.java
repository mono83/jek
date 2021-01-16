package com.github.mono83.jek.exceptions;

import lombok.NonNull;

public class ServerResponseUnmarshallingException extends GeneralJekException {
    public ServerResponseUnmarshallingException(@NonNull final String route, final Throwable cause) {
        super(
                String.format("Error reading response from %s. %s", route, cause.getMessage()),
                cause
        );
    }
}
