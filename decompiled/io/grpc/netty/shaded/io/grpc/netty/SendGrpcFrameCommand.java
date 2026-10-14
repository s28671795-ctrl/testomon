/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import io.grpc.netty.shaded.io.grpc.netty.StreamIdHolder;
import io.grpc.netty.shaded.io.grpc.netty.WriteQueue;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufHolder;
import io.grpc.netty.shaded.io.netty.buffer.DefaultByteBufHolder;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.perfmark.Link;
import io.perfmark.PerfMark;

final class SendGrpcFrameCommand
extends DefaultByteBufHolder
implements WriteQueue.QueuedCommand {
    private final StreamIdHolder stream;
    private final boolean endStream;
    private final Link link;
    private ChannelPromise promise;

    SendGrpcFrameCommand(StreamIdHolder stream, ByteBuf content, boolean endStream) {
        super(content);
        this.stream = stream;
        this.endStream = endStream;
        this.link = PerfMark.linkOut();
    }

    @Override
    public Link getLink() {
        return this.link;
    }

    StreamIdHolder stream() {
        return this.stream;
    }

    boolean endStream() {
        return this.endStream;
    }

    @Override
    public ByteBufHolder replace(ByteBuf content) {
        return new SendGrpcFrameCommand(this.stream, content, this.endStream);
    }

    @Override
    public boolean equals(Object that) {
        if (that == null || !that.getClass().equals(SendGrpcFrameCommand.class)) {
            return false;
        }
        SendGrpcFrameCommand thatCmd = (SendGrpcFrameCommand)that;
        return thatCmd.stream.equals(this.stream) && thatCmd.endStream == this.endStream && thatCmd.content().equals(this.content());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(streamId=" + this.stream.id() + ", endStream=" + this.endStream + ", content=" + this.content() + ")";
    }

    @Override
    public int hashCode() {
        int hash = this.content().hashCode();
        hash = hash * 31 + this.stream.hashCode();
        if (this.endStream) {
            hash = -hash;
        }
        return hash;
    }

    @Override
    public ChannelPromise promise() {
        return this.promise;
    }

    @Override
    public void promise(ChannelPromise promise) {
        this.promise = promise;
    }

    @Override
    public final void run(Channel channel) {
        channel.write(this, this.promise);
    }
}

