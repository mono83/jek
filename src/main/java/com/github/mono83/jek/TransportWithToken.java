package com.github.mono83.jek;

import com.github.mono83.jek.exceptions.GeneralJekException;
import lombok.NonNull;

/**
 * HTTP invoker implementation, configured with some token.
 * This token will be always invoked in all outgoing invocations.
 */
class TransportWithToken extends TransportAdapter {
    @NonNull
    private final String token;

    TransportWithToken(final Transport real, @NonNull final String token) {
        super(real);
        if (token.isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }
        this.token = token;
    }

    @Override
    public Response invoke(final String route, final byte[] payload) throws GeneralJekException {
        return this.invoke(route, token, payload);
    }

    @Override
    public Response invoke(final String route, final String token, final byte[] payload) throws GeneralJekException {
        return real.invoke(route, token, payload);
    }
}
