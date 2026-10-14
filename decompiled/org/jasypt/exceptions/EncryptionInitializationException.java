/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.exceptions;

public final class EncryptionInitializationException
extends RuntimeException {
    private static final long serialVersionUID = 8929638240023639778L;

    public EncryptionInitializationException() {
    }

    public EncryptionInitializationException(Throwable t2) {
        super(t2);
    }

    public EncryptionInitializationException(String msg, Throwable t2) {
        super(msg, t2);
    }

    public EncryptionInitializationException(String msg) {
        super(msg);
    }
}

