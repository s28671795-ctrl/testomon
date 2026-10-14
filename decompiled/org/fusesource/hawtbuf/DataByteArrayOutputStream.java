/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import org.fusesource.hawtbuf.AbstractVarIntSupport;
import org.fusesource.hawtbuf.Buffer;

public class DataByteArrayOutputStream
extends OutputStream
implements DataOutput {
    private static final int DEFAULT_SIZE = 2048;
    protected byte[] buf;
    protected int pos;
    protected AbstractVarIntSupport helper = new AbstractVarIntSupport(){

        protected byte readByte() {
            throw new UnsupportedOperationException();
        }

        protected void writeByte(int value) throws IOException {
            DataByteArrayOutputStream.this.writeByte(value);
        }
    };

    public DataByteArrayOutputStream(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid size: " + size);
        }
        this.buf = new byte[size];
    }

    public DataByteArrayOutputStream(byte[] buf) {
        if (buf == null || buf.length == 0) {
            throw new IllegalArgumentException("Invalid buffer");
        }
        this.buf = buf;
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

    public Buffer toBuffer() {
        return new Buffer(this.buf, 0, this.pos);
    }

    public void write(int b) throws IOException {
        int newcount = this.pos + 1;
        this.ensureEnoughBuffer(newcount);
        this.buf[this.pos] = (byte)b;
        this.pos = newcount;
        this.onWrite();
    }

    public void write(Buffer data) throws IOException {
        this.write(data.data, data.offset, data.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (len == 0) {
            return;
        }
        int newcount = this.pos + len;
        this.ensureEnoughBuffer(newcount);
        System.arraycopy(b, off, this.buf, this.pos, len);
        this.pos = newcount;
        this.onWrite();
    }

    public byte[] getData() {
        return this.buf;
    }

    public void reset() {
        this.pos = 0;
    }

    public void position(int offset) throws IOException {
        this.ensureEnoughBuffer(offset);
        this.pos = offset;
        this.onWrite();
    }

    public int position() {
        return this.pos;
    }

    public int size() {
        return this.pos;
    }

    public void writeBoolean(boolean v) throws IOException {
        this.ensureEnoughBuffer(this.pos + 1);
        this.buf[this.pos++] = (byte)(v ? 1 : 0);
        this.onWrite();
    }

    public void writeByte(int v) throws IOException {
        this.ensureEnoughBuffer(this.pos + 1);
        this.buf[this.pos++] = (byte)(v >>> 0);
        this.onWrite();
    }

    public void writeShort(int v) throws IOException {
        this.ensureEnoughBuffer(this.pos + 2);
        this.buf[this.pos++] = (byte)(v >>> 8);
        this.buf[this.pos++] = (byte)(v >>> 0);
        this.onWrite();
    }

    public void writeChar(int v) throws IOException {
        this.ensureEnoughBuffer(this.pos + 2);
        this.buf[this.pos++] = (byte)(v >>> 8);
        this.buf[this.pos++] = (byte)(v >>> 0);
        this.onWrite();
    }

    public void writeInt(int v) throws IOException {
        this.ensureEnoughBuffer(this.pos + 4);
        this.buf[this.pos++] = (byte)(v >>> 24);
        this.buf[this.pos++] = (byte)(v >>> 16);
        this.buf[this.pos++] = (byte)(v >>> 8);
        this.buf[this.pos++] = (byte)(v >>> 0);
        this.onWrite();
    }

    public void writeLong(long v) throws IOException {
        this.ensureEnoughBuffer(this.pos + 8);
        this.buf[this.pos++] = (byte)(v >>> 56);
        this.buf[this.pos++] = (byte)(v >>> 48);
        this.buf[this.pos++] = (byte)(v >>> 40);
        this.buf[this.pos++] = (byte)(v >>> 32);
        this.buf[this.pos++] = (byte)(v >>> 24);
        this.buf[this.pos++] = (byte)(v >>> 16);
        this.buf[this.pos++] = (byte)(v >>> 8);
        this.buf[this.pos++] = (byte)(v >>> 0);
        this.onWrite();
    }

    public void writeFloat(float v) throws IOException {
        this.writeInt(Float.floatToIntBits(v));
    }

    public void writeDouble(double v) throws IOException {
        this.writeLong(Double.doubleToLongBits(v));
    }

    public void writeBytes(String s2) throws IOException {
        int length = s2.length();
        for (int i = 0; i < length; ++i) {
            this.write((byte)s2.charAt(i));
        }
    }

    public void writeChars(String s2) throws IOException {
        int length = s2.length();
        for (int i = 0; i < length; ++i) {
            char c = s2.charAt(i);
            this.write(c >>> 8 & 0xFF);
            this.write(c >>> 0 & 0xFF);
        }
    }

    public void writeUTF(String str) throws IOException {
        char c;
        int i;
        int strlen = str.length();
        int encodedsize = 0;
        for (i = 0; i < strlen; ++i) {
            c = str.charAt(i);
            if (c >= '\u0001' && c <= '\u007f') {
                ++encodedsize;
                continue;
            }
            if (c > '\u07ff') {
                encodedsize += 3;
                continue;
            }
            encodedsize += 2;
        }
        if (encodedsize > 65535) {
            throw new UTFDataFormatException("encoded string too long: " + encodedsize + " bytes");
        }
        this.ensureEnoughBuffer(this.pos + encodedsize + 2);
        this.writeShort(encodedsize);
        i = 0;
        for (i = 0; i < strlen && (c = str.charAt(i)) >= '\u0001' && c <= '\u007f'; ++i) {
            this.buf[this.pos++] = (byte)c;
        }
        while (i < strlen) {
            c = str.charAt(i);
            if (c >= '\u0001' && c <= '\u007f') {
                this.buf[this.pos++] = (byte)c;
            } else if (c > '\u07ff') {
                this.buf[this.pos++] = (byte)(0xE0 | c >> 12 & 0xF);
                this.buf[this.pos++] = (byte)(0x80 | c >> 6 & 0x3F);
                this.buf[this.pos++] = (byte)(0x80 | c >> 0 & 0x3F);
            } else {
                this.buf[this.pos++] = (byte)(0xC0 | c >> 6 & 0x1F);
                this.buf[this.pos++] = (byte)(0x80 | c >> 0 & 0x3F);
            }
            ++i;
        }
        this.onWrite();
    }

    private void ensureEnoughBuffer(int newcount) {
        if (newcount > this.buf.length) {
            this.resize(newcount);
        }
    }

    protected void resize(int newcount) {
        byte[] newbuf = new byte[Math.max(this.buf.length << 1, newcount)];
        System.arraycopy(this.buf, 0, newbuf, 0, this.pos);
        this.buf = newbuf;
    }

    protected void onWrite() throws IOException {
    }

    public void skip(int size) throws IOException {
        this.ensureEnoughBuffer(this.pos + size);
        this.pos += size;
        this.onWrite();
    }

    public void writeVarInt(int value) throws IOException {
        this.helper.writeVarInt(value);
    }

    public void writeVarLong(long value) throws IOException {
        this.helper.writeVarLong(value);
    }

    public void writeVarSignedInt(int value) throws IOException {
        this.helper.writeVarSignedInt(value);
    }

    public void writeVarSignedLong(long value) throws IOException {
        this.helper.writeVarSignedLong(value);
    }
}

