/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.collection.immutable.Range;
import scala.runtime.ModuleSerializationProxy;

public final class Range$
implements Serializable {
    public static final Range$ MODULE$ = new Range$();

    public int count(int start2, int end, int step, boolean isInclusive) {
        if (step == 0) {
            throw new IllegalArgumentException("step cannot be 0.");
        }
        boolean bl = start2 == end ? !isInclusive : (start2 < end ? step < 0 : step > 0);
        if (bl) {
            return 0;
        }
        long gap = (long)end - (long)start2;
        long jumps = gap / (long)step;
        boolean hasStub = isInclusive || gap % (long)step != 0L;
        long result = jumps + (long)(hasStub ? 1 : 0);
        if (result > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)result;
    }

    public int count(int start2, int end, int step) {
        return this.count(start2, end, step, false);
    }

    public Range.Exclusive apply(int start2, int end, int step) {
        return new Range.Exclusive(start2, end, step);
    }

    public Range.Exclusive apply(int start2, int end) {
        return new Range.Exclusive(start2, end, 1);
    }

    public Range.Inclusive inclusive(int start2, int end, int step) {
        return new Range.Inclusive(start2, end, step);
    }

    public Range.Inclusive inclusive(int start2, int end) {
        return new Range.Inclusive(start2, end, 1);
    }

    public Throwable scala$collection$immutable$Range$$emptyRangeError(String what) {
        return new NoSuchElementException(new StringBuilder(15).append(what).append(" on empty Range").toString());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Range$.class);
    }

    private Range$() {
    }
}

