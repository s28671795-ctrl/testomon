/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.exception;

import nl.altindag.ssl.exception.GenericSecurityException;

public final class GenericCertificateException
extends GenericSecurityException {
    public GenericCertificateException(Throwable cause) {
        super(cause);
    }

    public GenericCertificateException(String message) {
        super(message);
    }

    public GenericCertificateException(String message, Throwable cause) {
        super(message, cause);
    }
}

