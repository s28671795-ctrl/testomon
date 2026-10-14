/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;
import org.fusesource.hawtbuf.AbstractVarIntSupport;
import org.fusesource.hawtbuf.Buffer;

public final class DataByteArrayInputStream
extends InputStream
implements DataInput {
    private byte[] buf;
    private int pos;
    private int offset;
    private int length;
    protected AbstractVarIntSupport helper = new AbstractVarIntSupport(){

        protected byte readByte() throws IOException {
            return DataByteArrayInputStream.this.readByte();
        }

        protected void writeByte(int value) throws IOException {
            throw new UnsupportedOperationException();
        }
    };

    public DataByteArrayInputStream(byte[] buf) {
        this.restart(buf);
    }

    public DataByteArrayInputStream(Buffer buffer) {
        this.restart(buffer);
    }

    public void restart(Buffer buffer) {
        this.buf = buffer.getData();
        this.pos = this.offset = buffer.getOffset();
        this.length = buffer.getLength();
    }

    public void restart(int size) {
        if (this.buf == null || this.buf.length < size) {
            this.buf = new byte[size];
        }
        this.restart(this.buf);
        this.length = size;
    }

    public DataByteArrayInputStream() {
        this(new byte[0]);
    }

    public int size() {
        return this.pos - this.offset;
    }

    public byte[] getRawData() {
        return this.buf;
    }

    public Buffer readBuffer(int len) {
        int endpos = this.offset + this.length;
        if (this.pos > endpos) {
            return null;
        }
        if (this.pos + len > endpos) {
            len = this.length - this.pos;
        }
        Buffer rc = new Buffer(this.buf, this.pos, len);
        this.pos += len;
        return rc;
    }

    public void restart(byte[] newBuff) {
        this.buf = newBuff;
        this.pos = 0;
        this.length = newBuff.length;
    }

    public void restart() {
        this.pos = 0;
        this.length = this.buf.length;
    }

    public int read() {
        return this.pos < this.offset + this.length ? this.buf[this.pos++] & 0xFF : -1;
    }

    public int read(byte[] b, int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        }
        int endpos = this.offset + this.length;
        if (this.pos >= endpos) {
            return -1;
        }
        if (this.pos + len > endpos) {
            len = this.length - this.pos;
        }
        if (len <= 0) {
            return 0;
        }
        System.arraycopy(this.buf, this.pos, b, off, len);
        this.pos += len;
        return len;
    }

    public int available() {
        return this.offset + this.length - this.pos;
    }

    public void readFully(byte[] b) {
        this.read(b, 0, b.length);
    }

    public void readFully(byte[] b, int off, int len) {
        this.read(b, off, len);
    }

    public int skip(int n) {
        return this.skipBytes(n);
    }

    public int skipBytes(int n) {
        int endpos = this.offset + this.length;
        if (this.pos + n > endpos) {
            n = endpos - this.pos;
        }
        if (n < 0) {
            return 0;
        }
        this.pos += n;
        return n;
    }

    public boolean readBoolean() {
        return this.read() != 0;
    }

    public byte readByte() {
        return (byte)this.read();
    }

    public int readUnsignedByte() {
        return this.read();
    }

    public short readShort() {
        int ch1 = this.read();
        int ch2 = this.read();
        return (short)((ch1 << 8) + (ch2 << 0));
    }

    public int readUnsignedShort() {
        int ch1 = this.read();
        int ch2 = this.read();
        return (ch1 << 8) + (ch2 << 0);
    }

    public char readChar() {
        int ch1 = this.read();
        int ch2 = this.read();
        return (char)((ch1 << 8) + (ch2 << 0));
    }

    public int readInt() {
        int ch1 = this.read();
        int ch2 = this.read();
        int ch3 = this.read();
        int ch4 = this.read();
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
    }

    public long readLong() {
        long rc = ((long)this.buf[this.pos++] << 56) + ((long)(this.buf[this.pos++] & 0xFF) << 48) + ((long)(this.buf[this.pos++] & 0xFF) << 40) + ((long)(this.buf[this.pos++] & 0xFF) << 32);
        return rc + ((long)(this.buf[this.pos++] & 0xFF) << 24) + (long)((this.buf[this.pos++] & 0xFF) << 16) + (long)((this.buf[this.pos++] & 0xFF) << 8) + (long)((this.buf[this.pos++] & 0xFF) << 0);
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    public String readLine() {
        int c;
        int start2 = this.pos;
        while (this.pos < this.offset + this.length && (c = this.read()) != 10) {
            if (c != 13) continue;
            c = this.read();
            if (c == 10 || c == -1) break;
            --this.pos;
            break;
        }
        return new String(this.buf, start2, this.pos);
    }

    public String readUTF() throws IOException {
        int c;
        int length = this.readUnsignedShort();
        char[] characters = new char[length];
        int count = 0;
        int total = this.pos + length;
        while (this.pos < total && (c = this.buf[this.pos] & 0xFF) <= 127) {
            ++this.pos;
            characters[count++] = (char)c;
        }
        block6: while (this.pos < total) {
            c = this.buf[this.pos] & 0xFF;
            switch (c >> 4) {
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: {
                    ++this.pos;
                    characters[count++] = (char)c;
                    continue block6;
                }
                case 12: 
                case 13: {
                    this.pos += 2;
                    if (this.pos > total) {
                        throw new UTFDataFormatException("bad string");
                    }
                    byte c2 = this.buf[this.pos - 1];
                    if ((c2 & 0xC0) != 128) {
                        throw new UTFDataFormatException("bad string");
                    }
                    characters[count++] = (char)((c & 0x1F) << 6 | c2 & 0x3F);
                    continue block6;
                }
                case 14: {
                    this.pos += 3;
                    if (this.pos > total) {
                        throw new UTFDataFormatException("bad string");
                    }
                    byte c2 = this.buf[this.pos - 2];
                    byte c3 = this.buf[this.pos - 1];
                    if ((c2 & 0xC0) != 128 || (c3 & 0xC0) != 128) {
                        throw new UTFDataFormatException("bad string");
                    }
                    characters[count++] = (char)((c & 0xF) << 12 | (c2 & 0x3F) << 6 | (c3 & 0x3F) << 0);
                    continue block6;
                }
            }
            throw new UTFDataFormatException("bad string");
        }
        return new String(characters, 0, count);
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int readVarInt() throws IOException {
        return this.helper.readVarInt();
    }

    public long readVarLong() throws IOException {
        return this.helper.readVarLong();
    }

    public int readVarSignedInt() throws IOException {
        return this.helper.readVarSignedInt();
    }

    public long readVarSignedLong() throws IOException {
        return this.helper.readVarSignedLong();
    }
}

