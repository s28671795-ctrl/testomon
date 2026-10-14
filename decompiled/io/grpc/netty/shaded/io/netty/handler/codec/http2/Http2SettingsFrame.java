/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Frame;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Settings;

public interface Http2SettingsFrame
extends Http2Frame {
    public Http2Settings settings();

    @Override
    public String name();
}

