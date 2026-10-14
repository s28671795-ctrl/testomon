/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.util.charstream;

import java.io.EOFException;
import org.codehaus.janino.util.charstream.CharStream;
import org.codehaus.janino.util.charstream.UnexpectedCharacterException;

public class StringCharStream
implements CharStream {
    private final String in;
    private int pos;

    public StringCharStream(String in) {
        this.in = in;
    }

    @Override
    public int peek() {
        return this.pos == this.in.length() ? -1 : (int)this.in.charAt(this.pos);
    }

    @Override
    public boolean peek(char c) {
        return this.pos < this.in.length() && this.in.charAt(this.pos) == c;
    }

    @Override
    public int peek(String chars) {
        return this.pos == this.in.length() ? -1 : chars.indexOf(this.in.charAt(this.pos));
    }

    @Override
    public char read() throws EOFException {
        if (this.pos == this.in.length()) {
            throw new EOFException("Unexpected end-of-input");
        }
        return this.in.charAt(this.pos++);
    }

    @Override
    public void read(char c) throws EOFException, UnexpectedCharacterException {
        if (this.pos == this.in.length()) {
            throw new EOFException("Expected '" + c + "' instead of end-of-input");
        }
        if (this.in.charAt(this.pos) != c) {
            throw new UnexpectedCharacterException("'" + c + "' expected instead of '" + this.in.substring(this.pos) + "'");
        }
        ++this.pos;
    }

    @Override
    public int read(String chars) throws EOFException, UnexpectedCharacterException {
        if (this.pos == this.in.length()) {
            throw new EOFException("Expected one of '" + chars + "' instead of end-of-input");
        }
        int res = chars.indexOf(this.in.charAt(this.pos));
        if (res == -1) {
            throw new UnexpectedCharacterException("One of '" + chars + "' expected instead of '" + this.in.charAt(this.pos) + "'");
        }
        ++this.pos;
        return res;
    }

    @Override
    public boolean peekRead(char c) {
        if (this.pos >= this.in.length()) {
            return false;
        }
        if (this.in.charAt(this.pos) == c) {
            ++this.pos;
            return true;
        }
        return false;
    }

    @Override
    public int peekRead(String chars) {
        if (this.pos >= this.in.length()) {
            return -1;
        }
        int res = chars.indexOf(this.in.charAt(this.pos));
        if (res != -1) {
            ++this.pos;
        }
        return res;
    }

    @Override
    public void eoi() throws UnexpectedCharacterException {
        if (this.pos < this.in.length()) {
            throw new UnexpectedCharacterException("Unexpected trailing characters '" + this.in.substring(this.pos) + "'");
        }
    }

    @Override
    public boolean atEoi() {
        return this.pos >= this.in.length();
    }

    public String toString() {
        return "'" + this.in + "' at offset " + this.pos;
    }
}

