/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.exception;

public class GenericException
extends RuntimeException {
    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }
}

