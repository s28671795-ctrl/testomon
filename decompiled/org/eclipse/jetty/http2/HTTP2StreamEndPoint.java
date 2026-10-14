/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadPendingException;
import java.nio.channels.WritePendingException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HTTP2StreamEndPoint
implements EndPoint {
    private static final Logger LOG = LoggerFactory.getLogger(HTTP2StreamEndPoint.class);
    private final AutoLock lock = new AutoLock();
    private final Deque<Entry> dataQueue = new ArrayDeque<Entry>();
    private final AtomicReference<WriteState> writeState = new AtomicReference<WriteState>(WriteState.IDLE);
    private final AtomicReference<Callback> readCallback = new AtomicReference();
    private final long created = System.currentTimeMillis();
    private final AtomicBoolean eof = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private final IStream stream;
    private Connection connection;

    public HTTP2StreamEndPoint(IStream stream) {
        this.stream = stream;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        SocketAddress local = this.getLocalSocketAddress();
        if (local instanceof InetSocketAddress) {
            return (InetSocketAddress)local;
        }
        return null;
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return this.stream.getSession().getLocalSocketAddress();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        SocketAddress remote = this.getRemoteSocketAddress();
        if (remote instanceof InetSocketAddress) {
            return (InetSocketAddress)remote;
        }
        return null;
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return this.stream.getSession().getRemoteSocketAddress();
    }

    @Override
    public boolean isOpen() {
        return !this.closed.get();
    }

    @Override
    public long getCreatedTimeStamp() {
        return this.created;
    }

    @Override
    public void shutdownOutput() {
        block5: while (true) {
            WriteState current = this.writeState.get();
            switch (current.state) {
                case IDLE: 
                case OSHUTTING: {
                    if (!this.writeState.compareAndSet(current, WriteState.OSHUT)) continue block5;
                    this.stream.data(new DataFrame(this.stream.getId(), BufferUtil.EMPTY_BUFFER, true), Callback.from(this::oshutSuccess, this::oshutFailure));
                    return;
                }
                case PENDING: {
                    if (!this.writeState.compareAndSet(current, WriteState.OSHUTTING)) continue block5;
                    return;
                }
                case OSHUT: 
                case FAILED: {
                    return;
                }
            }
        }
    }

    private void oshutSuccess() {
        WriteState current = this.writeState.get();
        switch (current.state) {
            case IDLE: 
            case OSHUTTING: 
            case PENDING: {
                throw new IllegalStateException();
            }
        }
    }

    private void oshutFailure(Throwable failure) {
        block5: while (true) {
            WriteState current = this.writeState.get();
            switch (current.state) {
                case IDLE: 
                case OSHUTTING: 
                case PENDING: {
                    throw new IllegalStateException();
                }
                case OSHUT: {
                    if (!this.writeState.compareAndSet(current, new WriteState(WriteState.State.FAILED, failure))) continue block5;
                    return;
                }
                case FAILED: {
                    return;
                }
            }
        }
    }

    @Override
    public boolean isOutputShutdown() {
        WriteState.State state = this.writeState.get().state;
        return state == WriteState.State.OSHUTTING || state == WriteState.State.OSHUT;
    }

    @Override
    public boolean isInputShutdown() {
        return this.eof.get();
    }

    @Override
    public void close(Throwable cause) {
        if (this.closed.compareAndSet(false, true)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("closing {}, cause: {}", (Object)this, (Object)cause);
            }
            this.shutdownOutput();
            this.stream.close();
            this.onClose(cause);
        }
    }

    @Override
    public int fill(ByteBuffer sink2) throws IOException {
        Entry entry;
        try (AutoLock l = this.lock.lock();){
            entry = this.dataQueue.poll();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("filled {} on {}", (Object)entry, (Object)this);
        }
        if (entry == null) {
            return 0;
        }
        if (entry.isEOF()) {
            entry.succeed();
            return this.shutdownInput();
        }
        IOException failure = entry.ioFailure();
        if (failure != null) {
            entry.fail(failure);
            throw failure;
        }
        int sinkPosition = BufferUtil.flipToFill(sink2);
        ByteBuffer source2 = entry.buffer;
        int sourceLength = source2.remaining();
        int length = Math.min(sourceLength, sink2.remaining());
        int sourceLimit = source2.limit();
        source2.limit(source2.position() + length);
        sink2.put(source2);
        source2.limit(sourceLimit);
        BufferUtil.flipToFlush(sink2, sinkPosition);
        if (source2.hasRemaining()) {
            try (AutoLock l = this.lock.lock();){
                this.dataQueue.offerFirst(entry);
            }
        } else {
            entry.succeed();
            this.stream.demand(1L);
        }
        return length;
    }

    private int shutdownInput() {
        this.eof.set(true);
        return -1;
    }

    @Override
    public boolean flush(ByteBuffer ... buffers) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("flushing {} on {}", (Object)BufferUtil.toDetailString(buffers), (Object)this);
        }
        if (buffers == null || buffers.length == 0) {
            return true;
        }
        block6: while (true) {
            WriteState current = this.writeState.get();
            switch (current.state) {
                case IDLE: {
                    if (!this.writeState.compareAndSet(current, WriteState.PENDING)) continue block6;
                    ByteBuffer buffer = this.coalesce(buffers, true);
                    Callback.Completable callback = new Callback.Completable(Invocable.InvocationType.NON_BLOCKING);
                    this.stream.data(new DataFrame(this.stream.getId(), buffer, false), callback);
                    callback.whenComplete((nothing, failure) -> {
                        if (failure == null) {
                            this.flushSuccess();
                        } else {
                            this.flushFailure((Throwable)failure);
                        }
                    });
                    return callback.isDone();
                }
                case PENDING: {
                    return false;
                }
                case OSHUTTING: 
                case OSHUT: {
                    throw new EofException("Output shutdown");
                }
                case FAILED: {
                    Throwable failure2 = current.failure;
                    if (failure2 instanceof IOException) {
                        throw (IOException)failure2;
                    }
                    throw new IOException(failure2);
                }
            }
        }
    }

    private void flushSuccess() {
        block6: while (true) {
            WriteState current = this.writeState.get();
            switch (current.state) {
                case IDLE: 
                case OSHUT: {
                    throw new IllegalStateException();
                }
                case PENDING: {
                    if (!this.writeState.compareAndSet(current, WriteState.IDLE)) continue block6;
                    return;
                }
                case OSHUTTING: {
                    this.shutdownOutput();
                    return;
                }
                case FAILED: {
                    return;
                }
            }
        }
    }

    private void flushFailure(Throwable failure) {
        block6: while (true) {
            WriteState current = this.writeState.get();
            switch (current.state) {
                case IDLE: 
                case OSHUT: {
                    throw new IllegalStateException();
                }
                case PENDING: {
                    if (!this.writeState.compareAndSet(current, new WriteState(WriteState.State.FAILED, failure))) continue block6;
                    return;
                }
                case OSHUTTING: {
                    this.shutdownOutput();
                    return;
                }
                case FAILED: {
                    return;
                }
            }
        }
    }

    @Override
    public Object getTransport() {
        return this.stream;
    }

    @Override
    public long getIdleTimeout() {
        return this.stream.getIdleTimeout();
    }

    @Override
    public void setIdleTimeout(long idleTimeout) {
        this.stream.setIdleTimeout(idleTimeout);
    }

    @Override
    public void fillInterested(Callback callback) throws ReadPendingException {
        if (!this.tryFillInterested(callback)) {
            throw new ReadPendingException();
        }
    }

    @Override
    public boolean tryFillInterested(Callback callback) {
        boolean result = this.readCallback.compareAndSet(null, callback);
        if (result) {
            this.process();
        }
        return result;
    }

    @Override
    public boolean isFillInterested() {
        return this.readCallback.get() != null;
    }

    @Override
    public void write(Callback callback, ByteBuffer ... buffers) throws WritePendingException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("writing {} on {}", (Object)BufferUtil.toDetailString(buffers), (Object)this);
        }
        if (buffers != null && buffers.length != 0 && this.remaining(buffers) != 0L) {
            block6: while (true) {
                WriteState current = this.writeState.get();
                switch (current.state) {
                    case IDLE: {
                        if (!this.writeState.compareAndSet(current, WriteState.PENDING)) continue block6;
                        ByteBuffer result = this.coalesce(buffers, false);
                        this.stream.data(new DataFrame(this.stream.getId(), result, false), Callback.from(() -> this.writeSuccess(callback), x -> this.writeFailure((Throwable)x, callback)));
                        return;
                    }
                    case PENDING: {
                        callback.failed(new WritePendingException());
                        return;
                    }
                    case OSHUTTING: 
                    case OSHUT: {
                        callback.failed(new EofException("Output shutdown"));
                        return;
                    }
                    case FAILED: {
                        callback.failed(current.failure);
                        return;
                    }
                }
            }
        }
        callback.succeeded();
    }

    private void writeSuccess(Callback callback) {
        block6: while (true) {
            WriteState current = this.writeState.get();
            switch (current.state) {
                case IDLE: 
                case OSHUT: {
                    callback.failed(new IllegalStateException());
                    return;
                }
                case PENDING: {
                    if (!this.writeState.compareAndSet(current, WriteState.IDLE)) continue block6;
                    callback.succeeded();
                    return;
                }
                case OSHUTTING: {
                    callback.succeeded();
                    this.shutdownOutput();
                    return;
                }
                case FAILED: {
                    callback.failed(current.failure);
                    return;
                }
            }
        }
    }

    private void writeFailure(Throwable failure, Callback callback) {
        block5: while (true) {
            WriteState current = this.writeState.get();
            switch (current.state) {
                case IDLE: 
                case OSHUT: {
                    callback.failed(new IllegalStateException(failure));
                    return;
                }
                case OSHUTTING: 
                case PENDING: {
                    if (!this.writeState.compareAndSet(current, new WriteState(WriteState.State.FAILED, failure))) continue block5;
                    callback.failed(failure);
                    return;
                }
                case FAILED: {
                    return;
                }
            }
        }
    }

    private long remaining(ByteBuffer ... buffers) {
        long total = 0L;
        for (ByteBuffer buffer : buffers) {
            total += (long)buffer.remaining();
        }
        return total;
    }

    private ByteBuffer coalesce(ByteBuffer[] buffers, boolean forceCopy) {
        if (buffers.length == 1 && !forceCopy) {
            return buffers[0];
        }
        long capacity = this.remaining(buffers);
        if (capacity > Integer.MAX_VALUE) {
            throw new BufferOverflowException();
        }
        ByteBuffer result = BufferUtil.allocateDirect((int)capacity);
        for (ByteBuffer buffer : buffers) {
            BufferUtil.append(result, buffer);
        }
        return result;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onOpen() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onOpen {}", (Object)this);
        }
    }

    @Override
    public void onClose(Throwable cause) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onClose {}", (Object)this);
        }
    }

    @Override
    public void upgrade(Connection newConnection) {
        Connection oldConnection = this.getConnection();
        ByteBuffer buffer = null;
        if (oldConnection instanceof Connection.UpgradeFrom) {
            buffer = ((Connection.UpgradeFrom)((Object)oldConnection)).onUpgradeFrom();
        }
        if (oldConnection != null) {
            oldConnection.onClose(null);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("upgrading from {} to {} with data {} on {}", oldConnection, newConnection, BufferUtil.toDetailString(buffer), this);
        }
        this.setConnection(newConnection);
        if (newConnection instanceof Connection.UpgradeTo && buffer != null) {
            ((Connection.UpgradeTo)((Object)newConnection)).onUpgradeTo(buffer);
        }
        newConnection.onOpen();
    }

    protected void offerData(DataFrame frame, Callback callback) {
        ByteBuffer buffer = frame.getData();
        if (LOG.isDebugEnabled()) {
            LOG.debug("offering {} on {}", (Object)frame, (Object)this);
        }
        if (frame.isEndStream()) {
            if (buffer.hasRemaining()) {
                this.offer(buffer, Callback.from(Callback.NOOP::succeeded, callback::failed), null);
            }
            this.offer(BufferUtil.EMPTY_BUFFER, callback, Entry.EOF);
        } else if (buffer.hasRemaining()) {
            this.offer(buffer, callback, null);
        } else {
            callback.succeeded();
        }
        this.process();
    }

    protected void offerFailure(Throwable failure) {
        this.offer(BufferUtil.EMPTY_BUFFER, Callback.NOOP, failure);
        this.process();
    }

    private void offer(ByteBuffer buffer, Callback callback, Throwable failure) {
        try (AutoLock l = this.lock.lock();){
            this.dataQueue.offer(new Entry(buffer, callback, failure));
        }
    }

    protected void process() {
        Callback callback;
        boolean empty;
        try (AutoLock l = this.lock.lock();){
            empty = this.dataQueue.isEmpty();
        }
        if (!empty && (callback = (Callback)this.readCallback.getAndSet(null)) != null) {
            callback.succeeded();
        }
    }

    public String toString() {
        return String.format("%s@%x[%s@%x#%d][w=%s]", this.getClass().getSimpleName(), this.hashCode(), this.stream.getClass().getSimpleName(), this.stream.hashCode(), this.stream.getId(), this.writeState);
    }

    private static class WriteState {
        public static final WriteState IDLE = new WriteState(State.IDLE);
        public static final WriteState PENDING = new WriteState(State.PENDING);
        public static final WriteState OSHUTTING = new WriteState(State.OSHUTTING);
        public static final WriteState OSHUT = new WriteState(State.OSHUT);
        private final State state;
        private final Throwable failure;

        private WriteState(State state) {
            this(state, null);
        }

        private WriteState(State state, Throwable failure) {
            this.state = state;
            this.failure = failure;
        }

        public String toString() {
            return this.state.toString();
        }

        private static enum State {
            IDLE,
            PENDING,
            OSHUTTING,
            OSHUT,
            FAILED;

        }
    }

    private static class Entry {
        private static final Throwable EOF = new Throwable();
        private final ByteBuffer buffer;
        private final Callback callback;
        private final Throwable failure;

        private Entry(ByteBuffer buffer, Callback callback, Throwable failure) {
            this.buffer = buffer;
            this.callback = callback;
            this.failure = failure;
        }

        private boolean isEOF() {
            return this.failure == EOF;
        }

        private IOException ioFailure() {
            if (this.failure == null || this.isEOF()) {
                return null;
            }
            return this.failure instanceof IOException ? (IOException)this.failure : new IOException(this.failure);
        }

        private void succeed() {
            this.callback.succeeded();
        }

        private void fail(Throwable failure) {
            this.callback.failed(failure);
        }

        public String toString() {
            return String.format("%s@%x[b=%s,eof=%b,f=%s]", this.getClass().getSimpleName(), this.hashCode(), BufferUtil.toDetailString(this.buffer), this.isEOF(), this.isEOF() ? null : this.failure);
        }
    }
}

