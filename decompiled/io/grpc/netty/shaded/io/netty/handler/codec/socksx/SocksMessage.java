/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx;

import io.grpc.netty.shaded.io.netty.handler.codec.DecoderResultProvider;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.SocksVersion;

public interface SocksMessage
extends DecoderResultProvider {
    public SocksVersion version();
}

