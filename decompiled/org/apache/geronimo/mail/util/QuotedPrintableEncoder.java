/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import org.apache.geronimo.mail.util.Encoder;

public class QuotedPrintableEncoder
implements Encoder {
    protected static final byte[] encodingTable = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    protected static final byte[] decodingTable = new byte[128];
    private static final int DEFAULT_CHARS_PER_LINE = 76;
    protected OutputStream out;
    protected int bytesWritten = 0;
    protected int lineCount = 0;
    protected int lineLength;
    protected int deferredWhitespace = 0;
    protected int cachedCharacter = -1;
    protected boolean lastCR = false;
    protected boolean lastWhitespace = false;

    public QuotedPrintableEncoder() {
        this(null, 76);
    }

    public QuotedPrintableEncoder(OutputStream out) {
        this(out, 76);
    }

    public QuotedPrintableEncoder(OutputStream out, int lineLength) {
        this.out = out;
        this.lineLength = lineLength;
    }

    private void checkDeferred(int ch) throws IOException {
        if (this.lastWhitespace) {
            if (ch == 13 || ch == 10) {
                this.writeEncodedCharacter(32);
            } else {
                this.writeCharacter(32);
            }
            this.lastWhitespace = false;
        } else if (this.lastCR) {
            if (ch != 10) {
                this.writeEOL();
            }
            this.lastCR = false;
        }
    }

    public int encode(byte[] data, int off, int length) throws IOException {
        int endOffset = off + length;
        while (off < endOffset) {
            byte ch = data[off++];
            this.encode(ch);
        }
        return this.bytesWritten;
    }

    public void encode(int ch) throws IOException {
        this.checkDeferred(ch &= 0xFF);
        switch (ch) {
            case 32: {
                this.lastWhitespace = true;
                this.lastCR = false;
                break;
            }
            case 13: {
                this.lastCR = true;
                break;
            }
            case 10: {
                this.writeEOL();
                break;
            }
            case 61: {
                this.writeEncodedCharacter(ch);
                break;
            }
            default: {
                if (ch < 32 || ch >= 127) {
                    this.writeEncodedCharacter(ch);
                    break;
                }
                this.writeCharacter(ch);
            }
        }
    }

    public int encode(byte[] data, int off, int length, String specials) throws IOException {
        int endOffset = off + length;
        while (off < endOffset) {
            byte ch = data[off++];
            this.encode(ch, specials);
        }
        return this.bytesWritten;
    }

    public int encode(PushbackInputStream in, StringBuffer out, String specials, int limit) throws IOException {
        int count = 0;
        while (count < limit) {
            int ch = in.read();
            if (ch == -1) {
                return count;
            }
            if ((ch &= 0xFF) == 32) {
                out.append('_');
                ++count;
                continue;
            }
            if (ch < 32 || ch >= 127 || specials.indexOf(ch) != -1) {
                if (count + 3 > limit) {
                    in.unread(ch);
                    return count;
                }
                out.append('=');
                out.append((char)encodingTable[ch >> 4]);
                out.append((char)encodingTable[ch & 0xF]);
                count += 3;
                continue;
            }
            out.append((char)ch);
            ++count;
        }
        return count;
    }

    public void encode(int ch, String specials) throws IOException {
        if ((ch &= 0xFF) == 32) {
            this.writeCharacter(95);
        } else if (ch < 32 || ch >= 127 || specials.indexOf(ch) != -1) {
            this.writeEncodedCharacter(ch);
        } else {
            this.writeCharacter(ch);
        }
    }

    public int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
        this.out = out;
        this.bytesWritten = 0;
        return this.encode(data, off, length);
    }

    public int decode(byte[] data, int off, int length, OutputStream out) throws IOException {
        this.out = out;
        int endOffset = off + length;
        int bytesWritten = 0;
        while (off < endOffset) {
            byte ch;
            if ((ch = data[off++]) == 32) {
                int trailingSpaces = 1;
                while (off < endOffset && data[off] == 32) {
                    ++off;
                    ++trailingSpaces;
                }
                if (off >= endOffset || data[off] == 13 || data[off] == 10) continue;
                bytesWritten += trailingSpaces;
                while (trailingSpaces-- > 0) {
                    out.write(32);
                }
                continue;
            }
            if (ch == 61) {
                if (off + 1 >= endOffset) {
                    throw new IOException("Invalid quoted printable encoding");
                }
                byte b1 = data[off++];
                byte b2 = data[off++];
                if (b1 == 13) {
                    if (b2 == 10) continue;
                    throw new IOException("Invalid quoted printable encoding");
                }
                b1 = decodingTable[b1];
                b2 = decodingTable[b2];
                out.write(b1 << 4 | b2);
                ++bytesWritten;
                continue;
            }
            out.write(ch);
            ++bytesWritten;
        }
        return bytesWritten;
    }

    public int decodeWord(byte[] data, OutputStream out) throws IOException {
        return this.decodeWord(data, 0, data.length, out);
    }

    public int decodeWord(byte[] data, int off, int length, OutputStream out) throws IOException {
        this.out = out;
        int endOffset = off + length;
        int bytesWritten = 0;
        while (off < endOffset) {
            byte ch;
            if ((ch = data[off++]) == 95) {
                out.write(32);
                continue;
            }
            if (ch == 61) {
                if (off + 1 >= endOffset) {
                    throw new IOException("Invalid quoted printable encoding");
                }
                byte b1 = data[off++];
                byte b2 = data[off++];
                if (b1 == 13) {
                    if (b2 == 10) continue;
                    throw new IOException("Invalid quoted printable encoding");
                }
                byte c1 = decodingTable[b1];
                byte c2 = decodingTable[b2];
                out.write(c1 << 4 | c2);
                ++bytesWritten;
                continue;
            }
            out.write(ch);
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

    private void checkLineLength(int required) throws IOException {
        if (this.lineCount + required >= this.lineLength) {
            this.out.write(61);
            this.out.write(13);
            this.out.write(10);
            this.bytesWritten += 3;
            this.lineCount = 0;
        }
    }

    public void writeEncodedCharacter(int ch) throws IOException {
        this.checkLineLength(3);
        this.out.write(61);
        this.out.write(encodingTable[ch >> 4]);
        this.out.write(encodingTable[ch & 0xF]);
        this.lineCount += 3;
        this.bytesWritten += 3;
    }

    public void writeCharacter(int ch) throws IOException {
        this.checkLineLength(1);
        this.out.write(ch);
        ++this.lineCount;
        ++this.bytesWritten;
    }

    public void writeEOL() throws IOException {
        this.out.write(13);
        this.out.write(10);
        this.lineCount = 0;
        this.bytesWritten += 3;
    }

    public int decode(InputStream in) throws IOException {
        if (this.deferredWhitespace > 0) {
            --this.deferredWhitespace;
            return 32;
        }
        if (this.cachedCharacter != -1) {
            int result = this.cachedCharacter;
            this.cachedCharacter = -1;
            return result;
        }
        int ch = in.read();
        if (ch == -1) {
            return -1;
        }
        if (ch == 32) {
            while ((ch = in.read()) == 32) {
                ++this.deferredWhitespace;
            }
            if (ch == -1 || ch == 13 || ch == 10) {
                this.deferredWhitespace = 0;
                return ch;
            }
            this.cachedCharacter = this.decodeNonspaceChar(in, ch);
            return 32;
        }
        return this.decodeNonspaceChar(in, ch);
    }

    private int decodeNonspaceChar(InputStream in, int ch) throws IOException {
        if (ch == 61) {
            int b1 = in.read();
            if (b1 == -1) {
                throw new IOException("Truncated quoted printable data");
            }
            int b2 = in.read();
            if (b2 == -1) {
                throw new IOException("Truncated quoted printable data");
            }
            if (b1 == 13) {
                if (b2 != 10) {
                    throw new IOException("Invalid quoted printable encoding");
                }
                return this.decode(in);
            }
            b1 = decodingTable[b1];
            b2 = decodingTable[b2];
            return b1 << 4 | b2;
        }
        return ch;
    }

    public void encodeWord(InputStream in, String charset, String specials, OutputStream out, boolean fold) throws IOException {
        PushbackInputStream inStream = new PushbackInputStream(in);
        PrintStream writer = new PrintStream(out);
        int limit = 68 - charset.length();
        boolean firstLine = true;
        StringBuffer encodedString = new StringBuffer(76);
        while (true) {
            this.encode(inStream, encodedString, specials, limit);
            if (encodedString.length() == 0) break;
            if (!firstLine) {
                if (fold) {
                    writer.print("\r\n");
                } else {
                    writer.print(" ");
                }
            }
            writer.print("=?");
            writer.print(charset);
            writer.print("?Q?");
            writer.print(encodedString.toString());
            writer.print("?=");
            writer.flush();
            encodedString.setLength(0);
            firstLine = false;
        }
    }

    public void encodeWord(byte[] data, StringBuffer out, String charset, String specials) throws IOException {
        out.append("=?");
        out.append(charset);
        out.append("?Q?");
        this.encodeWordData(data, out, specials);
        out.append("?=");
    }

    public void encodeWordData(byte[] data, StringBuffer out, String specials) throws IOException {
        for (int i = 0; i < data.length; ++i) {
            int ch = data[i] & 0xFF;
            if (ch == 32) {
                out.append('_');
                continue;
            }
            if (ch < 32 || ch >= 127 || specials.indexOf(ch) != -1) {
                out.append('=');
                out.append((char)encodingTable[ch >> 4]);
                out.append((char)encodingTable[ch & 0xF]);
                continue;
            }
            out.append((char)ch);
        }
    }

    public int estimateEncodedLength(byte[] data, String specials) {
        int count = 0;
        for (int i = 0; i < data.length; ++i) {
            int ch = data[i] & 0xFF;
            if (ch < 32 || ch >= 127 || specials.indexOf(ch) != -1) {
                count += 3;
                continue;
            }
            ++count;
        }
        return count;
    }

    static {
        for (int i = 0; i < encodingTable.length; ++i) {
            QuotedPrintableEncoder.decodingTable[QuotedPrintableEncoder.encodingTable[i]] = (byte)i;
        }
    }
}

