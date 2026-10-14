/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.io.BufferedWriter;
import java.io.Writer;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Indenter;
import org.simpleframework.xml.stream.Mode;
import org.simpleframework.xml.stream.NodeException;
import org.simpleframework.xml.stream.OutputBuffer;

class Formatter {
    private static final char[] NAMESPACE = new char[]{'x', 'm', 'l', 'n', 's'};
    private static final char[] LESS = new char[]{'&', 'l', 't', ';'};
    private static final char[] GREATER = new char[]{'&', 'g', 't', ';'};
    private static final char[] DOUBLE = new char[]{'&', 'q', 'u', 'o', 't', ';'};
    private static final char[] SINGLE = new char[]{'&', 'a', 'p', 'o', 's', ';'};
    private static final char[] AND = new char[]{'&', 'a', 'm', 'p', ';'};
    private static final char[] OPEN = new char[]{'<', '!', '-', '-', ' '};
    private static final char[] CLOSE = new char[]{' ', '-', '-', '>'};
    private OutputBuffer buffer;
    private Indenter indenter;
    private Writer result;
    private String prolog;
    private Tag last;

    public Formatter(Writer result, Format format) {
        this.result = new BufferedWriter(result, 1024);
        this.indenter = new Indenter(format);
        this.buffer = new OutputBuffer();
        this.prolog = format.getProlog();
    }

    public void writeProlog() throws Exception {
        if (this.prolog != null) {
            this.write(this.prolog);
            this.write("\n");
        }
    }

    public void writeComment(String comment) throws Exception {
        String text = this.indenter.top();
        if (this.last == Tag.START) {
            this.append('>');
        }
        if (text != null) {
            this.append(text);
            this.append(OPEN);
            this.append(comment);
            this.append(CLOSE);
        }
        this.last = Tag.COMMENT;
    }

    public void writeStart(String name, String prefix) throws Exception {
        String text = this.indenter.push();
        if (this.last == Tag.START) {
            this.append('>');
        }
        this.flush();
        this.append(text);
        this.append('<');
        if (!this.isEmpty(prefix)) {
            this.append(prefix);
            this.append(':');
        }
        this.append(name);
        this.last = Tag.START;
    }

    public void writeAttribute(String name, String value, String prefix) throws Exception {
        if (this.last != Tag.START) {
            throw new NodeException("Start element required");
        }
        this.write(' ');
        this.write(name, prefix);
        this.write('=');
        this.write('\"');
        this.escape(value);
        this.write('\"');
    }

    public void writeNamespace(String reference, String prefix) throws Exception {
        if (this.last != Tag.START) {
            throw new NodeException("Start element required");
        }
        this.write(' ');
        this.write(NAMESPACE);
        if (!this.isEmpty(prefix)) {
            this.write(':');
            this.write(prefix);
        }
        this.write('=');
        this.write('\"');
        this.escape(reference);
        this.write('\"');
    }

    public void writeText(String text) throws Exception {
        this.writeText(text, Mode.ESCAPE);
    }

    public void writeText(String text, Mode mode) throws Exception {
        if (this.last == Tag.START) {
            this.write('>');
        }
        if (mode == Mode.DATA) {
            this.data(text);
        } else {
            this.escape(text);
        }
        this.last = Tag.TEXT;
    }

    public void writeEnd(String name, String prefix) throws Exception {
        String text = this.indenter.pop();
        if (this.last == Tag.START) {
            this.write('/');
            this.write('>');
        } else {
            if (this.last != Tag.TEXT) {
                this.write(text);
            }
            if (this.last != Tag.START) {
                this.write('<');
                this.write('/');
                this.write(name, prefix);
                this.write('>');
            }
        }
        this.last = Tag.END;
    }

    private void write(char ch) throws Exception {
        this.buffer.write(this.result);
        this.buffer.clear();
        this.result.write(ch);
    }

    private void write(char[] plain) throws Exception {
        this.buffer.write(this.result);
        this.buffer.clear();
        this.result.write(plain);
    }

    private void write(String plain) throws Exception {
        this.buffer.write(this.result);
        this.buffer.clear();
        this.result.write(plain);
    }

    private void write(String plain, String prefix) throws Exception {
        this.buffer.write(this.result);
        this.buffer.clear();
        if (!this.isEmpty(prefix)) {
            this.result.write(prefix);
            this.result.write(58);
        }
        this.result.write(plain);
    }

    private void append(char ch) throws Exception {
        this.buffer.append(ch);
    }

    private void append(char[] plain) throws Exception {
        this.buffer.append(plain);
    }

    private void append(String plain) throws Exception {
        this.buffer.append(plain);
    }

    private void data(String value) throws Exception {
        this.write("<![CDATA[");
        this.write(value);
        this.write("]]>");
    }

    private void escape(String value) throws Exception {
        int size = value.length();
        for (int i = 0; i < size; ++i) {
            this.escape(value.charAt(i));
        }
    }

    private void escape(char ch) throws Exception {
        char[] text = this.symbol(ch);
        if (text != null) {
            this.write(text);
        } else {
            this.write(ch);
        }
    }

    public void flush() throws Exception {
        this.buffer.write(this.result);
        this.buffer.clear();
        this.result.flush();
    }

    private String unicode(char ch) {
        return Integer.toString(ch);
    }

    private boolean isEmpty(String value) {
        if (value != null) {
            return value.length() == 0;
        }
        return true;
    }

    private boolean isText(char ch) {
        switch (ch) {
            case '\t': 
            case '\n': 
            case '\r': 
            case ' ': {
                return true;
            }
        }
        if (ch > ' ' && ch <= '~') {
            return ch != '\u00f7';
        }
        return false;
    }

    private char[] symbol(char ch) {
        switch (ch) {
            case '<': {
                return LESS;
            }
            case '>': {
                return GREATER;
            }
            case '\"': {
                return DOUBLE;
            }
            case '\'': {
                return SINGLE;
            }
            case '&': {
                return AND;
            }
        }
        return null;
    }

    private static enum Tag {
        COMMENT,
        START,
        TEXT,
        END;

    }
}

