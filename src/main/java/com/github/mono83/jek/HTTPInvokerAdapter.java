package com.github.mono83.jek;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract class HTTPInvokerAdapter implements HTTPInvoker {
    @NonNull
    protected final HTTPInvoker real;
}
