/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.exception;

import nl.altindag.ssl.exception.GenericException;

public class GenericSecurityException
extends GenericException {
    public GenericSecurityException(String message) {
        super(message);
    }

    public GenericSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericSecurityException(Throwable cause) {
        super(cause);
    }
}

