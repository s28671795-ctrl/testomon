/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Settings;

public interface Http2SettingsReceivedConsumer {
    public void consumeReceivedSettings(Http2Settings var1);
}

