/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec;

import io.grpc.netty.shaded.io.netty.handler.codec.DecoderResult;

public interface DecoderResultProvider {
    public DecoderResult decoderResult();

    public void setDecoderResult(DecoderResult var1);
}

