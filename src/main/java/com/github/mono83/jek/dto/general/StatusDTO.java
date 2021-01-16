package com.github.mono83.jek.dto.general;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Data
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public final class StatusDTO {
    @JsonProperty
    private final int uptimeSeconds;
    @JsonProperty
    private final String releaseName;

    public Duration getUptime() {
        return Duration.ofSeconds(uptimeSeconds);
    }
}
