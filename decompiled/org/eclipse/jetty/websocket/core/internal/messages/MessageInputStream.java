/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.messages.MessageSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageInputStream
extends InputStream
implements MessageSink {
    private static final Logger LOG = LoggerFactory.getLogger(MessageInputStream.class);
    private static final Entry EOF = new Entry(BufferUtil.EMPTY_BUFFER, Callback.NOOP);
    private static final Entry CLOSED = new Entry(BufferUtil.EMPTY_BUFFER, Callback.NOOP);
    private final AutoLock lock = new AutoLock();
    private final BlockingArrayQueue<Entry> buffers = new BlockingArrayQueue();
    private boolean closed = false;
    private Entry currentEntry;
    private long timeoutMs = -1L;

    @Override
    public void accept(Frame frame, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("accepting {}", (Object)frame);
        }
        boolean succeed = false;
        try (AutoLock l = this.lock.lock();){
            if (this.closed || !frame.hasPayload() && !frame.isFin()) {
                succeed = true;
            } else {
                if (frame.hasPayload()) {
                    this.buffers.add(new Entry(frame.getPayload(), callback));
                } else {
                    succeed = true;
                }
                if (frame.isFin()) {
                    this.buffers.add(EOF);
                }
            }
        }
        if (succeed) {
            callback.succeeded();
        }
    }

    @Override
    public int read() throws IOException {
        int len;
        byte[] buf = new byte[1];
        do {
            if ((len = this.read(buf, 0, 1)) >= 0) continue;
            return -1;
        } while (len <= 0);
        return buf[0] & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(b, off, len).slice();
        BufferUtil.clear(buffer);
        return this.read(buffer);
    }

    public int read(ByteBuffer buffer) throws IOException {
        Entry currentEntry = this.getCurrentEntry();
        if (LOG.isDebugEnabled()) {
            LOG.debug("currentEntry = {}", (Object)currentEntry);
        }
        if (currentEntry == CLOSED) {
            throw new IOException("Closed");
        }
        if (currentEntry == EOF) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Read EOF");
            }
            return -1;
        }
        int fillLen = BufferUtil.append(buffer, currentEntry.buffer);
        if (!currentEntry.buffer.hasRemaining()) {
            this.succeedCurrentEntry();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("filled {} bytes from {}", (Object)fillLen, (Object)currentEntry);
        }
        return fillLen;
    }

    public void setTimeout(long timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    private void succeedCurrentEntry() {
        Entry current;
        try (AutoLock l = this.lock.lock();){
            current = this.currentEntry;
            this.currentEntry = null;
        }
        if (current != null) {
            current.callback.succeeded();
        }
    }

    private Entry getCurrentEntry() throws IOException {
        Entry result;
        try (AutoLock l = this.lock.lock();){
            if (this.currentEntry != null) {
                Entry entry = this.currentEntry;
                return entry;
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Waiting {} ms to read", (Object)this.timeoutMs);
        }
        if (this.timeoutMs < 0L) {
            result = this.buffers.take();
        } else {
            result = this.buffers.poll(this.timeoutMs, TimeUnit.MILLISECONDS);
            if (result == null) {
                throw new IOException(String.format("Read timeout: %,dms expired", this.timeoutMs));
            }
        }
        AutoLock l = this.lock.lock();
        try {
            Entry entry = this.currentEntry = result;
            if (l != null) {
                l.close();
            }
            return entry;
        }
        catch (Throwable throwable) {
            try {
                if (l != null) {
                    try {
                        l.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            catch (InterruptedException e) {
                this.close();
                throw new InterruptedIOException();
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.fail(null);
    }

    @Override
    public void fail(Throwable failure) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("close()");
        }
        ArrayList<Entry> entries = new ArrayList<Entry>();
        try (AutoLock l = this.lock.lock();){
            if (this.closed) {
                return;
            }
            this.closed = true;
            if (this.currentEntry != null) {
                entries.add(this.currentEntry);
                this.currentEntry = null;
            }
            entries.addAll(this.buffers);
            this.buffers.clear();
            this.buffers.offer(CLOSED);
        }
        for (Entry e : entries) {
            if (failure == null) {
                e.callback.succeeded();
                continue;
            }
            e.callback.failed(failure);
        }
        IO.close(() -> super.close());
    }

    private static class Entry {
        public ByteBuffer buffer;
        public Callback callback;

        public Entry(ByteBuffer buffer, Callback callback) {
            this.buffer = Objects.requireNonNull(buffer);
            this.callback = callback;
        }

        public String toString() {
            return String.format("Entry[%s,%s]", BufferUtil.toDetailString(this.buffer), this.callback.getClass().getSimpleName());
        }
    }
}

