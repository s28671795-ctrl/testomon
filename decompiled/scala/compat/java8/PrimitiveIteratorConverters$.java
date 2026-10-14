/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import scala.Predef$;
import scala.collection.Iterator;
import scala.compat.java8.PrimitiveIteratorConverters;
import scala.compat.java8.wrappers.IteratorPrimitiveDoubleWrapper;
import scala.compat.java8.wrappers.IteratorPrimitiveIntWrapper;
import scala.compat.java8.wrappers.IteratorPrimitiveLongWrapper;
import scala.runtime.BoxesRunTime;

public final class PrimitiveIteratorConverters$ {
    public static final PrimitiveIteratorConverters$ MODULE$ = new PrimitiveIteratorConverters$();
    private static final PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfDouble> specializer_PrimitiveIteratorDouble = new PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfDouble>(){

        public PrimitiveIterator.OfDouble fromJava(java.util.Iterator<Object> it) {
            return new IteratorPrimitiveDoubleWrapper(it);
        }

        public PrimitiveIterator.OfDouble fromScala(Iterator<Object> it) {
            return new PrimitiveIterator.OfDouble(null, it){
                private final Iterator it$1;

                public boolean hasNext() {
                    return this.it$1.hasNext();
                }

                public Double next() {
                    return (Double)this.it$1.next();
                }

                public double nextDouble() {
                    return BoxesRunTime.unboxToDouble(this.it$1.next());
                }

                public void remove() {
                    throw new UnsupportedOperationException("remove on scala.collection.Iterator");
                }

                public void forEachRemaining(Consumer<? super Double> c) {
                    while (this.it$1.hasNext()) {
                        c.accept(Predef$.MODULE$.double2Double(BoxesRunTime.unboxToDouble(this.it$1.next())));
                    }
                }

                public void forEachRemaining(DoubleConsumer c) {
                    while (this.it$1.hasNext()) {
                        c.accept(BoxesRunTime.unboxToDouble(this.it$1.next()));
                    }
                }
                {
                    this.it$1 = it$1;
                }
            };
        }
    };
    private static final PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfInt> specializer_PrimitiveIteratorInt = new PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfInt>(){

        public PrimitiveIterator.OfInt fromJava(java.util.Iterator<Object> it) {
            return new IteratorPrimitiveIntWrapper(it);
        }

        public PrimitiveIterator.OfInt fromScala(Iterator<Object> it) {
            return new PrimitiveIterator.OfInt(null, it){
                private final Iterator it$2;

                public boolean hasNext() {
                    return this.it$2.hasNext();
                }

                public Integer next() {
                    return (Integer)this.it$2.next();
                }

                public int nextInt() {
                    return BoxesRunTime.unboxToInt(this.it$2.next());
                }

                public void remove() {
                    throw new UnsupportedOperationException("remove on scala.collection.Iterator");
                }

                public void forEachRemaining(Consumer<? super Integer> c) {
                    while (this.it$2.hasNext()) {
                        c.accept(Predef$.MODULE$.int2Integer(BoxesRunTime.unboxToInt(this.it$2.next())));
                    }
                }

                public void forEachRemaining(IntConsumer c) {
                    while (this.it$2.hasNext()) {
                        c.accept(BoxesRunTime.unboxToInt(this.it$2.next()));
                    }
                }
                {
                    this.it$2 = it$2;
                }
            };
        }
    };
    private static final PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfLong> specializer_PrimitiveIteratorLong = new PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfLong>(){

        public PrimitiveIterator.OfLong fromJava(java.util.Iterator<Object> it) {
            return new IteratorPrimitiveLongWrapper(it);
        }

        public PrimitiveIterator.OfLong fromScala(Iterator<Object> it) {
            return new PrimitiveIterator.OfLong(null, it){
                private final Iterator it$3;

                public boolean hasNext() {
                    return this.it$3.hasNext();
                }

                public Long next() {
                    return (Long)this.it$3.next();
                }

                public long nextLong() {
                    return BoxesRunTime.unboxToLong(this.it$3.next());
                }

                public void remove() {
                    throw new UnsupportedOperationException("remove on scala.collection.Iterator");
                }

                public void forEachRemaining(Consumer<? super Long> c) {
                    while (this.it$3.hasNext()) {
                        c.accept(Predef$.MODULE$.long2Long(BoxesRunTime.unboxToLong(this.it$3.next())));
                    }
                }

                public void forEachRemaining(LongConsumer c) {
                    while (this.it$3.hasNext()) {
                        c.accept(BoxesRunTime.unboxToLong(this.it$3.next()));
                    }
                }
                {
                    this.it$3 = it$3;
                }
            };
        }
    };

    public PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfDouble> specializer_PrimitiveIteratorDouble() {
        return specializer_PrimitiveIteratorDouble;
    }

    public PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfInt> specializer_PrimitiveIteratorInt() {
        return specializer_PrimitiveIteratorInt;
    }

    public PrimitiveIteratorConverters.SpecializerOfIterators<Object, PrimitiveIterator.OfLong> specializer_PrimitiveIteratorLong() {
        return specializer_PrimitiveIteratorLong;
    }

    public final <A> java.util.Iterator<A> RichJavaIteratorToPrimitives(java.util.Iterator<A> underlying) {
        return underlying;
    }

    public final <A> Iterator<A> RichIteratorToPrimitives(Iterator<A> underlying) {
        return underlying;
    }

    private PrimitiveIteratorConverters$() {
    }
}

