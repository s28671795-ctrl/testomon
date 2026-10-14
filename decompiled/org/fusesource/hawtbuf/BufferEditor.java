/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import org.fusesource.hawtbuf.AbstractVarIntSupport;
import org.fusesource.hawtbuf.Buffer;

public abstract class BufferEditor
extends AbstractVarIntSupport {
    protected final Buffer buffer;

    private BufferEditor(Buffer buffer) {
        this.buffer = buffer;
    }

    protected boolean hasCapacity(int len) {
        return this.buffer.length >= len;
    }

    public int read() {
        --this.buffer.length;
        return this.buffer.data[this.buffer.offset++] & 0xFF;
    }

    public void readFully(byte[] b) {
        this.readFully(b, 0, b.length);
    }

    public void readFully(byte[] b, int off, int len) {
        assert (this.hasCapacity(len));
        System.arraycopy(this.buffer.data, this.buffer.offset, b, off, len);
        this.buffer.offset += len;
        this.buffer.length -= len;
    }

    public int skipBytes(int n) {
        int len = Math.min(n, this.buffer.length);
        this.buffer.offset += len;
        this.buffer.length -= len;
        return len;
    }

    public boolean readBoolean() {
        assert (this.hasCapacity(1));
        return this.read() != 0;
    }

    public byte readByte() {
        assert (this.hasCapacity(1));
        return (byte)this.read();
    }

    public int readUnsignedByte() {
        assert (this.hasCapacity(1));
        return this.read();
    }

    public void write(int b) {
        assert (this.hasCapacity(1));
        this.buffer.data[this.buffer.offset++] = (byte)b;
        --this.buffer.length;
    }

    public void write(byte[] b) {
        this.write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        assert (this.hasCapacity(len));
        System.arraycopy(b, off, this.buffer.data, this.buffer.offset, len);
        this.buffer.offset += len;
        this.buffer.length -= len;
    }

    public void writeBoolean(boolean v) {
        assert (this.hasCapacity(1));
        this.write(v ? 1 : 0);
    }

    public void writeByte(int v) {
        assert (this.hasCapacity(1));
        this.write(v);
    }

    public abstract short readShort();

    public abstract int readUnsignedShort();

    public abstract char readChar();

    public abstract int readInt();

    public abstract long readLong();

    public abstract double readDouble();

    public abstract float readFloat();

    public abstract void writeShort(int var1);

    public abstract void writeChar(int var1);

    public abstract void writeInt(int var1);

    public abstract void writeLong(long var1);

    public abstract void writeDouble(double var1);

    public abstract void writeFloat(float var1);

    public abstract void writeRawDouble(double var1);

    public abstract void writeRawFloat(float var1);

    public static BufferEditor big(Buffer buffer) {
        return new BigEndianBufferEditor(buffer);
    }

    public static BufferEditor little(Buffer buffer) {
        return new LittleEndianBufferEditor(buffer);
    }

    static class LittleEndianBufferEditor
    extends BufferEditor {
        LittleEndianBufferEditor(Buffer buffer) {
            super(buffer);
        }

        public short readShort() {
            assert (this.hasCapacity(2));
            return (short)((this.read() << 0) + (this.read() << 8));
        }

        public int readUnsignedShort() {
            assert (this.hasCapacity(2));
            return (this.read() << 0) + (this.read() << 8);
        }

        public char readChar() {
            assert (this.hasCapacity(2));
            return (char)((this.read() << 0) + (this.read() << 8));
        }

        public int readInt() {
            assert (this.hasCapacity(4));
            return (this.read() << 0) + (this.read() << 8) + (this.read() << 16) + (this.read() << 24);
        }

        public long readLong() {
            assert (this.hasCapacity(8));
            return (long)((this.read() << 0) + (this.read() << 8) + (this.read() << 16)) + ((long)this.read() << 24) + ((long)this.read() << 32) + ((long)this.read() << 40) + ((long)this.read() << 48) + ((long)this.read() << 56);
        }

        public double readDouble() {
            return Double.longBitsToDouble(this.readLong());
        }

        public float readFloat() {
            return Float.intBitsToFloat(this.readInt());
        }

        public void writeShort(int v) {
            assert (this.hasCapacity(2));
            this.write(v >>> 0 & 0xFF);
            this.write(v >>> 8 & 0xFF);
        }

        public void writeChar(int v) {
            assert (this.hasCapacity(2));
            this.write(v >>> 0 & 0xFF);
            this.write(v >>> 8 & 0xFF);
        }

        public void writeInt(int v) {
            assert (this.hasCapacity(4));
            this.write(v >>> 0 & 0xFF);
            this.write(v >>> 8 & 0xFF);
            this.write(v >>> 16 & 0xFF);
            this.write(v >>> 24 & 0xFF);
        }

        public void writeLong(long v) {
            assert (this.hasCapacity(8));
            this.write((int)(v >>> 0) & 0xFF);
            this.write((int)(v >>> 8) & 0xFF);
            this.write((int)(v >>> 16) & 0xFF);
            this.write((int)(v >>> 24) & 0xFF);
            this.write((int)(v >>> 32) & 0xFF);
            this.write((int)(v >>> 40) & 0xFF);
            this.write((int)(v >>> 48) & 0xFF);
            this.write((int)(v >>> 56) & 0xFF);
        }

        public void writeDouble(double v) {
            this.writeLong(Double.doubleToLongBits(v));
        }

        public void writeFloat(float v) {
            this.writeInt(Float.floatToIntBits(v));
        }

        public void writeRawDouble(double v) {
            this.writeLong(Double.doubleToRawLongBits(v));
        }

        public void writeRawFloat(float v) {
            this.writeInt(Float.floatToRawIntBits(v));
        }
    }

    static class BigEndianBufferEditor
    extends BufferEditor {
        BigEndianBufferEditor(Buffer buffer) {
            super(buffer);
        }

        public short readShort() {
            assert (this.hasCapacity(2));
            return (short)((this.read() << 8) + (this.read() << 0));
        }

        public int readUnsignedShort() {
            assert (this.hasCapacity(2));
            return (this.read() << 8) + (this.read() << 0);
        }

        public char readChar() {
            assert (this.hasCapacity(2));
            return (char)((this.read() << 8) + (this.read() << 0));
        }

        public int readInt() {
            assert (this.hasCapacity(4));
            return (this.read() << 24) + (this.read() << 16) + (this.read() << 8) + (this.read() << 0);
        }

        public long readLong() {
            assert (this.hasCapacity(8));
            return ((long)this.read() << 56) + ((long)this.read() << 48) + ((long)this.read() << 40) + ((long)this.read() << 32) + ((long)this.read() << 24) + (long)(this.read() << 16) + (long)(this.read() << 8) + (long)(this.read() << 0);
        }

        public double readDouble() {
            return Double.longBitsToDouble(this.readLong());
        }

        public float readFloat() {
            return Float.intBitsToFloat(this.readInt());
        }

        public void writeShort(int v) {
            assert (this.hasCapacity(2));
            this.write(v >>> 8 & 0xFF);
            this.write(v >>> 0 & 0xFF);
        }

        public void writeChar(int v) {
            assert (this.hasCapacity(2));
            this.write(v >>> 8 & 0xFF);
            this.write(v >>> 0 & 0xFF);
        }

        public void writeInt(int v) {
            assert (this.hasCapacity(4));
            this.write(v >>> 24 & 0xFF);
            this.write(v >>> 16 & 0xFF);
            this.write(v >>> 8 & 0xFF);
            this.write(v >>> 0 & 0xFF);
        }

        public void writeLong(long v) {
            assert (this.hasCapacity(8));
            this.write((int)(v >>> 56) & 0xFF);
            this.write((int)(v >>> 48) & 0xFF);
            this.write((int)(v >>> 40) & 0xFF);
            this.write((int)(v >>> 32) & 0xFF);
            this.write((int)(v >>> 24) & 0xFF);
            this.write((int)(v >>> 16) & 0xFF);
            this.write((int)(v >>> 8) & 0xFF);
            this.write((int)(v >>> 0) & 0xFF);
        }

        public void writeDouble(double v) {
            this.writeLong(Double.doubleToLongBits(v));
        }

        public void writeFloat(float v) {
            this.writeInt(Float.floatToIntBits(v));
        }

        public void writeRawDouble(double v) {
            this.writeLong(Double.doubleToRawLongBits(v));
        }

        public void writeRawFloat(float v) {
            this.writeInt(Float.floatToRawIntBits(v));
        }
    }
}

