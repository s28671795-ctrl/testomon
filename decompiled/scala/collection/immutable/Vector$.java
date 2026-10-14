/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedSeqFactory;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.NewVectorIterator;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector0$;
import scala.collection.immutable.Vector1;
import scala.collection.immutable.VectorBuilder;
import scala.collection.mutable.ReusableBuilder;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class Vector$
implements StrictOptimizedSeqFactory<Vector> {
    public static final Vector$ MODULE$ = new Vector$();
    private static final long serialVersionUID = 3L;
    private static final int scala$collection$immutable$Vector$$defaultApplyPreferredMaxLength = MODULE$.liftedTree1$1();
    private static final NewVectorIterator<Nothing$> scala$collection$immutable$Vector$$emptyIterator = new NewVectorIterator<Nothing$>(Vector0$.MODULE$, 0, 0);

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
    public <A> Factory<A, Vector<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    @Override
    public <A> Vector<A> empty() {
        return Vector0$.MODULE$;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public <E> Vector<E> from(IterableOnce<E> it) {
        block6: {
            if (it instanceof Vector) {
                return (Vector)it;
            }
            knownSize = it.knownSize();
            if (knownSize == 0) {
                return Vector0$.MODULE$;
            }
            if (knownSize <= 0 || knownSize > 32) break block6;
            if (!(it instanceof ArraySeq.ofRef)) ** GOTO lbl-1000
            var4_3 = (ArraySeq.ofRef)it;
            v0 = var4_3.elemTag().runtimeClass();
            var5_4 = Object.class;
            if (v0 != null && v0.equals(var5_4)) {
                v1 = var4_3.unsafeArray();
            } else if (it instanceof Iterable) {
                var6_5 = (Iterable)it;
                a1 = new Object[knownSize];
                var6_5.copyToArray(a1);
                v1 = a1;
            } else {
                a1 = new Object[knownSize];
                it.iterator().copyToArray(a1);
                v1 = a1;
            }
            a1 = v1;
            return new Vector1<A>(a1);
        }
        return (Vector)new VectorBuilder<A>().addAll((IterableOnce)it).result();
    }

    @Override
    public <A> ReusableBuilder<A, Vector<A>> newBuilder() {
        return new VectorBuilder();
    }

    public <A> Vector<A> fillSparse(int n, A elem) {
        if (n <= 0) {
            return Vector0$.MODULE$;
        }
        VectorBuilder<A> b = new VectorBuilder<A>();
        b.initSparse(n, elem);
        return b.result();
    }

    public int scala$collection$immutable$Vector$$defaultApplyPreferredMaxLength() {
        return scala$collection$immutable$Vector$$defaultApplyPreferredMaxLength;
    }

    public NewVectorIterator<Nothing$> scala$collection$immutable$Vector$$emptyIterator() {
        return scala$collection$immutable$Vector$$emptyIterator;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Vector$.class);
    }

    private static final /* synthetic */ int liftedTree1$1() {
        int n;
        try {
            n = Integer.parseInt(System.getProperty("scala.collection.immutable.Vector.defaultApplyPreferredMaxLength", "250"));
        }
        catch (SecurityException securityException) {
            n = 250;
        }
        return n;
    }

    private Vector$() {
    }
}

