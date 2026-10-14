/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageToByteEncoder;

interface CompressionEncoderFactory {
    public MessageToByteEncoder<ByteBuf> createEncoder();
}

