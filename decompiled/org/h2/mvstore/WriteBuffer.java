/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.nio.ByteBuffer;
import org.h2.mvstore.DataUtils;

public class WriteBuffer {
    private static final int MAX_REUSE_CAPACITY = 0x400000;
    private static final int MIN_GROW = 0x100000;
    private ByteBuffer reuse;
    private ByteBuffer buff;

    public WriteBuffer(int n) {
        this.buff = this.reuse = ByteBuffer.allocate(n);
    }

    public WriteBuffer() {
        this(0x100000);
    }

    public WriteBuffer putVarInt(int n) {
        DataUtils.writeVarInt(this.ensureCapacity(5), n);
        return this;
    }

    public WriteBuffer putVarLong(long l) {
        DataUtils.writeVarLong(this.ensureCapacity(10), l);
        return this;
    }

    public WriteBuffer putStringData(String string, int n) {
        ByteBuffer byteBuffer = this.ensureCapacity(3 * n);
        DataUtils.writeStringData(byteBuffer, string, n);
        return this;
    }

    public WriteBuffer put(byte by) {
        this.ensureCapacity(1).put(by);
        return this;
    }

    public WriteBuffer putChar(char c) {
        this.ensureCapacity(2).putChar(c);
        return this;
    }

    public WriteBuffer putShort(short s2) {
        this.ensureCapacity(2).putShort(s2);
        return this;
    }

    public WriteBuffer putInt(int n) {
        this.ensureCapacity(4).putInt(n);
        return this;
    }

    public WriteBuffer putLong(long l) {
        this.ensureCapacity(8).putLong(l);
        return this;
    }

    public WriteBuffer putFloat(float f) {
        this.ensureCapacity(4).putFloat(f);
        return this;
    }

    public WriteBuffer putDouble(double d) {
        this.ensureCapacity(8).putDouble(d);
        return this;
    }

    public WriteBuffer put(byte[] byArray) {
        this.ensureCapacity(byArray.length).put(byArray);
        return this;
    }

    public WriteBuffer put(byte[] byArray, int n, int n2) {
        this.ensureCapacity(n2).put(byArray, n, n2);
        return this;
    }

    public WriteBuffer put(ByteBuffer byteBuffer) {
        this.ensureCapacity(byteBuffer.remaining()).put(byteBuffer);
        return this;
    }

    public WriteBuffer limit(int n) {
        this.ensureCapacity(n - this.buff.position()).limit(n);
        return this;
    }

    public int capacity() {
        return this.buff.capacity();
    }

    public WriteBuffer position(int n) {
        this.buff.position(n);
        return this;
    }

    public int limit() {
        return this.buff.limit();
    }

    public int position() {
        return this.buff.position();
    }

    public WriteBuffer get(byte[] byArray) {
        this.buff.get(byArray);
        return this;
    }

    public WriteBuffer putInt(int n, int n2) {
        this.buff.putInt(n, n2);
        return this;
    }

    public WriteBuffer putShort(int n, short s2) {
        this.buff.putShort(n, s2);
        return this;
    }

    public WriteBuffer clear() {
        if (this.buff.limit() > 0x400000) {
            this.buff = this.reuse;
        } else if (this.buff != this.reuse) {
            this.reuse = this.buff;
        }
        this.buff.clear();
        return this;
    }

    public ByteBuffer getBuffer() {
        return this.buff;
    }

    private ByteBuffer ensureCapacity(int n) {
        if (this.buff.remaining() < n) {
            this.grow(n);
        }
        return this.buff;
    }

    private void grow(int n) {
        ByteBuffer byteBuffer = this.buff;
        int n2 = n - byteBuffer.remaining();
        long l = Math.max(n2, 0x100000);
        l = Math.max((long)(byteBuffer.capacity() / 2), l);
        int n3 = (int)Math.min(Integer.MAX_VALUE, (long)byteBuffer.capacity() + l);
        if (n3 < n2) {
            throw new OutOfMemoryError("Capacity: " + n3 + " needed: " + n2);
        }
        try {
            this.buff = ByteBuffer.allocate(n3);
        }
        catch (OutOfMemoryError outOfMemoryError) {
            throw new OutOfMemoryError("Capacity: " + n3);
        }
        byteBuffer.flip();
        this.buff.put(byteBuffer);
        if (n3 <= 0x400000) {
            this.reuse = this.buff;
        }
    }
}

