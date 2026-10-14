/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class MimeBodyPartInputStream
extends InputStream {
    PushbackInputStream inStream;
    boolean boundaryFound;
    int pbAmount;
    byte[] boundary;
    byte[] boundaryBuffer;
    private boolean closed;

    public MimeBodyPartInputStream(PushbackInputStream inStreamParam, byte[] boundaryParam, int pbsize) {
        this.inStream = inStreamParam;
        this.boundary = boundaryParam;
        this.pbAmount = pbsize;
    }

    @Override
    public int read(byte[] buf, int origOff, int origLen) throws IOException {
        byte[] b = buf;
        int off = origOff;
        int len = origLen;
        if (this.boundaryFound || this.closed) {
            return -1;
        }
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return 0;
        }
        boolean bufferCreated = false;
        if (len < this.boundary.length * 2) {
            bufferCreated = true;
            if (this.boundaryBuffer == null) {
                this.boundaryBuffer = new byte[this.boundary.length * 2];
            }
            b = this.boundaryBuffer;
            off = 0;
            len = this.boundaryBuffer.length;
        }
        if (len > this.pbAmount) {
            len = this.pbAmount;
        }
        int read = 0;
        int idx = 0;
        while (read >= 0 && idx < len && idx < this.boundary.length * 2) {
            read = this.inStream.read(b, off + idx, len - idx);
            if (read == -1) continue;
            idx += read;
        }
        if (read == -1 && idx == 0) {
            return -1;
        }
        len = idx;
        int i = this.processBuffer(b, off, len);
        if (bufferCreated && i > 0) {
            if (origLen >= i) {
                System.arraycopy(b, 0, buf, origOff, i);
            } else {
                System.arraycopy(b, 0, buf, origOff, origLen);
                this.inStream.unread(b, origLen, i - origLen);
                i = origLen;
            }
        } else if (i == 0 && this.boundaryFound) {
            return -1;
        }
        return i;
    }

    private boolean hasData(byte[] b, int initialPointer, int pointer, int off, int len) throws IOException {
        if (pointer < off + len) {
            return true;
        }
        if (pointer >= 1000000000) {
            this.inStream.unread(b, initialPointer, off + len - initialPointer);
            return false;
        }
        int x = this.inStream.read();
        if (x != -1) {
            this.inStream.unread(x);
            this.inStream.unread(b, initialPointer, off + len - initialPointer);
            return false;
        }
        return true;
    }

    protected int processBuffer(byte[] buffer, int off, int len) throws IOException {
        for (int i = off; i < off + len; ++i) {
            int boundaryIndex;
            boolean needUnread0d0a = false;
            byte value = buffer[i];
            int initialI = i++;
            if (value == 13) {
                if (!this.hasData(buffer, initialI, initialI + 1, off, len)) {
                    return initialI - off;
                }
                value = buffer[initialI + 1];
                if (value != 10) continue;
                if (!this.hasData(buffer, initialI, initialI + 2, off, len)) {
                    return initialI - off;
                }
                value = buffer[initialI + 2];
                if ((byte)value != this.boundary[0]) continue;
                needUnread0d0a = true;
                i += 2;
            } else if (value != this.boundary[0]) continue;
            for (boundaryIndex = 0; boundaryIndex < this.boundary.length && value == this.boundary[boundaryIndex]; ++boundaryIndex) {
                if (!this.hasData(buffer, initialI, i + 1, off, len)) {
                    return initialI - off;
                }
                value = buffer[++i];
            }
            if (boundaryIndex == this.boundary.length) {
                if (initialI != off) {
                    i = 1000000000;
                }
                if (initialI - off != 0 && !this.hasData(buffer, initialI, i + 1, off, len)) {
                    return initialI - off;
                }
                this.boundaryFound = true;
                int j = i + 1;
                if (j < len && buffer[j] == 45 && value == 45) {
                    i += 2;
                }
                int processed = initialI - off;
                if (len - (i - off + 2) > 0) {
                    this.inStream.unread(buffer, i + 2, len - (i + 2) + off);
                }
                return processed;
            }
            if (value != -1) {
                --i;
            }
            if (needUnread0d0a) {
                i -= boundaryIndex;
                --i;
                continue;
            }
            i -= boundaryIndex;
            ++i;
        }
        return len;
    }

    @Override
    public int read() throws IOException {
        int boundaryIndex;
        boolean needUnread0d0a = false;
        if (this.boundaryFound) {
            return -1;
        }
        int value = this.inStream.read();
        if (value == 13) {
            value = this.inStream.read();
            if (value != 10) {
                this.inStream.unread(value);
                return 13;
            }
            value = this.inStream.read();
            if ((byte)value != this.boundary[0]) {
                this.inStream.unread(value);
                this.inStream.unread(10);
                return 13;
            }
            needUnread0d0a = true;
        } else if ((byte)value != this.boundary[0]) {
            return value;
        }
        for (boundaryIndex = 0; boundaryIndex < this.boundary.length && (byte)value == this.boundary[boundaryIndex]; ++boundaryIndex) {
            value = this.inStream.read();
        }
        if (boundaryIndex == this.boundary.length) {
            this.boundaryFound = true;
            int dashNext = this.inStream.read();
            if (dashNext == 45 && value == 45) {
                this.inStream.read();
                this.inStream.read();
            }
            return -1;
        }
        if (value != -1) {
            this.inStream.unread(value);
        }
        if (needUnread0d0a) {
            this.inStream.unread(this.boundary, 0, boundaryIndex);
            this.inStream.unread(10);
            value = 13;
        } else {
            this.inStream.unread(this.boundary, 1, boundaryIndex - 1);
            value = this.boundary[0];
        }
        return value;
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }
}

