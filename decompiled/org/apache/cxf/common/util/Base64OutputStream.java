/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.apache.cxf.common.util.Base64Utility;

public class Base64OutputStream
extends FilterOutputStream {
    private byte[] lastChunk;
    private boolean flushed;
    private boolean urlSafe;

    public Base64OutputStream(OutputStream out, boolean urlSafe) {
        super(out);
        this.urlSafe = urlSafe;
    }

    @Override
    public void write(int value) throws IOException {
        byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
        this.write(bytes, 0, bytes.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.encodeAndWrite(b, off, len, false);
    }

    private void encodeAndWrite(byte[] b, int off, int len, boolean finalWrite) throws IOException {
        byte[] theChunk = this.lastChunk;
        int lenToEncode = len;
        if (theChunk != null) {
            theChunk = this.newArray(theChunk, 0, theChunk.length, b, off, len);
            lenToEncode = theChunk.length;
            off = 0;
        } else {
            theChunk = b;
        }
        int rem = finalWrite ? 0 : lenToEncode % 3;
        Base64Utility.encodeAndStream(theChunk, off, lenToEncode - rem, this.urlSafe, this.out);
        this.lastChunk = (byte[])(rem > 0 ? this.newArray(theChunk, lenToEncode - rem, rem) : null);
    }

    @Override
    public void flush() throws IOException {
        if (this.flushed || this.lastChunk == null) {
            return;
        }
        try {
            Base64Utility.encodeAndStream(this.lastChunk, 0, this.lastChunk.length, this.urlSafe, this.out);
            this.lastChunk = null;
        }
        catch (Exception ex) {
            throw new IOException(ex);
        }
        this.flushed = true;
    }

    private byte[] newArray(byte[] src, int srcPos, int srcLen) {
        byte[] buf = new byte[srcLen];
        System.arraycopy(src, srcPos, buf, 0, srcLen);
        return buf;
    }

    private byte[] newArray(byte[] src, int srcPos, int srcLen, byte[] src2, int srcPos2, int srcLen2) {
        byte[] buf = new byte[srcLen + srcLen2];
        System.arraycopy(src, srcPos, buf, 0, srcLen);
        System.arraycopy(src2, srcPos2, buf, srcLen, srcLen2);
        return buf;
    }
}

