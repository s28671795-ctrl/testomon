/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.math.Numeric$LongIsIntegral$;
import scala.math.Ordering$Long$;
import scala.runtime.RichLong;

public final class RichLong$ {
    public static final RichLong$ MODULE$ = new RichLong$();

    public final Numeric$LongIsIntegral$ num$extension(long $this) {
        return Numeric$LongIsIntegral$.MODULE$;
    }

    public final Ordering$Long$ ord$extension(long $this) {
        return Ordering$Long$.MODULE$;
    }

    public final double doubleValue$extension(long $this) {
        return $this;
    }

    public final float floatValue$extension(long $this) {
        return $this;
    }

    public final long longValue$extension(long $this) {
        return $this;
    }

    public final int intValue$extension(long $this) {
        return (int)$this;
    }

    public final byte byteValue$extension(long $this) {
        return (byte)$this;
    }

    public final short shortValue$extension(long $this) {
        return (short)$this;
    }

    public final boolean isValidByte$extension(long $this) {
        return (long)((byte)$this) == $this;
    }

    public final boolean isValidShort$extension(long $this) {
        return (long)((short)$this) == $this;
    }

    public final boolean isValidChar$extension(long $this) {
        return (long)((char)$this) == $this;
    }

    public final boolean isValidInt$extension(long $this) {
        return (long)((int)$this) == $this;
    }

    public final boolean isValidLong$extension(long $this) {
        return true;
    }

    public final long abs$extension(long $this) {
        return Math.abs($this);
    }

    public final long max$extension(long $this, long that) {
        return Math.max($this, that);
    }

    public final long min$extension(long $this, long that) {
        return Math.min($this, that);
    }

    public final long round$extension(long $this) {
        return $this;
    }

    public final String toBinaryString$extension(long $this) {
        return Long.toBinaryString($this);
    }

    public final String toHexString$extension(long $this) {
        return Long.toHexString($this);
    }

    public final String toOctalString$extension(long $this) {
        return Long.toOctalString($this);
    }

    public final int hashCode$extension(long $this) {
        return Long.hashCode($this);
    }

    public final boolean equals$extension(long $this, Object x$1) {
        long l;
        return x$1 instanceof RichLong && $this == (l = ((RichLong)x$1).self());
    }

    private RichLong$() {
    }
}

