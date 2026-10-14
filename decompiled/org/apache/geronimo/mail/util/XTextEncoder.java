/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.geronimo.mail.util.Encoder;

public class XTextEncoder
implements Encoder {
    protected final byte[] encodingTable = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    protected final byte[] decodingTable = new byte[128];

    protected void initialiseDecodingTable() {
        for (int i = 0; i < this.encodingTable.length; ++i) {
            this.decodingTable[this.encodingTable[i]] = (byte)i;
        }
    }

    public XTextEncoder() {
        this.initialiseDecodingTable();
    }

    public int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
        int bytesWritten = 0;
        for (int i = off; i < off + length; ++i) {
            int v = data[i] & 0xFF;
            if (v < 33 || v > 126 || v == 43 || v == 43) {
                out.write(43);
                out.write(this.encodingTable[v >>> 4]);
                out.write(this.encodingTable[v & 0xF]);
                bytesWritten += 3;
                continue;
            }
            out.write((byte)v);
            ++bytesWritten;
        }
        return bytesWritten;
    }

    public int decode(byte[] data, int off, int length, OutputStream out) throws IOException {
        int outLen = 0;
        int end = off + length;
        int i = off;
        while (i < end) {
            byte v;
            if ((v = data[i++]) == 43) {
                byte b1 = this.decodingTable[data[i++]];
                byte b2 = this.decodingTable[data[i++]];
                out.write(b1 << 4 | b2);
            } else {
                out.write(v);
            }
            ++outLen;
        }
        return outLen;
    }

    public int decode(String data, OutputStream out) throws IOException {
        int length = 0;
        int end = data.length();
        int i = 0;
        while (i < end) {
            char v;
            if ((v = data.charAt(i++)) == '+') {
                byte b1 = this.decodingTable[data.charAt(i++)];
                byte b2 = this.decodingTable[data.charAt(i++)];
                out.write(b1 << 4 | b2);
            } else {
                out.write((byte)v);
            }
            ++length;
        }
        return length;
    }
}

