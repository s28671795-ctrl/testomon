/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.internal;

import io.netty.util.internal.ObjectUtil;
import java.util.Arrays;

public final class AppendableCharSequence
implements CharSequence,
Appendable {
    private char[] chars;
    private int pos;

    public AppendableCharSequence(int length) {
        this.chars = new char[ObjectUtil.checkPositive(length, "length")];
    }

    private AppendableCharSequence(char[] chars) {
        this.chars = ObjectUtil.checkNonEmpty(chars, "chars");
        this.pos = chars.length;
    }

    public void setLength(int length) {
        if (length < 0 || length > this.pos) {
            throw new IllegalArgumentException("length: " + length + " (length: >= 0, <= " + this.pos + ')');
        }
        this.pos = length;
    }

    @Override
    public int length() {
        return this.pos;
    }

    @Override
    public char charAt(int index) {
        if (index > this.pos) {
            throw new IndexOutOfBoundsException();
        }
        return this.chars[index];
    }

    public char charAtUnsafe(int index) {
        return this.chars[index];
    }

    @Override
    public AppendableCharSequence subSequence(int start2, int end) {
        if (start2 == end) {
            return new AppendableCharSequence(Math.min(16, this.chars.length));
        }
        return new AppendableCharSequence(Arrays.copyOfRange(this.chars, start2, end));
    }

    @Override
    public AppendableCharSequence append(char c) {
        if (this.pos == this.chars.length) {
            char[] old = this.chars;
            this.chars = new char[old.length << 1];
            System.arraycopy(old, 0, this.chars, 0, old.length);
        }
        this.chars[this.pos++] = c;
        return this;
    }

    @Override
    public AppendableCharSequence append(CharSequence csq) {
        return this.append(csq, 0, csq.length());
    }

    @Override
    public AppendableCharSequence append(CharSequence csq, int start2, int end) {
        if (csq.length() < end) {
            throw new IndexOutOfBoundsException("expected: csq.length() >= (" + end + "),but actual is (" + csq.length() + ")");
        }
        int length = end - start2;
        if (length > this.chars.length - this.pos) {
            this.chars = AppendableCharSequence.expand(this.chars, this.pos + length, this.pos);
        }
        if (csq instanceof AppendableCharSequence) {
            AppendableCharSequence seq = (AppendableCharSequence)csq;
            char[] src = seq.chars;
            System.arraycopy(src, start2, this.chars, this.pos, length);
            this.pos += length;
            return this;
        }
        for (int i = start2; i < end; ++i) {
            this.chars[this.pos++] = csq.charAt(i);
        }
        return this;
    }

    public void reset() {
        this.pos = 0;
    }

    @Override
    public String toString() {
        return new String(this.chars, 0, this.pos);
    }

    public String substring(int start2, int end) {
        int length = end - start2;
        if (start2 > this.pos || length > this.pos) {
            throw new IndexOutOfBoundsException("expected: start and length <= (" + this.pos + ")");
        }
        return new String(this.chars, start2, length);
    }

    public String subStringUnsafe(int start2, int end) {
        return new String(this.chars, start2, end - start2);
    }

    private static char[] expand(char[] array, int neededSpace, int size) {
        int newCapacity = array.length;
        do {
            if ((newCapacity <<= 1) >= 0) continue;
            throw new IllegalStateException();
        } while (neededSpace > newCapacity);
        char[] newArray = new char[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }
}

