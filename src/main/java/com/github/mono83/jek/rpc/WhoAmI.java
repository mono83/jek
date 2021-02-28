package com.github.mono83.jek.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mono83.jek.Options;
import com.github.mono83.jek.RPCCallTyped;
import com.github.mono83.jek.Response;
import com.github.mono83.jek.Transport;
import com.github.mono83.jek.dto.general.WhoAmIDTO;
import com.github.mono83.jek.exceptions.GeneralJekException;

public class WhoAmI implements RPCCallTyped<WhoAmIDTO> {
    static final TypeReference<WhoAmIDTO> reference = new TypeReference<>() {
    };

    @Override
    public TypeReference<WhoAmIDTO> getTypeReference() {
        return reference;
    }

    @Override
    public Response call(final Transport transport) throws GeneralJekException {
        return transport.invoke(Options.WHO_AM_I, null);
    }
}
