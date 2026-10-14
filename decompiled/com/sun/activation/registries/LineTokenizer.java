/*
 * Decompiled with CFR 0.152.
 */
package com.sun.activation.registries;

import java.util.NoSuchElementException;
import java.util.Vector;

class LineTokenizer {
    private int currentPosition = 0;
    private int maxPosition;
    private String str;
    private Vector stack = new Vector();
    private static final String singles = "=";

    public LineTokenizer(String str) {
        this.str = str;
        this.maxPosition = str.length();
    }

    private void skipWhiteSpace() {
        while (this.currentPosition < this.maxPosition && Character.isWhitespace(this.str.charAt(this.currentPosition))) {
            ++this.currentPosition;
        }
    }

    public boolean hasMoreTokens() {
        if (this.stack.size() > 0) {
            return true;
        }
        this.skipWhiteSpace();
        return this.currentPosition < this.maxPosition;
    }

    public String nextToken() {
        int start2;
        char c;
        int size = this.stack.size();
        if (size > 0) {
            String t2 = (String)this.stack.elementAt(size - 1);
            this.stack.removeElementAt(size - 1);
            return t2;
        }
        this.skipWhiteSpace();
        if (this.currentPosition >= this.maxPosition) {
            throw new NoSuchElementException();
        }
        if ((c = this.str.charAt(start2 = this.currentPosition++)) == '\"') {
            boolean filter2 = false;
            while (this.currentPosition < this.maxPosition) {
                String s2;
                if ((c = this.str.charAt(this.currentPosition++)) == '\\') {
                    ++this.currentPosition;
                    filter2 = true;
                    continue;
                }
                if (c != '\"') continue;
                if (filter2) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = start2 + 1; i < this.currentPosition - 1; ++i) {
                        c = this.str.charAt(i);
                        if (c == '\\') continue;
                        sb.append(c);
                    }
                    s2 = sb.toString();
                } else {
                    s2 = this.str.substring(start2 + 1, this.currentPosition - 1);
                }
                return s2;
            }
        } else if (singles.indexOf(c) >= 0) {
            ++this.currentPosition;
        } else {
            while (this.currentPosition < this.maxPosition && singles.indexOf(this.str.charAt(this.currentPosition)) < 0 && !Character.isWhitespace(this.str.charAt(this.currentPosition))) {
                ++this.currentPosition;
            }
        }
        return this.str.substring(start2, this.currentPosition);
    }

    public void pushToken(String token) {
        this.stack.addElement(token);
    }
}

