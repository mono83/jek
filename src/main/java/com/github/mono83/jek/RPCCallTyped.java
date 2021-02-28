package com.github.mono83.jek;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mono83.jek.exceptions.GeneralJekException;

/**
 * Extended version of {@link RPCCall}, that provides ability to fetch
 * data into DTO.
 *
 * @param <T> DTO class.
 */
public interface RPCCallTyped<T> extends RPCCall {
    /**
     * @return Response type in Jackson wrapper.
     */
    TypeReference<T> getTypeReference();

    /**
     * Performs RPC call using given HTTP invoker.
     *
     * @param invoker HTTP invoker to use.
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default T get(Transport invoker) throws GeneralJekException {
        return call(invoker).get(getTypeReference());
    }

    /**
     * Performs RPC call using default HTTP invoker.
     * Default invoker must be configured in {@link TransportFactory}
     *
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default T get() throws GeneralJekException {
        return call().get(getTypeReference());
    }
}
