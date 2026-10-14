/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.ssl;

import io.grpc.netty.shaded.io.netty.handler.ssl.SslCompletionEvent;

public final class SslHandshakeCompletionEvent
extends SslCompletionEvent {
    public static final SslHandshakeCompletionEvent SUCCESS = new SslHandshakeCompletionEvent();

    private SslHandshakeCompletionEvent() {
    }

    public SslHandshakeCompletionEvent(Throwable cause) {
        super(cause);
    }
}

