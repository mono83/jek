package com.github.mono83.jek.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mono83.jek.Transport;
import com.github.mono83.jek.Options;
import com.github.mono83.jek.RPCCallTyped;
import com.github.mono83.jek.Response;
import com.github.mono83.jek.dto.general.StatusDTO;
import lombok.NonNull;

public class GetStatus implements RPCCallTyped<StatusDTO> {
    static final TypeReference<StatusDTO> reference = new TypeReference<>() {
    };

    @Override
    public TypeReference<StatusDTO> getTypeReference() {
        return reference;
    }

    public Response call(@NonNull final Transport invoker) {
        return invoker.invoke(Options.ROUTE_STATUS, null);
    }
}
