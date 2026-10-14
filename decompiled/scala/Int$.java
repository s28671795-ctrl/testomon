/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.AnyValCompanion;
import scala.NotImplementedError;

public final class Int$
implements AnyValCompanion {
    public static final Int$ MODULE$ = new Int$();

    public final int MinValue() {
        return Integer.MIN_VALUE;
    }

    public final int MaxValue() {
        return Integer.MAX_VALUE;
    }

    public Integer box(int x) {
        throw new NotImplementedError();
    }

    public int unbox(Object x) {
        throw new NotImplementedError();
    }

    public String toString() {
        return "object scala.Int";
    }

    public float int2float(int x) {
        return x;
    }

    public long int2long(int x) {
        return x;
    }

    public double int2double(int x) {
        return x;
    }

    private Int$() {
    }
}

