/*
 * Decompiled with CFR 0.152.
 */
package com.sun.mail.util;

import java.io.IOException;

public class SocketConnectException
extends IOException {
    private String host;
    private int port;
    private int cto;
    private static final long serialVersionUID = 3997871560538755463L;

    public SocketConnectException(String msg, Exception cause, String host2, int port, int cto) {
        super(msg);
        this.initCause(cause);
        this.host = host2;
        this.port = port;
        this.cto = cto;
    }

    public Exception getException() {
        Throwable t2 = this.getCause();
        assert (t2 == null || t2 instanceof Exception);
        return (Exception)t2;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public int getConnectionTimeout() {
        return this.cto;
    }
}

