/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.util.stream.IntStream;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r3AAC\u0006\u0003!!AA\u0004\u0001BC\u0002\u0013\u0005Q\u0004\u0003\u0005&\u0001\t\u0005\t\u0015!\u0003\u001f\u0011!1\u0003A!A!\u0002\u00139\u0003\u0002\u0003\u0016\u0001\u0005\u0003\u0005\u000b\u0011B\u0014\t\u000b-\u0002A\u0011\u0001\u0017\t\u000bI\u0002A\u0011A\u001a\t\u000bQ\u0002A\u0011A\u001b\t\u000ba\u0002A\u0011A\u001d\t\u000by\u0002A\u0011I \u0003#\u0005\u0013(/Y=DQ\u0006\u00148+Z9vK:\u001cWM\u0003\u0002\r\u001b\u00059!/\u001e8uS6,'\"\u0001\b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001!E\r\u0011\u0005I9R\"A\n\u000b\u0005Q)\u0012\u0001\u00027b]\u001eT\u0011AF\u0001\u0005U\u00064\u0018-\u0003\u0002\u0019'\t1qJ\u00196fGR\u0004\"A\u0005\u000e\n\u0005m\u0019\"\u0001D\"iCJ\u001cV-];f]\u000e,\u0017A\u0001=t+\u0005q\u0002cA\u0010!E5\tQ\"\u0003\u0002\"\u001b\t)\u0011I\u001d:bsB\u0011qdI\u0005\u0003I5\u0011Aa\u00115be\u0006\u0019\u0001p\u001d\u0011\u0002\u000bM$\u0018M\u001d;\u0011\u0005}A\u0013BA\u0015\u000e\u0005\rIe\u000e^\u0001\u0004K:$\u0017A\u0002\u001fj]&$h\b\u0006\u0003._A\n\u0004C\u0001\u0018\u0001\u001b\u0005Y\u0001\"\u0002\u000f\u0006\u0001\u0004q\u0002\"\u0002\u0014\u0006\u0001\u00049\u0003\"\u0002\u0016\u0006\u0001\u00049\u0013A\u00027f]\u001e$\b\u000eF\u0001(\u0003\u0019\u0019\u0007.\u0019:BiR\u0011!E\u000e\u0005\u0006o\u001d\u0001\raJ\u0001\u0006S:$W\r_\u0001\fgV\u00147+Z9vK:\u001cW\rF\u0002\u001auqBQa\u000f\u0005A\u0002\u001d\naa\u001d;beR\u0004\u0004\"B\u001f\t\u0001\u00049\u0013\u0001B3oIB\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002\u0001B\u0011!#Q\u0005\u0003\u0005N\u0011aa\u0015;sS:<\u0007")
public final class ArrayCharSequence
implements CharSequence {
    private final char[] xs;
    private final int start;
    private final int end;

    @Override
    public IntStream chars() {
        return CharSequence.super.chars();
    }

    @Override
    public IntStream codePoints() {
        return CharSequence.super.codePoints();
    }

    public char[] xs() {
        return this.xs;
    }

    @Override
    public int length() {
        return Math.max(0, this.end - this.start);
    }

    @Override
    public char charAt(int index) {
        if (0 <= index && index < this.length()) {
            return this.xs()[this.start + index];
        }
        throw new ArrayIndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(this.xs().length - 1).append(")").toString());
    }

    @Override
    public CharSequence subSequence(int start0, int end0) {
        if (start0 < 0) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(31).append(start0).append(" is out of bounds (min 0, max ").append(this.length() - 1).append(")").toString());
        }
        if (end0 > this.length()) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(31).append(end0).append(" is out of bounds (min 0, max ").append(this.xs().length - 1).append(")").toString());
        }
        if (end0 <= start0) {
            return new ArrayCharSequence(this.xs(), 0, 0);
        }
        int newlen = end0 - start0;
        int start1 = this.start + start0;
        return new ArrayCharSequence(this.xs(), start1, start1 + newlen);
    }

    @Override
    public String toString() {
        int start2 = Math.max(this.start, 0);
        int end = Math.min(this.xs().length, start2 + this.length());
        if (start2 >= end) {
            return "";
        }
        return new String(this.xs(), start2, end - start2);
    }

    public ArrayCharSequence(char[] xs, int start2, int end) {
        this.xs = xs;
        this.start = start2;
        this.end = end;
    }
}

