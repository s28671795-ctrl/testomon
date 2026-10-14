/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.AnyValCompanion;
import scala.NotImplementedError;

public final class Long$
implements AnyValCompanion {
    public static final Long$ MODULE$ = new Long$();

    public final long MinValue() {
        return Long.MIN_VALUE;
    }

    public final long MaxValue() {
        return Long.MAX_VALUE;
    }

    public Long box(long x) {
        throw new NotImplementedError();
    }

    public long unbox(Object x) {
        throw new NotImplementedError();
    }

    public String toString() {
        return "object scala.Long";
    }

    public float long2float(long x) {
        return x;
    }

    public double long2double(long x) {
        return x;
    }

    private Long$() {
    }
}

