/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.IOException;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.Token;
import org.codehaus.janino.TokenStream;
import org.codehaus.janino.TokenType;

public class TokenStreamImpl
implements TokenStream {
    private final Scanner scanner;
    @Nullable
    private String docComment;
    @Nullable
    private Token previousToken;
    @Nullable
    private Token nextToken;
    @Nullable
    private Token nextButOneToken;
    @Nullable
    private WarningHandler warningHandler;

    public TokenStreamImpl(Scanner scanner) {
        this.scanner = scanner;
        this.scanner.setIgnoreWhiteSpace(true);
    }

    private Token produceToken() throws CompileException, IOException {
        Token token;
        block4: while (true) {
            token = this.scanner.produce();
            switch (token.type) {
                case WHITE_SPACE: 
                case C_PLUS_PLUS_STYLE_COMMENT: {
                    continue block4;
                }
                case C_STYLE_COMMENT: {
                    if (!token.value.startsWith("/**") || this.docComment == null) continue block4;
                    this.warning("MDC", "Misplaced doc comment", this.scanner.location());
                    this.docComment = null;
                    continue block4;
                }
            }
            break;
        }
        return token;
    }

    @Nullable
    public String doc() {
        String s2 = this.docComment;
        this.docComment = null;
        return s2;
    }

    @Override
    public Token peek() throws CompileException, IOException {
        if (this.nextToken == null) {
            this.nextToken = this.produceToken();
        }
        assert (this.nextToken != null);
        return this.nextToken;
    }

    @Override
    public Token peekNextButOne() throws CompileException, IOException {
        if (this.nextToken == null) {
            this.nextToken = this.produceToken();
        }
        if (this.nextButOneToken == null) {
            this.nextButOneToken = this.produceToken();
        }
        assert (this.nextButOneToken != null);
        return this.nextButOneToken;
    }

    @Override
    public boolean peek(String suspected) throws CompileException, IOException {
        return this.peek().value.equals(suspected);
    }

    @Override
    public int peek(String ... suspected) throws CompileException, IOException {
        return TokenStreamImpl.indexOf(suspected, this.peek().value);
    }

    @Override
    public boolean peek(TokenType suspected) throws CompileException, IOException {
        return this.peek().type == suspected;
    }

    @Override
    public int peek(TokenType ... suspected) throws CompileException, IOException {
        return TokenStreamImpl.indexOf(suspected, this.peek().type);
    }

    @Override
    public boolean peekNextButOne(String suspected) throws CompileException, IOException {
        return this.peekNextButOne().value.equals(suspected);
    }

    @Override
    public Token read() throws CompileException, IOException {
        if (this.nextToken == null) {
            this.previousToken = this.produceToken();
            return this.previousToken;
        }
        Token result = this.nextToken;
        assert (result != null);
        this.previousToken = result;
        this.nextToken = this.nextButOneToken;
        this.nextButOneToken = null;
        return result;
    }

    @Override
    public void read(String expected) throws CompileException, IOException {
        String s2 = this.read().value;
        if (!s2.equals(expected)) {
            throw this.compileException("'" + expected + "' expected instead of '" + s2 + "'");
        }
    }

    @Override
    public int read(String ... expected) throws CompileException, IOException {
        int result;
        Token t2 = this.read();
        String value = t2.value;
        int idx = TokenStreamImpl.indexOf(expected, value);
        if (idx != -1) {
            return idx;
        }
        if (value.startsWith(">") && (result = TokenStreamImpl.indexOf(expected, ">")) != -1) {
            Location loc = t2.getLocation();
            this.nextToken = new Token(loc.getFileName(), loc.getLineNumber(), loc.getColumnNumber() + 1, TokenType.OPERATOR, value.substring(1));
            return result;
        }
        throw this.compileException("One of '" + TokenStreamImpl.join(expected, " ") + "' expected instead of '" + value + "'");
    }

    @Override
    public String read(TokenType expected) throws CompileException, IOException {
        Token t2 = this.read();
        if (t2.type != expected) {
            throw new CompileException((Object)((Object)expected) + " expected instead of '" + t2.value + "'", t2.getLocation());
        }
        return t2.value;
    }

    @Override
    public int read(TokenType ... expected) throws CompileException, IOException {
        Token t2 = this.read();
        TokenType type = t2.type;
        int idx = TokenStreamImpl.indexOf(expected, type);
        if (idx != -1) {
            return idx;
        }
        throw this.compileException("One of '" + TokenStreamImpl.join((Object[])expected, " ") + "' expected instead of '" + (Object)((Object)type) + "'");
    }

    @Override
    public boolean peekRead(String suspected) throws CompileException, IOException {
        if (this.nextToken != null) {
            if (!this.nextToken.value.equals(suspected)) {
                return false;
            }
            this.previousToken = this.nextToken;
            this.nextToken = this.nextButOneToken;
            this.nextButOneToken = null;
            return true;
        }
        Token result = this.produceToken();
        if (result.value.equals(suspected)) {
            this.previousToken = result;
            return true;
        }
        this.nextToken = result;
        return false;
    }

    @Override
    public int peekRead(String ... suspected) throws CompileException, IOException {
        if (this.nextToken != null) {
            int idx = TokenStreamImpl.indexOf(suspected, this.nextToken.value);
            if (idx == -1) {
                return -1;
            }
            this.previousToken = this.nextToken;
            this.nextToken = this.nextButOneToken;
            this.nextButOneToken = null;
            return idx;
        }
        Token t2 = this.produceToken();
        int idx = TokenStreamImpl.indexOf(suspected, t2.value);
        if (idx != -1) {
            this.previousToken = t2;
            return idx;
        }
        this.nextToken = t2;
        return -1;
    }

    @Override
    @Nullable
    public String peekRead(TokenType suspected) throws CompileException, IOException {
        Token nt = this.nextToken;
        if (nt != null) {
            if (nt.type != suspected) {
                return null;
            }
            this.previousToken = this.nextToken;
            this.nextToken = this.nextButOneToken;
            this.nextButOneToken = null;
            return nt.value;
        }
        nt = this.produceToken();
        if (nt.type == suspected) {
            this.previousToken = nt;
            return nt.value;
        }
        this.nextToken = nt;
        return null;
    }

    @Override
    public int peekRead(TokenType ... suspected) throws CompileException, IOException {
        if (this.nextToken != null) {
            int idx = TokenStreamImpl.indexOf(suspected, this.nextToken.type);
            if (idx != -1) {
                this.previousToken = this.nextToken;
                this.nextToken = this.nextButOneToken;
                this.nextButOneToken = null;
            }
            return idx;
        }
        Token t2 = this.produceToken();
        int idx = TokenStreamImpl.indexOf(suspected, t2.type);
        if (idx != -1) {
            this.previousToken = t2;
            return idx;
        }
        this.nextToken = t2;
        return -1;
    }

    @Override
    public Location location() {
        return this.previousToken != null ? this.previousToken.getLocation() : this.scanner.location();
    }

    private static int indexOf(String[] strings, String subject) {
        for (int i = 0; i < strings.length; ++i) {
            if (!strings[i].equals(subject)) continue;
            return i;
        }
        return -1;
    }

    private static int indexOf(TokenType[] tta, TokenType subject) {
        for (int i = 0; i < tta.length; ++i) {
            if (!tta[i].equals((Object)subject)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public void setWarningHandler(@Nullable WarningHandler warningHandler) {
        this.warningHandler = warningHandler;
    }

    public String toString() {
        return this.nextToken + "/" + this.nextButOneToken + "/" + this.scanner.location();
    }

    private void warning(String handle2, String message, @Nullable Location location) throws CompileException {
        if (this.warningHandler != null) {
            this.warningHandler.handleWarning(handle2, message, location);
        }
    }

    protected final CompileException compileException(String message) {
        return new CompileException(message, this.scanner.location());
    }

    private static String join(@Nullable Object[] oa, String glue) {
        if (oa == null) {
            return "(null)";
        }
        if (oa.length == 0) {
            return "(zero length array)";
        }
        StringBuilder sb = new StringBuilder().append(oa[0]);
        for (int i = 1; i < oa.length; ++i) {
            sb.append(glue).append(oa[i]);
        }
        return sb.toString();
    }
}

