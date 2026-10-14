/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec;

public class MessageAggregationException
extends IllegalStateException {
    private static final long serialVersionUID = -1995826182950310255L;

    public MessageAggregationException() {
    }

    public MessageAggregationException(String s2) {
        super(s2);
    }

    public MessageAggregationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageAggregationException(Throwable cause) {
        super(cause);
    }
}

