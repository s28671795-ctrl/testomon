/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;

public class TransportDisposedIOException
extends IOException {
    private static final long serialVersionUID = -7107323414439622596L;

    public TransportDisposedIOException() {
    }

    public TransportDisposedIOException(String message) {
        super(message);
    }

    public TransportDisposedIOException(String message, Throwable cause) {
        super(message, cause);
    }
}

