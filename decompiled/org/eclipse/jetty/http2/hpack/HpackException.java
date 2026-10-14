/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.hpack;

public abstract class HpackException
extends Exception {
    HpackException(String messageFormat, Object ... args2) {
        super(String.format(messageFormat, args2));
    }

    public static class CompressionException
    extends SessionException {
        public CompressionException(String messageFormat, Object ... args2) {
            super(messageFormat, args2);
        }
    }

    public static class SessionException
    extends HpackException {
        SessionException(String messageFormat, Object ... args2) {
            super(messageFormat, args2);
        }
    }

    public static class StreamException
    extends HpackException {
        StreamException(String messageFormat, Object ... args2) {
            super(messageFormat, args2);
        }
    }
}

