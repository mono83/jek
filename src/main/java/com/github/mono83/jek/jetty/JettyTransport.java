package com.github.mono83.jek.jetty;

import com.github.mono83.jek.Options;
import com.github.mono83.jek.Response;
import com.github.mono83.jek.Transport;
import com.github.mono83.jek.exceptions.GeneralJekException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesRequestContent;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Jetty implementation of HTTP RPC invoker.
 */
@Slf4j
public class JettyTransport implements Transport {
    private final HttpClient client;
    private final String address;

    /**
     * Constructs new HTTP RPC invoker.
     *
     * @param client  Jetty HTTP client, optional.
     * @param address Remote server address.
     */
    public JettyTransport(final HttpClient client, @NonNull final String address) {
        if (address.isEmpty()) {
            throw new IllegalStateException("Empty address");
        }

        this.client = client;
        this.address = address.endsWith("/")
                ? address.substring(0, address.length() - 1)
                : address;
    }

    public static JettyTransport create(final String address) {
        return new JettyTransport(null, address);
    }

    @Override
    public Response invoke(final String route, final byte[] payload) throws GeneralJekException {
        return invoke(route, null, payload);
    }

    @Override
    public Response invoke(@NonNull final String candidate, final String token, final byte[] payload) {
        if (candidate.isEmpty()) {
            throw new GeneralJekException(new IllegalArgumentException("Empty route"));
        }
        String route = candidate.startsWith("/")
                ? candidate.trim()
                : "/" + candidate.trim();

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
                if (log.isTraceEnabled()) {
                    log.trace(new String(payload, StandardCharsets.UTF_8));
                }
                request.body(new BytesRequestContent(payload));
            }
            ContentResponse res = request.send();
            Duration elapsed = Duration.ofNanos(System.nanoTime() - nano);
            int httpCode = res.getStatus();
            int appCode = Optional.ofNullable(res.getHeaders().get(Options.HEADER_CODE))
                    .map(Integer::parseInt)
                    .orElse(-1);
            String rayId = Optional.ofNullable(res.getHeaders()
                    .get(Options.HEADER_RAY_ID))
                    .orElse("none");
            String errorMessage = Optional.ofNullable(res.getHeaders().get(Options.HEADER_MESSAGE))
                    .filter($ -> !$.isEmpty())
                    .orElse(null);
            if (log.isDebugEnabled()) {
                log.debug("Request to {} done with codes {} {} in {}", route, httpCode, appCode, elapsed);
            }
            if (errorMessage != null && log.isErrorEnabled()) {
                log.info("Received error {}", errorMessage);
            }
            if (log.isTraceEnabled()) {
                log.trace(new String(res.getContent(), StandardCharsets.UTF_8));
            }

            return new Response(
                    route,
                    elapsed,
                    appCode,
                    rayId,
                    errorMessage,
                    res.getContent()
            );
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            if (log.isErrorEnabled()) {
                log.error("Error sending request", e);
            }
            throw new GeneralJekException(e);
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.error("Unexpected error", e);
            }
            throw e;
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
