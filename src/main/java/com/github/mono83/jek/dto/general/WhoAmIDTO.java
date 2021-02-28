package com.github.mono83.jek.dto.general;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class WhoAmIDTO {
    @JsonProperty
    private final long gateId;
}
