package com.github.mono83.jek.exceptions.delivered;

import com.github.mono83.jek.exceptions.DeliveredBadRequestException;
import com.github.mono83.jek.exceptions.GeneralJekDeliveredException;

public class BadSessionException extends DeliveredBadRequestException {
    public BadSessionException(GeneralJekDeliveredException cause) {
        super(cause);
    }
}
