/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx.v5;

import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v5.Socks5Message;
import java.util.List;

public interface Socks5InitialRequest
extends Socks5Message {
    public List<Socks5AuthMethod> authMethods();
}

