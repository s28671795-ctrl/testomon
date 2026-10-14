/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4;

import io.grpc.netty.shaded.io.netty.handler.codec.socksx.AbstractSocksMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.SocksVersion;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4.Socks4Message;

public abstract class AbstractSocks4Message
extends AbstractSocksMessage
implements Socks4Message {
    @Override
    public final SocksVersion version() {
        return SocksVersion.SOCKS4a;
    }
}

