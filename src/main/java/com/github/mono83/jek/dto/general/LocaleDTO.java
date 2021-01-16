package com.github.mono83.jek.dto.general;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Data
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class LocaleDTO {
    @JsonProperty
    private final long id;
    @JsonProperty
    private final String language;
    @JsonProperty
    private final String countryRegion;

    public Optional<String> getCountryRegion() {
        return Optional.ofNullable(countryRegion);
    }
}
