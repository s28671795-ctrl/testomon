/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.LongAdder;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.RetainableByteBuffer;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Scheduler;
import org.eclipse.jetty.websocket.core.Behavior;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.exception.WebSocketTimeoutException;
import org.eclipse.jetty.websocket.core.internal.FrameFlusher;
import org.eclipse.jetty.websocket.core.internal.Generator;
import org.eclipse.jetty.websocket.core.internal.Parser;
import org.eclipse.jetty.websocket.core.internal.WebSocketCoreSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketConnection
extends AbstractConnection
implements Connection.UpgradeTo,
Dumpable,
Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketConnection.class);
    private static final int MIN_BUFFER_SIZE = 28;
    private final AutoLock lock = new AutoLock();
    private final ByteBufferPool bufferPool;
    private final RetainableByteBufferPool retainableByteBufferPool;
    private final Generator generator;
    private final Parser parser;
    private final WebSocketCoreSession coreSession;
    private final Flusher flusher;
    private final Random random;
    private long demand;
    private boolean fillingAndParsing;
    private final LongAdder messagesIn = new LongAdder();
    private final LongAdder bytesIn = new LongAdder();
    private RetainableByteBuffer networkBuffer;
    private boolean useInputDirectByteBuffers;
    private boolean useOutputDirectByteBuffers;

    public WebSocketConnection(EndPoint endp, Executor executor2, Scheduler scheduler, ByteBufferPool bufferPool, RetainableByteBufferPool retainableByteBufferPool, WebSocketCoreSession coreSession) {
        this(endp, executor2, scheduler, bufferPool, retainableByteBufferPool, coreSession, null);
    }

    public WebSocketConnection(EndPoint endp, Executor executor2, Scheduler scheduler, ByteBufferPool bufferPool, RetainableByteBufferPool retainableByteBufferPool, WebSocketCoreSession coreSession, Random randomMask) {
        super(endp, executor2);
        Objects.requireNonNull(endp, "EndPoint");
        Objects.requireNonNull(coreSession, "Session");
        Objects.requireNonNull(executor2, "Executor");
        Objects.requireNonNull(bufferPool, "ByteBufferPool");
        Objects.requireNonNull(retainableByteBufferPool, "RetainableByteBufferPool");
        this.bufferPool = bufferPool;
        this.retainableByteBufferPool = retainableByteBufferPool;
        this.coreSession = coreSession;
        this.generator = new Generator();
        this.parser = new Parser(bufferPool, coreSession);
        this.flusher = new Flusher(scheduler, coreSession.getOutputBufferSize(), this.generator, endp);
        this.setInputBufferSize(coreSession.getInputBufferSize());
        if (this.coreSession.getBehavior() == Behavior.CLIENT && randomMask == null) {
            randomMask = new SecureRandom();
        }
        this.random = randomMask;
    }

    @Override
    public Executor getExecutor() {
        return super.getExecutor();
    }

    public ByteBufferPool getBufferPool() {
        return this.bufferPool;
    }

    public Generator getGenerator() {
        return this.generator;
    }

    public Parser getParser() {
        return this.parser;
    }

    @Deprecated
    public InetSocketAddress getLocalAddress() {
        SocketAddress local = this.getLocalSocketAddress();
        if (local instanceof InetSocketAddress) {
            return (InetSocketAddress)local;
        }
        return null;
    }

    public SocketAddress getLocalSocketAddress() {
        return this.getEndPoint().getLocalSocketAddress();
    }

    @Deprecated
    public InetSocketAddress getRemoteAddress() {
        SocketAddress remote = this.getRemoteSocketAddress();
        if (remote instanceof InetSocketAddress) {
            return (InetSocketAddress)remote;
        }
        return null;
    }

    public SocketAddress getRemoteSocketAddress() {
        return this.getEndPoint().getRemoteSocketAddress();
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
    public void onClose(Throwable cause) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onClose() of physical connection");
        }
        if (!this.coreSession.isClosed()) {
            this.coreSession.onEof();
        }
        this.flusher.onClose(cause);
        try (AutoLock l = this.lock.lock();){
            if (this.networkBuffer != null) {
                this.networkBuffer.clear();
                this.releaseNetworkBuffer();
            }
        }
        super.onClose(cause);
    }

    @Override
    public boolean onIdleExpired() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onIdleExpired()");
        }
        this.coreSession.processHandlerError(new WebSocketTimeoutException("Connection Idle Timeout"), Callback.NOOP);
        return true;
    }

    @Override
    protected boolean onReadTimeout(Throwable timeout2) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onReadTimeout()");
        }
        this.coreSession.processHandlerError(new WebSocketTimeoutException("Timeout on Read", timeout2), Callback.NOOP);
        return false;
    }

    protected void onFrame(final Parser.ParsedFrame frame) {
        RetainableByteBuffer referenced;
        if (LOG.isDebugEnabled()) {
            LOG.debug("onFrame({})", (Object)frame);
        }
        RetainableByteBuffer retainableByteBuffer = referenced = frame.hasPayload() && !frame.isReleaseable() ? this.networkBuffer : null;
        if (referenced != null) {
            referenced.retain();
        }
        this.coreSession.onFrame(frame, new Callback(){

            @Override
            public void succeeded() {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("succeeded onFrame({})", (Object)frame);
                }
                frame.close();
                if (referenced != null) {
                    referenced.release();
                }
            }

            @Override
            public void failed(Throwable cause) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("failed onFrame({}) {}", (Object)frame, (Object)cause.toString());
                }
                frame.close();
                if (referenced != null) {
                    referenced.release();
                }
                WebSocketConnection.this.coreSession.processHandlerError(cause, NOOP);
            }
        });
    }

    private void acquireNetworkBuffer() {
        try (AutoLock l = this.lock.lock();){
            if (this.networkBuffer == null) {
                this.networkBuffer = this.newNetworkBuffer(this.getInputBufferSize());
            }
        }
    }

    private void reacquireNetworkBuffer() {
        try (AutoLock l = this.lock.lock();){
            if (this.networkBuffer == null) {
                throw new IllegalStateException();
            }
            if (this.networkBuffer.getBuffer().hasRemaining()) {
                throw new IllegalStateException();
            }
            this.networkBuffer.release();
            this.networkBuffer = this.newNetworkBuffer(this.getInputBufferSize());
        }
    }

    private RetainableByteBuffer newNetworkBuffer(int capacity) {
        return this.retainableByteBufferPool.acquire(capacity, this.isUseInputDirectByteBuffers());
    }

    private void releaseNetworkBuffer() {
        try (AutoLock l = this.lock.lock();){
            if (this.networkBuffer == null) {
                throw new IllegalStateException();
            }
            if (this.networkBuffer.hasRemaining()) {
                throw new IllegalStateException();
            }
            this.networkBuffer.release();
            this.networkBuffer = null;
        }
    }

    @Override
    public void onFillable() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onFillable()");
        }
        this.fillAndParse();
    }

    @Override
    public void run() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("run()");
        }
        this.fillAndParse();
    }

    public void demand(long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("Demand must be positive");
        }
        boolean fillAndParse = false;
        try (AutoLock l = this.lock.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("demand {} d={} fp={} {} {}", n, this.demand, this.fillingAndParsing, this.networkBuffer, this);
            }
            if (this.demand < 0L) {
                return;
            }
            try {
                this.demand = Math.addExact(this.demand, n);
            }
            catch (ArithmeticException e) {
                this.demand = Long.MAX_VALUE;
            }
            if (!this.fillingAndParsing) {
                this.fillingAndParsing = true;
                fillAndParse = true;
            }
        }
        if (fillAndParse) {
            this.getExecutor().execute(this);
        }
    }

    public boolean moreDemand() {
        try (AutoLock l = this.lock.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("moreDemand? d={} fp={} {} {}", this.demand, this.fillingAndParsing, this.networkBuffer, this);
            }
            if (!this.fillingAndParsing) {
                throw new IllegalStateException();
            }
            if (this.demand != 0L) {
                boolean bl = true;
                return bl;
            }
            this.fillingAndParsing = false;
            if (this.networkBuffer.isEmpty()) {
                this.releaseNetworkBuffer();
            }
            boolean bl = false;
            return bl;
        }
    }

    public boolean meetDemand() {
        try (AutoLock l = this.lock.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("meetDemand d={} fp={} {} {}", this.demand, this.fillingAndParsing, this.networkBuffer, this);
            }
            if (this.demand == 0L) {
                throw new IllegalStateException();
            }
            if (!this.fillingAndParsing) {
                throw new IllegalStateException();
            }
            if (this.demand > 0L) {
                --this.demand;
            }
            boolean bl = true;
            return bl;
        }
    }

    public void cancelDemand() {
        try (AutoLock l = this.lock.lock();){
            if (LOG.isDebugEnabled()) {
                LOG.debug("cancelDemand d={} fp={} {} {}", this.demand, this.fillingAndParsing, this.networkBuffer, this);
            }
            this.demand = -1L;
        }
    }

    private void fillAndParse() {
        this.acquireNetworkBuffer();
        try {
            while (true) {
                Parser.ParsedFrame frame;
                if (!this.networkBuffer.isEmpty() && (frame = this.parser.parse(this.networkBuffer.getBuffer())) != null) {
                    this.messagesIn.increment();
                    if (this.meetDemand()) {
                        this.onFrame(frame);
                    }
                    if (this.moreDemand()) continue;
                    return;
                }
                assert (this.networkBuffer.isEmpty());
                if (!this.getEndPoint().isOpen()) {
                    this.releaseNetworkBuffer();
                    return;
                }
                if (this.networkBuffer.isRetained()) {
                    this.reacquireNetworkBuffer();
                }
                int filled = this.getEndPoint().fill(this.networkBuffer.getBuffer());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("endpointFill() filled={}: {}", (Object)filled, (Object)this.networkBuffer);
                }
                if (filled < 0) {
                    this.releaseNetworkBuffer();
                    this.coreSession.onEof();
                    return;
                }
                if (filled == 0) {
                    this.releaseNetworkBuffer();
                    this.fillInterested();
                    return;
                }
                this.bytesIn.add(filled);
            }
        }
        catch (Throwable t2) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error during fillAndParse() {}", (Object)t2.toString());
            }
            if (this.networkBuffer != null) {
                BufferUtil.clear(this.networkBuffer.getBuffer());
                this.releaseNetworkBuffer();
            }
            this.coreSession.processConnectionError(t2, Callback.NOOP);
            return;
        }
    }

    protected void setInitialBuffer(ByteBuffer initialBuffer) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Set initial buffer - {}", (Object)BufferUtil.toDetailString(initialBuffer));
        }
        try (AutoLock l = this.lock.lock();){
            this.networkBuffer = this.newNetworkBuffer(initialBuffer.remaining());
        }
        ByteBuffer buffer = this.networkBuffer.getBuffer();
        BufferUtil.clearToFill(buffer);
        BufferUtil.put(initialBuffer, buffer);
        BufferUtil.flipToFlush(buffer, 0);
    }

    @Override
    public void onOpen() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onOpen() {}", (Object)this);
        }
        super.onOpen();
        this.coreSession.onOpen();
    }

    @Override
    public void setInputBufferSize(int inputBufferSize) {
        if (inputBufferSize < 28) {
            throw new IllegalArgumentException("Cannot have buffer size less than 28");
        }
        super.setInputBufferSize(inputBufferSize);
    }

    @Override
    public String dump() {
        return Dumpable.dump(this);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObjects(out, indent, this, new Object[0]);
    }

    @Override
    public String toConnectionString() {
        return String.format("%s@%x[%s,p=%s,f=%s,g=%s]", new Object[]{this.getClass().getSimpleName(), this.hashCode(), this.coreSession.getBehavior(), this.parser, this.flusher, this.generator});
    }

    @Override
    public void onUpgradeTo(ByteBuffer buffer) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onUpgradeTo({})", (Object)BufferUtil.toDetailString(buffer));
        }
        this.setInitialBuffer(buffer);
    }

    public FrameFlusher getFrameFlusher() {
        return this.flusher;
    }

    @Override
    public long getMessagesIn() {
        return this.messagesIn.longValue();
    }

    @Override
    public long getBytesIn() {
        return this.bytesIn.longValue();
    }

    @Override
    public long getMessagesOut() {
        return this.flusher.getMessagesOut();
    }

    @Override
    public long getBytesOut() {
        return this.flusher.getBytesOut();
    }

    void enqueueFrame(Frame frame, Callback callback, boolean batch) {
        if (this.coreSession.getBehavior() == Behavior.CLIENT) {
            byte[] mask = new byte[4];
            this.random.nextBytes(mask);
            frame.setMask(mask);
        }
        if (this.flusher.enqueue(frame, callback, batch)) {
            this.flusher.iterate();
        }
    }

    private class Flusher
    extends FrameFlusher {
        private Flusher(Scheduler scheduler, int bufferSize, Generator generator, EndPoint endpoint) {
            super(WebSocketConnection.this.bufferPool, scheduler, generator, endpoint, bufferSize, 8);
            this.setUseDirectByteBuffers(WebSocketConnection.this.isUseOutputDirectByteBuffers());
        }

        @Override
        public void onCompleteFailure(Throwable x) {
            WebSocketConnection.this.coreSession.processConnectionError(x, NOOP);
            super.onCompleteFailure(x);
        }
    }
}

