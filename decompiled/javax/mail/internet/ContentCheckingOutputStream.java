/*
 * Decompiled with CFR 0.152.
 */
package javax.mail.internet;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.geronimo.mail.util.ASCIIUtil;

class ContentCheckingOutputStream
extends OutputStream {
    private int asciiChars = 0;
    private int nonAsciiChars = 0;
    private boolean containsLongLines = false;
    private boolean containsMalformedEOL = false;
    private int previousChar = 0;
    private int span = 0;

    ContentCheckingOutputStream() {
    }

    public void write(byte[] data) throws IOException {
        this.write(data, 0, data.length);
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        for (int i = 0; i < length; ++i) {
            this.write(data[offset + i]);
        }
    }

    public void write(int ch) {
        if (ch == 10 || ch == 13) {
            if (ch == 10 && this.previousChar != 13) {
                this.containsMalformedEOL = true;
            }
            this.span = 0;
        } else {
            ++this.span;
            if (this.span > 998) {
                this.containsLongLines = true;
            }
            if (!ASCIIUtil.isAscii(ch)) {
                ++this.nonAsciiChars;
            } else {
                ++this.asciiChars;
            }
        }
        this.previousChar = ch;
    }

    public String getBinaryTransferEncoding() {
        if (this.nonAsciiChars != 0 || this.containsLongLines || this.containsMalformedEOL) {
            return "base64";
        }
        return "7bit";
    }

    public String getTextTransferEncoding() {
        if (this.nonAsciiChars == 0) {
            if (this.containsLongLines) {
                return "quoted-printable";
            }
            return "7bit";
        }
        if (this.nonAsciiChars > this.asciiChars) {
            return "base64";
        }
        return "quoted-printable";
    }
}

