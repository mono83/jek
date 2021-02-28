package com.github.mono83.jek;

import com.github.mono83.jek.exceptions.GeneralJekException;
import com.github.mono83.jek.exceptions.delivered.BadCredentialsException;
import com.github.mono83.jek.exceptions.delivered.BadSessionException;
import com.github.mono83.jek.rpc.AuthWallet;
import lombok.NonNull;

/**
 * HTTP invoker implementation that will attempt to create and maintain
 * authenticated session.
 */
class TransportWithSessionAndLoginPassword extends TransportAdapter {
    private final Object lock = new Object();

    private final String login;
    private final String password;

    private volatile String token = null;
    private volatile boolean failed = false;

    TransportWithSessionAndLoginPassword(
            final Transport real,
            @NonNull final String login,
            @NonNull final String password
    ) {
        super(real);

        if (login.isEmpty()) {
            throw new IllegalArgumentException("Empty login");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Empty password");
        }

        this.login = login;
        this.password = password;
    }

    @Override
    public Response invoke(final String route, final byte[] payload) throws GeneralJekException {
        return this.invoke(route, token, payload);
    }

    @Override
    public Response invoke(final String route, final String ignore, final byte[] payload) throws GeneralJekException {
        synchronized (lock) {
            if (failed) {
                throw new GeneralJekException("Unable to authenticate");
            }
            if (this.token == null) {
                try {
                    this.token = new AuthWallet(login, password).get(real);
                } catch (BadCredentialsException e) {
                    this.failed = true;
                    throw e;
                }
                return this.invoke(route, null, payload);
            }
            try {
                return real.invoke(route, this.token, payload);
            } catch (BadSessionException e) {
                return this.invoke(route, null, payload);
            }
        }
    }
}
