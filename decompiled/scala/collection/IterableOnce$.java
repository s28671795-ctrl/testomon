/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.Iterable;
import scala.collection.IterableOnce;

public final class IterableOnce$ {
    public static final IterableOnce$ MODULE$ = new IterableOnce$();

    public <A> IterableOnce<A> iterableOnceExtensionMethods(IterableOnce<A> it) {
        return it;
    }

    public int elemsToCopyToArray(int srcLen, int destLen, int start2, int len) {
        return Math.max(Math.min(Math.min(len, srcLen), destLen - start2), 0);
    }

    public <A, B> int copyElemsToArray(IterableOnce<A> elems, Object xs, int start2, int len) {
        if (elems instanceof Iterable) {
            return ((Iterable)elems).copyToArray(xs, start2, len);
        }
        return elems.iterator().copyToArray(xs, start2, len);
    }

    public <A, B> int copyElemsToArray$default$3() {
        return 0;
    }

    public <A, B> int copyElemsToArray$default$4() {
        return Integer.MAX_VALUE;
    }

    public void checkArraySizeWithinVMLimit(int size) {
        if (size > 0x7FFFFFFD) {
            throw new Exception(new StringBuilder(83).append("Size of array-backed collection exceeds VM array size limit of ").append(0x7FFFFFFD).toString());
        }
    }

    private IterableOnce$() {
    }
}

