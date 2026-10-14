/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.exception;

import nl.altindag.ssl.pem.exception.PemParseException;

public final class CertificateParseException
extends PemParseException {
    public CertificateParseException(Throwable cause) {
        super(cause);
    }

    public CertificateParseException(String message) {
        super(message);
    }
}

