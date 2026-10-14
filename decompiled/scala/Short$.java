/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.AnyValCompanion;
import scala.NotImplementedError;

public final class Short$
implements AnyValCompanion {
    public static final Short$ MODULE$ = new Short$();

    public final short MinValue() {
        return Short.MIN_VALUE;
    }

    public final short MaxValue() {
        return Short.MAX_VALUE;
    }

    public Short box(short x) {
        throw new NotImplementedError();
    }

    public short unbox(Object x) {
        throw new NotImplementedError();
    }

    public String toString() {
        return "object scala.Short";
    }

    public int short2int(short x) {
        return x;
    }

    public long short2long(short x) {
        return x;
    }

    public float short2float(short x) {
        return x;
    }

    public double short2double(short x) {
        return x;
    }

    private Short$() {
    }
}

