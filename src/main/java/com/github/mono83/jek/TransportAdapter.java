package com.github.mono83.jek;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class TransportAdapter implements Transport {
    @NonNull
    protected final Transport real;
}
