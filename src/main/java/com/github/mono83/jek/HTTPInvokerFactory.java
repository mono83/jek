package com.github.mono83.jek;

import com.github.mono83.jek.exceptions.GeneralJekException;
import lombok.NonNull;

/**
 * Static holder for default HTTP invoker.
 */
public class HTTPInvokerFactory {
    private static volatile HTTPInvoker defaultValue;

    /**
     * Sets new default HTTP invoker.
     *
     * @param invoker HTTP invoker to use.
     */
    public static void setDefault(@NonNull final HTTPInvoker invoker) {
        defaultValue = invoker;
    }

    /**
     * Returns currently configured default HTTP invoker.
     *
     * @return Default HTTP invoker.
     * @throws GeneralJekException If no default HTTP invoker configured.
     */
    public static HTTPInvoker getDefault() {
        if (defaultValue == null) {
            throw new GeneralJekException(
                    new IllegalStateException(
                            "No default HTTP invoker configured. Call HttpInvokerFactory.setDefault"
                    )
            );
        }
        return defaultValue;
    }
}
