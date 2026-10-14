/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.csv;

import java.io.Closeable;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.ExtendedBufferedReader;
import org.apache.commons.csv.Token;

final class Lexer
implements Closeable {
    private static final String CR_STRING = Character.toString('\r');
    private static final String LF_STRING = Character.toString('\n');
    private static final char DISABLED = '\ufffe';
    private final char[] delimiter;
    private final char[] delimiterBuf;
    private final char[] escapeDelimiterBuf;
    private final char escape;
    private final char quoteChar;
    private final char commentStart;
    private final boolean ignoreSurroundingSpaces;
    private final boolean ignoreEmptyLines;
    private final ExtendedBufferedReader reader;
    private String firstEol;
    private boolean isLastTokenDelimiter;

    Lexer(CSVFormat format, ExtendedBufferedReader reader) {
        this.reader = reader;
        this.delimiter = format.getDelimiterString().toCharArray();
        this.escape = this.mapNullToDisabled(format.getEscapeCharacter());
        this.quoteChar = this.mapNullToDisabled(format.getQuoteCharacter());
        this.commentStart = this.mapNullToDisabled(format.getCommentMarker());
        this.ignoreSurroundingSpaces = format.getIgnoreSurroundingSpaces();
        this.ignoreEmptyLines = format.getIgnoreEmptyLines();
        this.delimiterBuf = new char[this.delimiter.length - 1];
        this.escapeDelimiterBuf = new char[2 * this.delimiter.length - 1];
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    long getCharacterPosition() {
        return this.reader.getPosition();
    }

    long getCurrentLineNumber() {
        return this.reader.getCurrentLineNumber();
    }

    String getFirstEol() {
        return this.firstEol;
    }

    boolean isClosed() {
        return this.reader.isClosed();
    }

    boolean isCommentStart(int ch) {
        return ch == this.commentStart;
    }

    boolean isDelimiter(int ch) throws IOException {
        this.isLastTokenDelimiter = false;
        if (ch != this.delimiter[0]) {
            return false;
        }
        if (this.delimiter.length == 1) {
            this.isLastTokenDelimiter = true;
            return true;
        }
        this.reader.lookAhead(this.delimiterBuf);
        for (int i = 0; i < this.delimiterBuf.length; ++i) {
            if (this.delimiterBuf[i] == this.delimiter[i + 1]) continue;
            return false;
        }
        int count = this.reader.read(this.delimiterBuf, 0, this.delimiterBuf.length);
        this.isLastTokenDelimiter = count != -1;
        return this.isLastTokenDelimiter;
    }

    boolean isEndOfFile(int ch) {
        return ch == -1;
    }

    boolean isEscape(int ch) {
        return ch == this.escape;
    }

    boolean isEscapeDelimiter() throws IOException {
        this.reader.lookAhead(this.escapeDelimiterBuf);
        if (this.escapeDelimiterBuf[0] != this.delimiter[0]) {
            return false;
        }
        for (int i = 1; i < this.delimiter.length; ++i) {
            if (this.escapeDelimiterBuf[2 * i] == this.delimiter[i] && this.escapeDelimiterBuf[2 * i - 1] == this.escape) continue;
            return false;
        }
        int count = this.reader.read(this.escapeDelimiterBuf, 0, this.escapeDelimiterBuf.length);
        return count != -1;
    }

    private boolean isMetaChar(int ch) {
        return ch == this.escape || ch == this.quoteChar || ch == this.commentStart;
    }

    boolean isQuoteChar(int ch) {
        return ch == this.quoteChar;
    }

    boolean isStartOfLine(int ch) {
        return ch == 10 || ch == 13 || ch == -2;
    }

    private char mapNullToDisabled(Character c) {
        return c == null ? (char)'\ufffe' : (char)c.charValue();
    }

    Token nextToken(Token token) throws IOException {
        int lastChar = this.reader.getLastChar();
        int c = this.reader.read();
        boolean eol = this.readEndOfLine(c);
        if (this.ignoreEmptyLines) {
            while (eol && this.isStartOfLine(lastChar)) {
                lastChar = c;
                c = this.reader.read();
                eol = this.readEndOfLine(c);
                if (!this.isEndOfFile(c)) continue;
                token.type = Token.Type.EOF;
                return token;
            }
        }
        if (this.isEndOfFile(lastChar) || !this.isLastTokenDelimiter && this.isEndOfFile(c)) {
            token.type = Token.Type.EOF;
            return token;
        }
        if (this.isStartOfLine(lastChar) && this.isCommentStart(c)) {
            String line = this.reader.readLine();
            if (line == null) {
                token.type = Token.Type.EOF;
                return token;
            }
            String comment = line.trim();
            token.content.append(comment);
            token.type = Token.Type.COMMENT;
            return token;
        }
        while (token.type == Token.Type.INVALID) {
            if (this.ignoreSurroundingSpaces) {
                while (Character.isWhitespace((char)c) && !this.isDelimiter(c) && !eol) {
                    c = this.reader.read();
                    eol = this.readEndOfLine(c);
                }
            }
            if (this.isDelimiter(c)) {
                token.type = Token.Type.TOKEN;
                continue;
            }
            if (eol) {
                token.type = Token.Type.EORECORD;
                continue;
            }
            if (this.isQuoteChar(c)) {
                this.parseEncapsulatedToken(token);
                continue;
            }
            if (this.isEndOfFile(c)) {
                token.type = Token.Type.EOF;
                token.isReady = true;
                continue;
            }
            this.parseSimpleToken(token, c);
        }
        return token;
    }

    private Token parseEncapsulatedToken(Token token) throws IOException {
        token.isQuoted = true;
        long startLineNumber = this.getCurrentLineNumber();
        while (true) {
            int c;
            if (this.isEscape(c = this.reader.read())) {
                if (this.isEscapeDelimiter()) {
                    token.content.append(this.delimiter);
                    continue;
                }
                int unescaped = this.readEscape();
                if (unescaped == -1) {
                    token.content.append((char)c).append((char)this.reader.getLastChar());
                    continue;
                }
                token.content.append((char)unescaped);
                continue;
            }
            if (this.isQuoteChar(c)) {
                if (this.isQuoteChar(this.reader.lookAhead())) {
                    c = this.reader.read();
                    token.content.append((char)c);
                    continue;
                }
                do {
                    if (this.isDelimiter(c = this.reader.read())) {
                        token.type = Token.Type.TOKEN;
                        return token;
                    }
                    if (this.isEndOfFile(c)) {
                        token.type = Token.Type.EOF;
                        token.isReady = true;
                        return token;
                    }
                    if (!this.readEndOfLine(c)) continue;
                    token.type = Token.Type.EORECORD;
                    return token;
                } while (Character.isWhitespace((char)c));
                throw new IOException("(line " + this.getCurrentLineNumber() + ") invalid char between encapsulated token and delimiter");
            }
            if (this.isEndOfFile(c)) {
                throw new IOException("(startline " + startLineNumber + ") EOF reached before encapsulated token finished");
            }
            token.content.append((char)c);
        }
    }

    private Token parseSimpleToken(Token token, int ch) throws IOException {
        while (true) {
            if (this.readEndOfLine(ch)) {
                token.type = Token.Type.EORECORD;
                break;
            }
            if (this.isEndOfFile(ch)) {
                token.type = Token.Type.EOF;
                token.isReady = true;
                break;
            }
            if (this.isDelimiter(ch)) {
                token.type = Token.Type.TOKEN;
                break;
            }
            if (this.isEscape(ch)) {
                if (this.isEscapeDelimiter()) {
                    token.content.append(this.delimiter);
                } else {
                    int unescaped = this.readEscape();
                    if (unescaped == -1) {
                        token.content.append((char)ch).append((char)this.reader.getLastChar());
                    } else {
                        token.content.append((char)unescaped);
                    }
                }
            } else {
                token.content.append((char)ch);
            }
            ch = this.reader.read();
        }
        if (this.ignoreSurroundingSpaces) {
            this.trimTrailingSpaces(token.content);
        }
        return token;
    }

    boolean readEndOfLine(int ch) throws IOException {
        if (ch == 13 && this.reader.lookAhead() == 10) {
            ch = this.reader.read();
            if (this.firstEol == null) {
                this.firstEol = "\r\n";
            }
        }
        if (this.firstEol == null) {
            if (ch == 10) {
                this.firstEol = LF_STRING;
            } else if (ch == 13) {
                this.firstEol = CR_STRING;
            }
        }
        return ch == 10 || ch == 13;
    }

    int readEscape() throws IOException {
        int ch = this.reader.read();
        switch (ch) {
            case 114: {
                return 13;
            }
            case 110: {
                return 10;
            }
            case 116: {
                return 9;
            }
            case 98: {
                return 8;
            }
            case 102: {
                return 12;
            }
            case 8: 
            case 9: 
            case 10: 
            case 12: 
            case 13: {
                return ch;
            }
            case -1: {
                throw new IOException("EOF whilst processing escape sequence");
            }
        }
        if (this.isMetaChar(ch)) {
            return ch;
        }
        return -1;
    }

    void trimTrailingSpaces(StringBuilder buffer) {
        int length;
        for (length = buffer.length(); length > 0 && Character.isWhitespace(buffer.charAt(length - 1)); --length) {
        }
        if (length != buffer.length()) {
            buffer.setLength(length);
        }
    }
}

