/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.text.lookup;

final class IllegalArgumentExceptions {
    static IllegalArgumentException format(String format, Object ... args2) {
        return new IllegalArgumentException(String.format(format, args2));
    }

    static IllegalArgumentException format(Throwable t2, String format, Object ... args2) {
        return new IllegalArgumentException(String.format(format, args2), t2);
    }

    private IllegalArgumentExceptions() {
    }
}

