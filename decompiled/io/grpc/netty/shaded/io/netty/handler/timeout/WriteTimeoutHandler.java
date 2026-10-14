/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.timeout;

import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelFutureListener;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.ChannelOutboundHandlerAdapter;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.handler.timeout.WriteTimeoutException;
import io.grpc.netty.shaded.io.netty.util.concurrent.Future;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;
import java.util.concurrent.TimeUnit;

public class WriteTimeoutHandler
extends ChannelOutboundHandlerAdapter {
    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
    private final long timeoutNanos;
    private WriteTimeoutTask lastTask;
    private boolean closed;

    public WriteTimeoutHandler(int timeoutSeconds) {
        this(timeoutSeconds, TimeUnit.SECONDS);
    }

    public WriteTimeoutHandler(long timeout2, TimeUnit unit) {
        ObjectUtil.checkNotNull(unit, "unit");
        this.timeoutNanos = timeout2 <= 0L ? 0L : Math.max(unit.toNanos(timeout2), MIN_TIMEOUT_NANOS);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (this.timeoutNanos > 0L) {
            promise = promise.unvoid();
            this.scheduleTimeout(ctx, promise);
        }
        ctx.write(msg, promise);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        assert (ctx.executor().inEventLoop());
        WriteTimeoutTask task = this.lastTask;
        this.lastTask = null;
        while (task != null) {
            assert (task.ctx.executor().inEventLoop());
            task.scheduledFuture.cancel(false);
            WriteTimeoutTask prev = task.prev;
            task.prev = null;
            task.next = null;
            task = prev;
        }
    }

    private void scheduleTimeout(ChannelHandlerContext ctx, ChannelPromise promise) {
        WriteTimeoutTask task = new WriteTimeoutTask(ctx, promise);
        task.scheduledFuture = ctx.executor().schedule(task, this.timeoutNanos, TimeUnit.NANOSECONDS);
        if (!task.scheduledFuture.isDone()) {
            this.addWriteTimeoutTask(task);
            promise.addListener(task);
        }
    }

    private void addWriteTimeoutTask(WriteTimeoutTask task) {
        assert (task.ctx.executor().inEventLoop());
        if (this.lastTask != null) {
            this.lastTask.next = task;
            task.prev = this.lastTask;
        }
        this.lastTask = task;
    }

    private void removeWriteTimeoutTask(WriteTimeoutTask task) {
        assert (task.ctx.executor().inEventLoop());
        if (task == this.lastTask) {
            assert (task.next == null);
            this.lastTask = this.lastTask.prev;
            if (this.lastTask != null) {
                this.lastTask.next = null;
            }
        } else {
            if (task.prev == null && task.next == null) {
                return;
            }
            if (task.prev == null) {
                task.next.prev = null;
            } else {
                task.prev.next = task.next;
                task.next.prev = task.prev;
            }
        }
        task.prev = null;
        task.next = null;
    }

    protected void writeTimedOut(ChannelHandlerContext ctx) throws Exception {
        if (!this.closed) {
            ctx.fireExceptionCaught(WriteTimeoutException.INSTANCE);
            ctx.close();
            this.closed = true;
        }
    }

    private final class WriteTimeoutTask
    implements Runnable,
    ChannelFutureListener {
        private final ChannelHandlerContext ctx;
        private final ChannelPromise promise;
        WriteTimeoutTask prev;
        WriteTimeoutTask next;
        Future<?> scheduledFuture;

        WriteTimeoutTask(ChannelHandlerContext ctx, ChannelPromise promise) {
            this.ctx = ctx;
            this.promise = promise;
        }

        @Override
        public void run() {
            if (!this.promise.isDone()) {
                try {
                    WriteTimeoutHandler.this.writeTimedOut(this.ctx);
                }
                catch (Throwable t2) {
                    this.ctx.fireExceptionCaught(t2);
                }
            }
            WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
        }

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            this.scheduledFuture.cancel(false);
            if (this.ctx.executor().inEventLoop()) {
                WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
            } else {
                assert (this.promise.isDone());
                this.ctx.executor().execute(this);
            }
        }
    }
}

