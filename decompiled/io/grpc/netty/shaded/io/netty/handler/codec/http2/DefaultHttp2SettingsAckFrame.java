/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http2;

import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2SettingsAckFrame;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

final class DefaultHttp2SettingsAckFrame
implements Http2SettingsAckFrame {
    DefaultHttp2SettingsAckFrame() {
    }

    @Override
    public String name() {
        return "SETTINGS(ACK)";
    }

    public String toString() {
        return StringUtil.simpleClassName(this);
    }
}

