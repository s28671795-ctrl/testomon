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
import scala.collection.immutable.ListSet;
import scala.collection.immutable.ListSet$;
import scala.collection.immutable.ListSet$EmptyListSet$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SetOps;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ImmutableBuilder;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;

public final class ListSet$
implements IterableFactory<ListSet> {
    public static final ListSet$ MODULE$ = new ListSet$();
    private static final long serialVersionUID = 3L;

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
    public Object fill(int n, Function0 elem) {
        return IterableFactory.fill$(this, n, elem);
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
    public Object tabulate(int n, Function1 f) {
        return IterableFactory.tabulate$(this, n, f);
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
    public Object concat(Seq xss) {
        return IterableFactory.concat$(this, xss);
    }

    @Override
    public <A> Factory<A, ListSet<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    @Override
    public <E> ListSet<E> from(IterableOnce<E> it) {
        if (it instanceof ListSet) {
            return (ListSet)it;
        }
        if (it.knownSize() == 0) {
            return ListSet$EmptyListSet$.MODULE$;
        }
        return (ListSet)((Builder)new ImmutableBuilder<A, ListSet<A>>(){

            public anon.1 addOne(A elem) {
                SetOps setOps = (SetOps)this.elems();
                if (setOps == null) {
                    throw null;
                }
                this.elems_$eq(setOps.incl(elem));
                return this;
            }
        }.addAll(it)).result();
    }

    public ListSet<Object> emptyInstance() {
        return ListSet$EmptyListSet$.MODULE$;
    }

    @Override
    public <A> ListSet<A> empty() {
        return ListSet$EmptyListSet$.MODULE$;
    }

    @Override
    public <A> Builder<A, ListSet<A>> newBuilder() {
        return new /* invalid duplicate definition of identical inner class */;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ListSet$.class);
    }

    private ListSet$() {
    }
}

