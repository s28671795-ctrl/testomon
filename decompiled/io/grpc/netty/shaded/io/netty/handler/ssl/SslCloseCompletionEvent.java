/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.ssl;

import io.grpc.netty.shaded.io.netty.handler.ssl.SslCompletionEvent;

public final class SslCloseCompletionEvent
extends SslCompletionEvent {
    public static final SslCloseCompletionEvent SUCCESS = new SslCloseCompletionEvent();

    private SslCloseCompletionEvent() {
    }

    public SslCloseCompletionEvent(Throwable cause) {
        super(cause);
    }
}

