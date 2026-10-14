/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.spdy;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.handler.codec.spdy.SpdyHeaderBlockJZlibEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.spdy.SpdyHeaderBlockZlibEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.grpc.netty.shaded.io.netty.handler.codec.spdy.SpdyVersion;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;

public abstract class SpdyHeaderBlockEncoder {
    static SpdyHeaderBlockEncoder newInstance(SpdyVersion version, int compressionLevel, int windowBits, int memLevel) {
        if (PlatformDependent.javaVersion() >= 7) {
            return new SpdyHeaderBlockZlibEncoder(version, compressionLevel);
        }
        return new SpdyHeaderBlockJZlibEncoder(version, compressionLevel, windowBits, memLevel);
    }

    abstract ByteBuf encode(ByteBufAllocator var1, SpdyHeadersFrame var2) throws Exception;

    abstract void end();
}

