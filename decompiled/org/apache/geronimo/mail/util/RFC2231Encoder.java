/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.mail.internet.MimeUtility;
import org.apache.geronimo.mail.util.Encoder;
import org.apache.geronimo.mail.util.StringBufferOutputStream;

public class RFC2231Encoder
implements Encoder {
    protected final byte[] encodingTable = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    protected String DEFAULT_SPECIALS;
    protected String specials = this.DEFAULT_SPECIALS = " *'%";
    protected final byte[] decodingTable = new byte[128];

    protected void initialiseDecodingTable() {
        for (int i = 0; i < this.encodingTable.length; ++i) {
            this.decodingTable[this.encodingTable[i]] = (byte)i;
        }
    }

    public RFC2231Encoder() {
        this(null);
    }

    public RFC2231Encoder(String specials) {
        if (specials != null) {
            this.specials = this.DEFAULT_SPECIALS + specials;
        }
        this.initialiseDecodingTable();
    }

    public int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
        int bytesWritten = 0;
        for (int i = off; i < off + length; ++i) {
            int ch = data[i] & 0xFF;
            if (ch <= 32 || ch >= 127 || this.specials.indexOf(ch) != -1) {
                out.write(37);
                out.write(this.encodingTable[ch >> 4]);
                out.write(this.encodingTable[ch & 0xF]);
                bytesWritten += 3;
                continue;
            }
            out.write((byte)ch);
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
            if ((v = data[i++]) == 37) {
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
            if ((v = data.charAt(i++)) == '%') {
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

    public String encode(String charset, String language2, String data) throws IOException {
        byte[] bytes = null;
        try {
            bytes = data.getBytes(MimeUtility.javaCharset(charset));
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        if (charset != null) {
            result.append(charset);
        }
        result.append("'");
        if (language2 != null) {
            result.append(language2);
        }
        result.append("'");
        StringBufferOutputStream out = new StringBufferOutputStream(result);
        this.encode(bytes, 0, bytes.length, out);
        return result.toString();
    }

    public String decode(String data) throws IOException, UnsupportedEncodingException {
        int charsetEnd = data.indexOf(39);
        if (charsetEnd == -1) {
            throw new IOException("Missing charset in RFC2231 encoded value");
        }
        String charset = data.substring(0, charsetEnd);
        int languageEnd = data.indexOf(39, charsetEnd + 1);
        if (languageEnd == -1) {
            throw new IOException("Missing language in RFC2231 encoded value");
        }
        String language2 = data.substring(charsetEnd + 1, languageEnd);
        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length());
        this.decode(data.substring(languageEnd + 1), out);
        byte[] bytes = out.toByteArray();
        return new String(bytes, 0, bytes.length, MimeUtility.javaCharset(charset));
    }
}

