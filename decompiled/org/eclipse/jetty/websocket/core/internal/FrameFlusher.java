/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.StaticException;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Scheduler;
import org.eclipse.jetty.websocket.core.CloseStatus;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.exception.WebSocketException;
import org.eclipse.jetty.websocket.core.exception.WebSocketWriteTimeoutException;
import org.eclipse.jetty.websocket.core.internal.FrameEntry;
import org.eclipse.jetty.websocket.core.internal.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameFlusher
extends IteratingCallback {
    public static final Frame FLUSH_FRAME = new Frame(-1){

        @Override
        public boolean isControlFrame() {
            return true;
        }
    };
    private static final Logger LOG = LoggerFactory.getLogger(FrameFlusher.class);
    private static final Throwable CLOSED_CHANNEL = new StaticException("Closed");
    private final AutoLock lock = new AutoLock();
    private final LongAdder messagesOut = new LongAdder();
    private final LongAdder bytesOut = new LongAdder();
    private final ByteBufferPool bufferPool;
    private final EndPoint endPoint;
    private final int bufferSize;
    private final Generator generator;
    private final int maxGather;
    private final Deque<Entry> queue = new ArrayDeque<Entry>();
    private final List<ByteBuffer> buffers;
    private final Scheduler timeoutScheduler;
    private final List<Entry> entries;
    private final List<Entry> previousEntries;
    private final List<Entry> failedEntries;
    private List<ByteBuffer> releasableBuffers = new ArrayList<ByteBuffer>();
    private ByteBuffer batchBuffer;
    private boolean canEnqueue = true;
    private boolean flushed = true;
    private Throwable closedCause;
    private long idleTimeout;
    private boolean useDirectByteBuffers;

    public FrameFlusher(ByteBufferPool bufferPool, Scheduler scheduler, Generator generator, EndPoint endPoint, int bufferSize, int maxGather) {
        this.bufferPool = bufferPool;
        this.endPoint = endPoint;
        this.bufferSize = bufferSize;
        this.generator = Objects.requireNonNull(generator);
        this.maxGather = maxGather;
        this.entries = new ArrayList<Entry>(maxGather);
        this.previousEntries = new ArrayList<Entry>(maxGather);
        this.failedEntries = new ArrayList<Entry>(maxGather);
        this.buffers = new ArrayList<ByteBuffer>(maxGather * 2 + 1);
        this.timeoutScheduler = scheduler;
    }

    public boolean isUseDirectByteBuffers() {
        return this.useDirectByteBuffers;
    }

    public void setUseDirectByteBuffers(boolean useDirectByteBuffers) {
        this.useDirectByteBuffers = useDirectByteBuffers;
    }

    public boolean enqueue(Frame frame, Callback callback, boolean batch) {
        Throwable dead;
        Entry entry = new Entry(frame, callback, batch);
        byte opCode = frame.getOpCode();
        ArrayList<Entry> failedEntries = null;
        CloseStatus closeStatus = null;
        try (AutoLock l = this.lock.lock();){
            if (this.canEnqueue) {
                dead = this.closedCause;
                if (dead == null) {
                    switch (opCode) {
                        case 8: {
                            closeStatus = CloseStatus.getCloseStatus(frame);
                            if (closeStatus.isAbnormal()) {
                                failedEntries = new ArrayList<Entry>(this.queue);
                                this.queue.clear();
                            }
                            this.queue.offerLast(entry);
                            this.canEnqueue = false;
                            break;
                        }
                        case 9: 
                        case 10: {
                            this.queue.offerFirst(entry);
                            break;
                        }
                        default: {
                            this.queue.offerLast(entry);
                        }
                    }
                    if (this.idleTimeout > 0L && this.queue.size() == 1 && this.entries.isEmpty()) {
                        this.timeoutScheduler.schedule(this::timeoutExpired, this.idleTimeout, TimeUnit.MILLISECONDS);
                    }
                }
            } else {
                dead = new ClosedChannelException();
            }
        }
        if (failedEntries != null) {
            WebSocketException failure = new WebSocketException("Flusher received abnormal CloseFrame: " + CloseStatus.codeString(closeStatus.getCode()), closeStatus.getCause());
            for (Entry e : failedEntries) {
                this.notifyCallbackFailure(e.callback, failure);
            }
        }
        if (dead == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Enqueued {} to {}", (Object)entry, (Object)this);
            }
            return true;
        }
        this.notifyCallbackFailure(callback, dead);
        return false;
    }

    public void onClose(Throwable cause) {
        try (AutoLock l = this.lock.lock();){
            this.closedCause = cause == null ? CLOSED_CHANNEL : cause;
        }
        this.iterate();
    }

    @Override
    protected IteratingCallback.Action process() throws Throwable {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Flushing {}", (Object)this);
        }
        boolean flush = false;
        Callback releasingCallback = this;
        try (AutoLock l = this.lock.lock();){
            if (this.closedCause != null) {
                throw this.closedCause;
            }
            this.previousEntries.addAll(this.entries);
            this.entries.clear();
            if (this.flushed && this.batchBuffer != null) {
                BufferUtil.clear(this.batchBuffer);
            }
            while (!this.queue.isEmpty() && this.entries.size() <= this.maxGather) {
                boolean batch;
                Entry entry = this.queue.poll();
                this.entries.add(entry);
                if (entry.frame == FLUSH_FRAME) {
                    flush = true;
                    break;
                }
                this.messagesOut.increment();
                int batchSpace = this.batchBuffer == null ? this.bufferSize : BufferUtil.space(this.batchBuffer);
                boolean bl = batch = entry.batch && !entry.frame.isControlFrame() && entry.frame.getPayloadLength() < this.bufferSize / 4 && batchSpace - 28 >= entry.frame.getPayloadLength();
                if (batch) {
                    if (this.batchBuffer == null) {
                        this.batchBuffer = this.acquireBuffer(this.bufferSize);
                        this.buffers.add(this.batchBuffer);
                    }
                    this.generator.generateWholeFrame(entry.frame, this.batchBuffer);
                } else {
                    if (this.batchBuffer != null && batchSpace >= 28) {
                        this.generator.generateHeader(entry.frame, this.batchBuffer);
                    } else {
                        ByteBuffer headerBuffer = this.acquireBuffer(28);
                        this.releasableBuffers.add(headerBuffer);
                        this.generator.generateHeader(entry.frame, headerBuffer);
                        this.buffers.add(headerBuffer);
                    }
                    ByteBuffer payload = entry.frame.getPayload();
                    if (BufferUtil.hasContent(payload)) {
                        if (entry.frame.isMasked()) {
                            payload = this.acquireBuffer(entry.frame.getPayloadLength());
                            this.releasableBuffers.add(payload);
                            this.generator.generatePayload(entry.frame, payload);
                        }
                        this.buffers.add(payload.slice());
                    }
                    flush = true;
                }
                this.flushed = flush;
            }
            if (flush) {
                List<ByteBuffer> callbackBuffers = this.releasableBuffers;
                this.releasableBuffers = new ArrayList<ByteBuffer>();
                releasingCallback = Callback.from(releasingCallback, () -> {
                    for (ByteBuffer buffer : callbackBuffers) {
                        this.bufferPool.release(buffer);
                    }
                });
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} processed {} entries flush={} batch={}: {}", this, this.entries.size(), flush, BufferUtil.toDetailString(this.batchBuffer), this.entries);
        }
        for (Entry entry : this.previousEntries) {
            if (entry.frame.getOpCode() == 8) {
                this.endPoint.shutdownOutput();
            }
            this.notifyCallbackSuccess(entry.callback);
        }
        this.previousEntries.clear();
        if (this.entries.isEmpty()) {
            this.releaseAggregate();
            return IteratingCallback.Action.IDLE;
        }
        if (flush) {
            int i = 0;
            int bytes = 0;
            ByteBuffer[] bufferArray = new ByteBuffer[this.buffers.size()];
            for (ByteBuffer bb : this.buffers) {
                bytes += bb.limit() - bb.position();
                bufferArray[i++] = bb;
            }
            this.bytesOut.add(bytes);
            this.endPoint.write(releasingCallback, bufferArray);
            this.buffers.clear();
        } else {
            this.succeeded();
        }
        return IteratingCallback.Action.SCHEDULED;
    }

    private ByteBuffer acquireBuffer(int capacity) {
        return this.bufferPool.acquire(capacity, this.isUseDirectByteBuffers());
    }

    private int getQueueSize() {
        try (AutoLock l = this.lock.lock();){
            int n = this.queue.size();
            return n;
        }
    }

    public void timeoutExpired() {
        boolean failed = false;
        try (AutoLock l = this.lock.lock();){
            if (this.closedCause != null) {
                return;
            }
            long currentTime = System.currentTimeMillis();
            long expiredIfCreatedBefore = currentTime - this.idleTimeout;
            long earliestEntry = currentTime;
            Iterator<Entry> iterator2 = TypeUtil.concat(this.entries.iterator(), this.queue.iterator());
            while (iterator2.hasNext()) {
                Entry entry = iterator2.next();
                if (entry.getTimeOfCreation() <= expiredIfCreatedBefore) {
                    LOG.warn("FrameFlusher write timeout on entry: {}", (Object)entry);
                    failed = true;
                    this.canEnqueue = false;
                    this.closedCause = new WebSocketWriteTimeoutException("FrameFlusher Write Timeout");
                    this.failedEntries.addAll(this.entries);
                    this.failedEntries.addAll(this.queue);
                    this.entries.clear();
                    this.queue.clear();
                    break;
                }
                if (entry.getTimeOfCreation() >= earliestEntry) continue;
                earliestEntry = entry.getTimeOfCreation();
            }
            if (!(failed || this.idleTimeout <= 0L || this.entries.isEmpty() && this.queue.isEmpty())) {
                long nextTimeout = earliestEntry + this.idleTimeout - currentTime;
                this.timeoutScheduler.schedule(this::timeoutExpired, nextTimeout, TimeUnit.MILLISECONDS);
            }
        }
        if (failed) {
            this.iterate();
        }
    }

    @Override
    public void onCompleteFailure(Throwable failure) {
        BufferUtil.clear(this.batchBuffer);
        this.releaseAggregate();
        try (AutoLock l = this.lock.lock();){
            this.failedEntries.addAll(this.queue);
            this.queue.clear();
            this.failedEntries.addAll(this.entries);
            this.entries.clear();
            for (ByteBuffer buffer : this.releasableBuffers) {
                this.bufferPool.release(buffer);
            }
            this.releasableBuffers.clear();
            if (this.closedCause == null) {
                this.closedCause = failure;
            } else if (this.closedCause != failure) {
                this.closedCause.addSuppressed(failure);
            }
        }
        for (Entry entry : this.failedEntries) {
            this.notifyCallbackFailure(entry.callback, failure);
        }
        this.failedEntries.clear();
        this.endPoint.close(this.closedCause);
    }

    private void releaseAggregate() {
        if (BufferUtil.isEmpty(this.batchBuffer)) {
            this.bufferPool.release(this.batchBuffer);
            this.batchBuffer = null;
        }
    }

    protected void notifyCallbackSuccess(Callback callback) {
        block3: {
            try {
                if (callback != null) {
                    callback.succeeded();
                }
            }
            catch (Throwable x) {
                if (!LOG.isDebugEnabled()) break block3;
                LOG.debug("Exception while notifying success of callback {}", (Object)callback, (Object)x);
            }
        }
    }

    protected void notifyCallbackFailure(Callback callback, Throwable failure) {
        block3: {
            try {
                if (callback != null) {
                    callback.failed(failure);
                }
            }
            catch (Throwable x) {
                if (!LOG.isDebugEnabled()) break block3;
                LOG.debug("Exception while notifying failure of callback {}", (Object)callback, (Object)x);
            }
        }
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public long getIdleTimeout() {
        return this.idleTimeout;
    }

    public long getMessagesOut() {
        return this.messagesOut.longValue();
    }

    public long getBytesOut() {
        return this.bytesOut.longValue();
    }

    @Override
    public String toString() {
        return String.format("%s[queueSize=%d,aggregate=%s]", super.toString(), this.getQueueSize(), BufferUtil.toDetailString(this.batchBuffer));
    }

    private class Entry
    extends FrameEntry {
        private ByteBuffer headerBuffer;
        private final long timeOfCreation;

        private Entry(Frame frame, Callback callback, boolean batch) {
            super(frame, callback, batch);
            this.timeOfCreation = System.currentTimeMillis();
        }

        private long getTimeOfCreation() {
            return this.timeOfCreation;
        }

        @Override
        public String toString() {
            return String.format("%s{%s,%s,%b}", this.getClass().getSimpleName(), this.frame, this.callback, this.batch);
        }
    }
}

