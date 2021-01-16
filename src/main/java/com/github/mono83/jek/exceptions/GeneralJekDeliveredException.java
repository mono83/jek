package com.github.mono83.jek.exceptions;

import lombok.Getter;
import lombok.NonNull;

public class GeneralJekDeliveredException extends GeneralJekException {
    @Getter
    private final int errorCode;
    @Getter
    private final String rayId;

    public GeneralJekDeliveredException(
            final int code,
            @NonNull final String rayId,
            @NonNull final String message
    ) {
        super(String.format("Received error code %d. %s", code, message));
        this.errorCode = code;
        this.rayId = rayId;
    }
}
