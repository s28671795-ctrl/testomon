/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.AbstractView;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.SeqView;
import scala.collection.View;
import scala.collection.View$Empty$;
import scala.collection.immutable.LazyList$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;

public final class View$
implements IterableFactory<View> {
    public static final View$ MODULE$ = new View$();
    private static final long serialVersionUID = 3L;

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
    public <A> Factory<A, View<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    public <A> View<A> fromIteratorProvider(Function0<Iterator<A>> it) {
        return new AbstractView<A>(it){
            private final Function0 it$1;

            public Iterator<A> iterator() {
                return (Iterator)this.it$1.apply();
            }
            {
                this.it$1 = it$1;
            }
        };
    }

    @Override
    public <E> View<E> from(IterableOnce<E> it) {
        if (it instanceof View) {
            return (View)it;
        }
        if (it instanceof Iterable) {
            Function0<Iterator> & Serializable fromIteratorProvider_it = () -> View$.$anonfun$from$1((Iterable)it);
            return new /* invalid duplicate definition of identical inner class */;
        }
        Object object = LazyList$.MODULE$.from((IterableOnce)it);
        if (object == null) {
            throw null;
        }
        Object view_this = object;
        return new SeqView.Id(view_this);
    }

    @Override
    public <A> View<A> empty() {
        return View$Empty$.MODULE$;
    }

    @Override
    public <A> Builder<A, View<A>> newBuilder() {
        return new GrowableBuilder<A, ArrayBuffer<A>>(){

            public void sizeHint(int size) {
                ((ArrayBuffer)this.elems()).ensureSize(size);
            }
        }.mapResult((Function1<IterableOnce, View> & Serializable)it -> MODULE$.from((IterableOnce)it));
    }

    @Override
    public <A> View<A> apply(Seq<A> xs) {
        return new View.Elems<A>(xs);
    }

    public <A> Iterator<A> takeRightIterator(Iterator<A> it, int n) {
        int k = it.knownSize();
        if (k == 0 || n <= 0) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        if (n == Integer.MAX_VALUE) {
            return it;
        }
        if (k > 0) {
            int n2 = k - n;
            int max$extension_that = 0;
            return it.drop(Math.max(n2, max$extension_that));
        }
        return new View.TakeRightIterator<A>(it, n);
    }

    public <A> Iterator<A> dropRightIterator(Iterator<A> it, int n) {
        if (n <= 0) {
            return it;
        }
        int k = it.knownSize();
        if (k >= 0) {
            return it.take(k - n);
        }
        return new View.DropRightIterator<A>(it, n);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(View$.class);
    }

    public static final /* synthetic */ Iterator $anonfun$from$1(Iterable x3$1) {
        return x3$1.iterator();
    }

    private View$() {
    }
}

