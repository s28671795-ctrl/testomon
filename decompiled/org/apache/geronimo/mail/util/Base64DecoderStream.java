/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.geronimo.mail.util.Base64Encoder;
import org.apache.geronimo.mail.util.SessionUtil;

public class Base64DecoderStream
extends FilterInputStream {
    protected static final String MAIL_BASE64_IGNOREERRORS = "mail.mime.base64.ignoreerrors";
    protected static final int BUFFERED_UNITS = 2000;
    protected Base64Encoder decoder = new Base64Encoder();
    protected boolean ignoreErrors = false;
    protected byte[] encodedChars = new byte[8000];
    protected byte[] decodedChars = new byte[6000];
    protected int decodedCount = 0;
    protected int decodedIndex = 0;

    public Base64DecoderStream(InputStream in) {
        super(in);
        this.ignoreErrors = SessionUtil.getBooleanProperty(MAIL_BASE64_IGNOREERRORS, false);
    }

    private boolean dataAvailable() {
        return this.decodedCount != 0;
    }

    private byte getBufferedChar() {
        --this.decodedCount;
        return this.decodedChars[this.decodedIndex++];
    }

    private boolean decodeStreamData() throws IOException {
        this.decodedIndex = 0;
        int readCharacters = this.fillEncodedBuffer();
        if (readCharacters > 0) {
            this.decodedCount = this.decoder.decode(this.encodedChars, 0, readCharacters, this.decodedChars);
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
                    if (!this.ignoreErrors) {
                        throw new IOException("Base64 encoding error, data truncated");
                    }
                    return readCharacters / 4 * 4;
                }
                return readCharacters;
            }
            if (!this.decoder.isValidBase64(ch)) continue;
            this.encodedChars[readCharacters++] = (byte)ch;
            if (readCharacters >= this.encodedChars.length) break;
        }
        return readCharacters;
    }

    public int read() throws IOException {
        return this.getByte();
    }

    public int read(byte[] buffer, int offset, int length) throws IOException {
        return this.getBytes(buffer, offset, length);
    }

    public boolean markSupported() {
        return false;
    }

    public int available() throws IOException {
        return this.in.available() / 4 * 3 + this.decodedCount;
    }
}

