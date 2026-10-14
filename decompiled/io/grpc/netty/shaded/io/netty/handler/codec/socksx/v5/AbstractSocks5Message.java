/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx.v5;

import io.grpc.netty.shaded.io.netty.handler.codec.socksx.AbstractSocksMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.SocksVersion;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v5.Socks5Message;

public abstract class AbstractSocks5Message
extends AbstractSocksMessage
implements Socks5Message {
    @Override
    public final SocksVersion version() {
        return SocksVersion.SOCKS5;
    }
}

