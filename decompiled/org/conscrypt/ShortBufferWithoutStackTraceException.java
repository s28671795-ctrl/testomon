/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import javax.crypto.ShortBufferException;

final class ShortBufferWithoutStackTraceException
extends ShortBufferException {
    private static final long serialVersionUID = 676150236007842683L;

    public ShortBufferWithoutStackTraceException() {
    }

    public ShortBufferWithoutStackTraceException(String msg) {
        super(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

