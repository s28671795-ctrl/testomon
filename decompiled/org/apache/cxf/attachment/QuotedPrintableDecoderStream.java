/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.IOException;
import java.io.InputStream;

public class QuotedPrintableDecoderStream
extends InputStream {
    private static final byte[] ENCODING_TABLE = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    private static final byte[] DECODING_TABLE = new byte[128];
    private int deferredWhitespace;
    private int cachedCharacter = -1;
    private final InputStream in;

    public QuotedPrintableDecoderStream(InputStream is) {
        this.in = is;
    }

    private int decodeNonspaceChar(int ch) throws IOException {
        if (ch != 61) {
            return ch;
        }
        byte[] b = new byte[2];
        if (this.in.read(b) < 2) {
            throw new IOException("Truncated quoted printable data");
        }
        if (b[0] == 13) {
            if (b[1] != 10) {
                throw new IOException("Invalid quoted printable encoding");
            }
            return this.read();
        }
        b[0] = DECODING_TABLE[b[0]];
        b[1] = DECODING_TABLE[b[1]];
        return b[0] << 4 | b[1];
    }

    @Override
    public int read() throws IOException {
        if (this.deferredWhitespace > 0) {
            --this.deferredWhitespace;
            return 32;
        }
        if (this.cachedCharacter != -1) {
            int result = this.cachedCharacter;
            this.cachedCharacter = -1;
            return result;
        }
        int ch = this.in.read();
        if (ch != 32) {
            return this.decodeNonspaceChar(ch);
        }
        while ((ch = this.in.read()) == 32) {
            ++this.deferredWhitespace;
        }
        if (ch == -1 || ch == 13 || ch == 10) {
            this.deferredWhitespace = 0;
            return ch;
        }
        this.cachedCharacter = this.decodeNonspaceChar(ch);
        return 32;
    }

    static {
        for (int i = 0; i < ENCODING_TABLE.length; ++i) {
            QuotedPrintableDecoderStream.DECODING_TABLE[QuotedPrintableDecoderStream.ENCODING_TABLE[i]] = (byte)i;
        }
    }
}

