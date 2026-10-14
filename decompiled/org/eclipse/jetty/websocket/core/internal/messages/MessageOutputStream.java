/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.FutureCallback;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageOutputStream
extends OutputStream {
    private static final Logger LOG = LoggerFactory.getLogger(MessageOutputStream.class);
    private final AutoLock lock = new AutoLock();
    private final CoreSession coreSession;
    private final ByteBufferPool bufferPool;
    private final int bufferSize;
    private long frameCount;
    private long bytesSent;
    private ByteBuffer buffer;
    private Callback callback;
    private boolean closed;
    private byte messageOpCode = (byte)2;

    public MessageOutputStream(CoreSession coreSession, ByteBufferPool bufferPool) {
        this.coreSession = coreSession;
        this.bufferPool = bufferPool;
        this.bufferSize = coreSession.getOutputBufferSize();
        this.buffer = bufferPool.acquire(this.bufferSize, true);
    }

    void setMessageType(byte opcode) {
        this.messageOpCode = opcode;
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        try {
            this.send(ByteBuffer.wrap(bytes, off, len));
        }
        catch (Throwable x) {
            this.notifyFailure(x);
            throw x;
        }
    }

    @Override
    public void write(int b) throws IOException {
        try {
            this.send(ByteBuffer.wrap(new byte[]{(byte)b}));
        }
        catch (Throwable x) {
            this.notifyFailure(x);
            throw x;
        }
    }

    public void write(ByteBuffer buffer) throws IOException {
        try {
            this.send(buffer);
        }
        catch (Throwable x) {
            this.notifyFailure(x);
            throw x;
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.flush(false);
        }
        catch (Throwable x) {
            this.notifyFailure(x);
            throw x;
        }
    }

    private void flush(boolean fin) throws IOException {
        try (AutoLock l = this.lock.lock();){
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            this.closed = fin;
            Frame frame = new Frame(this.frameCount == 0L ? this.messageOpCode : (byte)0);
            frame.setPayload(this.buffer);
            frame.setFin(fin);
            int initialBufferSize = this.buffer.remaining();
            FutureCallback b = new FutureCallback();
            this.coreSession.sendFrame(frame, b, false);
            b.block();
            this.bytesSent += (long)initialBufferSize;
            ++this.frameCount;
            try {
                assert (this.buffer.remaining() == initialBufferSize);
                BufferUtil.clear(this.buffer);
            }
            catch (Throwable t2) {
                t2.printStackTrace();
            }
        }
    }

    private void send(ByteBuffer data) throws IOException {
        try (AutoLock l = this.lock.lock();){
            if (this.closed) {
                throw new IOException("Stream is closed");
            }
            while (data.hasRemaining()) {
                int bufferRemainingSpace = this.bufferSize - this.buffer.remaining();
                int copied = Math.min(bufferRemainingSpace, data.remaining());
                BufferUtil.append(this.buffer, data.array(), data.arrayOffset() + data.position(), copied);
                data.position(data.position() + copied);
                if (!data.hasRemaining()) continue;
                this.flush(false);
            }
        }
    }

    @Override
    public void close() throws IOException {
        try {
            this.flush(true);
            this.bufferPool.release(this.buffer);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Stream closed, {} frames ({} bytes) sent", (Object)this.frameCount, (Object)this.bytesSent);
            }
            this.notifySuccess();
        }
        catch (Throwable x) {
            this.notifyFailure(x);
            throw x;
        }
    }

    public void setCallback(Callback callback) {
        try (AutoLock l = this.lock.lock();){
            this.callback = callback;
        }
    }

    private void notifySuccess() {
        Callback callback;
        try (AutoLock l = this.lock.lock();){
            callback = this.callback;
        }
        if (callback != null) {
            callback.succeeded();
        }
    }

    private void notifyFailure(Throwable failure) {
        Callback callback;
        try (AutoLock l = this.lock.lock();){
            callback = this.callback;
        }
        if (callback != null) {
            callback.failed(failure);
        }
    }
}

