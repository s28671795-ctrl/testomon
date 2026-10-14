/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.handler.codec.DecoderResult;
import io.grpc.netty.shaded.io.netty.handler.codec.DecoderResultProvider;

public interface HttpObject
extends DecoderResultProvider {
    @Deprecated
    public DecoderResult getDecoderResult();
}

