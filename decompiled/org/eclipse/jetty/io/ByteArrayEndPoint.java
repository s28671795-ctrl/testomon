/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.io;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import org.eclipse.jetty.io.AbstractEndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.RuntimeIOException;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteArrayEndPoint
extends AbstractEndPoint {
    private static final Logger LOG = LoggerFactory.getLogger(ByteArrayEndPoint.class);
    private static final SocketAddress NO_SOCKET_ADDRESS = ByteArrayEndPoint.noSocketAddress();
    private static final int MAX_BUFFER_SIZE = 0x7FFFFBFF;
    private static final ByteBuffer EOF = BufferUtil.allocate(0);
    private final Runnable _runFillable = () -> this.getFillInterest().fillable();
    private final AutoLock _lock = new AutoLock();
    private final Condition _hasOutput = this._lock.newCondition();
    private final Queue<ByteBuffer> _inQ = new ArrayDeque<ByteBuffer>();
    private final int _outputSize;
    private ByteBuffer _out;
    private boolean _growOutput;

    private static SocketAddress noSocketAddress() {
        try {
            return new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);
        }
        catch (Throwable x) {
            throw new RuntimeIOException(x);
        }
    }

    public ByteArrayEndPoint() {
        this(null, 0L, null, null);
    }

    public ByteArrayEndPoint(byte[] input, int outputSize) {
        this(null, 0L, input != null ? BufferUtil.toBuffer(input) : null, BufferUtil.allocate(outputSize));
    }

    public ByteArrayEndPoint(String input, int outputSize) {
        this(null, 0L, input != null ? BufferUtil.toBuffer(input) : null, BufferUtil.allocate(outputSize));
    }

    public ByteArrayEndPoint(Scheduler scheduler, long idleTimeoutMs) {
        this(scheduler, idleTimeoutMs, null, null);
    }

    public ByteArrayEndPoint(Scheduler timer, long idleTimeoutMs, byte[] input, int outputSize) {
        this(timer, idleTimeoutMs, input != null ? BufferUtil.toBuffer(input) : null, BufferUtil.allocate(outputSize));
    }

    public ByteArrayEndPoint(Scheduler timer, long idleTimeoutMs, String input, int outputSize) {
        this(timer, idleTimeoutMs, input != null ? BufferUtil.toBuffer(input) : null, BufferUtil.allocate(outputSize));
    }

    public ByteArrayEndPoint(Scheduler timer, long idleTimeoutMs, ByteBuffer input, ByteBuffer output) {
        super(timer);
        if (BufferUtil.hasContent(input)) {
            this.addInput(input);
        }
        this._outputSize = output == null ? 1024 : output.capacity();
        this._out = output == null ? BufferUtil.allocate(this._outputSize) : output;
        this.setIdleTimeout(idleTimeoutMs);
        this.onOpen();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return NO_SOCKET_ADDRESS;
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return NO_SOCKET_ADDRESS;
    }

    @Override
    public void doShutdownOutput() {
        super.doShutdownOutput();
        try (AutoLock l = this._lock.lock();){
            this._hasOutput.signalAll();
        }
    }

    @Override
    public void doClose() {
        super.doClose();
        try (AutoLock l = this._lock.lock();){
            this._hasOutput.signalAll();
        }
    }

    @Override
    protected void onIncompleteFlush() {
    }

    protected void execute(Runnable task) {
        new Thread(task, "BAEPoint-" + Integer.toHexString(this.hashCode())).start();
    }

    @Override
    protected void needsFillInterest() throws IOException {
        try (AutoLock lock = this._lock.lock();){
            if (!this.isOpen()) {
                throw new ClosedChannelException();
            }
            ByteBuffer in = this._inQ.peek();
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} needsFillInterest EOF={} {}", this, in == EOF, BufferUtil.toDetailString(in));
            }
            if (BufferUtil.hasContent(in) || ByteArrayEndPoint.isEOF(in)) {
                this.execute(this._runFillable);
            }
        }
    }

    public void addInputEOF() {
        this.addInput((ByteBuffer)null);
    }

    public void addInput(ByteBuffer in) {
        boolean fillable = false;
        try (AutoLock lock = this._lock.lock();){
            if (ByteArrayEndPoint.isEOF(this._inQ.peek())) {
                throw new RuntimeIOException(new EOFException());
            }
            boolean wasEmpty = this._inQ.isEmpty();
            if (in == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} addEOFAndRun=true", (Object)this);
                }
                this._inQ.add(EOF);
                fillable = true;
            }
            if (BufferUtil.hasContent(in)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} addInputAndRun={} {}", this, wasEmpty, BufferUtil.toDetailString(in));
                }
                this._inQ.add(in);
                fillable = wasEmpty;
            }
        }
        if (fillable) {
            this._runFillable.run();
        }
    }

    public void addInput(String s2) {
        this.addInput(BufferUtil.toBuffer(s2, StandardCharsets.UTF_8));
    }

    public void addInput(String s2, Charset charset) {
        this.addInput(BufferUtil.toBuffer(s2, charset));
    }

    public void addInputAndExecute(ByteBuffer in) {
        boolean fillable = false;
        try (AutoLock lock = this._lock.lock();){
            if (ByteArrayEndPoint.isEOF(this._inQ.peek())) {
                throw new RuntimeIOException(new EOFException());
            }
            boolean wasEmpty = this._inQ.isEmpty();
            if (in == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} addEOFAndExecute=true", (Object)this);
                }
                this._inQ.add(EOF);
                fillable = true;
            }
            if (BufferUtil.hasContent(in)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} addInputAndExecute={} {}", this, wasEmpty, BufferUtil.toDetailString(in));
                }
                this._inQ.add(in);
                fillable = wasEmpty;
            }
        }
        if (fillable) {
            this.execute(this._runFillable);
        }
    }

    public ByteBuffer getOutput() {
        try (AutoLock lock = this._lock.lock();){
            ByteBuffer byteBuffer = this._out;
            return byteBuffer;
        }
    }

    public String getOutputString() {
        return this.getOutputString(StandardCharsets.UTF_8);
    }

    public String getOutputString(Charset charset) {
        return BufferUtil.toString(this._out, charset);
    }

    public ByteBuffer takeOutput() {
        ByteBuffer b;
        try (AutoLock lock = this._lock.lock();){
            b = this._out;
            this._out = BufferUtil.allocate(this._outputSize);
        }
        this.getWriteFlusher().completeWrite();
        return b;
    }

    public ByteBuffer waitForOutput(long time, TimeUnit unit) throws InterruptedException {
        ByteBuffer b;
        try (AutoLock l = this._lock.lock();){
            while (BufferUtil.isEmpty(this._out) && !this.isOutputShutdown()) {
                if (this._hasOutput.await(time, unit)) continue;
                ByteBuffer byteBuffer = null;
                return byteBuffer;
            }
            b = this._out;
            this._out = BufferUtil.allocate(this._outputSize);
        }
        this.getWriteFlusher().completeWrite();
        return b;
    }

    public String takeOutputString() {
        return this.takeOutputString(StandardCharsets.UTF_8);
    }

    public String takeOutputString(Charset charset) {
        ByteBuffer buffer = this.takeOutput();
        return BufferUtil.toString(buffer, charset);
    }

    public void setOutput(ByteBuffer out) {
        try (AutoLock lock = this._lock.lock();){
            this._out = out;
        }
        this.getWriteFlusher().completeWrite();
    }

    public boolean hasMore() {
        return this.getOutput().position() > 0;
    }

    @Override
    public int fill(ByteBuffer buffer) throws IOException {
        int filled = 0;
        try (AutoLock lock = this._lock.lock();){
            while (true) {
                if (!this.isOpen()) {
                    throw new EofException("CLOSED");
                }
                if (this.isInputShutdown()) {
                    int n = -1;
                    return n;
                }
                if (this._inQ.isEmpty()) {
                    break;
                }
                ByteBuffer in = this._inQ.peek();
                if (ByteArrayEndPoint.isEOF(in)) {
                    filled = -1;
                    break;
                }
                if (BufferUtil.hasContent(in)) {
                    filled = BufferUtil.append(buffer, in);
                    if (BufferUtil.isEmpty(in)) {
                        this._inQ.poll();
                    }
                    break;
                }
                this._inQ.poll();
            }
        }
        if (filled > 0) {
            this.notIdle();
        } else if (filled < 0) {
            this.shutdownInput();
        }
        return filled;
    }

    @Override
    public boolean flush(ByteBuffer ... buffers) throws IOException {
        boolean flushed = true;
        try (AutoLock l = this._lock.lock();){
            if (!this.isOpen()) {
                throw new IOException("CLOSED");
            }
            if (this.isOutputShutdown()) {
                throw new IOException("OSHUT");
            }
            boolean idle = true;
            for (ByteBuffer b : buffers) {
                if (!BufferUtil.hasContent(b)) continue;
                if (this._growOutput && b.remaining() > BufferUtil.space(this._out)) {
                    BufferUtil.compact(this._out);
                    if (b.remaining() > BufferUtil.space(this._out) && this._out.capacity() < 0x7FFFFBFF) {
                        long newBufferCapacity = Math.min((long)((double)this._out.capacity() + (double)b.remaining() * 1.5), 0x7FFFFBFFL);
                        ByteBuffer n = BufferUtil.allocate(Math.toIntExact(newBufferCapacity));
                        BufferUtil.append(n, this._out);
                        this._out = n;
                    }
                }
                if (BufferUtil.append(this._out, b) > 0) {
                    idle = false;
                }
                if (!BufferUtil.hasContent(b)) continue;
                flushed = false;
                break;
            }
            if (!idle) {
                this.notIdle();
                this._hasOutput.signalAll();
            }
        }
        return flushed;
    }

    @Override
    public void reset() {
        try (AutoLock l = this._lock.lock();){
            this._inQ.clear();
            this._hasOutput.signalAll();
            BufferUtil.clear(this._out);
        }
        super.reset();
    }

    @Override
    public Object getTransport() {
        return null;
    }

    public boolean isGrowOutput() {
        return this._growOutput;
    }

    public void setGrowOutput(boolean growOutput) {
        this._growOutput = growOutput;
    }

    @Override
    public String toString() {
        String o;
        ByteBuffer b;
        int q;
        try (AutoLock lock = this._lock.lock();){
            q = this._inQ.size();
            b = this._inQ.peek();
            o = BufferUtil.toDetailString(this._out);
        }
        return String.format("%s[q=%d,q[0]=%s,o=%s]", super.toString(), q, b, o);
    }

    private static boolean isEOF(ByteBuffer buffer) {
        boolean isEof = buffer == EOF;
        return isEof;
    }
}

