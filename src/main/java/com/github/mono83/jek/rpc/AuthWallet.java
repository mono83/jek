package com.github.mono83.jek.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mono83.jek.HTTPInvoker;
import com.github.mono83.jek.Options;
import com.github.mono83.jek.RPCCallTyped;
import com.github.mono83.jek.Response;
import com.github.mono83.jek.exceptions.GeneralJekException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthWallet implements RPCCallTyped<String> {
    static final TypeReference<String> reference = new TypeReference<>() {
    };
    @NonNull
    private final String login, password;

    @Override
    public TypeReference<String> getTypeReference() {
        return reference;
    }

    @Override
    public Response call(@NonNull final HTTPInvoker invoker) throws GeneralJekException {
        return invoker.invoke(Options.ROUTE_AUTH_WALLET, new Credentials(login, password));
    }

    @RequiredArgsConstructor
    private static class Credentials {
        @JsonProperty
        private final String login;
        @JsonProperty
        private final String password;
        @JsonProperty
        private final String application = "web";
        @JsonProperty
        private final long elapsedMilli = 1;
    }
}
