/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Format;

class Indenter {
    private Cache cache;
    private int indent;
    private int count;
    private int index;

    public Indenter() {
        this(new Format());
    }

    public Indenter(Format format) {
        this(format, 16);
    }

    private Indenter(Format format, int size) {
        this.indent = format.getIndent();
        this.cache = new Cache(size);
    }

    public String top() {
        return this.indent(this.index);
    }

    public String push() {
        String text = this.indent(this.index++);
        if (this.indent > 0) {
            this.count += this.indent;
        }
        return text;
    }

    public String pop() {
        String text = this.indent(--this.index);
        if (this.indent > 0) {
            this.count -= this.indent;
        }
        return text;
    }

    private String indent(int index) {
        if (this.indent > 0) {
            String text = this.cache.get(index);
            if (text == null) {
                text = this.create();
                this.cache.set(index, text);
            }
            if (this.cache.size() > 0) {
                return text;
            }
        }
        return "";
    }

    private String create() {
        char[] text = new char[this.count + 1];
        if (this.count > 0) {
            text[0] = 10;
            for (int i = 1; i <= this.count; ++i) {
                text[i] = 32;
            }
            return new String(text);
        }
        return "\n";
    }

    private static class Cache {
        private String[] list;
        private int count;

        public Cache(int size) {
            this.list = new String[size];
        }

        public int size() {
            return this.count;
        }

        public void set(int index, String text) {
            if (index >= this.list.length) {
                this.resize(index * 2);
            }
            if (index > this.count) {
                this.count = index;
            }
            this.list[index] = text;
        }

        public String get(int index) {
            if (index < this.list.length) {
                return this.list[index];
            }
            return null;
        }

        private void resize(int size) {
            String[] temp = new String[size];
            for (int i = 0; i < this.list.length; ++i) {
                temp[i] = this.list[i];
            }
            this.list = temp;
        }
    }
}

