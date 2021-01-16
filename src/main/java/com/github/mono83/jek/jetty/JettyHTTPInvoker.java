package com.github.mono83.jek.jetty;

import com.github.mono83.jek.HTTPInvoker;
import com.github.mono83.jek.Options;
import com.github.mono83.jek.Response;
import com.github.mono83.jek.exceptions.GeneralJekException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesRequestContent;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Jetty implementation of HTTP RPC invoker.
 */
@RequiredArgsConstructor
@Slf4j
public class JettyHTTPInvoker implements HTTPInvoker {
    private final HttpClient client;
    @NonNull
    private final String address;

    public static JettyHTTPInvoker create(final String address) {
        return new JettyHTTPInvoker(null, address);
    }

    @Override
    public Response invoke(final String route, final byte[] payload) throws GeneralJekException {
        return invoke(route, null, payload);
    }

    @Override
    public Response invoke(@NonNull final String route, final String token, final byte[] payload) {
        HttpClient client;
        if (this.client == null) {
            client = new HttpClient();
            client.setUserAgentField(new HttpField(HttpHeader.USER_AGENT, Options.USER_AGENT));
            try {
                client.start();
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("Error starting Jetty HTTP client", e);
                }
                throw new GeneralJekException("Unable to start Jetty HTTP client", e);
            }
        } else {
            client = this.client;
        }

        try {
            if (log.isDebugEnabled()) {
                log.debug("Sending request to {}", route);
            }
            long nano = System.nanoTime();
            Request request = client.POST(address + route);
            if (token != null && !token.isEmpty()) {
                request.headers($ -> $.add(Options.HEADER_TOKEN, token));
            }
            if (payload != null && payload.length > 0) {
                request.body(new BytesRequestContent(payload));
            }
            ContentResponse res = request.send();
            Duration elapsed = Duration.ofNanos(System.nanoTime() - nano);
            if (log.isDebugEnabled()) {
                log.debug("Request to {} done in {}", route, elapsed);
            }
            if (log.isTraceEnabled()) {
                log.trace(new String(res.getContent(), StandardCharsets.UTF_8));
            }

            return new Response(
                    route,
                    elapsed,
                    Integer.parseInt(res.getHeaders().get(Options.HEADER_CODE)),
                    res.getHeaders().get(Options.HEADER_RAY_ID),
                    res.getHeaders().get(Options.HEADER_MESSAGE),
                    res.getContent()
            );
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            if (log.isErrorEnabled()) {
                log.error("Error sending request", e);
            }
            throw new GeneralJekException(e);
        } finally {
            try {
                if (this.client == null) {
                    client.stop();
                }
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("Error stopping Jetty HTTP client", e);
                }
                // Suppressing
            }
        }
    }
}
