/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelFuture;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.perfmark.Link;
import io.perfmark.PerfMark;
import io.perfmark.TaskCloseable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

class WriteQueue {
    @VisibleForTesting
    static final int DEQUE_CHUNK_SIZE = 128;
    private final Runnable later = new Runnable(){

        @Override
        public void run() {
            WriteQueue.this.flush();
        }
    };
    private final Channel channel;
    private final Queue<QueuedCommand> queue;
    private final AtomicBoolean scheduled = new AtomicBoolean();

    public WriteQueue(Channel channel) {
        this.channel = Preconditions.checkNotNull(channel, "channel");
        this.queue = new ConcurrentLinkedQueue<QueuedCommand>();
    }

    void scheduleFlush() {
        if (this.scheduled.compareAndSet(false, true)) {
            this.channel.eventLoop().execute(this.later);
        }
    }

    @CanIgnoreReturnValue
    ChannelFuture enqueue(QueuedCommand command, boolean flush) {
        Preconditions.checkArgument(command.promise() == null, "promise must not be set on command");
        ChannelPromise promise = this.channel.newPromise();
        command.promise(promise);
        this.queue.add(command);
        if (flush) {
            this.scheduleFlush();
        }
        return promise;
    }

    void enqueue(Runnable runnable2, boolean flush) {
        this.queue.add(new RunnableCommand(runnable2));
        if (flush) {
            this.scheduleFlush();
        }
    }

    void drainNow() {
        Preconditions.checkState(this.channel.eventLoop().inEventLoop(), "must be on the event loop");
        if (this.queue.peek() == null) {
            return;
        }
        this.flush();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void flush() {
        block24: {
            try (TaskCloseable ignore = PerfMark.traceTask("WriteQueue.periodicFlush");){
                TaskCloseable ignore2;
                QueuedCommand cmd;
                int i = 0;
                boolean flushedOnce = false;
                while ((cmd = this.queue.poll()) != null) {
                    cmd.run(this.channel);
                    if (++i != 128) continue;
                    i = 0;
                    ignore2 = PerfMark.traceTask("WriteQueue.flush0");
                    try {
                        this.channel.flush();
                    }
                    finally {
                        if (ignore2 != null) {
                            ignore2.close();
                        }
                    }
                    flushedOnce = true;
                }
                if (i == 0 && flushedOnce) break block24;
                ignore2 = PerfMark.traceTask("WriteQueue.flush1");
                try {
                    this.channel.flush();
                }
                finally {
                    if (ignore2 != null) {
                        ignore2.close();
                    }
                }
            }
            finally {
                this.scheduled.set(false);
                if (!this.queue.isEmpty()) {
                    this.scheduleFlush();
                }
            }
        }
    }

    static interface QueuedCommand {
        public ChannelPromise promise();

        public void promise(ChannelPromise var1);

        public void run(Channel var1);

        public Link getLink();
    }

    static abstract class AbstractQueuedCommand
    implements QueuedCommand {
        private ChannelPromise promise;
        private final Link link = PerfMark.linkOut();

        AbstractQueuedCommand() {
        }

        @Override
        public final void promise(ChannelPromise promise) {
            this.promise = promise;
        }

        @Override
        public final ChannelPromise promise() {
            return this.promise;
        }

        @Override
        public final void run(Channel channel) {
            channel.write(this, this.promise);
        }

        @Override
        public Link getLink() {
            return this.link;
        }
    }

    private static class RunnableCommand
    implements QueuedCommand {
        private final Runnable runnable;
        private final Link link = PerfMark.linkOut();

        public RunnableCommand(Runnable runnable2) {
            this.runnable = runnable2;
        }

        @Override
        public final void promise(ChannelPromise promise) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final ChannelPromise promise() {
            throw new UnsupportedOperationException();
        }

        @Override
        public final void run(Channel channel) {
            this.runnable.run();
        }

        @Override
        public Link getLink() {
            return this.link;
        }
    }
}

