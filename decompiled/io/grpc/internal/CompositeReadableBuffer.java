/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.AbstractReadableBuffer;
import io.grpc.internal.ReadableBuffer;
import io.grpc.internal.ReadableBuffers;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.InvalidMarkException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import javax.annotation.Nullable;

public class CompositeReadableBuffer
extends AbstractReadableBuffer {
    private final Deque<ReadableBuffer> readableBuffers;
    private Deque<ReadableBuffer> rewindableBuffers;
    private int readableBytes;
    private final Queue<ReadableBuffer> buffers = new ArrayDeque<ReadableBuffer>(2);
    private boolean marked;
    private static final NoThrowReadOperation<Void> UBYTE_OP = new NoThrowReadOperation<Void>(){

        @Override
        public int read(ReadableBuffer buffer, int length, Void unused2, int value) {
            return buffer.readUnsignedByte();
        }
    };
    private static final NoThrowReadOperation<Void> SKIP_OP = new NoThrowReadOperation<Void>(){

        @Override
        public int read(ReadableBuffer buffer, int length, Void unused2, int unused22) {
            buffer.skipBytes(length);
            return 0;
        }
    };
    private static final NoThrowReadOperation<byte[]> BYTE_ARRAY_OP = new NoThrowReadOperation<byte[]>(){

        @Override
        public int read(ReadableBuffer buffer, int length, byte[] dest, int offset) {
            buffer.readBytes(dest, offset, length);
            return offset + length;
        }
    };
    private static final NoThrowReadOperation<ByteBuffer> BYTE_BUF_OP = new NoThrowReadOperation<ByteBuffer>(){

        @Override
        public int read(ReadableBuffer buffer, int length, ByteBuffer dest, int unused2) {
            int prevLimit = dest.limit();
            ((Buffer)dest).limit(dest.position() + length);
            buffer.readBytes(dest);
            ((Buffer)dest).limit(prevLimit);
            return 0;
        }
    };
    private static final ReadOperation<OutputStream> STREAM_OP = new ReadOperation<OutputStream>(){

        @Override
        public int read(ReadableBuffer buffer, int length, OutputStream dest, int unused2) throws IOException {
            buffer.readBytes(dest, length);
            return 0;
        }
    };

    public CompositeReadableBuffer(int initialCapacity) {
        this.readableBuffers = new ArrayDeque<ReadableBuffer>(initialCapacity);
    }

    public CompositeReadableBuffer() {
        this.readableBuffers = new ArrayDeque<ReadableBuffer>();
    }

    public void addBuffer(ReadableBuffer buffer) {
        boolean markHead = this.marked && this.readableBuffers.isEmpty();
        this.enqueueBuffer(buffer);
        if (markHead) {
            this.readableBuffers.peek().mark();
        }
    }

    private void enqueueBuffer(ReadableBuffer buffer) {
        if (!(buffer instanceof CompositeReadableBuffer)) {
            this.readableBuffers.add(buffer);
            this.readableBytes += buffer.readableBytes();
            return;
        }
        CompositeReadableBuffer compositeBuffer = (CompositeReadableBuffer)buffer;
        while (!compositeBuffer.readableBuffers.isEmpty()) {
            ReadableBuffer subBuffer = compositeBuffer.readableBuffers.remove();
            this.readableBuffers.add(subBuffer);
        }
        this.readableBytes += compositeBuffer.readableBytes;
        compositeBuffer.readableBytes = 0;
        compositeBuffer.close();
    }

    @Override
    public int readableBytes() {
        return this.readableBytes;
    }

    @Override
    public int readUnsignedByte() {
        return this.executeNoThrow(UBYTE_OP, 1, null, 0);
    }

    @Override
    public void skipBytes(int length) {
        this.executeNoThrow(SKIP_OP, length, null, 0);
    }

    @Override
    public void readBytes(byte[] dest, int destOffset, int length) {
        this.executeNoThrow(BYTE_ARRAY_OP, length, dest, destOffset);
    }

    @Override
    public void readBytes(ByteBuffer dest) {
        this.executeNoThrow(BYTE_BUF_OP, dest.remaining(), dest, 0);
    }

    @Override
    public void readBytes(OutputStream dest, int length) throws IOException {
        this.execute(STREAM_OP, length, dest, 0);
    }

    public void readBytes(CompositeReadableBuffer dest, int length) {
        this.checkReadable(length);
        this.readableBytes -= length;
        while (length > 0) {
            ReadableBuffer buffer = this.buffers.peek();
            if (buffer.readableBytes() > length) {
                dest.addBuffer(buffer.readBytes(length));
                length = 0;
                continue;
            }
            dest.addBuffer(this.buffers.poll());
            length -= buffer.readableBytes();
        }
    }

    @Override
    public ReadableBuffer readBytes(int length) {
        if (length <= 0) {
            return ReadableBuffers.empty();
        }
        this.checkReadable(length);
        this.readableBytes -= length;
        ReadableBuffer newBuffer = null;
        CompositeReadableBuffer newComposite = null;
        do {
            ReadableBuffer readBuffer;
            ReadableBuffer buffer;
            int readable;
            if ((readable = (buffer = this.readableBuffers.peek()).readableBytes()) > length) {
                readBuffer = buffer.readBytes(length);
                length = 0;
            } else {
                if (this.marked) {
                    readBuffer = buffer.readBytes(readable);
                    this.advanceBuffer();
                } else {
                    readBuffer = this.readableBuffers.poll();
                }
                length -= readable;
            }
            if (newBuffer == null) {
                newBuffer = readBuffer;
                continue;
            }
            if (newComposite == null) {
                newComposite = new CompositeReadableBuffer(length == 0 ? 2 : Math.min(this.readableBuffers.size() + 2, 16));
                newComposite.addBuffer(newBuffer);
                newBuffer = newComposite;
            }
            newComposite.addBuffer(readBuffer);
        } while (length > 0);
        return newBuffer;
    }

    @Override
    public boolean markSupported() {
        for (ReadableBuffer buffer : this.readableBuffers) {
            if (buffer.markSupported()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void mark() {
        if (this.rewindableBuffers == null) {
            this.rewindableBuffers = new ArrayDeque<ReadableBuffer>(Math.min(this.readableBuffers.size(), 16));
        }
        while (!this.rewindableBuffers.isEmpty()) {
            this.rewindableBuffers.remove().close();
        }
        this.marked = true;
        ReadableBuffer buffer = this.readableBuffers.peek();
        if (buffer != null) {
            buffer.mark();
        }
    }

    @Override
    public void reset() {
        if (!this.marked) {
            throw new InvalidMarkException();
        }
        ReadableBuffer buffer = this.readableBuffers.peek();
        if (buffer != null) {
            int currentRemain = buffer.readableBytes();
            buffer.reset();
            this.readableBytes += buffer.readableBytes() - currentRemain;
        }
        while ((buffer = this.rewindableBuffers.pollLast()) != null) {
            buffer.reset();
            this.readableBuffers.addFirst(buffer);
            this.readableBytes += buffer.readableBytes();
        }
    }

    @Override
    public boolean byteBufferSupported() {
        for (ReadableBuffer buffer : this.readableBuffers) {
            if (buffer.byteBufferSupported()) continue;
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public ByteBuffer getByteBuffer() {
        if (this.readableBuffers.isEmpty()) {
            return null;
        }
        return this.readableBuffers.peek().getByteBuffer();
    }

    @Override
    public void close() {
        while (!this.readableBuffers.isEmpty()) {
            this.readableBuffers.remove().close();
        }
        if (this.rewindableBuffers != null) {
            while (!this.rewindableBuffers.isEmpty()) {
                this.rewindableBuffers.remove().close();
            }
        }
    }

    private <T> int execute(ReadOperation<T> op, int length, T dest, int value) throws IOException {
        this.checkReadable(length);
        if (!this.readableBuffers.isEmpty()) {
            this.advanceBufferIfNecessary();
        }
        while (length > 0 && !this.readableBuffers.isEmpty()) {
            ReadableBuffer buffer = this.readableBuffers.peek();
            int lengthToCopy = Math.min(length, buffer.readableBytes());
            value = op.read(buffer, lengthToCopy, dest, value);
            length -= lengthToCopy;
            this.readableBytes -= lengthToCopy;
            this.advanceBufferIfNecessary();
        }
        if (length > 0) {
            throw new AssertionError((Object)"Failed executing read operation");
        }
        return value;
    }

    private <T> int executeNoThrow(NoThrowReadOperation<T> op, int length, T dest, int value) {
        try {
            return this.execute(op, length, dest, value);
        }
        catch (IOException e) {
            throw new AssertionError((Object)e);
        }
    }

    private void advanceBufferIfNecessary() {
        ReadableBuffer buffer = this.readableBuffers.peek();
        if (buffer.readableBytes() == 0) {
            this.advanceBuffer();
        }
    }

    private void advanceBuffer() {
        if (this.marked) {
            this.rewindableBuffers.add(this.readableBuffers.remove());
            ReadableBuffer next = this.readableBuffers.peek();
            if (next != null) {
                next.mark();
            }
        } else {
            this.readableBuffers.remove().close();
        }
    }

    private static interface NoThrowReadOperation<T>
    extends ReadOperation<T> {
        @Override
        public int read(ReadableBuffer var1, int var2, T var3, int var4);
    }

    private static interface ReadOperation<T> {
        public int read(ReadableBuffer var1, int var2, T var3, int var4) throws IOException;
    }
}

