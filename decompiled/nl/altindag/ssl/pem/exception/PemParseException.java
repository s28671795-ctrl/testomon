/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.exception;

import nl.altindag.ssl.exception.GenericSecurityException;

public class PemParseException
extends GenericSecurityException {
    public PemParseException(Throwable cause) {
        super(cause);
    }

    public PemParseException(String message) {
        super(message);
    }
}

