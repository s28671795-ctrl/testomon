/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.exception;

import nl.altindag.ssl.exception.GenericSecurityException;

public final class GenericKeyStoreException
extends GenericSecurityException {
    public GenericKeyStoreException(String message) {
        super(message);
    }

    public GenericKeyStoreException(Throwable cause) {
        super(cause);
    }

    public GenericKeyStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}

