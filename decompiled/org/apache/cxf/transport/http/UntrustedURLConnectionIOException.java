/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.io.IOException;

public class UntrustedURLConnectionIOException
extends IOException {
    private static final long serialVersionUID = 1L;

    public UntrustedURLConnectionIOException(String message) {
        super(message);
    }
}

