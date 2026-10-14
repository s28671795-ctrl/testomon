/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function0;
import scala.Function1;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.IndexedSeqOps;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedSet;
import scala.collection.immutable.Range;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.RedBlackTree;
import scala.collection.mutable.RedBlackTree$;
import scala.collection.mutable.RedBlackTree$Tree$;
import scala.collection.mutable.ReusableBuilder;
import scala.collection.mutable.TreeSet;
import scala.collection.mutable.TreeSet$;
import scala.math.Ordering;
import scala.math.Ordering$Int$;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Null$;

public final class TreeSet$
implements SortedIterableFactory<TreeSet> {
    public static final TreeSet$ MODULE$ = new TreeSet$();
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
    public <A> TreeSet<A> empty(Ordering<A> evidence$1) {
        return new TreeSet<A>(evidence$1);
    }

    @Override
    public <E> TreeSet<E> from(IterableOnce<E> it, Ordering<E> ordering) {
        if (it instanceof TreeSet) {
            TreeSet treeSet = (TreeSet)it;
            Ordering<E> ordering2 = ordering;
            Ordering ordering3 = treeSet.ordering();
            if (!(ordering2 != null ? !ordering2.equals(ordering3) : ordering3 != null)) {
                return new TreeSet(treeSet.scala$collection$mutable$TreeSet$$tree().treeCopy(), ordering);
            }
        }
        if (it instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet)it;
            Ordering<E> ordering4 = ordering;
            Ordering ordering5 = sortedSet.ordering();
            if (!(ordering4 != null ? !ordering4.equals(ordering5) : ordering5 != null)) {
                return new TreeSet(RedBlackTree$.MODULE$.fromOrderedKeys(sortedSet.iterator(), sortedSet.size()), ordering);
            }
        }
        if (it instanceof Range) {
            Range range = (Range)it;
            if (ordering == Ordering$Int$.MODULE$ || ordering == Ordering$Int$.MODULE$.scala$math$Ordering$CachedReverse$$_reverse()) {
                Iterator it2 = ordering == Ordering$Int$.MODULE$ == range.step() > 0 ? range.iterator() : IndexedSeqOps.reverseIterator$(range);
                return new TreeSet(RedBlackTree$.MODULE$.fromOrderedKeys(it2, range.length()), ordering);
            }
        }
        RedBlackTree.Tree t2 = RedBlackTree$Tree$.MODULE$.empty();
        Iterator<E> i = it.iterator();
        while (i.hasNext()) {
            RedBlackTree$.MODULE$.insert(t2, i.next(), null, ordering);
        }
        return new TreeSet(t2, ordering);
    }

    @Override
    public <A> Builder<A, TreeSet<A>> newBuilder(Ordering<A> ordering) {
        return new ReusableBuilder<A, TreeSet<A>>(ordering){
            private RedBlackTree.Tree<A, Null$> tree;
            private final Ordering ordering$1;

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

            public <NewTo> Builder<A, NewTo> mapResult(Function1<TreeSet<A>, NewTo> f) {
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

            public anon.1 addOne(A elem) {
                RedBlackTree$.MODULE$.insert(this.tree, elem, null, this.ordering$1);
                return this;
            }

            public TreeSet<A> result() {
                return new TreeSet<A>(this.tree, this.ordering$1);
            }

            public void clear() {
                this.tree = RedBlackTree$Tree$.MODULE$.empty();
            }
            {
                this.ordering$1 = ordering$1;
                this.tree = RedBlackTree$Tree$.MODULE$.empty();
            }
        };
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TreeSet$.class);
    }

    private TreeSet$() {
    }
}

