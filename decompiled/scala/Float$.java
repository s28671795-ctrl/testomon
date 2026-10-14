/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.AnyValCompanion;
import scala.NotImplementedError;

public final class Float$
implements AnyValCompanion {
    public static final Float$ MODULE$ = new Float$();
    private static final float MinValue = -Float.MAX_VALUE;

    public final float MinPositiveValue() {
        return Float.MIN_VALUE;
    }

    public final float NaN() {
        return Float.NaN;
    }

    public final float PositiveInfinity() {
        return Float.POSITIVE_INFINITY;
    }

    public final float NegativeInfinity() {
        return Float.NEGATIVE_INFINITY;
    }

    public final float MinValue() {
        return MinValue;
    }

    public final float MaxValue() {
        return Float.MAX_VALUE;
    }

    public Float box(float x) {
        throw new NotImplementedError();
    }

    public float unbox(Object x) {
        throw new NotImplementedError();
    }

    public String toString() {
        return "object scala.Float";
    }

    public double float2double(float x) {
        return x;
    }

    private Float$() {
    }
}

