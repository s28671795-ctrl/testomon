/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.collection.immutable.Range;
import scala.math.Numeric$IntIsIntegral$;
import scala.math.Ordering$Int$;
import scala.runtime.RichInt;

public final class RichInt$ {
    public static final RichInt$ MODULE$ = new RichInt$();

    public final Numeric$IntIsIntegral$ num$extension(int $this) {
        return Numeric$IntIsIntegral$.MODULE$;
    }

    public final Ordering$Int$ ord$extension(int $this) {
        return Ordering$Int$.MODULE$;
    }

    public final double doubleValue$extension(int $this) {
        return $this;
    }

    public final float floatValue$extension(int $this) {
        return $this;
    }

    public final long longValue$extension(int $this) {
        return $this;
    }

    public final int intValue$extension(int $this) {
        return $this;
    }

    public final byte byteValue$extension(int $this) {
        return (byte)$this;
    }

    public final short shortValue$extension(int $this) {
        return (short)$this;
    }

    public final boolean isWhole$extension(int $this) {
        return true;
    }

    public final boolean isValidInt$extension(int $this) {
        return true;
    }

    public final boolean isValidLong$extension(int $this) {
        return true;
    }

    public final int abs$extension(int $this) {
        return Math.abs($this);
    }

    public final int max$extension(int $this, int that) {
        return Math.max($this, that);
    }

    public final int min$extension(int $this, int that) {
        return Math.min($this, that);
    }

    public final int round$extension(int $this) {
        return $this;
    }

    public final String toBinaryString$extension(int $this) {
        return Integer.toBinaryString($this);
    }

    public final String toHexString$extension(int $this) {
        return Integer.toHexString($this);
    }

    public final String toOctalString$extension(int $this) {
        return Integer.toOctalString($this);
    }

    public final Range until$extension(int $this, int end) {
        return new Range.Exclusive($this, end, 1);
    }

    public final Range until$extension(int $this, int end, int step) {
        return new Range.Exclusive($this, end, step);
    }

    public final Range.Inclusive to$extension(int $this, int end) {
        return new Range.Inclusive($this, end, 1);
    }

    public final Range.Inclusive to$extension(int $this, int end, int step) {
        return new Range.Inclusive($this, end, step);
    }

    public final int hashCode$extension(int $this) {
        return Integer.hashCode($this);
    }

    public final boolean equals$extension(int $this, Object x$1) {
        int n;
        return x$1 instanceof RichInt && $this == (n = ((RichInt)x$1).self());
    }

    private RichInt$() {
    }
}

