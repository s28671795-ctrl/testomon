/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.MarshallingSupport;

public final class DataByteArrayOutputStream
extends OutputStream
implements DataOutput {
    private static final int DEFAULT_SIZE = 2048;
    private byte[] buf;
    private int pos;

    public DataByteArrayOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Invalid size: " + size);
        }
        this.buf = new byte[size];
    }

    public DataByteArrayOutputStream() {
        this(2048);
    }

    public void restart(int size) {
        this.buf = new byte[size];
        this.pos = 0;
    }

    public void restart() {
        this.restart(2048);
    }

    public ByteSequence toByteSequence() {
        return new ByteSequence(this.buf, 0, this.pos);
    }

    @Override
    public void write(int b) {
        int newcount = this.pos + 1;
        this.ensureEnoughBuffer(newcount);
        this.buf[this.pos] = (byte)b;
        this.pos = newcount;
    }

    @Override
    public void write(byte[] b, int off, int len) {
        if (len == 0) {
            return;
        }
        int newcount = this.pos + len;
        this.ensureEnoughBuffer(newcount);
        System.arraycopy(b, off, this.buf, this.pos, len);
        this.pos = newcount;
    }

    public byte[] getData() {
        return this.buf;
    }

    public void reset() {
        this.pos = 0;
    }

    public void position(int offset) {
        this.ensureEnoughBuffer(offset);
        this.pos = offset;
    }

    public int size() {
        return this.pos;
    }

    @Override
    public void writeBoolean(boolean v) {
        this.ensureEnoughBuffer(this.pos + 1);
        this.buf[this.pos++] = (byte)(v ? 1 : 0);
    }

    @Override
    public void writeByte(int v) {
        this.ensureEnoughBuffer(this.pos + 1);
        this.buf[this.pos++] = (byte)(v >>> 0);
    }

    @Override
    public void writeShort(int v) {
        this.ensureEnoughBuffer(this.pos + 2);
        this.buf[this.pos++] = (byte)(v >>> 8);
        this.buf[this.pos++] = (byte)(v >>> 0);
    }

    @Override
    public void writeChar(int v) {
        this.ensureEnoughBuffer(this.pos + 2);
        this.buf[this.pos++] = (byte)(v >>> 8);
        this.buf[this.pos++] = (byte)(v >>> 0);
    }

    @Override
    public void writeInt(int v) {
        this.ensureEnoughBuffer(this.pos + 4);
        this.buf[this.pos++] = (byte)(v >>> 24);
        this.buf[this.pos++] = (byte)(v >>> 16);
        this.buf[this.pos++] = (byte)(v >>> 8);
        this.buf[this.pos++] = (byte)(v >>> 0);
    }

    @Override
    public void writeLong(long v) {
        this.ensureEnoughBuffer(this.pos + 8);
        this.buf[this.pos++] = (byte)(v >>> 56);
        this.buf[this.pos++] = (byte)(v >>> 48);
        this.buf[this.pos++] = (byte)(v >>> 40);
        this.buf[this.pos++] = (byte)(v >>> 32);
        this.buf[this.pos++] = (byte)(v >>> 24);
        this.buf[this.pos++] = (byte)(v >>> 16);
        this.buf[this.pos++] = (byte)(v >>> 8);
        this.buf[this.pos++] = (byte)(v >>> 0);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        this.writeInt(Float.floatToIntBits(v));
    }

    @Override
    public void writeDouble(double v) throws IOException {
        this.writeLong(Double.doubleToLongBits(v));
    }

    @Override
    public void writeBytes(String s2) {
        int length = s2.length();
        for (int i = 0; i < length; ++i) {
            this.write((byte)s2.charAt(i));
        }
    }

    @Override
    public void writeChars(String s2) {
        int length = s2.length();
        for (int i = 0; i < length; ++i) {
            char c = s2.charAt(i);
            this.write(c >>> 8 & 0xFF);
            this.write(c >>> 0 & 0xFF);
        }
    }

    @Override
    public void writeUTF(String text) throws IOException {
        long encodedsize = MarshallingSupport.countUTFBytes(text);
        if (encodedsize > 65535L) {
            throw new UTFDataFormatException("encoded string too long: " + encodedsize + " bytes");
        }
        this.ensureEnoughBuffer((int)((long)this.pos + encodedsize + 2L));
        this.writeShort((int)encodedsize);
        byte[] buffer = new byte[(int)encodedsize];
        MarshallingSupport.writeUTFBytesToBuffer(text, (int)encodedsize, this.buf, this.pos);
        this.pos = (int)((long)this.pos + encodedsize);
    }

    private void ensureEnoughBuffer(int newcount) {
        if (newcount > this.buf.length) {
            byte[] newbuf = new byte[Math.max(this.buf.length << 1, newcount)];
            System.arraycopy(this.buf, 0, newbuf, 0, this.pos);
            this.buf = newbuf;
        }
    }
}

