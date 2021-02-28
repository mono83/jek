package com.github.mono83.jek;

import com.github.mono83.jek.exceptions.GeneralJekException;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Describes RPC call, that can be made.
 */
public interface RPCCall {
    /**
     * Performs RPC call using given HTTP transport.
     *
     * @param transport HTTP transport to use.
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    Response call(Transport transport) throws GeneralJekException;

    /**
     * Performs RPC call using default HTTP transport.
     * Default invoker must be configured in {@link TransportFactory}
     *
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default Response call() {
        return call(TransportFactory.getDefault());
    }

    /**
     * Performs async RPC call using given HTTP transport.
     *
     * @param executor  Executor service to use.
     * @param transport HTTP transport to use.
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default CompletableFuture<Response> callAsync(
            @NonNull final Executor executor,
            @NonNull final Transport transport
    ) {
        return CompletableFuture.supplyAsync(() -> RPCCall.this.call(transport), executor);
    }

    /**
     * Performs async RPC call using default HTTP transport.
     *
     * @param executor Executor service to use.
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default CompletableFuture<Response> callAsync(
            @NonNull final Executor executor
    ) {
        return callAsync(executor, TransportFactory.getDefault());
    }
}
