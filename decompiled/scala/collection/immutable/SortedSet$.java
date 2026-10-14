/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.IterableOnce;
import scala.collection.SortedIterableFactory;
import scala.collection.immutable.SortedSet;
import scala.collection.immutable.TreeSet$;
import scala.math.Ordering;
import scala.package$;
import scala.runtime.ModuleSerializationProxy;

public final class SortedSet$
extends SortedIterableFactory.Delegate<SortedSet> {
    public static final SortedSet$ MODULE$ = new SortedSet$();
    private static final long serialVersionUID = 3L;

    @Override
    public <E> SortedSet<E> from(IterableOnce<E> it, Ordering<E> evidence$1) {
        if (it instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet)it;
            if (package$.MODULE$.Ordering() == null) {
                throw null;
            }
            Ordering<E> ordering = evidence$1;
            Ordering ordering2 = sortedSet.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                return sortedSet;
            }
        }
        return (SortedSet)super.from(it, evidence$1);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SortedSet$.class);
    }

    private SortedSet$() {
        super(TreeSet$.MODULE$);
    }
}

