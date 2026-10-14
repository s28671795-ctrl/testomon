/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.exception;

import nl.altindag.ssl.exception.GenericSecurityException;

public final class GenericTrustManagerException
extends GenericSecurityException {
    public GenericTrustManagerException(String message) {
        super(message);
    }

    public GenericTrustManagerException(Throwable cause) {
        super(cause);
    }
}

