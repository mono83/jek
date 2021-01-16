package com.github.mono83.jek;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Contains configured object mapper.
 * For internal use only.
 */
class Mapping {
    private Mapping() {
    }

    static final ObjectMapper mapper = new ObjectMapper();
}
