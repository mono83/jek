package com.github.mono83.jek.exceptions.delivered;

import com.github.mono83.jek.exceptions.DeliveredBadRequestException;
import com.github.mono83.jek.exceptions.GeneralJekDeliveredException;

public class TFAIsRequired extends DeliveredBadRequestException {
    public TFAIsRequired(GeneralJekDeliveredException cause) {
        super(cause);
    }
}
