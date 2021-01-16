package com.github.mono83.jek;

import com.github.mono83.jek.exceptions.GeneralJekException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * HTTP invoker implementation, configured with some token.
 * This token will be always invoked in all outgoing invocations.
 */
@RequiredArgsConstructor
class HTTPInvokerWithToken implements HTTPInvoker {
    @NonNull
    final HTTPInvoker real;
    @NonNull
    private final String token;

    @Override
    public Response invoke(final String route, final byte[] payload) throws GeneralJekException {
        return this.invoke(route, token, payload);
    }

    @Override
    public Response invoke(final String route, final String token, final byte[] payload) throws GeneralJekException {
        return real.invoke(route, token, payload);
    }
}
