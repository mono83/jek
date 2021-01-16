package com.github.mono83.jek;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mono83.jek.exceptions.GeneralJekDeliveredException;
import com.github.mono83.jek.exceptions.ServerResponseUnmarshallingException;
import lombok.Data;
import lombok.NonNull;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

/**
 * Contains response, delivered from server.
 */
@Data
public final class Response {
    private final String route;
    private final Duration elapsed;
    private final int code;
    @NonNull
    private final String rayId;
    private final String errorMessage;
    @NonNull
    private final byte[] payload;

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    public Optional<Exception> getError() {
        if (code == 0) {
            return Optional.empty();
        }

        return Optional.of(new GeneralJekDeliveredException(code, rayId, errorMessage));
    }

    public <T> T get(@NonNull final Class<T> clazz) {
        try {
            return Mapping.mapper.readValue(payload, clazz);
        } catch (IOException e) {
            throw new ServerResponseUnmarshallingException(route, e);
        }
    }

    public <T> T get(@NonNull final TypeReference<T> reference) {
        try {
            return Mapping.mapper.readValue(payload, reference);
        } catch (IOException e) {
            throw new ServerResponseUnmarshallingException(route, e);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Response(code=%d, rayId=%s, %s bytes)",
                getCode(),
                getRayId(),
                getPayload() == null || getPayload().length == 0 ? 0 : getPayload().length
        );
    }
}
