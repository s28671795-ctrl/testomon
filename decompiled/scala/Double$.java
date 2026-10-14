/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.AnyValCompanion;
import scala.NotImplementedError;

public final class Double$
implements AnyValCompanion {
    public static final Double$ MODULE$ = new Double$();
    private static final double MinValue = -Double.MAX_VALUE;

    public final double MinPositiveValue() {
        return Double.MIN_VALUE;
    }

    public final double NaN() {
        return Double.NaN;
    }

    public final double PositiveInfinity() {
        return Double.POSITIVE_INFINITY;
    }

    public final double NegativeInfinity() {
        return Double.NEGATIVE_INFINITY;
    }

    public final double MinValue() {
        return MinValue;
    }

    public final double MaxValue() {
        return Double.MAX_VALUE;
    }

    public Double box(double x) {
        throw new NotImplementedError();
    }

    public double unbox(Object x) {
        throw new NotImplementedError();
    }

    public String toString() {
        return "object scala.Double";
    }

    private Double$() {
    }
}

