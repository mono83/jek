package com.github.mono83.jek;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.mono83.jek.exceptions.GeneralJekException;
import com.github.mono83.jek.exceptions.RequestMarshallingException;

/**
 * Defines components, responsible for simple HTTP communication.
 */
public interface HTTPInvoker {
    /**
     * Constructs new HTTP invoker that will contain given token and apply it
     * to all outgoing RPC calls.
     *
     * @param real  HTTP invoker to wrap with feature.
     * @param token Token to bind.
     * @return HTTP invoker with bound token.
     */
    static HTTPInvoker authenticated(final HTTPInvoker real, final String token) {
        return new HTTPInvokerWithToken(
            real instanceof HTTPInvokerWithToken ? ((HTTPInvokerWithToken) real).real : real,
            token
        );
    }

    /**
     * Constructs new HTTP invoker that will contain given token and apply it
     * to all outgoing RPC calls.
     *
     * @param token Token to bind.
     * @return HTTP invoker with bound token.
     */
    static HTTPInvoker authenticated(final String token) {
        return new HTTPInvokerWithToken(HTTPInvokerFactory.getDefault(), token);
    }

    /**
     * Performs RPC call invocation using HTTP(s) transport.
     *
     * @param route   Route to invoke.
     * @param payload Payload to send, optional.
     * @return Response, delivered from server.
     * @throws GeneralJekException on any error.
     */
    Response invoke(String route, byte[] payload) throws GeneralJekException;

    /**
     * Performs RPC call invocation using HTTP(s) transport with given session token.
     *
     * @param route   Route to invoke.
     * @param token   Session token.
     * @param payload Payload to send, optional.
     * @return Response, delivered from server.
     * @throws GeneralJekException on any error.
     */
    Response invoke(String route, final String token, byte[] payload) throws GeneralJekException;

    /**
     * Performs RPC call invocation using HTTP(s) transport.
     *
     * @param route   Route to invoke.
     * @param payload Payload to send, optional.
     * @return Response, delivered from server.
     * @throws GeneralJekException on any error.
     */
    default Response invoke(String route, Object payload) throws GeneralJekException {
        try {
            return invoke(route, Mapping.mapper.writeValueAsBytes(payload));
        } catch (JsonProcessingException e) {
            throw new RequestMarshallingException(e);
        }
    }

    /**
     * Performs RPC call invocation using HTTP(s) transport with given session token.
     *
     * @param route   Route to invoke.
     * @param token   Session token.
     * @param payload Payload to send, optional.
     * @return Response, delivered from server.
     * @throws GeneralJekException on any error.
     */
    default Response invoke(String route, final String token, Object payload) throws GeneralJekException {
        try {
            return invoke(route, token, Mapping.mapper.writeValueAsBytes(payload));
        } catch (JsonProcessingException e) {
            throw new RequestMarshallingException(e);
        }
    }
}
