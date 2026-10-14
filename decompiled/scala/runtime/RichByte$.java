/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.math.Numeric$ByteIsIntegral$;
import scala.math.Ordering$Byte$;
import scala.runtime.RichByte;

public final class RichByte$ {
    public static final RichByte$ MODULE$ = new RichByte$();

    public final Numeric$ByteIsIntegral$ num$extension(byte $this) {
        return Numeric$ByteIsIntegral$.MODULE$;
    }

    public final Ordering$Byte$ ord$extension(byte $this) {
        return Ordering$Byte$.MODULE$;
    }

    public final double doubleValue$extension(byte $this) {
        return $this;
    }

    public final float floatValue$extension(byte $this) {
        return $this;
    }

    public final long longValue$extension(byte $this) {
        return $this;
    }

    public final int intValue$extension(byte $this) {
        return $this;
    }

    public final byte byteValue$extension(byte $this) {
        return $this;
    }

    public final short shortValue$extension(byte $this) {
        return $this;
    }

    public final boolean isValidByte$extension(byte $this) {
        return true;
    }

    public final byte abs$extension(byte $this) {
        return (byte)Math.abs($this);
    }

    public final byte max$extension(byte $this, byte that) {
        return (byte)Math.max($this, that);
    }

    public final byte min$extension(byte $this, byte that) {
        return (byte)Math.min($this, that);
    }

    public final int hashCode$extension(byte $this) {
        return Byte.hashCode($this);
    }

    public final boolean equals$extension(byte $this, Object x$1) {
        byte by;
        return x$1 instanceof RichByte && $this == (by = ((RichByte)x$1).self());
    }

    private RichByte$() {
    }
}

