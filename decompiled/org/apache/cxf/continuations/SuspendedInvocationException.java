/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.continuations;

public class SuspendedInvocationException
extends RuntimeException {
    private static final long serialVersionUID = 6889545463301144757L;

    public SuspendedInvocationException(Throwable cause) {
        super(cause);
    }

    public SuspendedInvocationException() {
    }

    public RuntimeException getRuntimeException() {
        Throwable ex = this.getCause();
        return ex instanceof RuntimeException ? (RuntimeException)ex : null;
    }
}

