/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4;

import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4.Socks4CommandStatus;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4.Socks4Message;

public interface Socks4CommandResponse
extends Socks4Message {
    public Socks4CommandStatus status();

    public String dstAddr();

    public int dstPort();
}

