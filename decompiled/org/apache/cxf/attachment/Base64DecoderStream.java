/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;

public class Base64DecoderStream
extends FilterInputStream {
    static final String MAIL_BASE64_IGNOREERRORS = "mail.mime.base64.ignoreerrors";
    static final int BUFFERED_UNITS = 2000;
    protected boolean ignoreErrors;
    protected char[] encodedChars = new char[8000];
    protected byte[] decodedChars;
    protected int decodedCount;
    protected int decodedIndex;

    public Base64DecoderStream(InputStream in) {
        super(in);
    }

    private boolean dataAvailable() {
        return this.decodedCount != 0;
    }

    private boolean decodeStreamData() throws IOException {
        this.decodedIndex = 0;
        int readCharacters = this.fillEncodedBuffer();
        if (readCharacters > 0) {
            try {
                this.decodedChars = Base64Utility.decodeChunk(this.encodedChars, 0, readCharacters);
            }
            catch (Base64Exception e) {
                throw new IOException(e);
            }
            this.decodedCount = this.decodedChars.length;
            return true;
        }
        return false;
    }

    private int getByte() throws IOException {
        if (!this.dataAvailable() && !this.decodeStreamData()) {
            return -1;
        }
        --this.decodedCount;
        return this.decodedChars[this.decodedIndex++] & 0xFF;
    }

    private int getBytes(byte[] data, int offset, int length) throws IOException {
        int readCharacters = 0;
        while (length > 0) {
            if (!this.dataAvailable() && !this.decodeStreamData()) {
                return readCharacters > 0 ? readCharacters : -1;
            }
            int copyCount = Math.min(this.decodedCount, length);
            System.arraycopy(this.decodedChars, this.decodedIndex, data, offset, copyCount);
            this.decodedIndex += copyCount;
            this.decodedCount -= copyCount;
            offset += copyCount;
            length -= copyCount;
            readCharacters += copyCount;
        }
        return readCharacters;
    }

    private int fillEncodedBuffer() throws IOException {
        int readCharacters = 0;
        while (true) {
            int ch;
            if ((ch = this.in.read()) == -1) {
                if (readCharacters % 4 != 0) {
                    throw new IOException("Base64 encoding error, data truncated: " + readCharacters + " " + new String(this.encodedChars, 0, readCharacters));
                }
                return readCharacters;
            }
            if (!Base64Utility.isValidBase64(ch)) continue;
            this.encodedChars[readCharacters++] = (char)ch;
            if (readCharacters >= this.encodedChars.length) break;
        }
        return readCharacters;
    }

    @Override
    public int read() throws IOException {
        return this.getByte();
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        return this.getBytes(buffer, offset, length);
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int available() throws IOException {
        return this.in.available() / 4 * 3 + this.decodedCount;
    }
}

