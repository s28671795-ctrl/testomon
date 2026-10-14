/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel;

import io.grpc.netty.shaded.io.netty.util.ReferenceCounted;
import java.net.SocketAddress;

public interface AddressedEnvelope<M, A extends SocketAddress>
extends ReferenceCounted {
    public M content();

    public A sender();

    public A recipient();

    @Override
    public AddressedEnvelope<M, A> retain();

    @Override
    public AddressedEnvelope<M, A> retain(int var1);

    @Override
    public AddressedEnvelope<M, A> touch();

    @Override
    public AddressedEnvelope<M, A> touch(Object var1);
}

