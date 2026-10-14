/*
 * Decompiled with CFR 0.152.
 */
package com.ctc.wstx.util;

import java.io.IOException;

public final class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static void throwRuntimeException(Throwable t2) {
        ExceptionUtil.throwIfUnchecked(t2);
        throw new RuntimeException("[was " + t2.getClass() + "] " + t2.getMessage(), t2);
    }

    public static IOException constructIOException(String msg, Throwable src) {
        IOException e = new IOException(msg);
        ExceptionUtil.setInitCause(e, src);
        return e;
    }

    public static void throwAsIllegalArgument(Throwable t2) {
        ExceptionUtil.throwIfUnchecked(t2);
        IllegalArgumentException rex = new IllegalArgumentException("[was " + t2.getClass() + "] " + t2.getMessage());
        ExceptionUtil.setInitCause(rex, t2);
        throw rex;
    }

    public static void throwIfUnchecked(Throwable t2) {
        if (t2 instanceof RuntimeException) {
            throw (RuntimeException)t2;
        }
        if (t2 instanceof Error) {
            throw (Error)t2;
        }
    }

    public static void throwGenericInternal() {
        ExceptionUtil.throwInternal(null);
    }

    public static void throwInternal(String msg) {
        if (msg == null) {
            msg = "[no description]";
        }
        throw new RuntimeException("Internal error: " + msg);
    }

    public static void setInitCause(Throwable newT, Throwable rootT) {
        if (newT.getCause() == null) {
            newT.initCause(rootT);
        }
    }
}

