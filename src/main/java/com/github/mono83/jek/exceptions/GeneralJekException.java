package com.github.mono83.jek.exceptions;

public class GeneralJekException extends RuntimeException {
    public GeneralJekException() {
        super();
    }

    public GeneralJekException(final String message) {
        super(message);
    }

    public GeneralJekException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GeneralJekException(final Throwable cause) {
        super(cause);
    }
}
