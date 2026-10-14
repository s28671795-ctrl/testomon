/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Option;
import scala.Tuple2;
import scala.collection.AbstractIterator;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ImmutableBuilder;
import scala.math.Integral;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class Iterator$
implements IterableFactory<Iterator> {
    public static final Iterator$ MODULE$ = new Iterator$();
    private static final long serialVersionUID = 3L;
    public static final Iterator<Nothing$> scala$collection$Iterator$$_empty = new AbstractIterator<Nothing$>(){

        public boolean hasNext() {
            return false;
        }

        public Nothing$ next() {
            throw new NoSuchElementException("next on empty iterator");
        }

        public int knownSize() {
            return 0;
        }

        public anon.19 sliceIterator(int from, int until) {
            return this;
        }
    };

    @Override
    public Object iterate(Object start2, int len, Function1 f) {
        return IterableFactory.iterate$(this, start2, len, f);
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
    public Object concat(Seq xss) {
        return IterableFactory.concat$(this, xss);
    }

    @Override
    public <A> Factory<A, Iterator<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    @Override
    public <A> Iterator<A> from(IterableOnce<A> source2) {
        return source2.iterator();
    }

    @Override
    public final <T> Iterator<T> empty() {
        return scala$collection$Iterator$$_empty;
    }

    public <A> Iterator<A> single(A a) {
        return new AbstractIterator<A>(a){
            private boolean consumed;
            private final Object a$1;

            public boolean hasNext() {
                return !this.consumed;
            }

            public A next() {
                if (this.consumed) {
                    return (A)Iterator$.scala$collection$Iterator$$_empty.next();
                }
                this.consumed = true;
                return (A)this.a$1;
            }

            public Iterator<A> sliceIterator(int from, int until) {
                if (this.consumed || from > 0 || until == 0) {
                    return Iterator$.scala$collection$Iterator$$_empty;
                }
                return this;
            }
            {
                this.a$1 = a$1;
                this.consumed = false;
            }
        };
    }

    @Override
    public <A> Iterator<A> apply(Seq<A> xs) {
        return xs.iterator();
    }

    @Override
    public <A> Builder<A, Iterator<A>> newBuilder() {
        return new ImmutableBuilder<A, Iterator<A>>(){

            /*
             * WARNING - void declaration
             */
            public anon.21 addOne(A elem) {
                void $plus$plus_xs;
                Iterator iterator2 = (Iterator)this.elems();
                Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> Iterator$.MODULE$.single(elem);
                if (iterator2 == null) {
                    throw null;
                }
                intersect = null;
                this.elems_$eq(iterator2.concat($plus$plus_xs));
                return this;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$addOne$1(java.lang.Object )}, serializedLambda);
            }
        };
    }

    @Override
    public <A> Iterator<A> fill(int len, Function0<A> elem) {
        return new AbstractIterator<A>(len, elem){
            private int i;
            private final int len$2;
            private final Function0 elem$4;

            public int knownSize() {
                int n = this.len$2 - this.i;
                int max$extension_that = 0;
                return Math.max(n, max$extension_that);
            }

            public boolean hasNext() {
                return this.i < this.len$2;
            }

            public A next() {
                if (this.hasNext()) {
                    ++this.i;
                    return (A)this.elem$4.apply();
                }
                return (A)Iterator$.scala$collection$Iterator$$_empty.next();
            }
            {
                this.len$2 = len$2;
                this.elem$4 = elem$4;
                this.i = 0;
            }
        };
    }

    @Override
    public <A> Iterator<A> tabulate(int end, Function1<Object, A> f) {
        return new AbstractIterator<A>(end, f){
            private int i;
            private final int end$1;
            private final Function1 f$5;

            public int knownSize() {
                int n = this.end$1 - this.i;
                int max$extension_that = 0;
                return Math.max(n, max$extension_that);
            }

            public boolean hasNext() {
                return this.i < this.end$1;
            }

            public A next() {
                if (this.hasNext()) {
                    R result = this.f$5.apply(this.i);
                    ++this.i;
                    return (A)result;
                }
                return (A)Iterator$.scala$collection$Iterator$$_empty.next();
            }
            {
                this.end$1 = end$1;
                this.f$5 = f$5;
                this.i = 0;
            }
        };
    }

    public Iterator<Object> from(int start2) {
        int from_step = 1;
        return new AbstractIterator<Object>(start2, from_step){
            private int i;
            private final int step$1;

            public boolean hasNext() {
                return true;
            }

            public int next() {
                int result = this.i;
                this.i += this.step$1;
                return result;
            }
            {
                this.step$1 = step$1;
                this.i = start$1;
            }
        };
    }

    public Iterator<Object> from(int start2, int step) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public Iterator<Object> range(int start2, int end) {
        int range_step = 1;
        return new AbstractIterator<Object>(range_step, start2, end){
            private int i;
            private boolean hasOverflowed;
            private final int end$2;
            private final int step$2;

            public int knownSize() {
                double size = Math.ceil((double)((long)this.end$2 - (long)this.i) / (double)this.step$2);
                if (size < 0.0) {
                    return 0;
                }
                if (size > (double)Integer.MAX_VALUE) {
                    return -1;
                }
                return (int)size;
            }

            public boolean hasNext() {
                return !(this.step$2 > 0 && this.i >= this.end$2 || this.step$2 < 0 && this.i <= this.end$2 || this.hasOverflowed);
            }

            public int next() {
                if (this.hasNext()) {
                    int result = this.i;
                    int nextValue = this.i + this.step$2;
                    this.hasOverflowed = this.step$2 > 0 == nextValue < this.i;
                    this.i = nextValue;
                    return result;
                }
                return BoxesRunTime.unboxToInt(Iterator$.scala$collection$Iterator$$_empty.next());
            }
            {
                this.end$2 = end$2;
                this.step$2 = step$2;
                if (step$2 == 0) {
                    throw new IllegalArgumentException("zero step");
                }
                this.i = start$2;
                this.hasOverflowed = false;
            }
        };
    }

    public Iterator<Object> range(int start2, int end, int step) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public <T> Iterator<T> iterate(T start2, Function1<T, T> f) {
        return new AbstractIterator<T>(start2, f){
            private boolean first;
            private T acc;
            private final Function1 f$6;

            public boolean hasNext() {
                return true;
            }

            public T next() {
                if (this.first) {
                    this.first = false;
                } else {
                    this.acc = this.f$6.apply(this.acc);
                }
                return this.acc;
            }
            {
                this.f$6 = f$6;
                this.first = true;
                this.acc = start$3;
            }
        };
    }

    @Override
    public <A, S> Iterator<A> unfold(S init2, Function1<S, Option<Tuple2<A, S>>> f) {
        return new Iterator.UnfoldIterator<A, S>(init2, f);
    }

    public <A> Iterator<A> continually(Function0<A> elem) {
        return new AbstractIterator<A>(elem){
            private final Function0 elem$5;

            public boolean hasNext() {
                return true;
            }

            public A next() {
                return (A)this.elem$5.apply();
            }
            {
                this.elem$5 = elem$5;
            }
        };
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Iterator$.class);
    }

    private Iterator$() {
    }
}

