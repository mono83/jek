package com.github.mono83.jek.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mono83.jek.HTTPInvoker;
import com.github.mono83.jek.RPCCallTyped;
import com.github.mono83.jek.Response;
import com.github.mono83.jek.dto.general.StatusDTO;
import lombok.Getter;
import lombok.NonNull;

public class GetStatus implements RPCCallTyped<StatusDTO> {
    static final TypeReference<StatusDTO> reference = new TypeReference<>() {
    };
    @Getter
    private final String route = "/api/public/status";

    @Override
    public TypeReference<StatusDTO> getTypeReference() {
        return reference;
    }

    public Response call(@NonNull final HTTPInvoker invoker) {
        return invoker.invoke(route, null);
    }
}
