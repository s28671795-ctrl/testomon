/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.PartialFunction;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.WithFilter;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Stream$Empty$;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.math.Integral;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Stream$
implements SeqFactory<Stream> {
    public static final Stream$ MODULE$ = new Stream$();
    private static final long serialVersionUID = 3L;

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
    public <A> Factory<A, Stream<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    public <A> Function0<Stream<A>> toDeferrer(Function0<Stream<A>> l) {
        return l;
    }

    @Override
    public <A> Stream<A> from(IterableOnce<A> coll) {
        if (coll instanceof Stream) {
            return (Stream)coll;
        }
        return this.fromIterator(coll.iterator());
    }

    public <A> Stream<A> fromIterator(Iterator<A> it) {
        if (it.hasNext()) {
            return new Stream.Cons<A>(it.next(), (Function0<Stream> & Serializable)() -> MODULE$.fromIterator(it));
        }
        return Stream$Empty$.MODULE$;
    }

    @Override
    public <A> Stream<A> empty() {
        return Stream$Empty$.MODULE$;
    }

    @Override
    public <A> Builder<A, Stream<A>> newBuilder() {
        return new GrowableBuilder<A, ArrayBuffer<A>>(){

            public void sizeHint(int size) {
                ((ArrayBuffer)this.elems()).ensureSize(size);
            }
        }.mapResult((Function1<ArrayBuffer, Stream> & Serializable)array -> MODULE$.from((IterableOnce)array));
    }

    public <A> WithFilter<A, Stream> withFilter(Stream<A> l, Function1<A, Object> p) {
        return new Stream.WithFilter<A>(l, p);
    }

    public <A> Stream<A> iterate(A start2, Function1<A, A> f) {
        Function0<Stream> & Serializable apply_tl = (Function0<Stream> & Serializable)() -> {
            Object iterate_start = f.apply(start2);
            Function0<Stream> & Serializable iterate_apply_tl = () -> Stream$.$anonfun$iterate$1(f, iterate_start);
            return new Stream.Cons(iterate_start, iterate_apply_tl);
        };
        return new Stream.Cons<A>(start2, apply_tl);
    }

    /*
     * WARNING - void declaration
     */
    public Stream<Object> from(int start2, int step) {
        void apply_tl;
        Function0<Stream> & Serializable intersect = (Function0<Stream> & Serializable)() -> MODULE$.from(start2 + step, step);
        Integer apply_hd = start2;
        return new Stream.Cons<Object>(apply_hd, (Function0<Stream<Object>>)apply_tl);
    }

    public Stream<Object> from(int start2) {
        return this.from(start2, 1);
    }

    public <A> Stream<A> continually(Function0<A> elem) {
        Function0<Stream> & Serializable apply_tl = (Function0<Stream> & Serializable)() -> MODULE$.continually(elem);
        A apply_hd = elem.apply();
        return new Stream.Cons<A>(apply_hd, apply_tl);
    }

    /*
     * WARNING - void declaration
     */
    public <A> Stream<A> filteredTail(Stream<A> stream, Function1<A, Object> p, boolean isFlipped) {
        void apply_tl;
        Function0<Stream> & Serializable intersect = (Function0<Stream> & Serializable)() -> {
            Stream stream = (Stream)stream.tail();
            if (stream == null) {
                throw null;
            }
            Stream filterImpl_rest = stream;
            while (filterImpl_rest.nonEmpty() && BoxesRunTime.unboxToBoolean(p.apply(filterImpl_rest.head())) == isFlipped) {
                filterImpl_rest = (Stream)filterImpl_rest.tail();
            }
            if (filterImpl_rest.nonEmpty()) {
                void filterImpl_filteredTail_apply_tl;
                Function0<Stream> & Serializable intersect = () -> Stream$.$anonfun$filteredTail$1(filterImpl_rest, p, isFlipped);
                Object filterImpl_filteredTail_apply_hd = filterImpl_rest.head();
                return new Stream.Cons(filterImpl_filteredTail_apply_hd, filterImpl_filteredTail_apply_tl);
            }
            return Stream$Empty$.MODULE$;
        };
        Object apply_hd = stream.head();
        return new Stream.Cons(apply_hd, apply_tl);
    }

    public <A, B> Stream<B> collectedTail(B head, Stream<A> stream, PartialFunction<A, B> pf) {
        Function0<Stream> & Serializable apply_tl = (Function0<Stream> & Serializable)() -> ((Stream)stream.tail()).collect(pf);
        return new Stream.Cons<B>(head, apply_tl);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Stream$.class);
    }

    private Stream$() {
    }
}

