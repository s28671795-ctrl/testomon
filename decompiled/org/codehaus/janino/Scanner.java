/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.compiler.io.Readers;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Token;
import org.codehaus.janino.TokenType;
import org.codehaus.janino.UnicodeUnescapeException;
import org.codehaus.janino.UnicodeUnescapeReader;

public class Scanner {
    public static final String SYSTEM_PROPERTY_SOURCE_DEBUGGING_ENABLE = "org.codehaus.janino.source_debugging.enable";
    public static final String SYSTEM_PROPERTY_SOURCE_DEBUGGING_DIR = "org.codehaus.janino.source_debugging.dir";
    public static final String SYSTEM_PROPERTY_SOURCE_DEBUGGING_KEEP = "org.codehaus.janino.source_debugging.keep";
    private final StringBuilder sb = new StringBuilder();
    @Nullable
    private final String fileName;
    private final Reader in;
    private boolean ignoreWhiteSpace;
    private int nextChar = -1;
    private int nextButOneChar = -1;
    private boolean crLfPending;
    private int nextCharLineNumber;
    private int nextCharColumnNumber;
    private int tokenLineNumber;
    private int tokenColumnNumber;
    private static final Set<String> JAVA_KEYWORDS = new HashSet<String>(Arrays.asList("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"));
    private static final Set<String> JAVA_OPERATORS = new HashSet<String>(Arrays.asList("(", ")", "{", "}", "[", "]", ";", ",", ".", "@", "::", "=", ">", "<", "!", "~", "?", ":", "->", "==", "<=", ">=", "!=", "&&", "||", "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>="));

    @Deprecated
    public Scanner(String fileName) throws IOException {
        this(fileName, new FileInputStream(fileName));
    }

    @Deprecated
    public Scanner(String fileName, String encoding) throws IOException {
        this(fileName, new FileInputStream(fileName), encoding);
    }

    @Deprecated
    public Scanner(File file) throws IOException {
        this(file.getAbsolutePath(), new FileInputStream(file), null);
    }

    @Deprecated
    public Scanner(File file, @Nullable String encoding) throws IOException {
        this(file.getAbsolutePath(), new FileInputStream(file), encoding);
    }

    public Scanner(@Nullable String fileName, InputStream is) throws IOException {
        this(fileName, new InputStreamReader(is), 1, 0);
    }

    public Scanner(@Nullable String fileName, InputStream is, @Nullable String encoding) throws IOException {
        this(fileName, encoding == null ? new InputStreamReader(is) : new InputStreamReader(is, encoding), 1, 0);
    }

    public Scanner(@Nullable String fileName, Reader in) throws IOException {
        this(fileName, in, 1, 0);
    }

    public Scanner(@Nullable String fileName, Reader in, int initialLineNumber, int initialColumnNumber) throws IOException {
        if (fileName == null && Boolean.getBoolean(SYSTEM_PROPERTY_SOURCE_DEBUGGING_ENABLE)) {
            String dirName = System.getProperty(SYSTEM_PROPERTY_SOURCE_DEBUGGING_DIR);
            boolean keep = Boolean.getBoolean(SYSTEM_PROPERTY_SOURCE_DEBUGGING_KEEP);
            File dir = dirName == null ? null : new File(dirName);
            File temporaryFile = File.createTempFile("janino", ".java", dir);
            if (!keep) {
                temporaryFile.deleteOnExit();
            }
            in = Readers.teeReader(in, new FileWriter(temporaryFile), true);
            fileName = temporaryFile.getAbsolutePath();
        }
        this.fileName = fileName;
        this.in = new UnicodeUnescapeReader(in);
        this.nextCharLineNumber = initialLineNumber;
        this.nextCharColumnNumber = initialColumnNumber;
    }

    public void setIgnoreWhiteSpace(boolean value) {
        this.ignoreWhiteSpace = value;
    }

    @Nullable
    public String getFileName() {
        return this.fileName;
    }

    @Deprecated
    public void close() throws IOException {
        this.in.close();
    }

    public Location location() {
        return new Location(this.fileName, this.tokenLineNumber, this.tokenColumnNumber);
    }

    private Token token(TokenType type, String value) {
        return new Token(this.fileName, this.tokenLineNumber, this.tokenColumnNumber, type, value);
    }

