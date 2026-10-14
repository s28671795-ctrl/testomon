/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function0;
import scala.Function1;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.SortedIterableFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.PriorityQueue;
import scala.collection.mutable.PriorityQueue$;
import scala.math.Ordering;
import scala.runtime.ModuleSerializationProxy;

public final class PriorityQueue$
implements SortedIterableFactory<PriorityQueue> {
    public static final PriorityQueue$ MODULE$ = new PriorityQueue$();
    private static final long serialVersionUID = 3L;

    @Override
    public Object apply(Seq xs, Object evidence$7) {
        return EvidenceIterableFactory.apply$(this, xs, evidence$7);
    }

    @Override
    public Object fill(int n, Function0 elem, Object evidence$8) {
        return EvidenceIterableFactory.fill$(this, n, elem, evidence$8);
    }

    @Override
    public Object tabulate(int n, Function1 f, Object evidence$9) {
        return EvidenceIterableFactory.tabulate$(this, n, f, evidence$9);
    }

    @Override
    public Object iterate(Object start2, int len, Function1 f, Object evidence$10) {
        return EvidenceIterableFactory.iterate$(this, start2, len, f, evidence$10);
    }

    @Override
    public Object unfold(Object init2, Function1 f, Object evidence$11) {
        return EvidenceIterableFactory.unfold$(this, init2, f, evidence$11);
    }

    @Override
    public Factory evidenceIterableFactory(Object evidence$13) {
        return EvidenceIterableFactory.evidenceIterableFactory$(this, evidence$13);
    }

    @Override
    public <A> Builder<A, PriorityQueue<A>> newBuilder(Ordering<A> evidence$1) {
        return new Builder<A, PriorityQueue<A>>(evidence$1){
            private final PriorityQueue<A> pq;

            public void sizeHint(int size) {
                Builder.sizeHint$(this, size);
            }

            public final void sizeHint(IterableOnce<?> coll, int delta) {
                Builder.sizeHint$(this, coll, delta);
            }

            public final int sizeHint$default$2() {
                return Builder.sizeHint$default$2$(this);
            }

            public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
                Builder.sizeHintBounded$(this, size, boundingColl);
            }

            public <NewTo> Builder<A, NewTo> mapResult(Function1<PriorityQueue<A>, NewTo> f) {
                return Builder.mapResult$(this, f);
            }

            public final Growable<A> $plus$eq(A elem) {
                return Growable.$plus$eq$(this, elem);
            }

            public final Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
                return Growable.$plus$eq$(this, elem1, elem2, elems);
            }

            public Growable<A> addAll(IterableOnce<A> xs) {
                return Growable.addAll$(this, xs);
            }

            public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
                return Growable.$plus$plus$eq$(this, xs);
            }

            public int knownSize() {
                return Growable.knownSize$(this);
            }

            private PriorityQueue<A> pq() {
                return this.pq;
            }

            public anon.2 addOne(A elem) {
                this.pq().scala$collection$mutable$PriorityQueue$$unsafeAdd(elem);
                return this;
            }

            public PriorityQueue<A> result() {
                this.pq().scala$collection$mutable$PriorityQueue$$heapify(1);
                return this.pq();
            }

            public void clear() {
                this.pq().clear();
            }
            {
                this.pq = new PriorityQueue<A>(evidence$1$1);
            }
        };
    }

    @Override
    public <A> PriorityQueue<A> empty(Ordering<A> evidence$2) {
        return new PriorityQueue<A>(evidence$2);
    }

    @Override
    public <E> PriorityQueue<E> from(IterableOnce<E> it, Ordering<E> evidence$3) {
        Builder b = new /* invalid duplicate definition of identical inner class */;
        Growable.addAll$(b, it);
        return b.result();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PriorityQueue$.class);
    }

    private PriorityQueue$() {
    }
}

