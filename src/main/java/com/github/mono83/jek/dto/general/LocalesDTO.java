package com.github.mono83.jek.dto.general;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class LocalesDTO {
    @JsonProperty
    public final LocaleDTO defaultLocale;
    @JsonProperty
    public final List<LocaleDTO> locales;
}
