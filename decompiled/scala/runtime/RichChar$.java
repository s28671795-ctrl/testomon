/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.math.Numeric$CharIsIntegral$;
import scala.math.Ordering$Char$;
import scala.runtime.RichChar;

public final class RichChar$ {
    public static final RichChar$ MODULE$ = new RichChar$();

    public final Numeric$CharIsIntegral$ num$extension(char $this) {
        return Numeric$CharIsIntegral$.MODULE$;
    }

    public final Ordering$Char$ ord$extension(char $this) {
        return Ordering$Char$.MODULE$;
    }

    public final double doubleValue$extension(char $this) {
        return $this;
    }

    public final float floatValue$extension(char $this) {
        return $this;
    }

    public final long longValue$extension(char $this) {
        return $this;
    }

    public final int intValue$extension(char $this) {
        return $this;
    }

    public final byte byteValue$extension(char $this) {
        return (byte)$this;
    }

    public final short shortValue$extension(char $this) {
        return (short)$this;
    }

    public final boolean isValidChar$extension(char $this) {
        return true;
    }

    public final char abs$extension(char $this) {
        return $this;
    }

    public final char max$extension(char $this, char that) {
        return (char)Math.max($this, that);
    }

    public final char min$extension(char $this, char that) {
        return (char)Math.min($this, that);
    }

    public final int asDigit$extension(char $this) {
        return Character.digit($this, 36);
    }

    public final boolean isControl$extension(char $this) {
        return Character.isISOControl($this);
    }

    public final boolean isDigit$extension(char $this) {
        return Character.isDigit($this);
    }

    public final boolean isLetter$extension(char $this) {
        return Character.isLetter($this);
    }

    public final boolean isLetterOrDigit$extension(char $this) {
        return Character.isLetterOrDigit($this);
    }

    public final boolean isWhitespace$extension(char $this) {
        return Character.isWhitespace($this);
    }

    public final boolean isSpaceChar$extension(char $this) {
        return Character.isSpaceChar($this);
    }

    public final boolean isHighSurrogate$extension(char $this) {
        return Character.isHighSurrogate($this);
    }

    public final boolean isLowSurrogate$extension(char $this) {
        return Character.isLowSurrogate($this);
    }

    public final boolean isSurrogate$extension(char $this) {
        return Character.isHighSurrogate($this) || Character.isLowSurrogate($this);
    }

    public final boolean isUnicodeIdentifierStart$extension(char $this) {
        return Character.isUnicodeIdentifierStart($this);
    }

    public final boolean isUnicodeIdentifierPart$extension(char $this) {
        return Character.isUnicodeIdentifierPart($this);
    }

    public final boolean isIdentifierIgnorable$extension(char $this) {
        return Character.isIdentifierIgnorable($this);
    }

    public final boolean isMirrored$extension(char $this) {
        return Character.isMirrored($this);
    }

    public final boolean isLower$extension(char $this) {
        return Character.isLowerCase($this);
    }

    public final boolean isUpper$extension(char $this) {
        return Character.isUpperCase($this);
    }

    public final boolean isTitleCase$extension(char $this) {
        return Character.isTitleCase($this);
    }

    public final char toLower$extension(char $this) {
        return Character.toLowerCase($this);
    }

    public final char toUpper$extension(char $this) {
        return Character.toUpperCase($this);
    }

    public final char toTitleCase$extension(char $this) {
        return Character.toTitleCase($this);
    }

    public final int getType$extension(char $this) {
        return Character.getType($this);
    }

    public final int getNumericValue$extension(char $this) {
        return Character.getNumericValue($this);
    }

    public final byte getDirectionality$extension(char $this) {
        return Character.getDirectionality($this);
    }

    public final char reverseBytes$extension(char $this) {
        return Character.reverseBytes($this);
    }

    public final int hashCode$extension(char $this) {
        return Character.hashCode($this);
    }

    public final boolean equals$extension(char $this, Object x$1) {
        char c;
        return x$1 instanceof RichChar && $this == (c = ((RichChar)x$1).self());
    }

    private RichChar$() {
    }
}

