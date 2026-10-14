/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.math.Fractional;
import scala.math.Numeric$DoubleIsFractional$;
import scala.math.Ordering;
import scala.math.Ordering$Double$TotalOrdering$;
import scala.runtime.RichDouble;

public final class RichDouble$ {
    public static final RichDouble$ MODULE$ = new RichDouble$();

    public final Fractional<Object> num$extension(double $this) {
        return Numeric$DoubleIsFractional$.MODULE$;
    }

    public final Ordering<Object> ord$extension(double $this) {
        return Ordering$Double$TotalOrdering$.MODULE$;
    }

    public final double doubleValue$extension(double $this) {
        return $this;
    }

    public final float floatValue$extension(double $this) {
        return (float)$this;
    }

    public final long longValue$extension(double $this) {
        return (long)$this;
    }

    public final int intValue$extension(double $this) {
        return (int)$this;
    }

    public final byte byteValue$extension(double $this) {
        return (byte)$this;
    }

    public final short shortValue$extension(double $this) {
        return (short)$this;
    }

    public final boolean isWhole$extension(double $this) {
        long l = (long)$this;
        return (double)l == $this || l == Long.MAX_VALUE && $this < Double.POSITIVE_INFINITY || l == Long.MIN_VALUE && $this > Double.NEGATIVE_INFINITY;
    }

    public final boolean isValidByte$extension(double $this) {
        return (double)((byte)$this) == $this;
    }

    public final boolean isValidShort$extension(double $this) {
        return (double)((short)$this) == $this;
    }

    public final boolean isValidChar$extension(double $this) {
        return (double)((char)$this) == $this;
    }

    public final boolean isValidInt$extension(double $this) {
        return (double)((int)$this) == $this;
    }

    public final boolean isNaN$extension(double $this) {
        return Double.isNaN($this);
    }

    public final boolean isInfinity$extension(double $this) {
        return Double.isInfinite($this);
    }

    public final boolean isFinite$extension(double $this) {
        return Double.isFinite($this);
    }

    public final boolean isPosInfinity$extension(double $this) {
        return Double.POSITIVE_INFINITY == $this;
    }

    public final boolean isNegInfinity$extension(double $this) {
        return Double.NEGATIVE_INFINITY == $this;
    }

    public final double abs$extension(double $this) {
        return Math.abs($this);
    }

    public final double max$extension(double $this, double that) {
        return Math.max($this, that);
    }

    public final double min$extension(double $this, double that) {
        return Math.min($this, that);
    }

    public final int signum$extension(double $this) {
        return (int)Math.signum($this);
    }

    public final long round$extension(double $this) {
        return Math.round($this);
    }

    public final double ceil$extension(double $this) {
        return Math.ceil($this);
    }

    public final double floor$extension(double $this) {
        return Math.floor($this);
    }

    public final double toRadians$extension(double $this) {
        return Math.toRadians($this);
    }

    public final double toDegrees$extension(double $this) {
        return Math.toDegrees($this);
    }

    public final int hashCode$extension(double $this) {
        return Double.hashCode($this);
    }

    public final boolean equals$extension(double $this, Object x$1) {
        double d;
        return x$1 instanceof RichDouble && $this == (d = ((RichDouble)x$1).self());
    }

    private RichDouble$() {
    }
}

