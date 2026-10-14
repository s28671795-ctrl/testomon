/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.exception;

import nl.altindag.ssl.pem.exception.PemParseException;

public final class PrivateKeyParseException
extends PemParseException {
    public PrivateKeyParseException(Throwable cause) {
        super(cause);
    }

    public PrivateKeyParseException(String message) {
        super(message);
    }
}

