/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class IMAPResponseBuffer
extends ByteArrayOutputStream {
    public int read(int index) {
        if (index >= this.size()) {
            return -1;
        }
        return this.buf[index];
    }

    public int read(byte[] buffer, int offset, int length) {
        int available = this.size() - offset;
        if ((length = Math.min(length, available)) <= 0) {
            return 0;
        }
        System.arraycopy(this.buf, offset, buffer, 0, length);
        return length;
    }

    public int lastIndex(byte target) {
        for (int i = this.size() - 1; i > 0; --i) {
            if (this.buf[i] != target) continue;
            return i;
        }
        return -1;
    }

    public int lastByte() {
        if (this.size() > 0) {
            return this.buf[this.size() - 1];
        }
        return -1;
    }

    public int getLiteralLength() {
        if (this.lastByte() == 125) {
            int literalStart = this.lastIndex((byte)123);
            if (literalStart == -1) {
                return -1;
            }
            try {
                String lenString = new String(this.buf, literalStart + 1, this.size() - (literalStart + 2), "US-ASCII");
                try {
                    return Integer.parseInt(lenString);
                }
                catch (NumberFormatException numberFormatException) {
                }
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        return -1;
    }
}

