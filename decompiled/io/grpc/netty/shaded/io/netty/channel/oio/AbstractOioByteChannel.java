/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.oio;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelConfig;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelMetadata;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import io.grpc.netty.shaded.io.netty.channel.ChannelOutboundBuffer;
import io.grpc.netty.shaded.io.netty.channel.ChannelPipeline;
import io.grpc.netty.shaded.io.netty.channel.FileRegion;
import io.grpc.netty.shaded.io.netty.channel.RecvByteBufAllocator;
import io.grpc.netty.shaded.io.netty.channel.oio.AbstractOioChannel;
import io.grpc.netty.shaded.io.netty.channel.socket.ChannelInputShutdownEvent;
import io.grpc.netty.shaded.io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import java.io.IOException;

public abstract class AbstractOioByteChannel
extends AbstractOioChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';

    protected AbstractOioByteChannel(Channel parent) {
        super(parent);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    protected abstract boolean isInputShutdown();

    protected abstract ChannelFuture shutdownInput();

    private void closeOnRead(ChannelPipeline pipeline) {
        if (this.isOpen()) {
            if (Boolean.TRUE.equals(this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                this.shutdownInput();
                pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
            } else {
                this.unsafe().close(this.unsafe().voidPromise());
            }
            pipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
        }
    }

    private void handleReadException(ChannelPipeline pipeline, ByteBuf byteBuf, Throwable cause, boolean close, RecvByteBufAllocator.Handle allocHandle) {
        if (byteBuf != null) {
            if (byteBuf.isReadable()) {
                this.readPending = false;
                pipeline.fireChannelRead(byteBuf);
            } else {
                byteBuf.release();
            }
        }
        allocHandle.readComplete();
        pipeline.fireChannelReadComplete();
        pipeline.fireExceptionCaught(cause);
        if (close || cause instanceof OutOfMemoryError || cause instanceof IOException) {
            this.closeOnRead(pipeline);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doRead() {
        ChannelConfig config = this.config();
        if (this.isInputShutdown() || !this.readPending) {
            return;
        }
        this.readPending = false;
        ChannelPipeline pipeline = this.pipeline();
        ByteBufAllocator allocator = config.getAllocator();
        RecvByteBufAllocator.Handle allocHandle = this.unsafe().recvBufAllocHandle();
        allocHandle.reset(config);
        ByteBuf byteBuf = null;
        boolean close = false;
        boolean readData = false;
        try {
            byteBuf = allocHandle.allocate(allocator);
            do {
                int maxCapacity;
                allocHandle.lastBytesRead(this.doReadBytes(byteBuf));
                if (allocHandle.lastBytesRead() <= 0) {
                    if (byteBuf.isReadable()) break;
                    byteBuf.release();
                    byteBuf = null;
                    boolean bl = close = allocHandle.lastBytesRead() < 0;
                    if (!close) break;
                    this.readPending = false;
                    break;
                }
                readData = true;
                int available = this.available();
                if (available <= 0) break;
                if (byteBuf.isWritable()) continue;
                int capacity = byteBuf.capacity();
                if (capacity == (maxCapacity = byteBuf.maxCapacity())) {
                    allocHandle.incMessagesRead(1);
                    this.readPending = false;
                    pipeline.fireChannelRead(byteBuf);
                    byteBuf = allocHandle.allocate(allocator);
                    continue;
                }
                int writerIndex = byteBuf.writerIndex();
                if (writerIndex + available > maxCapacity) {
                    byteBuf.capacity(maxCapacity);
                    continue;
                }
                byteBuf.ensureWritable(available);
            } while (allocHandle.continueReading());
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    this.readPending = false;
                    pipeline.fireChannelRead(byteBuf);
                } else {
                    byteBuf.release();
                }
                byteBuf = null;
            }
            if (readData) {
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
            }
            if (close) {
                this.closeOnRead(pipeline);
            }
        }
        catch (Throwable t2) {
            this.handleReadException(pipeline, byteBuf, t2, close, allocHandle);
        }
        finally {
            if (this.readPending || config.isAutoRead() || !readData && this.isActive()) {
                this.read();
            }
        }
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        Object msg;
        while ((msg = in.current()) != null) {
            if (msg instanceof ByteBuf) {
                ByteBuf buf = (ByteBuf)msg;
                int readableBytes = buf.readableBytes();
                while (readableBytes > 0) {
                    this.doWriteBytes(buf);
                    int newReadableBytes = buf.readableBytes();
                    in.progress(readableBytes - newReadableBytes);
                    readableBytes = newReadableBytes;
                }
                in.remove();
                continue;
            }
            if (msg instanceof FileRegion) {
                FileRegion region = (FileRegion)msg;
                long transferred = region.transferred();
                this.doWriteFileRegion(region);
                in.progress(region.transferred() - transferred);
                in.remove();
                continue;
            }
            in.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg)));
        }
    }

    @Override
    protected final Object filterOutboundMessage(Object msg) throws Exception {
        if (msg instanceof ByteBuf || msg instanceof FileRegion) {
            return msg;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
    }

    protected abstract int available();

    protected abstract int doReadBytes(ByteBuf var1) throws Exception;

    protected abstract void doWriteBytes(ByteBuf var1) throws Exception;

    protected abstract void doWriteFileRegion(FileRegion var1) throws Exception;
}

