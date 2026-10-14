/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.geronimo.mail.util.UUEncoder;

public class UUDecoderStream
extends FilterInputStream {
    protected static final int MAX_CHARS_PER_LINE = 45;
    protected UUEncoder decoder = new UUEncoder();
    protected byte[] decodedChars;
    protected int decodedCount = 0;
    protected int decodedIndex = 0;
    protected boolean beginRead = false;

    public UUDecoderStream(InputStream in) {
        super(in);
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
        return this.fillEncodedBuffer() != -1;
    }

    private int getByte() throws IOException {
        if (!this.dataAvailable() && !this.decodeStreamData()) {
            return -1;
        }
        --this.decodedCount;
        return this.decodedChars[this.decodedIndex++];
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

    private void checkBegin() throws IOException {
        String line;
        if (this.beginRead) {
            return;
        }
        do {
            if ((line = this.readLine()) != null) continue;
            throw new IOException("Missing UUEncode begin command");
        } while (!line.regionMatches(true, 0, "begin ", 0, 6));
        this.beginRead = true;
    }

    protected String readLine() throws IOException {
        this.decodedIndex = 0;
        StringBuffer buffer = new StringBuffer();
        int ch = this.in.read();
        while (ch != -1 && ch != 10) {
            if (ch != 13) {
                buffer.append((char)ch);
            }
            ch = this.in.read();
        }
        if (ch == -1 && buffer.length() == 0) {
            return null;
        }
        return buffer.toString();
    }

    private int fillEncodedBuffer() throws IOException {
        ByteArrayOutputStream out;
        this.checkBegin();
        this.decodedIndex = 0;
        do {
            byte[] lineBytes;
            String line;
            if ((line = this.readLine()) == null) {
                throw new IOException("Missing end in UUEncoded data");
            }
            if (line.equalsIgnoreCase("end")) {
                return -1;
            }
            out = new ByteArrayOutputStream(45);
            try {
                lineBytes = line.getBytes("US-ASCII");
            }
            catch (UnsupportedEncodingException e) {
                throw new IOException("Invalid UUEncoding");
            }
            this.decodedCount = this.decoder.decode(lineBytes, 0, lineBytes.length, out);
        } while (this.decodedCount == 0);
        this.decodedChars = out.toByteArray();
        return this.decodedCount;
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

