package com.github.mono83.jek.exceptions;

public class DeliveredBadRequestException extends GeneralJekDeliveredException {
    public DeliveredBadRequestException(final GeneralJekDeliveredException cause) {
        super(cause, null);
    }

    public DeliveredBadRequestException(final GeneralJekDeliveredException cause, final String message) {
        super(cause, message);
    }
}
