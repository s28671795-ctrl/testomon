/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.Arrays;
import java.util.NoSuchElementException;
import org.h2.util.Utils;

public final class ByteStack {
    private static final int MAX_ARRAY_SIZE = 0x7FFFFFF7;
    private int size;
    private byte[] array = Utils.EMPTY_BYTES;

    public void push(byte by) {
        int n = this.size;
        int n2 = this.array.length;
        if (n >= n2) {
            this.grow(n2);
        }
        this.array[n] = by;
        this.size = n + 1;
    }

    public byte pop() {
        int n = this.size - 1;
        if (n < 0) {
            throw new NoSuchElementException();
        }
        this.size = n;
        return this.array[n];
    }

    public int poll(int n) {
        int n2 = this.size - 1;
        if (n2 < 0) {
            return n;
        }
        this.size = n2;
        return this.array[n2];
    }

    public int peek(int n) {
        int n2 = this.size - 1;
        if (n2 < 0) {
            return n;
        }
        return this.array[n2];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    private void grow(int n) {
        if (n == 0) {
            n = 16;
        } else {
            if (n >= 0x7FFFFFF7) {
                throw new OutOfMemoryError();
            }
            if ((n <<= 1) < 0) {
                n = 0x7FFFFFF7;
            }
        }
        this.array = Arrays.copyOf(this.array, n);
    }
}

