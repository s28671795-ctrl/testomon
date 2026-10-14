/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.uri.internal;

import java.util.NoSuchElementException;

final class CharacterIterator {
    private int pos;
    private String s;

    public CharacterIterator(String s2) {
        this.s = s2;
        this.pos = -1;
    }

    public boolean hasNext() {
        return this.pos < this.s.length() - 1;
    }

    public char next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.s.charAt(++this.pos);
    }

    public char peek() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.s.charAt(this.pos + 1);
    }

    public int pos() {
        return this.pos;
    }

    public String getInput() {
        return this.s;
    }

    public void setPosition(int newPosition) {
        if (newPosition > this.s.length() - 1) {
            throw new IndexOutOfBoundsException("Given position " + newPosition + " is outside the input string range.");
        }
        this.pos = newPosition;
    }

    public char current() {
        if (this.pos == -1) {
            throw new IllegalStateException("Iterator not used yet.");
        }
        return this.s.charAt(this.pos);
    }
}

