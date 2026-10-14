/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.exception;

import nl.altindag.ssl.exception.GenericSecurityException;

public final class GenericKeyManagerException
extends GenericSecurityException {
    public GenericKeyManagerException(String message) {
        super(message);
    }

    public GenericKeyManagerException(Throwable cause) {
        super(cause);
    }
}

