/*
 * Decompiled with CFR 0.152.
 */
package javax.mail.internet;

import javax.mail.internet.ParseException;

public class HeaderTokenizer {
    private String string;
    private boolean skipComments;
    private String delimiters;
    private int currentPos;
    private int maxPos;
    private int nextPos;
    private int peekPos;
    public static final String RFC822 = "()<>@,;:\\\"\t .[]";
    public static final String MIME = "()<>@,;:\\\"\t []/?=";
    private static final Token EOFToken = new Token(-4, null);

    public HeaderTokenizer(String header, String delimiters, boolean skipComments) {
        this.string = header == null ? "" : header;
        this.skipComments = skipComments;
        this.delimiters = delimiters;
        this.peekPos = 0;
        this.nextPos = 0;
        this.currentPos = 0;
        this.maxPos = this.string.length();
    }

    public HeaderTokenizer(String header, String delimiters) {
        this(header, delimiters, true);
    }

    public HeaderTokenizer(String header) {
        this(header, RFC822);
    }

    public Token next() throws ParseException {
        return this.next('\u0000', false);
    }

    public Token next(char endOfAtom) throws ParseException {
        return this.next(endOfAtom, false);
    }

    public Token next(char endOfAtom, boolean keepEscapes) throws ParseException {
        this.currentPos = this.nextPos;
        Token tk = this.getNext(endOfAtom, keepEscapes);
        this.nextPos = this.peekPos = this.currentPos;
        return tk;
    }

    public Token peek() throws ParseException {
        this.currentPos = this.peekPos;
        Token tk = this.getNext('\u0000', false);
        this.peekPos = this.currentPos;
        return tk;
    }

    public String getRemainder() {
        if (this.nextPos >= this.string.length()) {
            return null;
        }
        return this.string.substring(this.nextPos);
    }

    private Token getNext(char endOfAtom, boolean keepEscapes) throws ParseException {
        int start2;
        if (this.currentPos >= this.maxPos) {
            return EOFToken;
        }
        if (this.skipWhiteSpace() == -4) {
            return EOFToken;
        }
        boolean filter2 = false;
        char c = this.string.charAt(this.currentPos);
        while (c == '(') {
            start2 = ++this.currentPos;
            int nesting = 1;
            while (nesting > 0 && this.currentPos < this.maxPos) {
                c = this.string.charAt(this.currentPos);
                if (c == '\\') {
                    ++this.currentPos;
                    filter2 = true;
                } else if (c == '\r') {
                    filter2 = true;
                } else if (c == '(') {
                    ++nesting;
                } else if (c == ')') {
                    --nesting;
                }
                ++this.currentPos;
            }
            if (nesting != 0) {
                throw new ParseException("Unbalanced comments");
            }
            if (!this.skipComments) {
                String s2 = filter2 ? HeaderTokenizer.filterToken(this.string, start2, this.currentPos - 1, keepEscapes) : this.string.substring(start2, this.currentPos - 1);
                return new Token(-3, s2);
            }
            if (this.skipWhiteSpace() == -4) {
                return EOFToken;
            }
            c = this.string.charAt(this.currentPos);
        }
        if (c == '\"') {
            ++this.currentPos;
            return this.collectString('\"', keepEscapes);
        }
        if (c < ' ' || c >= '\u007f' || this.delimiters.indexOf(c) >= 0) {
            if (endOfAtom > '\u0000' && c != endOfAtom) {
                return this.collectString(endOfAtom, keepEscapes);
            }
            ++this.currentPos;
            char[] ch = new char[]{c};
            return new Token(c, new String(ch));
        }
        start2 = this.currentPos;
        while (this.currentPos < this.maxPos) {
            c = this.string.charAt(this.currentPos);
            if (c < ' ' || c >= '\u007f' || c == '(' || c == ' ' || c == '\"' || this.delimiters.indexOf(c) >= 0) {
                if (endOfAtom <= '\u0000' || c == endOfAtom) break;
                this.currentPos = start2;
                return this.collectString(endOfAtom, keepEscapes);
            }
            ++this.currentPos;
        }
        return new Token(-1, this.string.substring(start2, this.currentPos));
    }

    private Token collectString(char eos, boolean keepEscapes) throws ParseException {
        boolean filter2 = false;
        int start2 = this.currentPos;
        while (this.currentPos < this.maxPos) {
            char c = this.string.charAt(this.currentPos);
            if (c == '\\') {
                ++this.currentPos;
                filter2 = true;
            } else if (c == '\r') {
                filter2 = true;
            } else if (c == eos) {
                ++this.currentPos;
                String s2 = filter2 ? HeaderTokenizer.filterToken(this.string, start2, this.currentPos - 1, keepEscapes) : this.string.substring(start2, this.currentPos - 1);
                if (c != '\"') {
                    s2 = HeaderTokenizer.trimWhiteSpace(s2);
                    --this.currentPos;
                }
                return new Token(-2, s2);
            }
            ++this.currentPos;
        }
        if (eos == '\"') {
            throw new ParseException("Unbalanced quoted string");
        }
        String s3 = filter2 ? HeaderTokenizer.filterToken(this.string, start2, this.currentPos, keepEscapes) : this.string.substring(start2, this.currentPos);
        s3 = HeaderTokenizer.trimWhiteSpace(s3);
        return new Token(-2, s3);
    }

    private int skipWhiteSpace() {
        while (this.currentPos < this.maxPos) {
            char c = this.string.charAt(this.currentPos);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return this.currentPos;
            }
            ++this.currentPos;
        }
        return -4;
    }

    private static String trimWhiteSpace(String s2) {
        char c;
        int i;
        for (i = s2.length() - 1; i >= 0 && ((c = s2.charAt(i)) == ' ' || c == '\t' || c == '\r' || c == '\n'); --i) {
        }
        if (i <= 0) {
            return "";
        }
        return s2.substring(0, i + 1);
    }

    private static String filterToken(String s2, int start2, int end, boolean keepEscapes) {
        StringBuffer sb = new StringBuffer();
        boolean gotEscape = false;
        boolean gotCR = false;
        for (int i = start2; i < end; ++i) {
            char c = s2.charAt(i);
            if (c == '\n' && gotCR) {
                gotCR = false;
                continue;
            }
            gotCR = false;
            if (!gotEscape) {
                if (c == '\\') {
                    gotEscape = true;
                    continue;
                }
                if (c == '\r') {
                    gotCR = true;
                    continue;
                }
                sb.append(c);
                continue;
            }
            if (keepEscapes) {
                sb.append('\\');
            }
            sb.append(c);
            gotEscape = false;
        }
        return sb.toString();
    }

    public static class Token {
        private int type;
        private String value;
        public static final int ATOM = -1;
        public static final int QUOTEDSTRING = -2;
        public static final int COMMENT = -3;
        public static final int EOF = -4;

        public Token(int type, String value) {
            this.type = type;
            this.value = value;
        }

        public int getType() {
            return this.type;
        }

        public String getValue() {
            return this.value;
        }
    }
}

