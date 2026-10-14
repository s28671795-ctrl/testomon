/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.epoll;

import io.grpc.netty.shaded.io.netty.channel.RecvByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.epoll.EpollRecvByteAllocatorHandle;

final class EpollRecvByteAllocatorStreamingHandle
extends EpollRecvByteAllocatorHandle {
    EpollRecvByteAllocatorStreamingHandle(RecvByteBufAllocator.ExtendedHandle handle2) {
        super(handle2);
    }

    @Override
    boolean maybeMoreDataToRead() {
        return this.lastBytesRead() == this.attemptedBytesRead() || this.isReceivedRdHup();
    }
}

