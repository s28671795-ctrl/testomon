/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx.v5;

import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v5.Socks5Message;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v5.Socks5PasswordAuthStatus;

public interface Socks5PasswordAuthResponse
extends Socks5Message {
    public Socks5PasswordAuthStatus status();
}

