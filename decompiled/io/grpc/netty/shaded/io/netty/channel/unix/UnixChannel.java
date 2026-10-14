/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.unix;

import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.unix.FileDescriptor;

public interface UnixChannel
extends Channel {
    public FileDescriptor fd();
}