    public Token produce() throws CompileException, IOException {
        if (this.peek() == -1) {
            return this.token(TokenType.END_OF_INPUT, "end-of-input");
        }
        if (this.ignoreWhiteSpace && Character.isWhitespace(this.peek())) {
            do {
                this.read();
                if (this.peek() != -1) continue;
                return this.token(TokenType.END_OF_INPUT, "end-of-input");
            } while (Character.isWhitespace(this.peek()));
        }
        this.tokenLineNumber = this.nextCharLineNumber;
        this.tokenColumnNumber = this.nextCharColumnNumber;
        this.sb.setLength(0);
        TokenType tokenType = this.scan();
        String tokenValue = this.sb.toString();
        if (tokenType == TokenType.KEYWORD || tokenType == TokenType.BOOLEAN_LITERAL || tokenType == TokenType.NULL_LITERAL || tokenType == TokenType.OPERATOR) {
            tokenValue = tokenValue.intern();
        }
        return this.token(tokenType, tokenValue);
    }

    private TokenType scan() throws CompileException, IOException {
        if (Character.isWhitespace(this.peek())) {
            do {
                this.read();
            } while (this.peek() != -1 && Character.isWhitespace(this.peek()));
            return TokenType.WHITE_SPACE;
        }
        if (this.peekRead(47)) {
            if (this.peekRead(-1)) {
                return TokenType.OPERATOR;
            }
            if (this.peekRead(61)) {
                return TokenType.OPERATOR;
            }
            if (this.peekRead(47)) {
                while (!this.peek("\r\n")) {
                    this.read();
                }
                return TokenType.C_PLUS_PLUS_STYLE_COMMENT;
            }
            if (this.peekRead(42)) {
                boolean asteriskPending = false;
                while (true) {
                    if (this.peek() == -1) {
                        throw new CompileException("Unexpected end-of-input in C-style comment", this.location());
                    }
                    char c = this.read();
                    if (asteriskPending) {
                        if (c == '/') {
                            return TokenType.C_STYLE_COMMENT;
                        }
                        if (c == '*') continue;
                        asteriskPending = false;
                        continue;
                    }
                    if (c != '*') continue;
                    asteriskPending = true;
                }
            }
            return TokenType.OPERATOR;
        }
        if (Character.isJavaIdentifierStart((char)this.peek())) {
            this.read();
            while (Character.isJavaIdentifierPart((char)this.peek())) {
                this.read();
            }
            String s2 = this.sb.toString();
            if ("true".equals(s2)) {
                return TokenType.BOOLEAN_LITERAL;
            }
            if ("false".equals(s2)) {
                return TokenType.BOOLEAN_LITERAL;
            }
            if ("null".equals(s2)) {
                return TokenType.NULL_LITERAL;
            }
            if (JAVA_KEYWORDS.contains(s2)) {
                return TokenType.KEYWORD;
            }
            return TokenType.IDENTIFIER;
        }
        if (Character.isDigit((char)this.peek()) || this.peek() == 46 && Character.isDigit(this.peekButOne())) {
            return this.scanNumericLiteral();
        }
        if (this.peekRead(34)) {
            while (!this.peekRead(34)) {
                this.scanLiteralCharacter();
            }
            return TokenType.STRING_LITERAL;
        }
        if (this.peekRead(39)) {
            if (this.peek() == 39) {
                throw new CompileException("Single quote must be backslash-escaped in character literal", this.location());
            }
            this.scanLiteralCharacter();
            if (!this.peekRead(39)) {
                throw new CompileException("Closing single quote missing", this.location());
            }
            return TokenType.CHARACTER_LITERAL;
        }
        if (JAVA_OPERATORS.contains(String.valueOf((char)this.peek()))) {
            do {
                this.read();
            } while (JAVA_OPERATORS.contains(this.sb.toString() + (char)this.peek()));
            return TokenType.OPERATOR;
        }
        throw new CompileException("Invalid character input \"" + (char)this.peek() + "\" (character code " + this.peek() + ")", this.location());
    }

