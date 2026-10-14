/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

public class ControlFlowSignal
extends RuntimeException {
    public ControlFlowSignal() {
    }

    public ControlFlowSignal(String message) {
        super(message, null, false, false);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

