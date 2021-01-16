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
        @NonNull final String deliveredMessage
    ) {
        super(String.format("Received error code %d. %s", code, deliveredMessage));
        this.errorCode = code;
        this.rayId = rayId;
    }

    GeneralJekDeliveredException(final GeneralJekDeliveredException cause, final String message) {
        super(
            message == null || message.isEmpty() ? cause.getMessage() : message,
            cause
        );
        this.errorCode = cause.getErrorCode();
        this.rayId = cause.getRayId();
    }
}
