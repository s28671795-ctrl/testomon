/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

abstract class Splitter {
    protected StringBuilder builder = new StringBuilder();
    protected char[] text;
    protected int count;
    protected int off;

    public Splitter(String source2) {
        this.text = source2.toCharArray();
        this.count = this.text.length;
    }

    public String process() {
        while (this.off < this.count) {
            char ch;
            while (this.off < this.count && this.isSpecial(ch = this.text[this.off])) {
                ++this.off;
            }
            if (this.acronym()) continue;
            this.token();
            this.number();
        }
        return this.builder.toString();
    }

    private void token() {
        char ch;
        int mark;
        for (mark = this.off; mark < this.count && this.isLetter(ch = this.text[mark]) && (mark <= this.off || !this.isUpper(ch)); ++mark) {
        }
        if (mark > this.off) {
            this.parse(this.text, this.off, mark - this.off);
            this.commit(this.text, this.off, mark - this.off);
        }
        this.off = mark;
    }

    private boolean acronym() {
        char ch;
        int mark;
        int size = 0;
        for (mark = this.off; mark < this.count && this.isUpper(ch = this.text[mark]); ++mark) {
            ++size;
        }
        if (size > 1) {
            if (mark < this.count && this.isUpper(ch = this.text[mark - 1])) {
                --mark;
            }
            this.commit(this.text, this.off, mark - this.off);
            this.off = mark;
        }
        return size > 1;
    }

    private boolean number() {
        char ch;
        int mark;
        int size = 0;
        for (mark = this.off; mark < this.count && this.isDigit(ch = this.text[mark]); ++mark) {
            ++size;
        }
        if (size > 0) {
            this.commit(this.text, this.off, mark - this.off);
        }
        this.off = mark;
        return size > 0;
    }

    private boolean isLetter(char ch) {
        return Character.isLetter(ch);
    }

    private boolean isSpecial(char ch) {
        return !Character.isLetterOrDigit(ch);
    }

    private boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    private boolean isUpper(char ch) {
        return Character.isUpperCase(ch);
    }

    protected char toUpper(char ch) {
        return Character.toUpperCase(ch);
    }

    protected char toLower(char ch) {
        return Character.toLowerCase(ch);
    }

    protected abstract void parse(char[] var1, int var2, int var3);

    protected abstract void commit(char[] var1, int var2, int var3);
}

