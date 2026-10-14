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
import scala.collection.mutable.ArrayDeque;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;

public final class ArrayDeque$
implements StrictOptimizedSeqFactory<ArrayDeque> {
    public static final ArrayDeque$ MODULE$ = new ArrayDeque$();
    private static final long serialVersionUID = 3L;

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
    public <A> Factory<A, ArrayDeque<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    public <A> int $lessinit$greater$default$1() {
        return 16;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> ArrayDeque<B> from(IterableOnce<B> coll) {
        int s2 = coll.knownSize();
        if (s2 >= 0) {
            void copyElemsToArray_len;
            Object[] array = this.alloc(s2);
            int n = Integer.MAX_VALUE;
            int copyElemsToArray_start = 0;
            if (coll instanceof Iterable) {
                ((Iterable)coll).copyToArray(array, copyElemsToArray_start, (int)copyElemsToArray_len);
            } else {
                coll.iterator().copyToArray(array, copyElemsToArray_start, (int)copyElemsToArray_len);
            }
            return new ArrayDeque(array, 0, s2);
        }
        return (ArrayDeque)new ArrayDeque<B>(16).addAll(coll);
    }

    @Override
    public <A> Builder<A, ArrayDeque<A>> newBuilder() {
        return new GrowableBuilder<A, ArrayDeque<A>>(){

            public void sizeHint(int size) {
                ((ArrayDeque)this.elems()).ensureSize(size);
            }
        };
    }

    @Override
    public <A> ArrayDeque<A> empty() {
        return new ArrayDeque(16);
    }

    public final int DefaultInitialSize() {
        return 16;
    }

    public final int StableSize() {
        return 128;
    }

    public Object[] alloc(int len) {
        if (!(len >= 0)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"Non-negative array size required").toString());
        }
        int size = Integer.MIN_VALUE >>> Integer.numberOfLeadingZeros(len) << 1;
        if (!(size >= 0)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)ArrayDeque$.$anonfun$alloc$2(len)).toString());
        }
        return new Object[Math.max(size, 16)];
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ArrayDeque$.class);
    }

    public static final /* synthetic */ String $anonfun$alloc$1() {
        return "Non-negative array size required";
    }

    public static final /* synthetic */ String $anonfun$alloc$2(int len$1) {
        return new StringBuilder(58).append("ArrayDeque too big - cannot allocate ArrayDeque of length ").append(len$1).toString();
    }

    private ArrayDeque$() {
    }
}

