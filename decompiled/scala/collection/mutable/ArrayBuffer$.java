/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedSeqFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;

public final class ArrayBuffer$
implements StrictOptimizedSeqFactory<ArrayBuffer> {
    public static final ArrayBuffer$ MODULE$ = new ArrayBuffer$();
    private static final long serialVersionUID = 3L;
    private static final Object[] emptyArray = new Object[0];

    @Override
    public SeqOps fill(int n, Function0 elem) {
        return StrictOptimizedSeqFactory.fill$(this, n, elem);
    }

    @Override
    public SeqOps tabulate(int n, Function1 f) {
        return StrictOptimizedSeqFactory.tabulate$(this, n, f);
    }

    @Override
    public SeqOps concat(Seq xss) {
        return StrictOptimizedSeqFactory.concat$(this, xss);
    }

    @Override
    public final SeqOps unapplySeq(SeqOps x) {
        return SeqFactory.unapplySeq$(this, x);
    }

    @Override
    public Object apply(Seq elems) {
        return IterableFactory.apply$(this, elems);
    }

    @Override
    public Object iterate(Object start2, int len, Function1 f) {
        return IterableFactory.iterate$(this, start2, len, f);
    }

    @Override
    public Object unfold(Object init2, Function1 f) {
        return IterableFactory.unfold$(this, init2, f);
    }

    @Override
    public Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(this, start2, end, evidence$3);
    }

    @Override
    public Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(this, start2, end, step, evidence$4);
    }

    @Override
    public Object fill(int n1, int n2, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, n4, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, n4, n5, elem);
    }

    @Override
    public Object tabulate(int n1, int n2, Function2 f) {
        return IterableFactory.tabulate$(this, n1, n2, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, Function3 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, n4, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, n4, n5, f);
    }

    @Override
    public <A> Factory<A, ArrayBuffer<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    public final int DefaultInitialSize() {
        return 16;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> ArrayBuffer<B> from(IterableOnce<B> coll) {
        int k = coll.knownSize();
        if (k >= 0) {
            void copyElemsToArray_len;
            Object[] array = this.scala$collection$mutable$ArrayBuffer$$ensureSize(emptyArray, 0, k);
            int n = Integer.MAX_VALUE;
            int copyElemsToArray_start = 0;
            if (coll instanceof Iterable) {
                ((Iterable)coll).copyToArray(array, copyElemsToArray_start, (int)copyElemsToArray_len);
            } else {
                coll.iterator().copyToArray(array, copyElemsToArray_start, (int)copyElemsToArray_len);
            }
            return new ArrayBuffer(array, k);
        }
        return (ArrayBuffer)new ArrayBuffer<B>().addAll(coll);
    }

    @Override
    public <A> Builder<A, ArrayBuffer<A>> newBuilder() {
        return new GrowableBuilder<A, ArrayBuffer<A>>(){

            public void sizeHint(int size) {
                ((ArrayBuffer)this.elems()).ensureSize(size);
            }
        };
    }

    @Override
    public <A> ArrayBuffer<A> empty() {
        return new ArrayBuffer();
    }

    private int resizeUp(long arrayLen, long targetLen) {
        if (targetLen <= arrayLen) {
            return -1;
        }
        if (targetLen > Integer.MAX_VALUE) {
            throw new Exception(new StringBuilder(63).append("Collections cannot have more than ").append(Integer.MAX_VALUE).append(" elements").toString());
        }
        if ((int)targetLen > 0x7FFFFFFD) {
            throw new Exception(new StringBuilder(83).append("Size of array-backed collection exceeds VM array size limit of ").append(0x7FFFFFFD).toString());
        }
        long max_y = Math.max(arrayLen * 2L, 16L);
        long newLen = Math.max(targetLen, max_y);
        long min_y = 0x7FFFFFFDL;
        return (int)Math.min(newLen, min_y);
    }

    public Object[] scala$collection$mutable$ArrayBuffer$$ensureSize(Object[] array, int curSize, long targetSize) {
        int newLen = this.resizeUp(array.length, targetSize);
        if (newLen < 0) {
            return array;
        }
        Object[] res = new Object[newLen];
        System.arraycopy(array, 0, res, 0, curSize);
        return res;
    }

    private int resizeDown(int arrayLen, int targetLen) {
        if (targetLen >= arrayLen) {
            return -1;
        }
        int max_y = 0;
        return Math.max(targetLen, max_y);
    }

    public Object[] scala$collection$mutable$ArrayBuffer$$downsize(Object[] array, int targetSize) {
        int newLen = this.resizeDown(array.length, targetSize);
        if (newLen < 0) {
            return array;
        }
        if (newLen == 0) {
            return emptyArray;
        }
        Object[] res = new Object[newLen];
        System.arraycopy(array, 0, res, 0, targetSize);
        return res;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ArrayBuffer$.class);
    }

    private ArrayBuffer$() {
    }
}

