package com.github.mono83.jek;

/**
 * Contains global constants.
 */
public class Options {
    public static final String USER_AGENT = "JEK prototype 1.0";

    public static final String HEADER_TOKEN = "token";
    public static final String HEADER_CODE = "x-itc-code";
    public static final String HEADER_RAY_ID = "x-itc-rayid";
    public static final String HEADER_MESSAGE = "x-itc-message";

    // Public routes
    public static final String ROUTE_STATUS = "/api/public/status";

    // Not protected routes
    public static final String WHO_AM_I = "/api/whoami";
    public static final String ROUTE_AUTH_WALLET = "/api/wallet/auth";

    private Options() {
    }
}
