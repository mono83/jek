package com.github.mono83.jek;

import com.github.mono83.jek.exceptions.GeneralJekException;

/**
 * Describes RPC call, that can be made.
 */
public interface RPCCall {
    /**
     * Performs RPC call using given HTTP invoker.
     *
     * @param invoker HTTP invoker to use.
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    Response call(Transport invoker) throws GeneralJekException;

    /**
     * Performs RPC call using default HTTP invoker.
     * Default invoker must be configured in {@link TransportFactory}
     *
     * @return Server response.
     * @throws GeneralJekException On any error.
     */
    default Response call() {
        return call(TransportFactory.getDefault());
    }
}
