/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.exception;

import nl.altindag.ssl.exception.GenericSecurityException;

public final class GenericIOException
extends GenericSecurityException {
    public GenericIOException(Throwable cause) {
        super(cause);
    }

    public GenericIOException(String message, Throwable cause) {
        super(message, cause);
    }
}

