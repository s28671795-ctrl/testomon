/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.util.Arrays;

public class IntArray {
    private int[] data;
    private int size;
    private int hash;

    public IntArray() {
        this(10);
    }

    public IntArray(int n) {
        this.data = new int[n];
    }

    public IntArray(int[] nArray) {
        this.data = nArray;
        this.size = nArray.length;
    }

    public void add(int n) {
        if (this.size >= this.data.length) {
            this.ensureCapacity(this.size + this.size);
        }
        this.data[this.size++] = n;
    }

    public int get(int n) {
        if (n >= this.size) {
            throw new ArrayIndexOutOfBoundsException("i=" + n + " size=" + this.size);
        }
        return this.data[n];
    }

    public void remove(int n) {
        if (n >= this.size) {
            throw new ArrayIndexOutOfBoundsException("i=" + n + " size=" + this.size);
        }
        System.arraycopy(this.data, n + 1, this.data, n, this.size - n - 1);
        --this.size;
    }

    public void ensureCapacity(int n) {
        if ((n = Math.max(4, n)) >= this.data.length) {
            this.data = Arrays.copyOf(this.data, n);
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof IntArray)) {
            return false;
        }
        IntArray intArray = (IntArray)object;
        if (this.hashCode() != intArray.hashCode() || this.size != intArray.size) {
            return false;
        }
        for (int i = 0; i < this.size; ++i) {
            if (this.data[i] == intArray.data[i]) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.hash != 0) {
            return this.hash;
        }
        int n = this.size + 1;
        for (int i = 0; i < this.size; ++i) {
            n = n * 31 + this.data[i];
        }
        this.hash = n;
        return n;
    }

    public int size() {
        return this.size;
    }

    public void toArray(int[] nArray) {
        System.arraycopy(this.data, 0, nArray, 0, this.size);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (int i = 0; i < this.size; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.data[i]);
        }
        return stringBuilder.append('}').toString();
    }

    public void removeRange(int n, int n2) {
        if (n > n2 || n2 > this.size) {
            throw new ArrayIndexOutOfBoundsException("from=" + n + " to=" + n2 + " size=" + this.size);
        }
        System.arraycopy(this.data, n2, this.data, n, this.size - n2);
        this.size -= n2 - n;
    }
}

