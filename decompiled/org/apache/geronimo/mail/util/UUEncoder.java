/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.geronimo.mail.util.Encoder;

public class UUEncoder
implements Encoder {
    private static final int MAX_CHARS_PER_LINE = 45;

    public int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
        int byteCount = 0;
        while (length > 45) {
            byteCount += this.encodeLine(data, off, 45, out);
            length -= 45;
            off += 45;
        }
        return byteCount += this.encodeLine(data, off, 45, out);
    }

    private int encodeLine(byte[] data, int offset, int length, OutputStream out) throws IOException {
        out.write((byte)((length & 0x3F) + 32));
        int bytesWritten = 2;
        int i = 0;
        while (i < length) {
            byte b = 1;
            byte c = 1;
            byte a = data[offset + i++];
            if (i < length) {
                b = data[offset + i++];
                if (i < length) {
                    c = data[offset + i++];
                }
            }
            byte d1 = (byte)((a >>> 2 & 0x3F) + 32);
            byte d2 = (byte)((a << 4 & 0x30 | b >>> 4 & 0xF) + 32);
            byte d3 = (byte)((b << 2 & 0x3C | c >>> 6 & 3) + 32);
            byte d4 = (byte)((c & 0x3F) + 32);
            out.write(d1);
            out.write(d2);
            out.write(d3);
            out.write(d4);
            bytesWritten += 4;
        }
        out.write(10);
        return bytesWritten;
    }

    public int decode(byte[] data, int off, int length, OutputStream out) throws IOException {
        int bytesWritten = 0;
        while (length > 0) {
            int lineOffset = off;
            while (length > 0 && data[off] != 10) {
                ++off;
                --length;
            }
            bytesWritten += this.decodeLine(data, lineOffset, off - lineOffset, out);
            ++off;
            --length;
        }
        return bytesWritten;
    }

    private int decodeLine(byte[] data, int off, int length, OutputStream out) throws IOException {
        int count;
        if ((count = data[off++]) < 32) {
            throw new IOException("Invalid UUEncode line length");
        }
        int requiredLength = ((count = count - 32 & 0x3F) * 8 + 5) / 6 + 1;
        if (length < requiredLength) {
            throw new IOException("UUEncoded data and length do not match");
        }
        int bytesWritten = 0;
        while (bytesWritten < count) {
            byte a = (byte)(data[off++] - 32 & 0x3F);
            byte b = (byte)(data[off++] - 32 & 0x3F);
            byte c = 0;
            byte d = 0;
            byte first = (byte)(a << 2 & 0xFC | b >>> 4 & 3);
            out.write(first);
            if (++bytesWritten >= count) continue;
            c = (byte)(data[off++] - 32 & 0x3F);
            byte second = (byte)(b << 4 & 0xF0 | c >>> 2 & 0xF);
            out.write(second);
            if (++bytesWritten >= count) continue;
            d = (byte)(data[off++] - 32 & 0x3F);
            byte third = (byte)(c << 6 & 0xC0 | d & 0x3F);
            out.write(third);
            ++bytesWritten;
        }
        return bytesWritten;
    }

    public int decode(String data, OutputStream out) throws IOException {
        try {
            byte[] bytes = data.getBytes("US-ASCII");
            return this.decode(bytes, 0, bytes.length, out);
        }
        catch (UnsupportedEncodingException e) {
            throw new IOException("Invalid UUEncoding");
        }
    }
}

