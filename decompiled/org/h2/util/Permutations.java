/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import org.h2.message.DbException;

public class Permutations<T> {
    private final T[] in;
    private final T[] out;
    private final int n;
    private final int m;
    private final int[] index;
    private boolean hasNext = true;

    private Permutations(T[] TArray, T[] TArray2, int n) {
        this.n = TArray.length;
        this.m = n;
        if (this.n < n || n < 0) {
            throw DbException.getInternalError("n < m or m < 0");
        }
        this.in = TArray;
        this.out = TArray2;
        this.index = new int[this.n];
        for (int i = 0; i < this.n; ++i) {
            this.index[i] = i;
        }
        this.reverseAfter(n - 1);
    }

    public static <T> Permutations<T> create(T[] TArray, T[] TArray2) {
        return new Permutations<T>(TArray, TArray2, TArray.length);
    }

    public static <T> Permutations<T> create(T[] TArray, T[] TArray2, int n) {
        return new Permutations<T>(TArray, TArray2, n);
    }

    private void moveIndex() {
        int n;
        int n2 = this.rightmostDip();
        if (n2 < 0) {
            this.hasNext = false;
            return;
        }
        int n3 = n2 + 1;
        for (n = n2 + 2; n < this.n; ++n) {
            if (this.index[n] >= this.index[n3] || this.index[n] <= this.index[n2]) continue;
            n3 = n;
        }
        n = this.index[n2];
        this.index[n2] = this.index[n3];
        this.index[n3] = n;
        if (this.m - 1 > n2) {
            this.reverseAfter(n2);
            this.reverseAfter(this.m - 1);
        }
    }

    private int rightmostDip() {
        for (int i = this.n - 2; i >= 0; --i) {
            if (this.index[i] >= this.index[i + 1]) continue;
            return i;
        }
        return -1;
    }

    private void reverseAfter(int n) {
        int n2 = n + 1;
        for (int i = this.n - 1; n2 < i; ++n2, --i) {
            int n3 = this.index[n2];
            this.index[n2] = this.index[i];
            this.index[i] = n3;
        }
    }

    public boolean next() {
        if (!this.hasNext) {
            return false;
        }
        for (int i = 0; i < this.m; ++i) {
            this.out[i] = this.in[this.index[i]];
        }
        this.moveIndex();
        return true;
    }
}

