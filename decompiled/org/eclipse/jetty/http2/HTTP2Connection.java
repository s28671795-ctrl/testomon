/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.HTTP2Session;
import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.parser.Parser;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.RetainableByteBuffer;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.io.WriteFlusher;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.ExecutionStrategy;
import org.eclipse.jetty.util.thread.Invocable;
import org.eclipse.jetty.util.thread.strategy.AdaptiveExecutionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTP2Connection
extends AbstractConnection
implements WriteFlusher.Listener,
Connection.UpgradeTo {
    protected static final Logger LOG = LoggerFactory.getLogger(HTTP2Connection.class);
    private final AutoLock lock = new AutoLock();
    private final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
    private final HTTP2Producer producer = new HTTP2Producer();
    private final AtomicLong bytesIn = new AtomicLong();
    private final RetainableByteBufferPool retainableByteBufferPool;
    private final Parser parser;
    private final ISession session;
    private final int bufferSize;
    private final ExecutionStrategy strategy;
    private boolean useInputDirectByteBuffers;
    private boolean useOutputDirectByteBuffers;

    protected HTTP2Connection(RetainableByteBufferPool retainableByteBufferPool, Executor executor2, EndPoint endPoint, Parser parser, ISession session, int bufferSize) {
        super(endPoint, executor2);
        this.retainableByteBufferPool = retainableByteBufferPool;
        this.parser = parser;
        this.session = session;
        this.bufferSize = bufferSize;
        this.strategy = new AdaptiveExecutionStrategy(this.producer, executor2);
        LifeCycle.start(this.strategy);
        parser.init(x$0 -> new ParserListener((Parser.Listener)x$0));
    }

    @Override
    public long getMessagesIn() {
        HTTP2Session session = (HTTP2Session)this.getSession();
        return session.getStreamsOpened();
    }

    @Override
    public long getMessagesOut() {
        HTTP2Session session = (HTTP2Session)this.getSession();
        return session.getStreamsClosed();
    }

    @Override
    public long getBytesIn() {
        return this.bytesIn.get();
    }

    @Override
    public long getBytesOut() {
        return this.session.getBytesWritten();
    }

    public ISession getSession() {
        return this.session;
    }

    protected Parser getParser() {
        return this.parser;
    }

    @Override
    public void onUpgradeTo(ByteBuffer buffer) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 onUpgradeTo {} {}", (Object)this, (Object)BufferUtil.toDetailString(buffer));
        }
        this.producer.setInputBuffer(buffer);
    }

    public boolean isUseInputDirectByteBuffers() {
        return this.useInputDirectByteBuffers;
    }

    public void setUseInputDirectByteBuffers(boolean useInputDirectByteBuffers) {
        this.useInputDirectByteBuffers = useInputDirectByteBuffers;
    }

    public boolean isUseOutputDirectByteBuffers() {
        return this.useOutputDirectByteBuffers;
    }

    public void setUseOutputDirectByteBuffers(boolean useOutputDirectByteBuffers) {
        this.useOutputDirectByteBuffers = useOutputDirectByteBuffers;
    }

    @Override
    public void onOpen() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 Open {} ", (Object)this);
        }
        super.onOpen();
    }

    @Override
    public void onClose(Throwable cause) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 Close {} ", (Object)this);
        }
        super.onClose(cause);
        LifeCycle.stop(this.strategy);
    }

    @Override
    public void onFillable() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 onFillable {} ", (Object)this);
        }
        this.produce();
    }

    private int fill(EndPoint endPoint, ByteBuffer buffer) {
        try {
            if (endPoint.isInputShutdown()) {
                return -1;
            }
            return endPoint.fill(buffer);
        }
        catch (IOException x) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Could not read from {}", (Object)endPoint, (Object)x);
            }
            return -1;
        }
    }

    @Override
    public boolean onIdleExpired() {
        boolean close;
        boolean idle = this.isFillInterested();
        if (idle && (close = this.session.onIdleTimeout())) {
            this.session.close(ErrorCode.NO_ERROR.code, "idle_timeout", Callback.NOOP);
        }
        return false;
    }

    protected void offerTask(Runnable task, boolean dispatch) {
        this.offerTask(task);
        if (dispatch) {
            this.dispatch();
        } else {
            this.produce();
        }
    }

    private void offerTask(Runnable task) {
        try (AutoLock l = this.lock.lock();){
            this.tasks.offer(task);
        }
    }

    protected void produce() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 produce {} ", (Object)this);
        }
        this.strategy.produce();
    }

    protected void dispatch() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("HTTP2 dispatch {} ", (Object)this);
        }
        this.strategy.dispatch();
    }

    @Override
    public void close() {
        this.session.close(ErrorCode.NO_ERROR.code, "close", Callback.NOOP);
    }

    private Runnable pollTask() {
        try (AutoLock l = this.lock.lock();){
            Runnable runnable2 = this.tasks.poll();
            return runnable2;
        }
    }

    @Override
    public void onFlushed(long bytes) throws IOException {
        this.session.onFlushed(bytes);
    }

    protected class HTTP2Producer
    implements ExecutionStrategy.Producer {
        private final Callback fillableCallback;
        private NetworkBuffer networkBuffer;
        private boolean shutdown;
        private boolean failed;

        protected HTTP2Producer() {
            this.fillableCallback = new FillableCallback();
        }

        private void setInputBuffer(ByteBuffer byteBuffer) {
            this.acquireNetworkBuffer();
            this.networkBuffer.put(byteBuffer);
        }

        @Override
        public Runnable produce() {
            Runnable task = HTTP2Connection.this.pollTask();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Dequeued task {}", (Object)String.valueOf(task));
            }
            if (task != null) {
                return task;
            }
            if (HTTP2Connection.this.isFillInterested() || this.shutdown || this.failed) {
                return null;
            }
            boolean interested = false;
            this.acquireNetworkBuffer();
            try {
                boolean parse = this.networkBuffer.hasRemaining();
                while (true) {
                    if (parse) {
                        while (this.networkBuffer.hasRemaining()) {
                            HTTP2Connection.this.parser.parse(this.networkBuffer.getBuffer());
                            if (!this.failed) continue;
                            Runnable runnable2 = null;
                            return runnable2;
                        }
                        task = HTTP2Connection.this.pollTask();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Dequeued new task {}", (Object)task);
                        }
                        if (task != null) {
                            Runnable runnable3 = task;
                            return runnable3;
                        }
                        if (this.networkBuffer.isRetained()) {
                            this.reacquireNetworkBuffer();
                        }
                    }
                    int filled = HTTP2Connection.this.fill(HTTP2Connection.this.getEndPoint(), this.networkBuffer.getBuffer());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Filled {} bytes in {}", (Object)filled, (Object)this.networkBuffer);
                    }
                    if (filled <= 0) {
                        if (filled == 0) {
                            interested = true;
                            Runnable runnable4 = null;
                            return runnable4;
                        }
                        this.shutdown = true;
                        HTTP2Connection.this.session.onShutdown();
                        Runnable runnable5 = null;
                        return runnable5;
                    }
                    HTTP2Connection.this.bytesIn.addAndGet(filled);
                    parse = true;
                }
            }
            finally {
                this.releaseNetworkBuffer();
                if (interested) {
                    HTTP2Connection.this.getEndPoint().fillInterested(this.fillableCallback);
                }
            }
        }

        private void acquireNetworkBuffer() {
            if (this.networkBuffer == null) {
                this.networkBuffer = new NetworkBuffer();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Acquired {}", (Object)this.networkBuffer);
                }
            }
        }

        private void reacquireNetworkBuffer() {
            NetworkBuffer currentBuffer = this.networkBuffer;
            if (currentBuffer == null) {
                throw new IllegalStateException();
            }
            if (currentBuffer.hasRemaining()) {
                throw new IllegalStateException();
            }
            currentBuffer.release();
            this.networkBuffer = new NetworkBuffer();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Reacquired {}<-{}", (Object)currentBuffer, (Object)this.networkBuffer);
            }
        }

        private void releaseNetworkBuffer() {
            NetworkBuffer currentBuffer = this.networkBuffer;
            if (currentBuffer == null) {
                throw new IllegalStateException();
            }
            if (currentBuffer.hasRemaining() && !this.shutdown && !this.failed) {
                throw new IllegalStateException();
            }
            currentBuffer.release();
            this.networkBuffer = null;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Released {}", (Object)currentBuffer);
            }
        }

        public String toString() {
            return String.format("%s@%x", this.getClass().getSimpleName(), this.hashCode());
        }
    }

    private class ParserListener
    extends Parser.Listener.Wrapper {
        private ParserListener(Parser.Listener listener) {
            super(listener);
        }

        @Override
        public void onData(DataFrame frame) {
            NetworkBuffer networkBuffer = HTTP2Connection.this.producer.networkBuffer;
            networkBuffer.retain();
            NetworkBuffer callback = networkBuffer;
            HTTP2Connection.this.session.onData(frame, callback);
        }

        @Override
        public void onConnectionFailure(int error, String reason) {
            HTTP2Connection.this.producer.failed = true;
            super.onConnectionFailure(error, reason);
        }
    }

    private class NetworkBuffer
    implements Callback {
        private final RetainableByteBuffer delegate;

        private NetworkBuffer() {
            this.delegate = HTTP2Connection.this.retainableByteBufferPool.acquire(HTTP2Connection.this.bufferSize, HTTP2Connection.this.isUseInputDirectByteBuffers());
        }

        public ByteBuffer getBuffer() {
            return this.delegate.getBuffer();
        }

        public boolean isRetained() {
            return this.delegate.isRetained();
        }

        public boolean hasRemaining() {
            return this.delegate.hasRemaining();
        }

        public boolean release() {
            return this.delegate.release();
        }

        public void retain() {
            this.delegate.retain();
        }

        private void put(ByteBuffer source2) {
            BufferUtil.append(this.delegate.getBuffer(), source2);
        }

        @Override
        public void succeeded() {
            this.completed(null);
        }

        @Override
        public void failed(Throwable failure) {
            this.completed(failure);
        }

        private void completed(Throwable failure) {
            if (this.delegate.release() && LOG.isDebugEnabled()) {
                LOG.debug("Released retained {}", (Object)this, (Object)failure);
            }
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return Invocable.InvocationType.NON_BLOCKING;
        }
    }

    private class FillableCallback
    implements Callback {
        private FillableCallback() {
        }

        @Override
        public void succeeded() {
            HTTP2Connection.this.onFillable();
        }

        @Override
        public void failed(Throwable x) {
            HTTP2Connection.this.onFillInterestedFailed(x);
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return Invocable.InvocationType.EITHER;
        }
    }
}

