package com.github.mono83.jek;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mono83.jek.exceptions.GeneralJekException;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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
     * Performs RPC call using given HTTP transport.
     *
     * @param transport HTTP transport to use.
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default T get(final Transport transport) throws GeneralJekException {
        return call(transport).get(getTypeReference());
    }

    /**
     * Performs RPC call using default HTTP transport.
     * Default invoker must be configured in {@link TransportFactory}
     *
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default T get() throws GeneralJekException {
        return call().get(getTypeReference());
    }

    /**
     * Performs async RPC call using given HTTP transport.
     *
     * @param executor  Executor service to use.
     * @param transport HTTP transport to use.
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default CompletableFuture<T> getAsync(
            @NonNull final Executor executor,
            final Transport transport
    ) {
        return CompletableFuture.supplyAsync(() -> get(transport), executor);
    }

    /**
     * Performs RPC call using default HTTP transport.
     * Default invoker must be configured in {@link TransportFactory}
     *
     * @param executor Executor service to use.
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default CompletableFuture<T> getAsync(
            final Executor executor
    ) {
        return getAsync(executor, TransportFactory.getDefault());
    }
}
