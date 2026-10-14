/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function0;
import scala.Function1;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.IndexedSeqOps;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedSet;
import scala.collection.immutable.Range;
import scala.collection.immutable.RedBlackTree;
import scala.collection.immutable.RedBlackTree$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.TreeSet;
import scala.collection.mutable.ReusableBuilder;
import scala.math.Ordering;
import scala.math.Ordering$Int$;
import scala.runtime.ModuleSerializationProxy;

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
                return treeSet;
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
            if (ordering == Ordering$Int$.MODULE$ || Ordering.CachedReverse.isReverseOf$(Ordering$Int$.MODULE$, ordering)) {
                Iterator it2 = ordering == Ordering$Int$.MODULE$ == range.step() > 0 ? range.iterator() : IndexedSeqOps.reverseIterator$(range);
                RedBlackTree.Tree tree = RedBlackTree$.MODULE$.fromOrderedKeys(it2, range.length());
                return new TreeSet(tree, ordering);
            }
        }
        RedBlackTree.Tree<E, Object> t2 = null;
        Iterator<E> i = it.iterator();
        while (i.hasNext()) {
            t2 = RedBlackTree$.MODULE$.update(t2, i.next(), null, false, ordering);
        }
        return new TreeSet<E>(t2, ordering);
    }

    @Override
    public <A> ReusableBuilder<A, TreeSet<A>> newBuilder(Ordering<A> ordering) {
        return new TreeSet.TreeSetBuilder<A>(ordering);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TreeSet$.class);
    }

    private TreeSet$() {
    }
}

