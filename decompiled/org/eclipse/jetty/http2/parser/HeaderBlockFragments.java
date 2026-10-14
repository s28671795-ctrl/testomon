/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.io.ByteBufferPool;

public class HeaderBlockFragments {
    private final ByteBufferPool byteBufferPool;
    private PriorityFrame priorityFrame;
    private boolean endStream;
    private int streamId;
    private ByteBuffer storage;

    public HeaderBlockFragments(ByteBufferPool byteBufferPool) {
        this.byteBufferPool = byteBufferPool;
    }

    public ByteBufferPool getByteBufferPool() {
        return this.byteBufferPool;
    }

    public void storeFragment(ByteBuffer fragment, int length, boolean last) {
        int space;
        if (this.storage == null) {
            space = last ? length : length * 2;
            this.storage = this.byteBufferPool.acquire(space, fragment.isDirect());
            this.storage.clear();
        }
        if (this.storage.remaining() < length) {
            space = last ? length : length * 2;
            int capacity = this.storage.position() + space;
            ByteBuffer newStorage = this.byteBufferPool.acquire(capacity, this.storage.isDirect());
            newStorage.clear();
            this.storage.flip();
            newStorage.put(this.storage);
            this.byteBufferPool.release(this.storage);
            this.storage = newStorage;
        }
        int limit = fragment.limit();
        fragment.limit(fragment.position() + length);
        this.storage.put(fragment);
        fragment.limit(limit);
    }

    public PriorityFrame getPriorityFrame() {
        return this.priorityFrame;
    }

    public void setPriorityFrame(PriorityFrame priorityFrame) {
        this.priorityFrame = priorityFrame;
    }

    public boolean isEndStream() {
        return this.endStream;
    }

    public void setEndStream(boolean endStream) {
        this.endStream = endStream;
    }

    public ByteBuffer complete() {
        ByteBuffer result = this.storage;
        this.storage = null;
        result.flip();
        return result;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }
}

