package com.github.mono83.jek;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mono83.jek.exceptions.GeneralJekDeliveredException;
import com.github.mono83.jek.exceptions.GeneralJekException;
import com.github.mono83.jek.exceptions.delivered.BadCredentialsException;
import com.github.mono83.jek.exceptions.delivered.TFAIsRequired;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Contains configured object mapper.
 * For internal use only.
 */
class Mapping {
    private Mapping() {
    }

    static final ObjectMapper mapper = new ObjectMapper();

    private static final Map<Integer, Function<GeneralJekDeliveredException, ? extends GeneralJekException>> errors;

    /**
     * Converts given delivered exception into concrete class, if possible.
     *
     * @param deliveredException Exception, delivered from server.
     * @return Converted exception.
     */
    public static GeneralJekException convert(@NonNull final GeneralJekDeliveredException deliveredException) {
        if (errors.containsKey(deliveredException.getErrorCode())) {
            return errors.get(deliveredException.getErrorCode()).apply(deliveredException);
        }
        return deliveredException;
    }

    static {
        errors = new HashMap<>();
        errors.put(104, BadCredentialsException::new);
        errors.put(111, TFAIsRequired::new);
    }
}