    private TokenType scanNumericLiteral() throws CompileException, IOException {
        if (this.peekRead(48)) {
            if (Scanner.isOctalDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isOctalDigit(this.peekButOne()))) {
                this.read();
                while (Scanner.isOctalDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isOctalDigit(this.peekButOne()))) {
                    this.read();
                }
                if (this.peek("89")) {
                    throw new CompileException("Digit '" + (char)this.peek() + "' not allowed in octal literal", this.location());
                }
                if (this.peekRead("lL")) {
                    return TokenType.INTEGER_LITERAL;
                }
                return TokenType.INTEGER_LITERAL;
            }
            if (this.peekRead("lL")) {
                return TokenType.INTEGER_LITERAL;
            }
            if (this.peekRead("fFdD")) {
                return TokenType.FLOATING_POINT_LITERAL;
            }
            if (this.peek(".Ee")) {
                if (this.peekRead(46)) {
                    while (Scanner.isDecimalDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isDecimalDigit(this.peekButOne()))) {
                        this.read();
                    }
                }
                if (this.peekRead("eE")) {
                    this.peekRead("-+");
                    if (!Scanner.isDecimalDigit(this.peek())) {
                        throw new CompileException("Exponent missing after \"E\"", this.location());
                    }
                    this.read();
                    while (Scanner.isDecimalDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isDecimalDigit(this.peekButOne()))) {
                        this.read();
                    }
                }
                this.peekRead("fFdD");
                return TokenType.FLOATING_POINT_LITERAL;
            }
            if (this.peekRead("xX")) {
                while (Scanner.isHexDigit(this.peek())) {
                    this.read();
                }
                while (Scanner.isHexDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isHexDigit(this.peekButOne()))) {
                    this.read();
                }
                if (this.peek(".pP")) {
                    if (this.peekRead(46) && Scanner.isHexDigit(this.peek())) {
                        this.read();
                        while (Scanner.isHexDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isHexDigit(this.peekButOne()))) {
                            this.read();
                        }
                    }
                    if (!this.peekRead("pP")) {
                        throw new CompileException("\"p\" missing in hexadecimal floating-point literal", this.location());
                    }
                    this.peekRead("-+");
                    if (!Scanner.isDecimalDigit(this.peek())) {
                        throw new CompileException("Unexpected character \"" + (char)this.peek() + "\" in hexadecimal floating point literal", this.location());
                    }
                    this.read();
                    while (Scanner.isDecimalDigit(this.peek()) || this.peek() == 95 && (Scanner.isDecimalDigit(this.peekButOne()) || this.peekButOne() == 95)) {
                        this.read();
                    }
                    this.peekRead("fFdD");
                    return TokenType.FLOATING_POINT_LITERAL;
                }
                if (this.peekRead("lL")) {
                    return TokenType.INTEGER_LITERAL;
                }
                return TokenType.INTEGER_LITERAL;
            }
            if (this.peekRead("bB")) {
                if (!Scanner.isBinaryDigit(this.peek())) {
                    throw new CompileException("Binary digit expected after \"0b\"", this.location());
                }
                this.read();
                while (Scanner.isBinaryDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isBinaryDigit(this.peekButOne()))) {
                    this.read();
                }
                if (this.peekRead("lL")) {
                    return TokenType.INTEGER_LITERAL;
                }
                return TokenType.INTEGER_LITERAL;
            }
            return TokenType.INTEGER_LITERAL;
        }
        if (this.peek() != 46 || !Scanner.isDecimalDigit(this.peekButOne())) {
            if (Scanner.isDecimalDigit(this.peek())) {
                this.read();
                while (Scanner.isDecimalDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isDecimalDigit(this.peekButOne()))) {
                    this.read();
                }
                if (this.peekRead("lL")) {
                    return TokenType.INTEGER_LITERAL;
                }
                if (this.peekRead("fFdD")) {
                    return TokenType.FLOATING_POINT_LITERAL;
                }
                if (!this.peek(".eE")) {
                    return TokenType.INTEGER_LITERAL;
                }
            } else {
                throw new CompileException("Numeric literal begins with invalid character '" + (char)this.peek() + "'", this.location());
            }
        }
        if (this.peekRead(46) && Scanner.isDecimalDigit(this.peek())) {
            this.read();
            while (Scanner.isDecimalDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isDecimalDigit(this.peekButOne()))) {
                this.read();
            }
        }
        if (this.peekRead("eE")) {
            this.peekRead("-+");
            if (!Scanner.isDecimalDigit(this.peek())) {
                throw new CompileException("Exponent missing after \"E\"", this.location());
            }
            this.read();
            while (Scanner.isDecimalDigit(this.peek()) || this.peek() == 95 && (this.peekButOne() == 95 || Scanner.isDecimalDigit(this.peekButOne()))) {
                this.read();
            }
        }
        this.peekRead("fFdD");
        return TokenType.FLOATING_POINT_LITERAL;
    }

    private static boolean isDecimalDigit(int c) {
        return c >= 48 && c <= 57;
    }

    private static boolean isHexDigit(int c) {
        return c >= 48 && c <= 57 || c >= 65 && c <= 70 || c >= 97 && c <= 102;
    }

    private static boolean isOctalDigit(int c) {
        return c >= 48 && c <= 55;
    }

    private static boolean isBinaryDigit(int c) {
        return c == 48 || c == 49;
    }

    private void scanLiteralCharacter() throws CompileException, IOException {
        if (this.peek() == -1) {
            throw new CompileException("EOF in literal", this.location());
        }
        if (this.peek() == 13 || this.peek() == 10) {
            throw new CompileException("Line break in literal not allowed", this.location());
        }
        if (!this.peekRead(92)) {
            this.read();
            return;
        }
        int idx = "btnfr\"'\\".indexOf(this.peek());
        if (idx != -1) {
            this.read();
            return;
        }
        if (this.peek("01234567")) {
            char firstChar = this.read();
            if (!this.peekRead("01234567")) {
                return;
            }
            if (!this.peekRead("01234567")) {
                return;
            }
            if ("0123".indexOf(firstChar) == -1) {
                throw new CompileException("Invalid octal escape", this.location());
            }
            return;
        }
        throw new CompileException("Invalid escape sequence", this.location());
    }

    private int peek() throws CompileException, IOException {
        if (this.nextChar != -1) {
            return this.nextChar;
        }
        try {
            this.nextChar = this.internalRead();
            return this.nextChar;
        }
        catch (UnicodeUnescapeException ex) {
            throw new CompileException(ex.getMessage(), this.location(), ex);
        }
    }

    private boolean peek(String expectedCharacters) throws CompileException, IOException {
        return expectedCharacters.indexOf((char)this.peek()) != -1;
    }

    private int peekButOne() throws CompileException, IOException {
        if (this.nextButOneChar != -1) {
            return this.nextButOneChar;
        }
        this.peek();
        try {
            this.nextButOneChar = this.internalRead();
            return this.nextButOneChar;
        }
        catch (UnicodeUnescapeException ex) {
            throw new CompileException(ex.getMessage(), this.location(), ex);
        }
    }

    private char read() throws CompileException, IOException {
        this.peek();
        if (this.nextChar == -1) {
            throw new CompileException("Unexpected end-of-input", this.location());
        }
        char result = (char)this.nextChar;
        this.sb.append(result);
        this.nextChar = this.nextButOneChar;
        this.nextButOneChar = -1;
        return result;
    }

    private boolean peekRead(int expected) throws CompileException, IOException {
        if (this.peek() == expected) {
            if (this.nextChar != -1) {
                this.sb.append((char)this.nextChar);
            }
            this.nextChar = this.nextButOneChar;
            this.nextButOneChar = -1;
            return true;
        }
        return false;
    }

    private boolean peekRead(String expectedCharacters) throws CompileException, IOException {
        if (this.peek() == -1) {
            return false;
        }
        if (expectedCharacters.indexOf((char)this.nextChar) == -1) {
            return false;
        }
        this.sb.append((char)this.nextChar);
        this.nextChar = this.nextButOneChar;
        this.nextButOneChar = -1;
        return true;
    }

    private int internalRead() throws IOException, CompileException {
        int result;
        try {
            result = this.in.read();
        }
        catch (UnicodeUnescapeException ex) {
            throw new CompileException(ex.getMessage(), this.location(), ex);
        }
        if (result == 13) {
            ++this.nextCharLineNumber;
            this.nextCharColumnNumber = 0;
            this.crLfPending = true;
        } else if (result == 10) {
            if (this.crLfPending) {
                this.crLfPending = false;
            } else {
                ++this.nextCharLineNumber;
                this.nextCharColumnNumber = 0;
            }
        } else if (result == 9) {
            this.nextCharColumnNumber = this.nextCharColumnNumber - this.nextCharColumnNumber % 8 + 8;
            this.crLfPending = false;
        } else {
            ++this.nextCharColumnNumber;
            this.crLfPending = false;
        }
        return result;
    }
}

