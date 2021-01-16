package com.github.mono83.jek.exceptions.delivered;

import com.github.mono83.jek.exceptions.DeliveredBadRequestException;
import com.github.mono83.jek.exceptions.GeneralJekDeliveredException;

public class BadCredentialsException extends DeliveredBadRequestException {
    public BadCredentialsException(final GeneralJekDeliveredException cause) {
        super(cause, "Bad credentials");
    }
}
