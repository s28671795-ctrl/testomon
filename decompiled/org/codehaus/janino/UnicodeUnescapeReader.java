/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.UnicodeUnescapeException;

public class UnicodeUnescapeReader
extends FilterReader {
    private int unreadChar = -1;
    private boolean oddPrecedingBackslashes;

    public UnicodeUnescapeReader(Reader in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int c;
        if (this.unreadChar == -1) {
            c = this.in.read();
        } else {
            c = this.unreadChar;
            this.unreadChar = -1;
        }
        if (c != 92 || this.oddPrecedingBackslashes) {
            this.oddPrecedingBackslashes = false;
            return c;
        }
        c = this.in.read();
        if (c != 117) {
            this.unreadChar = c;
            this.oddPrecedingBackslashes = true;
            return 92;
        }
        do {
            if ((c = this.in.read()) != -1) continue;
            throw new UnicodeUnescapeException("Incomplete escape sequence");
        } while (c == 117);
        char[] ca = new char[4];
        ca[0] = (char)c;
        if (this.in.read(ca, 1, 3) != 3) {
            throw new UnicodeUnescapeException("Incomplete escape sequence");
        }
        try {
            return Integer.parseInt(new String(ca), 16);
        }
        catch (NumberFormatException ex) {
            throw new UnicodeUnescapeException("Invalid escape sequence \"\\u" + new String(ca) + "\"", ex);
        }
    }

    @Override
    public int read(@Nullable char[] cbuf, int off, int len) throws IOException {
        int c;
        assert (cbuf != null);
        if (len == 0) {
            return 0;
        }
        int res = 0;
        while ((c = this.read()) != -1) {
            cbuf[off++] = (char)c;
            if (++res < len) continue;
        }
        return res == 0 ? -1 : res;
    }

    public static void main(String[] args2) throws IOException {
        int c;
        UnicodeUnescapeReader r = new UnicodeUnescapeReader(new StringReader(args2[0]));
        while ((c = ((Reader)r).read()) != -1) {
            System.out.print((char)c);
        }
        System.out.println();
    }
}

