/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.MarshallingSupport;

public final class DataByteArrayInputStream
extends InputStream
implements DataInput {
    private byte[] buf;
    private int pos;
    private int offset;

    public DataByteArrayInputStream(byte[] buf) {
        this.buf = buf;
        this.pos = 0;
        this.offset = 0;
    }

    public DataByteArrayInputStream(ByteSequence sequence2) {
        this.buf = sequence2.getData();
        this.pos = this.offset = sequence2.getOffset();
    }

    public DataByteArrayInputStream() {
        this(new byte[0]);
    }

    public int size() {
        return this.pos - this.offset;
    }

    public int position() {
        return this.pos;
    }

    public byte[] getRawData() {
        return this.buf;
    }

    public void restart(byte[] newBuff) {
        this.buf = newBuff;
        this.pos = 0;
    }

    public void restart(ByteSequence sequence2) {
        this.buf = sequence2.getData();
        this.pos = sequence2.getOffset();
    }

    public void restart(int size) {
        if (this.buf == null || this.buf.length < size) {
            this.buf = new byte[size];
        }
        this.restart(this.buf);
    }

    @Override
    public int read() {
        return this.pos < this.buf.length ? this.buf[this.pos++] & 0xFF : -1;
    }

    public int readOrIOException() throws IOException {
        int rc = this.read();
        if (rc == -1) {
            throw new EOFException();
        }
        return rc;
    }

    @Override
    public int read(byte[] b, int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        }
        if (this.pos >= this.buf.length) {
            return -1;
        }
        if (this.pos + len > this.buf.length) {
            len = this.buf.length - this.pos;
        }
        if (len <= 0) {
            return 0;
        }
        System.arraycopy(this.buf, this.pos, b, off, len);
        this.pos += len;
        return len;
    }

    @Override
    public int available() {
        return this.buf.length - this.pos;
    }

    @Override
    public void readFully(byte[] b) {
        this.read(b, 0, b.length);
    }

    @Override
    public void readFully(byte[] b, int off, int len) {
        this.read(b, off, len);
    }

    @Override
    public int skipBytes(int n) {
        if (this.pos + n > this.buf.length) {
            n = this.buf.length - this.pos;
        }
        if (n < 0) {
            return 0;
        }
        this.pos += n;
        return n;
    }

    @Override
    public boolean readBoolean() throws IOException {
        return this.readOrIOException() != 0;
    }

    @Override
    public byte readByte() throws IOException {
        return (byte)this.readOrIOException();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return this.readOrIOException();
    }

    @Override
    public short readShort() throws IOException {
        int ch1 = this.readOrIOException();
        int ch2 = this.readOrIOException();
        return (short)((ch1 << 8) + (ch2 << 0));
    }

    @Override
    public int readUnsignedShort() throws IOException {
        int ch1 = this.readOrIOException();
        int ch2 = this.readOrIOException();
        return (ch1 << 8) + (ch2 << 0);
    }

    @Override
    public char readChar() throws IOException {
        int ch1 = this.readOrIOException();
        int ch2 = this.readOrIOException();
        return (char)((ch1 << 8) + (ch2 << 0));
    }

    @Override
    public int readInt() throws IOException {
        int ch1 = this.readOrIOException();
        int ch2 = this.readOrIOException();
        int ch3 = this.readOrIOException();
        int ch4 = this.readOrIOException();
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
    }

    @Override
    public long readLong() throws IOException {
        if (this.pos + 8 > this.buf.length) {
            throw new EOFException();
        }
        long rc = ((long)this.buf[this.pos++] << 56) + ((long)(this.buf[this.pos++] & 0xFF) << 48) + ((long)(this.buf[this.pos++] & 0xFF) << 40) + ((long)(this.buf[this.pos++] & 0xFF) << 32);
        return rc + ((long)(this.buf[this.pos++] & 0xFF) << 24) + (long)((this.buf[this.pos++] & 0xFF) << 16) + (long)((this.buf[this.pos++] & 0xFF) << 8) + (long)((this.buf[this.pos++] & 0xFF) << 0);
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public String readLine() {
        int c;
        int start2 = this.pos;
        while (this.pos < this.buf.length && (c = this.read()) != 10) {
            if (c != 13) continue;
            c = this.read();
            if (c == 10 || c == -1) break;
            --this.pos;
            break;
        }
        return new String(this.buf, start2, this.pos);
    }

    @Override
    public String readUTF() throws IOException {
        int length = this.readUnsignedShort();
        if (this.pos + length > this.buf.length) {
            throw new UTFDataFormatException("bad string");
        }
        char[] chararr = new char[length];
        String result = MarshallingSupport.convertUTF8WithBuf(this.buf, chararr, this.pos, length);
        this.pos += length;
        return result;
    }
}

