/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;
import scala.$less$colon$less$;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.ArrayOps;
import scala.collection.ArrayOps$ArrayIterator$mcB$sp;
import scala.collection.ArrayOps$ArrayIterator$mcC$sp;
import scala.collection.ArrayOps$ArrayIterator$mcD$sp;
import scala.collection.ArrayOps$ArrayIterator$mcF$sp;
import scala.collection.ArrayOps$ArrayIterator$mcI$sp;
import scala.collection.ArrayOps$ArrayIterator$mcJ$sp;
import scala.collection.ArrayOps$ArrayIterator$mcS$sp;
import scala.collection.ArrayOps$ArrayIterator$mcV$sp;
import scala.collection.ArrayOps$ArrayIterator$mcZ$sp;
import scala.collection.ArrayOps$ReverseIterator$mcB$sp;
import scala.collection.ArrayOps$ReverseIterator$mcC$sp;
import scala.collection.ArrayOps$ReverseIterator$mcD$sp;
import scala.collection.ArrayOps$ReverseIterator$mcF$sp;
import scala.collection.ArrayOps$ReverseIterator$mcI$sp;
import scala.collection.ArrayOps$ReverseIterator$mcJ$sp;
import scala.collection.ArrayOps$ReverseIterator$mcS$sp;
import scala.collection.ArrayOps$ReverseIterator$mcV$sp;
import scala.collection.ArrayOps$ReverseIterator$mcZ$sp;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.LazyZip2;
import scala.collection.Seq;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.convert.impl.BoxedBooleanArrayStepper;
import scala.collection.convert.impl.DoubleArrayStepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.collection.convert.impl.IntArrayStepper;
import scala.collection.convert.impl.LongArrayStepper;
import scala.collection.convert.impl.ObjectArrayStepper;
import scala.collection.convert.impl.WidenedByteArrayStepper;
import scala.collection.convert.impl.WidenedCharArrayStepper;
import scala.collection.convert.impl.WidenedFloatArrayStepper;
import scala.collection.convert.impl.WidenedShortArrayStepper;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Range;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArrayBuilder$;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.ArraySeq$;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.math.Ordering;
import scala.math.Ordering$Boolean$;
import scala.math.Ordering$Byte$;
import scala.math.Ordering$Char$;
import scala.math.Ordering$Int$;
import scala.math.Ordering$Long$;
import scala.math.Ordering$Short$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;
import scala.util.Sorting$;

public final class ArrayOps$ {
    public static final ArrayOps$ MODULE$ = new ArrayOps$();
    private static final Function1<Object, Object> fallback = (Function1<Object, Function1> & Serializable)x$1 -> MODULE$.fallback();

    private final int MaxStableSortLength() {
        return 300;
    }

    private Function1<Object, Object> fallback() {
        return fallback;
    }

    public final <A> ClassTag<A> scala$collection$ArrayOps$$elemTag$extension(Object $this) {
        return ClassTag$.MODULE$.apply($this.getClass().getComponentType());
    }

    public final <A> int size$extension(Object $this) {
        return Array.getLength($this);
    }

    public final <A> int knownSize$extension(Object $this) {
        return Array.getLength($this);
    }

    public final <A> boolean isEmpty$extension(Object $this) {
        return Array.getLength($this) == 0;
    }

    public final <A> boolean nonEmpty$extension(Object $this) {
        return Array.getLength($this) != 0;
    }

    public final <A> A head$extension(Object $this) {
        try {
            return (A)ScalaRunTime$.MODULE$.array_apply($this, 0);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new NoSuchElementException("head of empty array");
        }
    }

    public final <A> A last$extension(Object $this) {
        try {
            return (A)ScalaRunTime$.MODULE$.array_apply($this, Array.getLength($this) - 1);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new NoSuchElementException("last of empty array");
        }
    }

    public final <A> Option<A> headOption$extension(Object $this) {
        if (Array.getLength($this) == 0) {
            return None$.MODULE$;
        }
        return new Some<A>(this.head$extension($this));
    }

    public final <A> Option<A> lastOption$extension(Object $this) {
        if (Array.getLength($this) == 0) {
            return None$.MODULE$;
        }
        return new Some<A>(this.last$extension($this));
    }

    public final <A> int sizeCompare$extension(Object $this, int otherSize) {
        return Integer.compare(Array.getLength($this), otherSize);
    }

    public final <A> int lengthCompare$extension(Object $this, int len) {
        return Integer.compare(Array.getLength($this), len);
    }

    public final <A> int sizeIs$extension(Object $this) {
        return Array.getLength($this);
    }

    public final <A> int lengthIs$extension(Object $this) {
        return Array.getLength($this);
    }

    public final <A> Object slice$extension(Object $this, int from, int until) {
        int lo = Math.max(from, 0);
        int hi = Math.min(until, Array.getLength($this));
        if (hi > lo) {
            if ($this instanceof Object[]) {
                return Arrays.copyOfRange((Object[])$this, lo, hi);
            }
            if ($this instanceof int[]) {
                return Arrays.copyOfRange((int[])$this, lo, hi);
            }
            if ($this instanceof double[]) {
                return Arrays.copyOfRange((double[])$this, lo, hi);
            }
            if ($this instanceof long[]) {
                return Arrays.copyOfRange((long[])$this, lo, hi);
            }
            if ($this instanceof float[]) {
                return Arrays.copyOfRange((float[])$this, lo, hi);
            }
            if ($this instanceof char[]) {
                return Arrays.copyOfRange((char[])$this, lo, hi);
            }
            if ($this instanceof byte[]) {
                return Arrays.copyOfRange((byte[])$this, lo, hi);
            }
            if ($this instanceof short[]) {
                return Arrays.copyOfRange((short[])$this, lo, hi);
            }
            if ($this instanceof boolean[]) {
                return Arrays.copyOfRange((boolean[])$this, lo, hi);
            }
            throw new MatchError($this);
        }
        return ClassTag$.MODULE$.apply($this.getClass().getComponentType()).newArray(0);
    }

    public final <A> Object tail$extension(Object $this) {
        if (Array.getLength($this) == 0) {
            throw new UnsupportedOperationException("tail of empty array");
        }
        return this.slice$extension($this, 1, Array.getLength($this));
    }

    public final <A> Object init$extension(Object $this) {
        if (Array.getLength($this) == 0) {
            throw new UnsupportedOperationException("init of empty array");
        }
        return this.slice$extension($this, 0, Array.getLength($this) - 1);
    }

    /*
     * WARNING - void declaration
     */
    public final <A> Iterator<Object> tails$extension(Object $this) {
        void iterateUntilEmpty$extension_$plus$plus_xs;
        Function1<Object, Object> & Serializable iterateUntilEmpty$extension_f = (Function1<Object, Object> & Serializable)xs -> MODULE$.tail$extension(xs);
        Iterator iterator2 = new AbstractIterator<T>($this, iterateUntilEmpty$extension_f){
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
        }.takeWhile((Function1<Object, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(ArrayOps$.$anonfun$iterateUntilEmpty$1(x)));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> {
            ClassTag empty_evidence$4 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
            Object var2_1 = null;
            Object single_a = empty_evidence$4.newArray(0);
            return new AbstractIterator<A>(single_a){
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
        };
        if (iterator2 == null) {
            throw null;
        }
        return iterator2.concat(iterateUntilEmpty$extension_$plus$plus_xs);
    }

    /*
     * WARNING - void declaration
     */
    public final <A> Iterator<Object> inits$extension(Object $this) {
        void iterateUntilEmpty$extension_$plus$plus_xs;
        Function1<Object, Object> & Serializable iterateUntilEmpty$extension_f = (Function1<Object, Object> & Serializable)xs -> MODULE$.init$extension(xs);
        Iterator iterator2 = new /* invalid duplicate definition of identical inner class */.takeWhile((Function1<Object, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(ArrayOps$.$anonfun$iterateUntilEmpty$1(x)));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> {
            ClassTag empty_evidence$4 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
            Object var2_1 = null;
            Object single_a = empty_evidence$4.newArray(0);
            return new /* invalid duplicate definition of identical inner class */;
        };
        if (iterator2 == null) {
            throw null;
        }
        return iterator2.concat(iterateUntilEmpty$extension_$plus$plus_xs);
    }

    /*
     * WARNING - void declaration
     */
    public final <A> Iterator<Object> iterateUntilEmpty$extension(Object $this, Function1<Object, Object> f) {
        void $plus$plus_xs;
        Iterator iterator2 = new /* invalid duplicate definition of identical inner class */.takeWhile((Function1<Object, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(ArrayOps$.$anonfun$iterateUntilEmpty$1(x)));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> {
            ClassTag empty_evidence$4 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
            Object var2_1 = null;
            Object single_a = empty_evidence$4.newArray(0);
            return new /* invalid duplicate definition of identical inner class */;
        };
        if (iterator2 == null) {
            throw null;
        }
        return iterator2.concat($plus$plus_xs);
    }

    public final <A> Object take$extension(Object $this, int n) {
        return this.slice$extension($this, 0, n);
    }

    public final <A> Object drop$extension(Object $this, int n) {
        return this.slice$extension($this, n, Array.getLength($this));
    }

    public final <A> Object takeRight$extension(Object $this, int n) {
        return this.drop$extension($this, Array.getLength($this) - Math.max(n, 0));
    }

    public final <A> Object dropRight$extension(Object $this, int n) {
        int take$extension_n = Array.getLength($this) - Math.max(n, 0);
        return this.slice$extension($this, 0, take$extension_n);
    }

    public final <A> Object takeWhile$extension(Object $this, Function1<A, Object> p) {
        int n;
        block1: {
            for (int indexWhere$extension_i = 0; indexWhere$extension_i < Array.getLength($this); ++indexWhere$extension_i) {
                Object object = ScalaRunTime$.MODULE$.array_apply($this, indexWhere$extension_i);
                if (!(!BoxesRunTime.unboxToBoolean(p.apply(object)))) continue;
                n = indexWhere$extension_i;
                break block1;
            }
            n = -1;
        }
        int i = n;
        int hi = i < 0 ? Array.getLength($this) : i;
        return this.slice$extension($this, 0, hi);
    }

    public final <A> Object dropWhile$extension(Object $this, Function1<A, Object> p) {
        int n;
        block1: {
            for (int indexWhere$extension_i = 0; indexWhere$extension_i < Array.getLength($this); ++indexWhere$extension_i) {
                Object object = ScalaRunTime$.MODULE$.array_apply($this, indexWhere$extension_i);
                if (!(!BoxesRunTime.unboxToBoolean(p.apply(object)))) continue;
                n = indexWhere$extension_i;
                break block1;
            }
            n = -1;
        }
        int i = n;
        int lo = i < 0 ? Array.getLength($this) : i;
        return this.slice$extension($this, lo, Array.getLength($this));
    }

    public final <A> Iterator<A> iterator$extension(Object $this) {
        if ($this instanceof Object[]) {
            Object[] objectArray = (Object[])$this;
            return new ArrayOps.ArrayIterator(objectArray);
        }
        if ($this instanceof int[]) {
            int[] nArray = (int[])$this;
            return new ArrayOps$ArrayIterator$mcI$sp(nArray);
        }
        if ($this instanceof double[]) {
            double[] dArray = (double[])$this;
            return new ArrayOps$ArrayIterator$mcD$sp(dArray);
        }
        if ($this instanceof long[]) {
            long[] lArray = (long[])$this;
            return new ArrayOps$ArrayIterator$mcJ$sp(lArray);
        }
        if ($this instanceof float[]) {
            float[] fArray = (float[])$this;
            return new ArrayOps$ArrayIterator$mcF$sp(fArray);
        }
        if ($this instanceof char[]) {
            char[] cArray = (char[])$this;
            return new ArrayOps$ArrayIterator$mcC$sp(cArray);
        }
        if ($this instanceof byte[]) {
            byte[] byArray = (byte[])$this;
            return new ArrayOps$ArrayIterator$mcB$sp(byArray);
        }
        if ($this instanceof short[]) {
            short[] sArray = (short[])$this;
            return new ArrayOps$ArrayIterator$mcS$sp(sArray);
        }
        if ($this instanceof boolean[]) {
            boolean[] blArray = (boolean[])$this;
            return new ArrayOps$ArrayIterator$mcZ$sp(blArray);
        }
        if ($this instanceof BoxedUnit[]) {
            BoxedUnit[] boxedUnitArray = (BoxedUnit[])$this;
            return new ArrayOps$ArrayIterator$mcV$sp(boxedUnitArray);
        }
        if ($this == null) {
            throw new NullPointerException();
        }
        throw new MatchError($this);
    }

    public final <S extends Stepper<?>, A> S stepper$extension(Object $this, StepperShape<A, S> shape) {
        IndexedStepperBase indexedStepperBase;
        int n = shape.shape();
        if (StepperShape$.MODULE$.ReferenceShape() == n) {
            if ($this instanceof boolean[]) {
                boolean[] blArray = (boolean[])$this;
                indexedStepperBase = new BoxedBooleanArrayStepper(blArray, 0, Array.getLength($this));
            } else {
                indexedStepperBase = new ObjectArrayStepper<Object>((Object[])$this, 0, Array.getLength($this));
            }
        } else if (StepperShape$.MODULE$.IntShape() == n) {
            indexedStepperBase = new IntArrayStepper((int[])$this, 0, Array.getLength($this));
        } else if (StepperShape$.MODULE$.LongShape() == n) {
            indexedStepperBase = new LongArrayStepper((long[])$this, 0, Array.getLength($this));
        } else if (StepperShape$.MODULE$.DoubleShape() == n) {
            indexedStepperBase = new DoubleArrayStepper((double[])$this, 0, Array.getLength($this));
        } else if (StepperShape$.MODULE$.ByteShape() == n) {
            indexedStepperBase = new WidenedByteArrayStepper((byte[])$this, 0, Array.getLength($this));
        } else if (StepperShape$.MODULE$.ShortShape() == n) {
            indexedStepperBase = new WidenedShortArrayStepper((short[])$this, 0, Array.getLength($this));
        } else if (StepperShape$.MODULE$.CharShape() == n) {
            indexedStepperBase = new WidenedCharArrayStepper((char[])$this, 0, Array.getLength($this));
        } else if (StepperShape$.MODULE$.FloatShape() == n) {
            indexedStepperBase = new WidenedFloatArrayStepper((float[])$this, 0, Array.getLength($this));
        } else {
            throw new MatchError(new StepperShape.Shape(n));
        }
        return (S)indexedStepperBase;
    }

    public final <A> Iterator<Object> grouped$extension(Object $this, int size) {
        return new ArrayOps.GroupedIterator($this, size);
    }

    public final <A> Tuple2<Object, Object> span$extension(Object $this, Function1<A, Object> p) {
        int n;
        block1: {
            for (int indexWhere$extension_i = 0; indexWhere$extension_i < Array.getLength($this); ++indexWhere$extension_i) {
                Object object = ScalaRunTime$.MODULE$.array_apply($this, indexWhere$extension_i);
                if (!(!BoxesRunTime.unboxToBoolean(p.apply(object)))) continue;
                n = indexWhere$extension_i;
                break block1;
            }
            n = -1;
        }
        int i = n;
        int idx = i < 0 ? Array.getLength($this) : i;
        return new Tuple2<Object, Object>(this.slice$extension($this, 0, idx), this.slice$extension($this, idx, Array.getLength($this)));
    }

    public final <A> Tuple2<Object, Object> splitAt$extension(Object $this, int n) {
        return new Tuple2<Object, Object>(this.slice$extension($this, 0, n), this.drop$extension($this, n));
    }

    public final <A> Tuple2<Object, Object> partition$extension(Object $this, Function1<A, Object> p) {
        ArrayBuilder arrayBuilder;
        ArrayBuilder arrayBuilder2;
        ClassTag make_evidence$1 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
        Class<?> clazz = make_evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder2 = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder2 = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder2 = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder2 = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder2 = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder2 = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder2 = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder2 = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder2 = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var7_3 = null;
        clazz = null;
        ArrayBuilder res1 = arrayBuilder2;
        ClassTag make_evidence$12 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
        Class<?> clazz11 = make_evidence$12.runtimeClass();
        Class<Byte> clazz12 = Byte.TYPE;
        if (!(clazz12 != null ? !clazz12.equals(clazz11) : clazz11 != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz13 = Short.TYPE;
            if (!(clazz13 != null ? !clazz13.equals(clazz11) : clazz11 != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz14 = Character.TYPE;
                if (!(clazz14 != null ? !clazz14.equals(clazz11) : clazz11 != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz15 = Integer.TYPE;
                    if (!(clazz15 != null ? !clazz15.equals(clazz11) : clazz11 != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz16 = Long.TYPE;
                        if (!(clazz16 != null ? !clazz16.equals(clazz11) : clazz11 != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz17 = Float.TYPE;
                            if (!(clazz17 != null ? !clazz17.equals(clazz11) : clazz11 != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz18 = Double.TYPE;
                                if (!(clazz18 != null ? !clazz18.equals(clazz11) : clazz11 != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz19 = Boolean.TYPE;
                                    if (!(clazz19 != null ? !clazz19.equals(clazz11) : clazz11 != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz20 = Void.TYPE;
                                        arrayBuilder = !(clazz20 != null ? !clazz20.equals(clazz11) : clazz11 != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(make_evidence$12);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var9_6 = null;
        clazz11 = null;
        ArrayBuilder res2 = arrayBuilder;
        for (int i = 0; i < Array.getLength($this); ++i) {
            Object x = ScalaRunTime$.MODULE$.array_apply($this, i);
            (BoxesRunTime.unboxToBoolean(p.apply(x)) ? res1 : res2).addOne(x);
        }
        return new Tuple2<Object, Object>(res1.result(), res2.result());
    }

    public final <A1, A2, A> Tuple2<Object, Object> partitionMap$extension(Object $this, Function1<A, Either<A1, A2>> f, ClassTag<A1> evidence$3, ClassTag<A2> evidence$4) {
        ArrayBuilder arrayBuilder;
        ArrayBuilder arrayBuilder2;
        Class<?> clazz = evidence$3.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder2 = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder2 = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder2 = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder2 = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder2 = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder2 = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder2 = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder2 = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder2 = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<A1>(evidence$3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte res1 = arrayBuilder2;
        Class<?> clazz11 = evidence$4.runtimeClass();
        Class<Byte> clazz12 = Byte.TYPE;
        if (!(clazz12 != null ? !clazz12.equals(clazz11) : clazz11 != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz13 = Short.TYPE;
            if (!(clazz13 != null ? !clazz13.equals(clazz11) : clazz11 != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz14 = Character.TYPE;
                if (!(clazz14 != null ? !clazz14.equals(clazz11) : clazz11 != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz15 = Integer.TYPE;
                    if (!(clazz15 != null ? !clazz15.equals(clazz11) : clazz11 != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz16 = Long.TYPE;
                        if (!(clazz16 != null ? !clazz16.equals(clazz11) : clazz11 != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz17 = Float.TYPE;
                            if (!(clazz17 != null ? !clazz17.equals(clazz11) : clazz11 != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz18 = Double.TYPE;
                                if (!(clazz18 != null ? !clazz18.equals(clazz11) : clazz11 != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz19 = Boolean.TYPE;
                                    if (!(clazz19 != null ? !clazz19.equals(clazz11) : clazz11 != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz20 = Void.TYPE;
                                        arrayBuilder = !(clazz20 != null ? !clazz20.equals(clazz11) : clazz11 != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<A2>(evidence$4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz11 = null;
        ArrayBuilder.ofByte res2 = arrayBuilder;
        for (int i = 0; i < Array.getLength($this); ++i) {
            Either<A1, A2> either = f.apply(ScalaRunTime$.MODULE$.array_apply($this, i));
            if (either instanceof Left) {
                Object x = ((Left)either).value();
                ArrayBuilder cfr_ignored_0 = (ArrayBuilder)res1.addOne(x);
                continue;
            }
            if (either instanceof Right) {
                Object x = ((Right)either).value();
                ArrayBuilder cfr_ignored_1 = (ArrayBuilder)res2.addOne(x);
                continue;
            }
            throw new MatchError(either);
        }
        return new Tuple2<Object, Object>(res1.result(), res2.result());
    }

    public final <A> Object reverse$extension(Object $this) {
        int len = Array.getLength($this);
        Object res = ClassTag$.MODULE$.apply($this.getClass().getComponentType()).newArray(len);
        for (int i = 0; i < len; ++i) {
            ScalaRunTime$.MODULE$.array_update(res, len - i - 1, ScalaRunTime$.MODULE$.array_apply($this, i));
        }
        return res;
    }

    public final <A> Iterator<A> reverseIterator$extension(Object $this) {
        if ($this instanceof Object[]) {
            Object[] objectArray = (Object[])$this;
            return new ArrayOps.ReverseIterator(objectArray);
        }
        if ($this instanceof int[]) {
            int[] nArray = (int[])$this;
            return new ArrayOps$ReverseIterator$mcI$sp(nArray);
        }
        if ($this instanceof double[]) {
            double[] dArray = (double[])$this;
            return new ArrayOps$ReverseIterator$mcD$sp(dArray);
        }
        if ($this instanceof long[]) {
            long[] lArray = (long[])$this;
            return new ArrayOps$ReverseIterator$mcJ$sp(lArray);
        }
        if ($this instanceof float[]) {
            float[] fArray = (float[])$this;
            return new ArrayOps$ReverseIterator$mcF$sp(fArray);
        }
        if ($this instanceof char[]) {
            char[] cArray = (char[])$this;
            return new ArrayOps$ReverseIterator$mcC$sp(cArray);
        }
        if ($this instanceof byte[]) {
            byte[] byArray = (byte[])$this;
            return new ArrayOps$ReverseIterator$mcB$sp(byArray);
        }
        if ($this instanceof short[]) {
            short[] sArray = (short[])$this;
            return new ArrayOps$ReverseIterator$mcS$sp(sArray);
        }
        if ($this instanceof boolean[]) {
            boolean[] blArray = (boolean[])$this;
            return new ArrayOps$ReverseIterator$mcZ$sp(blArray);
        }
        if ($this instanceof BoxedUnit[]) {
            BoxedUnit[] boxedUnitArray = (BoxedUnit[])$this;
            return new ArrayOps$ReverseIterator$mcV$sp(boxedUnitArray);
        }
        if ($this == null) {
            throw new NullPointerException();
        }
        throw new MatchError($this);
    }

    public final <A> Object filter$extension(Object $this, Function1<A, Object> p) {
        ArrayBuilder arrayBuilder;
        ClassTag make_evidence$1 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
        Class<?> clazz = make_evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var6_3 = null;
        clazz = null;
        ArrayBuilder res = arrayBuilder;
        for (int i = 0; i < Array.getLength($this); ++i) {
            Object x = ScalaRunTime$.MODULE$.array_apply($this, i);
            if (!BoxesRunTime.unboxToBoolean(p.apply(x))) continue;
            res.addOne(x);
        }
        return res.result();
    }

    public final <A> Object filterNot$extension(Object $this, Function1<A, Object> p) {
        ArrayBuilder arrayBuilder;
        ClassTag filter$extension_make_evidence$1 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
        Class<?> clazz = filter$extension_make_evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(filter$extension_make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var6_3 = null;
        clazz = null;
        ArrayBuilder filter$extension_res = arrayBuilder;
        for (int filter$extension_i = 0; filter$extension_i < Array.getLength($this); ++filter$extension_i) {
            Object filter$extension_x = ScalaRunTime$.MODULE$.array_apply($this, filter$extension_i);
            if (!(!BoxesRunTime.unboxToBoolean(p.apply(filter$extension_x)))) continue;
            filter$extension_res.addOne(filter$extension_x);
        }
        return filter$extension_res.result();
    }

    public final <B, A> Object sorted$extension(Object $this, Ordering<B> ord) {
        int len = Array.getLength($this);
        if (len <= 1) {
            return ScalaRunTime$.MODULE$.array_clone($this);
        }
        if ($this instanceof Object[]) {
            Object[] a = Arrays.copyOf((Object[])$this, len);
            Arrays.sort(a, ord);
            return a;
        }
        if ($this instanceof int[]) {
            int[] nArray = (int[])$this;
            if (ord == Ordering$Int$.MODULE$) {
                int[] a = Arrays.copyOf(nArray, len);
                Arrays.sort(a);
                return a;
            }
            return ArrayOps$.boxed$1(len, $this, ord);
        }
        if ($this instanceof long[]) {
            long[] lArray = (long[])$this;
            if (ord == Ordering$Long$.MODULE$) {
                long[] a = Arrays.copyOf(lArray, len);
                Arrays.sort(a);
                return a;
            }
            return ArrayOps$.boxed$1(len, $this, ord);
        }
        if ($this instanceof char[]) {
            char[] cArray = (char[])$this;
            if (ord == Ordering$Char$.MODULE$) {
                char[] a = Arrays.copyOf(cArray, len);
                Arrays.sort(a);
                return a;
            }
            return ArrayOps$.boxed$1(len, $this, ord);
        }
        if ($this instanceof byte[]) {
            byte[] byArray = (byte[])$this;
            if (ord == Ordering$Byte$.MODULE$) {
                byte[] a = Arrays.copyOf(byArray, len);
                Arrays.sort(a);
                return a;
            }
            return ArrayOps$.boxed$1(len, $this, ord);
        }
        if ($this instanceof short[]) {
            short[] sArray = (short[])$this;
            if (ord == Ordering$Short$.MODULE$) {
                short[] a = Arrays.copyOf(sArray, len);
                Arrays.sort(a);
                return a;
            }
            return ArrayOps$.boxed$1(len, $this, ord);
        }
        if ($this instanceof boolean[]) {
            boolean[] blArray = (boolean[])$this;
            if (ord == Ordering$Boolean$.MODULE$) {
                boolean[] a = Arrays.copyOf(blArray, len);
                Ordering$Boolean$ stableSort_evidence$3 = Ordering$Boolean$.MODULE$;
                Sorting$.MODULE$.stableSort((Object)a, 0, a.length, stableSort_evidence$3);
                return a;
            }
            return ArrayOps$.boxed$1(len, $this, ord);
        }
        return ArrayOps$.boxed$1(len, $this, ord);
    }

    public final <A> Object sortWith$extension(Object $this, Function2<A, A, Object> lt) {
        return this.sorted$extension($this, new Ordering<T>(lt){
            private final Function2 cmp$2;

            public Some<Object> tryCompare(T x, T y) {
                return Ordering.tryCompare$(this, x, y);
            }

            public boolean equiv(T x, T y) {
                return Ordering.equiv$(this, x, y);
            }

            public <U extends T> U max(U x, U y) {
                return (U)Ordering.max$(this, x, y);
            }

            public <U extends T> U min(U x, U y) {
                return (U)Ordering.min$(this, x, y);
            }

            public Ordering<T> reverse() {
                return Ordering.reverse$(this);
            }

            public boolean isReverseOf(Ordering<?> other) {
                return Ordering.isReverseOf$(this, other);
            }

            public <U> Ordering<U> on(Function1<U, T> f) {
                return Ordering.on$(this, f);
            }

            public Ordering<T> orElse(Ordering<T> other) {
                return Ordering.orElse$(this, other);
            }

            public <S> Ordering<T> orElseBy(Function1<T, S> f, Ordering<S> ord) {
                return Ordering.orElseBy$(this, f, ord);
            }

            public Ordering.OrderingOps mkOrderingOps(T lhs) {
                return Ordering.mkOrderingOps$(this, lhs);
            }

            public int compare(T x, T y) {
                if (BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y))) {
                    return -1;
                }
                if (BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x))) {
                    return 1;
                }
                return 0;
            }

            public boolean lt(T x, T y) {
                return BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y));
            }

            public boolean gt(T x, T y) {
                return BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x));
            }

            public boolean gteq(T x, T y) {
                return !BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y));
            }

            public boolean lteq(T x, T y) {
                return !BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x));
            }
            {
                this.cmp$2 = cmp$2;
            }
        });
    }

    public final <B, A> Object sortBy$extension(Object $this, Function1<A, B> f, Ordering<B> ord) {
        return this.sorted$extension($this, ord.on(f));
    }

    public final <A> ArrayOps.WithFilter<A> withFilter$extension(Object $this, Function1<A, Object> p) {
        return new ArrayOps.WithFilter<A>(p, $this);
    }

    public final <A> int indexOf$extension(Object $this, A elem, int from) {
        for (int i = from; i < Array.getLength($this); ++i) {
            if (!BoxesRunTime.equals(elem, ScalaRunTime$.MODULE$.array_apply($this, i))) continue;
            return i;
        }
        return -1;
    }

    public final <A> int indexOf$default$2$extension(Object $this) {
        return 0;
    }

    public final <A> int indexWhere$extension(Object $this, Function1<A, Object> p, int from) {
        for (int i = from; i < Array.getLength($this); ++i) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(ScalaRunTime$.MODULE$.array_apply($this, i)))) continue;
            return i;
        }
        return -1;
    }

    public final <A> int indexWhere$default$2$extension(Object $this) {
        return 0;
    }

    public final <A> int lastIndexOf$extension(Object $this, A elem, int end) {
        for (int i = Math.min(end, Array.getLength($this) - 1); i >= 0; --i) {
            if (!BoxesRunTime.equals(elem, ScalaRunTime$.MODULE$.array_apply($this, i))) continue;
            return i;
        }
        return -1;
    }

    public final <A> int lastIndexOf$default$2$extension(Object $this) {
        return Array.getLength($this) - 1;
    }

    public final <A> int lastIndexWhere$extension(Object $this, Function1<A, Object> p, int end) {
        for (int i = Math.min(end, Array.getLength($this) - 1); i >= 0; --i) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(ScalaRunTime$.MODULE$.array_apply($this, i)))) continue;
            return i;
        }
        return -1;
    }

    public final <A> int lastIndexWhere$default$2$extension(Object $this) {
        return Array.getLength($this) - 1;
    }

    public final <A> Option<A> find$extension(Object $this, Function1<A, Object> p) {
        int idx;
        block2: {
            int n;
            for (int indexWhere$extension_i = 0; indexWhere$extension_i < Array.getLength($this); ++indexWhere$extension_i) {
                if (!BoxesRunTime.unboxToBoolean(p.apply(ScalaRunTime$.MODULE$.array_apply($this, indexWhere$extension_i)))) continue;
                n = indexWhere$extension_i;
                break block2;
            }
            n = idx = -1;
        }
        if (idx == -1) {
            return None$.MODULE$;
        }
        return new Some<Object>(ScalaRunTime$.MODULE$.array_apply($this, idx));
    }

    public final <A> boolean exists$extension(Object $this, Function1<A, Object> p) {
        int n;
        block1: {
            for (int indexWhere$extension_i = 0; indexWhere$extension_i < Array.getLength($this); ++indexWhere$extension_i) {
                if (!BoxesRunTime.unboxToBoolean(p.apply(ScalaRunTime$.MODULE$.array_apply($this, indexWhere$extension_i)))) continue;
                n = indexWhere$extension_i;
                break block1;
            }
            n = -1;
        }
        return n >= 0;
    }

    public final <A> boolean forall$extension(Object $this, Function1<A, Object> p) {
        for (int i = 0; i < Array.getLength($this); ++i) {
            if (BoxesRunTime.unboxToBoolean(p.apply(ScalaRunTime$.MODULE$.array_apply($this, i)))) continue;
            return false;
        }
        return true;
    }

    public final <B, A> B foldLeft$extension(Object $this, B z, Function2<B, A, B> op) {
        if ($this == null) {
            throw new NullPointerException();
        }
        if ($this instanceof Object[]) {
            Object[] objectArray = (Object[])$this;
            int f$2_length = objectArray.length;
            B f$2_v = z;
            for (int f$2_i = 0; f$2_i < f$2_length; ++f$2_i) {
                f$2_v = op.apply(f$2_v, objectArray[f$2_i]);
            }
            return f$2_v;
        }
        if ($this instanceof int[]) {
            int[] nArray = (int[])$this;
            int f$mIc$sp$1_length = nArray.length;
            B f$mIc$sp$1_v = z;
            for (int f$mIc$sp$1_i = 0; f$mIc$sp$1_i < f$mIc$sp$1_length; ++f$mIc$sp$1_i) {
                f$mIc$sp$1_v = op.apply(f$mIc$sp$1_v, nArray[f$mIc$sp$1_i]);
            }
            return f$mIc$sp$1_v;
        }
        if ($this instanceof double[]) {
            double[] dArray = (double[])$this;
            int f$mDc$sp$1_length = dArray.length;
            B f$mDc$sp$1_v = z;
            for (int f$mDc$sp$1_i = 0; f$mDc$sp$1_i < f$mDc$sp$1_length; ++f$mDc$sp$1_i) {
                f$mDc$sp$1_v = op.apply(f$mDc$sp$1_v, dArray[f$mDc$sp$1_i]);
            }
            return f$mDc$sp$1_v;
        }
        if ($this instanceof long[]) {
            long[] lArray = (long[])$this;
            int f$mJc$sp$1_length = lArray.length;
            B f$mJc$sp$1_v = z;
            for (int f$mJc$sp$1_i = 0; f$mJc$sp$1_i < f$mJc$sp$1_length; ++f$mJc$sp$1_i) {
                f$mJc$sp$1_v = op.apply(f$mJc$sp$1_v, lArray[f$mJc$sp$1_i]);
            }
            return f$mJc$sp$1_v;
        }
        if ($this instanceof float[]) {
            float[] fArray = (float[])$this;
            int f$mFc$sp$1_length = fArray.length;
            B f$mFc$sp$1_v = z;
            for (int f$mFc$sp$1_i = 0; f$mFc$sp$1_i < f$mFc$sp$1_length; ++f$mFc$sp$1_i) {
                f$mFc$sp$1_v = op.apply(f$mFc$sp$1_v, Float.valueOf(fArray[f$mFc$sp$1_i]));
            }
            return f$mFc$sp$1_v;
        }
        if ($this instanceof char[]) {
            char[] cArray = (char[])$this;
            int f$mCc$sp$1_length = cArray.length;
            B f$mCc$sp$1_v = z;
            for (int f$mCc$sp$1_i = 0; f$mCc$sp$1_i < f$mCc$sp$1_length; ++f$mCc$sp$1_i) {
                f$mCc$sp$1_v = op.apply(f$mCc$sp$1_v, Character.valueOf(cArray[f$mCc$sp$1_i]));
            }
            return f$mCc$sp$1_v;
        }
        if ($this instanceof byte[]) {
            byte[] byArray = (byte[])$this;
            int f$mBc$sp$1_length = byArray.length;
            B f$mBc$sp$1_v = z;
            for (int f$mBc$sp$1_i = 0; f$mBc$sp$1_i < f$mBc$sp$1_length; ++f$mBc$sp$1_i) {
                f$mBc$sp$1_v = op.apply(f$mBc$sp$1_v, byArray[f$mBc$sp$1_i]);
            }
            return f$mBc$sp$1_v;
        }
        if ($this instanceof short[]) {
            short[] sArray = (short[])$this;
            int f$mSc$sp$1_length = sArray.length;
            B f$mSc$sp$1_v = z;
            for (int f$mSc$sp$1_i = 0; f$mSc$sp$1_i < f$mSc$sp$1_length; ++f$mSc$sp$1_i) {
                f$mSc$sp$1_v = op.apply(f$mSc$sp$1_v, sArray[f$mSc$sp$1_i]);
            }
            return f$mSc$sp$1_v;
        }
        if ($this instanceof boolean[]) {
            boolean[] blArray = (boolean[])$this;
            int f$mZc$sp$1_length = blArray.length;
            B f$mZc$sp$1_v = z;
            for (int f$mZc$sp$1_i = 0; f$mZc$sp$1_i < f$mZc$sp$1_length; ++f$mZc$sp$1_i) {
                f$mZc$sp$1_v = op.apply(f$mZc$sp$1_v, blArray[f$mZc$sp$1_i]);
            }
            return f$mZc$sp$1_v;
        }
        if ($this instanceof BoxedUnit[]) {
            BoxedUnit[] boxedUnitArray = (BoxedUnit[])$this;
            int f$mVc$sp$1_length = boxedUnitArray.length;
            B f$mVc$sp$1_v = z;
            for (int f$mVc$sp$1_i = 0; f$mVc$sp$1_i < f$mVc$sp$1_length; ++f$mVc$sp$1_i) {
                f$mVc$sp$1_v = op.apply(f$mVc$sp$1_v, boxedUnitArray[f$mVc$sp$1_i]);
            }
            return f$mVc$sp$1_v;
        }
        throw new MatchError($this);
    }

    public final <B, A> Object scanLeft$extension(Object $this, B z, Function2<B, A, B> op, ClassTag<B> evidence$5) {
        int i;
        B v = z;
        Object res = evidence$5.newArray(Array.getLength($this) + 1);
        for (i = 0; i < Array.getLength($this); ++i) {
            ScalaRunTime$.MODULE$.array_update(res, i, v);
            v = op.apply(v, ScalaRunTime$.MODULE$.array_apply($this, i));
        }
        ScalaRunTime$.MODULE$.array_update(res, i, v);
        return res;
    }

    public final <B, A> Object scan$extension(Object $this, B z, Function2<B, B, B> op, ClassTag<B> evidence$6) {
        int scanLeft$extension_i;
        B scanLeft$extension_v = z;
        Object scanLeft$extension_res = evidence$6.newArray(Array.getLength($this) + 1);
        for (scanLeft$extension_i = 0; scanLeft$extension_i < Array.getLength($this); ++scanLeft$extension_i) {
            ScalaRunTime$.MODULE$.array_update(scanLeft$extension_res, scanLeft$extension_i, scanLeft$extension_v);
            scanLeft$extension_v = op.apply(scanLeft$extension_v, ScalaRunTime$.MODULE$.array_apply($this, scanLeft$extension_i));
        }
        ScalaRunTime$.MODULE$.array_update(scanLeft$extension_res, scanLeft$extension_i, scanLeft$extension_v);
        return scanLeft$extension_res;
    }

    public final <B, A> Object scanRight$extension(Object $this, B z, Function2<A, B, B> op, ClassTag<B> evidence$7) {
        B v = z;
        Object res = evidence$7.newArray(Array.getLength($this) + 1);
        ScalaRunTime$.MODULE$.array_update(res, Array.getLength($this), z);
        for (int i = Array.getLength($this) - 1; i >= 0; --i) {
            v = op.apply(ScalaRunTime$.MODULE$.array_apply($this, i), v);
            ScalaRunTime$.MODULE$.array_update(res, i, v);
        }
        return res;
    }

    public final <B, A> B foldRight$extension(Object $this, B z, Function2<A, B, B> op) {
        if ($this == null) {
            throw new NullPointerException();
        }
        if ($this instanceof Object[]) {
            Object[] objectArray = (Object[])$this;
            B f$3_v = z;
            for (int f$3_i = objectArray.length - 1; f$3_i >= 0; --f$3_i) {
                f$3_v = op.apply(objectArray[f$3_i], f$3_v);
            }
            return f$3_v;
        }
        if ($this instanceof int[]) {
            int[] nArray = (int[])$this;
            B f$mIc$sp$2_v = z;
            for (int f$mIc$sp$2_i = nArray.length - 1; f$mIc$sp$2_i >= 0; --f$mIc$sp$2_i) {
                f$mIc$sp$2_v = op.apply(nArray[f$mIc$sp$2_i], f$mIc$sp$2_v);
            }
            return f$mIc$sp$2_v;
        }
        if ($this instanceof double[]) {
            double[] dArray = (double[])$this;
            B f$mDc$sp$2_v = z;
            for (int f$mDc$sp$2_i = dArray.length - 1; f$mDc$sp$2_i >= 0; --f$mDc$sp$2_i) {
                f$mDc$sp$2_v = op.apply(dArray[f$mDc$sp$2_i], f$mDc$sp$2_v);
            }
            return f$mDc$sp$2_v;
        }
        if ($this instanceof long[]) {
            long[] lArray = (long[])$this;
            B f$mJc$sp$2_v = z;
            for (int f$mJc$sp$2_i = lArray.length - 1; f$mJc$sp$2_i >= 0; --f$mJc$sp$2_i) {
                f$mJc$sp$2_v = op.apply(lArray[f$mJc$sp$2_i], f$mJc$sp$2_v);
            }
            return f$mJc$sp$2_v;
        }
        if ($this instanceof float[]) {
            float[] fArray = (float[])$this;
            B f$mFc$sp$2_v = z;
            for (int f$mFc$sp$2_i = fArray.length - 1; f$mFc$sp$2_i >= 0; --f$mFc$sp$2_i) {
                f$mFc$sp$2_v = op.apply(Float.valueOf(fArray[f$mFc$sp$2_i]), f$mFc$sp$2_v);
            }
            return f$mFc$sp$2_v;
        }
        if ($this instanceof char[]) {
            char[] cArray = (char[])$this;
            B f$mCc$sp$2_v = z;
            for (int f$mCc$sp$2_i = cArray.length - 1; f$mCc$sp$2_i >= 0; --f$mCc$sp$2_i) {
                f$mCc$sp$2_v = op.apply(Character.valueOf(cArray[f$mCc$sp$2_i]), f$mCc$sp$2_v);
            }
            return f$mCc$sp$2_v;
        }
        if ($this instanceof byte[]) {
            byte[] byArray = (byte[])$this;
            B f$mBc$sp$2_v = z;
            for (int f$mBc$sp$2_i = byArray.length - 1; f$mBc$sp$2_i >= 0; --f$mBc$sp$2_i) {
                f$mBc$sp$2_v = op.apply(byArray[f$mBc$sp$2_i], f$mBc$sp$2_v);
            }
            return f$mBc$sp$2_v;
        }
        if ($this instanceof short[]) {
            short[] sArray = (short[])$this;
            B f$mSc$sp$2_v = z;
            for (int f$mSc$sp$2_i = sArray.length - 1; f$mSc$sp$2_i >= 0; --f$mSc$sp$2_i) {
                f$mSc$sp$2_v = op.apply(sArray[f$mSc$sp$2_i], f$mSc$sp$2_v);
            }
            return f$mSc$sp$2_v;
        }
        if ($this instanceof boolean[]) {
            boolean[] blArray = (boolean[])$this;
            B f$mZc$sp$2_v = z;
            for (int f$mZc$sp$2_i = blArray.length - 1; f$mZc$sp$2_i >= 0; --f$mZc$sp$2_i) {
                f$mZc$sp$2_v = op.apply(blArray[f$mZc$sp$2_i], f$mZc$sp$2_v);
            }
            return f$mZc$sp$2_v;
        }
        if ($this instanceof BoxedUnit[]) {
            BoxedUnit[] boxedUnitArray = (BoxedUnit[])$this;
            B f$mVc$sp$2_v = z;
            for (int f$mVc$sp$2_i = boxedUnitArray.length - 1; f$mVc$sp$2_i >= 0; --f$mVc$sp$2_i) {
                f$mVc$sp$2_v = op.apply(boxedUnitArray[f$mVc$sp$2_i], f$mVc$sp$2_v);
            }
            return f$mVc$sp$2_v;
        }
        throw new MatchError($this);
    }

    public final <A1, A> A1 fold$extension(Object $this, A1 z, Function2<A1, A1, A1> op) {
        if ($this == null) {
            throw new NullPointerException();
        }
        if ($this instanceof Object[]) {
            Object[] objectArray = (Object[])$this;
            int foldLeft$extension_f$2_length = objectArray.length;
            A1 foldLeft$extension_f$2_v = z;
            for (int foldLeft$extension_f$2_i = 0; foldLeft$extension_f$2_i < foldLeft$extension_f$2_length; ++foldLeft$extension_f$2_i) {
                foldLeft$extension_f$2_v = op.apply(foldLeft$extension_f$2_v, objectArray[foldLeft$extension_f$2_i]);
            }
            return foldLeft$extension_f$2_v;
        }
        if ($this instanceof int[]) {
            int[] nArray = (int[])$this;
            int foldLeft$extension_f$mIc$sp$1_length = nArray.length;
            A1 foldLeft$extension_f$mIc$sp$1_v = z;
            for (int foldLeft$extension_f$mIc$sp$1_i = 0; foldLeft$extension_f$mIc$sp$1_i < foldLeft$extension_f$mIc$sp$1_length; ++foldLeft$extension_f$mIc$sp$1_i) {
                foldLeft$extension_f$mIc$sp$1_v = op.apply(foldLeft$extension_f$mIc$sp$1_v, nArray[foldLeft$extension_f$mIc$sp$1_i]);
            }
            return foldLeft$extension_f$mIc$sp$1_v;
        }
        if ($this instanceof double[]) {
            double[] dArray = (double[])$this;
            int foldLeft$extension_f$mDc$sp$1_length = dArray.length;
            A1 foldLeft$extension_f$mDc$sp$1_v = z;
            for (int foldLeft$extension_f$mDc$sp$1_i = 0; foldLeft$extension_f$mDc$sp$1_i < foldLeft$extension_f$mDc$sp$1_length; ++foldLeft$extension_f$mDc$sp$1_i) {
                foldLeft$extension_f$mDc$sp$1_v = op.apply(foldLeft$extension_f$mDc$sp$1_v, dArray[foldLeft$extension_f$mDc$sp$1_i]);
            }
            return foldLeft$extension_f$mDc$sp$1_v;
        }
        if ($this instanceof long[]) {
            long[] lArray = (long[])$this;
            int foldLeft$extension_f$mJc$sp$1_length = lArray.length;
            A1 foldLeft$extension_f$mJc$sp$1_v = z;
            for (int foldLeft$extension_f$mJc$sp$1_i = 0; foldLeft$extension_f$mJc$sp$1_i < foldLeft$extension_f$mJc$sp$1_length; ++foldLeft$extension_f$mJc$sp$1_i) {
                foldLeft$extension_f$mJc$sp$1_v = op.apply(foldLeft$extension_f$mJc$sp$1_v, lArray[foldLeft$extension_f$mJc$sp$1_i]);
            }
            return foldLeft$extension_f$mJc$sp$1_v;
        }
        if ($this instanceof float[]) {
            float[] fArray = (float[])$this;
            int foldLeft$extension_f$mFc$sp$1_length = fArray.length;
            A1 foldLeft$extension_f$mFc$sp$1_v = z;
            for (int foldLeft$extension_f$mFc$sp$1_i = 0; foldLeft$extension_f$mFc$sp$1_i < foldLeft$extension_f$mFc$sp$1_length; ++foldLeft$extension_f$mFc$sp$1_i) {
                foldLeft$extension_f$mFc$sp$1_v = op.apply(foldLeft$extension_f$mFc$sp$1_v, Float.valueOf(fArray[foldLeft$extension_f$mFc$sp$1_i]));
            }
            return foldLeft$extension_f$mFc$sp$1_v;
        }
        if ($this instanceof char[]) {
            char[] cArray = (char[])$this;
            int foldLeft$extension_f$mCc$sp$1_length = cArray.length;
            A1 foldLeft$extension_f$mCc$sp$1_v = z;
            for (int foldLeft$extension_f$mCc$sp$1_i = 0; foldLeft$extension_f$mCc$sp$1_i < foldLeft$extension_f$mCc$sp$1_length; ++foldLeft$extension_f$mCc$sp$1_i) {
                foldLeft$extension_f$mCc$sp$1_v = op.apply(foldLeft$extension_f$mCc$sp$1_v, Character.valueOf(cArray[foldLeft$extension_f$mCc$sp$1_i]));
            }
            return foldLeft$extension_f$mCc$sp$1_v;
        }
        if ($this instanceof byte[]) {
            byte[] byArray = (byte[])$this;
            int foldLeft$extension_f$mBc$sp$1_length = byArray.length;
            A1 foldLeft$extension_f$mBc$sp$1_v = z;
            for (int foldLeft$extension_f$mBc$sp$1_i = 0; foldLeft$extension_f$mBc$sp$1_i < foldLeft$extension_f$mBc$sp$1_length; ++foldLeft$extension_f$mBc$sp$1_i) {
                foldLeft$extension_f$mBc$sp$1_v = op.apply(foldLeft$extension_f$mBc$sp$1_v, byArray[foldLeft$extension_f$mBc$sp$1_i]);
            }
            return foldLeft$extension_f$mBc$sp$1_v;
        }
        if ($this instanceof short[]) {
            short[] sArray = (short[])$this;
            int foldLeft$extension_f$mSc$sp$1_length = sArray.length;
            A1 foldLeft$extension_f$mSc$sp$1_v = z;
            for (int foldLeft$extension_f$mSc$sp$1_i = 0; foldLeft$extension_f$mSc$sp$1_i < foldLeft$extension_f$mSc$sp$1_length; ++foldLeft$extension_f$mSc$sp$1_i) {
                foldLeft$extension_f$mSc$sp$1_v = op.apply(foldLeft$extension_f$mSc$sp$1_v, sArray[foldLeft$extension_f$mSc$sp$1_i]);
            }
            return foldLeft$extension_f$mSc$sp$1_v;
        }
        if ($this instanceof boolean[]) {
            boolean[] blArray = (boolean[])$this;
            int foldLeft$extension_f$mZc$sp$1_length = blArray.length;
            A1 foldLeft$extension_f$mZc$sp$1_v = z;
            for (int foldLeft$extension_f$mZc$sp$1_i = 0; foldLeft$extension_f$mZc$sp$1_i < foldLeft$extension_f$mZc$sp$1_length; ++foldLeft$extension_f$mZc$sp$1_i) {
                foldLeft$extension_f$mZc$sp$1_v = op.apply(foldLeft$extension_f$mZc$sp$1_v, blArray[foldLeft$extension_f$mZc$sp$1_i]);
            }
            return foldLeft$extension_f$mZc$sp$1_v;
        }
        if ($this instanceof BoxedUnit[]) {
            BoxedUnit[] boxedUnitArray = (BoxedUnit[])$this;
            int foldLeft$extension_f$mVc$sp$1_length = boxedUnitArray.length;
            A1 foldLeft$extension_f$mVc$sp$1_v = z;
            for (int foldLeft$extension_f$mVc$sp$1_i = 0; foldLeft$extension_f$mVc$sp$1_i < foldLeft$extension_f$mVc$sp$1_length; ++foldLeft$extension_f$mVc$sp$1_i) {
                foldLeft$extension_f$mVc$sp$1_v = op.apply(foldLeft$extension_f$mVc$sp$1_v, boxedUnitArray[foldLeft$extension_f$mVc$sp$1_i]);
            }
            return foldLeft$extension_f$mVc$sp$1_v;
        }
        throw new MatchError($this);
    }

    public final <B, A> Object map$extension(Object $this, Function1<A, B> f, ClassTag<B> ct) {
        int len = Array.getLength($this);
        Object ys = ct.newArray(len);
        if (len > 0) {
            int i;
            if ($this instanceof Object[]) {
                Object[] objectArray = (Object[])$this;
                for (i = 0; i < len; ++i) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(objectArray[i]));
                }
            } else if ($this instanceof int[]) {
                int[] nArray = (int[])$this;
                while (i < len) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(nArray[i]));
                    ++i;
                }
            } else if ($this instanceof double[]) {
                double[] dArray = (double[])$this;
                while (i < len) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(dArray[i]));
                    ++i;
                }
            } else if ($this instanceof long[]) {
                long[] lArray = (long[])$this;
                while (i < len) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(lArray[i]));
                    ++i;
                }
            } else if ($this instanceof float[]) {
                float[] fArray = (float[])$this;
                while (i < len) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(Float.valueOf(fArray[i])));
                    ++i;
                }
            } else if ($this instanceof char[]) {
                char[] cArray = (char[])$this;
                while (i < len) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(Character.valueOf(cArray[i])));
                    ++i;
                }
            } else if ($this instanceof byte[]) {
                byte[] byArray = (byte[])$this;
                while (i < len) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(byArray[i]));
                    ++i;
                }
            } else if ($this instanceof short[]) {
                short[] sArray = (short[])$this;
                while (i < len) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(sArray[i]));
                    ++i;
                }
            } else if ($this instanceof boolean[]) {
                boolean[] blArray = (boolean[])$this;
                while (i < len) {
                    ScalaRunTime$.MODULE$.array_update(ys, i, f.apply(blArray[i]));
                    ++i;
                }
            } else {
                throw new MatchError($this);
            }
        }
        return ys;
    }

    public final <A> Object mapInPlace$extension(Object $this, Function1<A, A> f) {
        for (int i = 0; i < Array.getLength($this); ++i) {
            ScalaRunTime$.MODULE$.array_update($this, i, f.apply(ScalaRunTime$.MODULE$.array_apply($this, i)));
        }
        return $this;
    }

    public final <B, A> Object flatMap$extension(Object $this, Function1<A, IterableOnce<B>> f, ClassTag<B> evidence$8) {
        ArrayBuilder arrayBuilder;
        Class<?> clazz = evidence$8.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<B>(evidence$8);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte b = arrayBuilder;
        for (int i = 0; i < Array.getLength($this); ++i) {
            IterableOnce<B> $plus$plus$eq_xs = f.apply(ScalaRunTime$.MODULE$.array_apply($this, i));
            b.addAll($plus$plus$eq_xs);
            Object var7_7 = null;
        }
        return b.result();
    }

    public final <BS, B, A> Object flatMap$extension(Object $this, Function1<A, BS> f, Function1<BS, Iterable<B>> asIterable, ClassTag<B> m4) {
        ArrayBuilder arrayBuilder;
        Class<?> clazz = m4.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<B>(m4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte flatMap$extension_b = arrayBuilder;
        for (int flatMap$extension_i = 0; flatMap$extension_i < Array.getLength($this); ++flatMap$extension_i) {
            Object object = ScalaRunTime$.MODULE$.array_apply($this, flatMap$extension_i);
            Iterable<B> flatMap$extension_$plus$plus$eq_xs = asIterable.apply(f.apply(object));
            flatMap$extension_b.addAll(flatMap$extension_$plus$plus$eq_xs);
            Object var8_8 = null;
        }
        return flatMap$extension_b.result();
    }

    public final <B, A> Object flatten$extension(Object $this, Function1<A, IterableOnce<B>> asIterable, ClassTag<B> m4) {
        int i;
        ArrayBuilder arrayBuilder;
        Class<?> clazz = m4.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<B>(m4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte b = arrayBuilder;
        int len = Array.getLength($this);
        int size = 0;
        for (i = 0; i < len; ++i) {
            Object object = ScalaRunTime$.MODULE$.array_apply($this, i);
            if (object instanceof IterableOnce) {
                int k = ((IterableOnce)object).knownSize();
                if (k <= 0) continue;
                size += k;
                continue;
            }
            if (!ScalaRunTime$.MODULE$.isArray(object, 1)) continue;
            size += Array.getLength(object);
        }
        if (size > 0) {
            b.sizeHint(size);
        }
        for (i = 0; i < len; ++i) {
            IterableOnce<B> $plus$plus$eq_xs = asIterable.apply(ScalaRunTime$.MODULE$.array_apply($this, i));
            b.addAll($plus$plus$eq_xs);
            Object var11_11 = null;
        }
        return b.result();
    }

    public final <B, A> Object collect$extension(Object $this, PartialFunction<A, B> pf, ClassTag<B> evidence$9) {
        ArrayBuilder arrayBuilder;
        Function1<Object, Object> fallback = this.fallback();
        Class<?> clazz = evidence$9.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<B>(evidence$9);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte b = arrayBuilder;
        for (int i = 0; i < Array.getLength($this); ++i) {
            Object v = pf.applyOrElse(ScalaRunTime$.MODULE$.array_apply($this, i), fallback);
            if (v == fallback) continue;
            b.addOne(v);
        }
        return b.result();
    }

    public final <B, A> Option<B> collectFirst$extension(Object $this, PartialFunction<A, B> pf) {
        Function1<Object, Object> fallback = this.fallback();
        for (int i = 0; i < Array.getLength($this); ++i) {
            Object v = pf.applyOrElse(ScalaRunTime$.MODULE$.array_apply($this, i), fallback);
            if (v == fallback) continue;
            return new Some<Object>(v);
        }
        return None$.MODULE$;
    }

    public final <B, A> Tuple2<A, B>[] zip$extension(Object $this, IterableOnce<B> that) {
        ArrayBuilder.ofRef b = new ArrayBuilder.ofRef(ClassTag$.MODULE$.apply(Tuple2.class));
        int k = that.knownSize();
        b.sizeHint(k >= 0 ? Math.min(k, Array.getLength($this)) : Array.getLength($this));
        Iterator<B> it = that.iterator();
        for (int i = 0; i < Array.getLength($this) && it.hasNext(); ++i) {
            Tuple2<Object, B> $plus$eq_elem = new Tuple2<Object, B>(ScalaRunTime$.MODULE$.array_apply($this, i), it.next());
            b.addOne($plus$eq_elem);
            Object var7_7 = null;
        }
        return (Tuple2[])b.result();
    }

    public final <B, A> LazyZip2<A, B, Object> lazyZip$extension(Object $this, Iterable<B> that) {
        return new LazyZip2($this, scala.collection.immutable.ArraySeq$.MODULE$.unsafeWrapArray($this), that);
    }

    public final <A1, B, A> Tuple2<A1, B>[] zipAll$extension(Object $this, Iterable<B> that, A1 thisElem, B thatElem) {
        int i;
        ArrayBuilder.ofRef b = new ArrayBuilder.ofRef(ClassTag$.MODULE$.apply(Tuple2.class));
        int k = that.knownSize();
        b.sizeHint(Math.max(k, Array.getLength($this)));
        Iterator it = that.iterator();
        for (i = 0; i < Array.getLength($this) && it.hasNext(); ++i) {
            Tuple2 $plus$eq_elem = new Tuple2(ScalaRunTime$.MODULE$.array_apply($this, i), it.next());
            b.addOne($plus$eq_elem);
            Object var9_9 = null;
        }
        while (it.hasNext()) {
            Object a = it.next();
            Tuple2 $plus$eq_elem = new Tuple2(thisElem, a);
            b.addOne($plus$eq_elem);
            Object var10_10 = null;
            ++i;
        }
        while (i < Array.getLength($this)) {
            Tuple2<Object, B> $plus$eq_elem = new Tuple2<Object, B>(ScalaRunTime$.MODULE$.array_apply($this, i), thatElem);
            b.addOne($plus$eq_elem);
            Object var11_12 = null;
            ++i;
        }
        return (Tuple2[])b.result();
    }

    public final <A> Tuple2<A, Object>[] zipWithIndex$extension(Object $this) {
        Tuple2[] b = new Tuple2[Array.getLength($this)];
        for (int i = 0; i < Array.getLength($this); ++i) {
            b[i] = new Tuple2<Object, Integer>(ScalaRunTime$.MODULE$.array_apply($this, i), i);
        }
        return b;
    }

    public final <B, A> Object appended$extension(Object $this, B x, ClassTag<B> evidence$10) {
        Object dest = Array$.MODULE$.copyAs($this, Array.getLength($this) + 1, evidence$10);
        ScalaRunTime$.MODULE$.array_update(dest, Array.getLength($this), x);
        return dest;
    }

    public final <B, A> Object $colon$plus$extension(Object $this, B x, ClassTag<B> evidence$11) {
        return this.appended$extension($this, x, evidence$11);
    }

    public final <B, A> Object prepended$extension(Object $this, B x, ClassTag<B> evidence$12) {
        Object dest = evidence$12.newArray(Array.getLength($this) + 1);
        ScalaRunTime$.MODULE$.array_update(dest, 0, x);
        Array$.MODULE$.copy($this, 0, dest, 1, Array.getLength($this));
        return dest;
    }

    public final <B, A> Object $plus$colon$extension(Object $this, B x, ClassTag<B> evidence$13) {
        return this.prepended$extension($this, x, evidence$13);
    }

    public final <B, A> Object prependedAll$extension(Object $this, IterableOnce<B> prefix, ClassTag<B> evidence$14) {
        ArrayBuilder arrayBuilder;
        Class<?> clazz = evidence$14.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<B>(evidence$14);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte b = arrayBuilder;
        int k = prefix.knownSize();
        if (k >= 0) {
            b.sizeHint(k + Array.getLength($this));
        }
        b.addAll((IterableOnce)prefix);
        if (k < 0) {
            b.sizeHint(b.length() + Array.getLength($this));
        }
        b.addAll($this);
        return b.result();
    }

    public final <B, A> Object prependedAll$extension(Object $this, Object prefix, ClassTag<B> evidence$15) {
        Object dest = Array$.MODULE$.copyAs(prefix, Array.getLength(prefix) + Array.getLength($this), evidence$15);
        Array$.MODULE$.copy($this, 0, dest, Array.getLength(prefix), Array.getLength($this));
        return dest;
    }

    public final <B, A> Object $plus$plus$colon$extension(Object $this, IterableOnce<B> prefix, ClassTag<B> evidence$16) {
        return this.prependedAll$extension($this, prefix, evidence$16);
    }

    public final <B, A> Object $plus$plus$colon$extension(Object $this, Object prefix, ClassTag<B> evidence$17) {
        return this.prependedAll$extension($this, prefix, evidence$17);
    }

    public final <B, A> Object appendedAll$extension(Object $this, IterableOnce<B> suffix, ClassTag<B> evidence$18) {
        ArrayBuilder arrayBuilder;
        Class<?> clazz = evidence$18.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<B>(evidence$18);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte b = arrayBuilder;
        int k = suffix.knownSize();
        if (k >= 0) {
            b.sizeHint(k + Array.getLength($this));
        }
        b.addAll($this);
        b.addAll((IterableOnce)suffix);
        return b.result();
    }

    public final <B, A> Object appendedAll$extension(Object $this, Object suffix, ClassTag<B> evidence$19) {
        Object dest = Array$.MODULE$.copyAs($this, Array.getLength($this) + Array.getLength(suffix), evidence$19);
        Array$.MODULE$.copy(suffix, 0, dest, Array.getLength($this), Array.getLength(suffix));
        return dest;
    }

    public final <B, A> Object $colon$plus$plus$extension(Object $this, IterableOnce<B> suffix, ClassTag<B> evidence$20) {
        return this.appendedAll$extension($this, suffix, evidence$20);
    }

    public final <B, A> Object $colon$plus$plus$extension(Object $this, Object suffix, ClassTag<B> evidence$21) {
        return this.appendedAll$extension($this, suffix, evidence$21);
    }

    public final <B, A> Object concat$extension(Object $this, IterableOnce<B> suffix, ClassTag<B> evidence$22) {
        return this.appendedAll$extension($this, suffix, evidence$22);
    }

    public final <B, A> Object concat$extension(Object $this, Object suffix, ClassTag<B> evidence$23) {
        return this.appendedAll$extension($this, suffix, evidence$23);
    }

    public final <B, A> Object $plus$plus$extension(Object $this, IterableOnce<B> xs, ClassTag<B> evidence$24) {
        return this.appendedAll$extension($this, xs, evidence$24);
    }

    public final <B, A> Object $plus$plus$extension(Object $this, Object xs, ClassTag<B> evidence$25) {
        return this.appendedAll$extension($this, xs, evidence$25);
    }

    public final <A> boolean contains$extension(Object $this, A elem) {
        int n;
        block1: {
            for (int exists$extension_indexWhere$extension_i = 0; exists$extension_indexWhere$extension_i < Array.getLength($this); ++exists$extension_indexWhere$extension_i) {
                Object object = ScalaRunTime$.MODULE$.array_apply($this, exists$extension_indexWhere$extension_i);
                if (!ArrayOps$.$anonfun$contains$1(elem, object)) continue;
                n = exists$extension_indexWhere$extension_i;
                break block1;
            }
            n = -1;
        }
        return n >= 0;
    }

    public final <B, A> Object patch$extension(Object $this, int from, IterableOnce<B> other, int replaced, ClassTag<B> evidence$26) {
        int chunk1;
        int r;
        ArrayBuilder arrayBuilder;
        Class<?> clazz = evidence$26.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<B>(evidence$26);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte b = arrayBuilder;
        int k = other.knownSize();
        int n = r = replaced < 0 ? 0 : replaced;
        if (k >= 0) {
            b.sizeHint(Array.getLength($this) + k - r);
        }
        int n2 = chunk1 = from > 0 ? Math.min(from, Array.getLength($this)) : 0;
        if (chunk1 > 0) {
            b.addAll($this, 0, chunk1);
        }
        b.addAll(other);
        int remaining = Array.getLength($this) - chunk1 - r;
        if (remaining > 0) {
            b.addAll($this, Array.getLength($this) - remaining, remaining);
        }
        return b.result();
    }

    public final <A1, A2, A> Tuple2<Object, Object> unzip$extension(Object $this, Function1<A, Tuple2<A1, A2>> asPair, ClassTag<A1> ct1, ClassTag<A2> ct2) {
        Object a1 = ct1.newArray(Array.getLength($this));
        Object a2 = ct2.newArray(Array.getLength($this));
        for (int i = 0; i < Array.getLength($this); ++i) {
            Tuple2<A1, A2> e = asPair.apply(ScalaRunTime$.MODULE$.array_apply($this, i));
            ScalaRunTime$.MODULE$.array_update(a1, i, e._1());
            ScalaRunTime$.MODULE$.array_update(a2, i, e._2());
        }
        return new Tuple2<Object, Object>(a1, a2);
    }

    public final <A1, A2, A3, A> Tuple3<Object, Object, Object> unzip3$extension(Object $this, Function1<A, Tuple3<A1, A2, A3>> asTriple, ClassTag<A1> ct1, ClassTag<A2> ct2, ClassTag<A3> ct3) {
        Object a1 = ct1.newArray(Array.getLength($this));
        Object a2 = ct2.newArray(Array.getLength($this));
        Object a3 = ct3.newArray(Array.getLength($this));
        for (int i = 0; i < Array.getLength($this); ++i) {
            Tuple3<A1, A2, A3> e = asTriple.apply(ScalaRunTime$.MODULE$.array_apply($this, i));
            ScalaRunTime$.MODULE$.array_update(a1, i, e._1());
            ScalaRunTime$.MODULE$.array_update(a2, i, e._2());
            ScalaRunTime$.MODULE$.array_update(a3, i, e._3());
        }
        return new Tuple3<Object, Object, Object>(a1, a2, a3);
    }

    public final <B, A> Object[] transpose$extension(Object $this, Function1<A, Object> asArray) {
        boolean[] blArray;
        short[] sArray;
        byte[] byArray;
        char[] cArray;
        float[] fArray;
        long[] lArray;
        double[] dArray;
        int[] nArray;
        Object object;
        int foreach$extension_i;
        Object[] objectArray;
        boolean[] blArray2;
        short[] sArray2;
        byte[] byArray2;
        char[] cArray2;
        float[] fArray2;
        long[] lArray2;
        double[] dArray2;
        int[] nArray2;
        Object[] objectArray2;
        Class<?> aClass = $this.getClass().getComponentType();
        ArrayBuilder.ofRef bb = new ArrayBuilder.ofRef(ClassTag$.MODULE$.apply(aClass));
        if (Array.getLength($this) == 0) {
            return bb.result();
        }
        Object map$extension_$this = asArray.apply(ScalaRunTime$.MODULE$.array_apply($this, 0));
        int map$extension_len = Array.getLength(map$extension_$this);
        ArrayBuilder[] map$extension_ys = new ArrayBuilder[map$extension_len];
        if (map$extension_len > 0) {
            int map$extension_i;
            if (map$extension_$this instanceof Object[]) {
                objectArray2 = (Object[])map$extension_$this;
                for (map$extension_i = 0; map$extension_i < map$extension_len; ++map$extension_i) {
                    ArrayBuilder array_update_value;
                    Object cfr_ignored_0 = objectArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var34_10 = null;
                }
            } else if (map$extension_$this instanceof int[]) {
                nArray2 = (int[])map$extension_$this;
                while (map$extension_i < map$extension_len) {
                    ArrayBuilder array_update_value;
                    int cfr_ignored_1 = nArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var35_12 = null;
                    ++map$extension_i;
                }
            } else if (map$extension_$this instanceof double[]) {
                dArray2 = (double[])map$extension_$this;
                while (map$extension_i < map$extension_len) {
                    ArrayBuilder array_update_value;
                    double cfr_ignored_2 = dArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var36_14 = null;
                    ++map$extension_i;
                }
            } else if (map$extension_$this instanceof long[]) {
                lArray2 = (long[])map$extension_$this;
                while (map$extension_i < map$extension_len) {
                    ArrayBuilder array_update_value;
                    long cfr_ignored_3 = lArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var37_16 = null;
                    ++map$extension_i;
                }
            } else if (map$extension_$this instanceof float[]) {
                fArray2 = (float[])map$extension_$this;
                while (map$extension_i < map$extension_len) {
                    ArrayBuilder array_update_value;
                    float cfr_ignored_4 = fArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var38_18 = null;
                    ++map$extension_i;
                }
            } else if (map$extension_$this instanceof char[]) {
                cArray2 = (char[])map$extension_$this;
                while (map$extension_i < map$extension_len) {
                    ArrayBuilder array_update_value;
                    char cfr_ignored_5 = cArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var39_20 = null;
                    ++map$extension_i;
                }
            } else if (map$extension_$this instanceof byte[]) {
                byArray2 = (byte[])map$extension_$this;
                while (map$extension_i < map$extension_len) {
                    ArrayBuilder array_update_value;
                    byte cfr_ignored_6 = byArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var40_22 = null;
                    ++map$extension_i;
                }
            } else if (map$extension_$this instanceof short[]) {
                sArray2 = (short[])map$extension_$this;
                while (map$extension_i < map$extension_len) {
                    ArrayBuilder array_update_value;
                    short cfr_ignored_7 = sArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var41_24 = null;
                    ++map$extension_i;
                }
            } else if (map$extension_$this instanceof boolean[]) {
                blArray2 = (boolean[])map$extension_$this;
                while (map$extension_i < map$extension_len) {
                    ArrayBuilder array_update_value;
                    boolean cfr_ignored_8 = blArray2[map$extension_i];
                    map$extension_ys[map$extension_i] = array_update_value = ArrayOps$.mkRowBuilder$1(aClass);
                    Object var42_26 = null;
                    ++map$extension_i;
                }
            } else {
                throw new MatchError(map$extension_$this);
            }
        }
        Object var6_5 = null;
        Object var8_7 = null;
        objectArray2 = null;
        nArray2 = null;
        dArray2 = null;
        lArray2 = null;
        fArray2 = null;
        cArray2 = null;
        byArray2 = null;
        sArray2 = null;
        blArray2 = null;
        ArrayBuilder[] bs = map$extension_ys;
        int foreach$extension_len = Array.getLength($this);
        if ($this instanceof Object[]) {
            objectArray = (Object[])$this;
            for (foreach$extension_i = 0; foreach$extension_i < foreach$extension_len; ++foreach$extension_i) {
                object = objectArray[foreach$extension_i];
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
            }
        } else if ($this instanceof int[]) {
            nArray = (int[])$this;
            while (foreach$extension_i < foreach$extension_len) {
                object = nArray[foreach$extension_i];
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
                ++foreach$extension_i;
            }
        } else if ($this instanceof double[]) {
            dArray = (double[])$this;
            while (foreach$extension_i < foreach$extension_len) {
                object = dArray[foreach$extension_i];
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
                ++foreach$extension_i;
            }
        } else if ($this instanceof long[]) {
            lArray = (long[])$this;
            while (foreach$extension_i < foreach$extension_len) {
                object = lArray[foreach$extension_i];
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
                ++foreach$extension_i;
            }
        } else if ($this instanceof float[]) {
            fArray = (float[])$this;
            while (foreach$extension_i < foreach$extension_len) {
                object = Float.valueOf(fArray[foreach$extension_i]);
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
                ++foreach$extension_i;
            }
        } else if ($this instanceof char[]) {
            cArray = (char[])$this;
            while (foreach$extension_i < foreach$extension_len) {
                object = Character.valueOf(cArray[foreach$extension_i]);
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
                ++foreach$extension_i;
            }
        } else if ($this instanceof byte[]) {
            byArray = (byte[])$this;
            while (foreach$extension_i < foreach$extension_len) {
                object = byArray[foreach$extension_i];
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
                ++foreach$extension_i;
            }
        } else if ($this instanceof short[]) {
            sArray = (short[])$this;
            while (foreach$extension_i < foreach$extension_len) {
                object = sArray[foreach$extension_i];
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
                ++foreach$extension_i;
            }
        } else if ($this instanceof boolean[]) {
            blArray = (boolean[])$this;
            while (foreach$extension_i < foreach$extension_len) {
                object = blArray[foreach$extension_i];
                ArrayOps$.$anonfun$transpose$2(asArray, bs, object);
                ++foreach$extension_i;
            }
        } else {
            throw new MatchError($this);
        }
        objectArray = null;
        nArray = null;
        dArray = null;
        lArray = null;
        fArray = null;
        cArray = null;
        byArray = null;
        sArray = null;
        blArray = null;
        for (ArrayBuilder arrayBuilder : bs) {
            ArrayOps$.$anonfun$transpose$4(bb, arrayBuilder);
        }
        return bb.result();
    }

    public final <U, A> void foreach$extension(Object $this, Function1<A, U> f) {
        int i;
        int len = Array.getLength($this);
        if ($this instanceof Object[]) {
            Object[] objectArray = (Object[])$this;
            for (i = 0; i < len; ++i) {
                f.apply(objectArray[i]);
            }
            return;
        }
        if ($this instanceof int[]) {
            int[] nArray = (int[])$this;
            while (i < len) {
                f.apply(nArray[i]);
                ++i;
            }
            return;
        }
        if ($this instanceof double[]) {
            double[] dArray = (double[])$this;
            while (i < len) {
                f.apply(dArray[i]);
                ++i;
            }
            return;
        }
        if ($this instanceof long[]) {
            long[] lArray = (long[])$this;
            while (i < len) {
                f.apply(lArray[i]);
                ++i;
            }
            return;
        }
        if ($this instanceof float[]) {
            float[] fArray = (float[])$this;
            while (i < len) {
                f.apply(Float.valueOf(fArray[i]));
                ++i;
            }
            return;
        }
        if ($this instanceof char[]) {
            char[] cArray = (char[])$this;
            while (i < len) {
                f.apply(Character.valueOf(cArray[i]));
                ++i;
            }
            return;
        }
        if ($this instanceof byte[]) {
            byte[] byArray = (byte[])$this;
            while (i < len) {
                f.apply(byArray[i]);
                ++i;
            }
            return;
        }
        if ($this instanceof short[]) {
            short[] sArray = (short[])$this;
            while (i < len) {
                f.apply(sArray[i]);
                ++i;
            }
            return;
        }
        if ($this instanceof boolean[]) {
            boolean[] blArray = (boolean[])$this;
            while (i < len) {
                f.apply(blArray[i]);
                ++i;
            }
            return;
        }
        throw new MatchError($this);
    }

    public final <A> Object distinct$extension(Object $this) {
        ArrayBuilder arrayBuilder;
        Function1<Object, Object> & Serializable distinctBy$extension_f = (Function1<Object, Object> & Serializable)x -> Predef$.MODULE$.identity(x);
        ClassTag distinctBy$extension_make_evidence$1 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
        Class<?> clazz = distinctBy$extension_make_evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(distinctBy$extension_make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var3_3 = null;
        clazz = null;
        return arrayBuilder.addAll((IterableOnce)this.iterator$extension($this).distinctBy(distinctBy$extension_f)).result();
    }

    public final <B, A> Object distinctBy$extension(Object $this, Function1<A, B> f) {
        ArrayBuilder arrayBuilder;
        ClassTag make_evidence$1 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
        Class<?> clazz = make_evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var3_3 = null;
        clazz = null;
        return arrayBuilder.addAll((IterableOnce)this.iterator$extension($this).distinctBy(f)).result();
    }

    public final <B, A> Object padTo$extension(Object $this, int len, B elem, ClassTag<B> evidence$27) {
        int i;
        int newlen = Math.max(i, len);
        Object dest = Array$.MODULE$.copyAs($this, newlen, evidence$27);
        for (i = Array.getLength($this); i < newlen; ++i) {
            ScalaRunTime$.MODULE$.array_update(dest, i, elem);
        }
        return dest;
    }

    public final <A> Range indices$extension(Object $this) {
        int apply_end = Array.getLength($this);
        int apply_start = 0;
        return new Range.Exclusive(apply_start, apply_end, 1);
    }

    public final <K, A> scala.collection.immutable.Map<K, Object> groupBy$extension(Object $this, Function1<A, K> f) {
        Map m4 = (Map)Map$.MODULE$.empty();
        int len = Array.getLength($this);
        for (int i = 0; i < len; ++i) {
            Object elem = ScalaRunTime$.MODULE$.array_apply($this, i);
            K key = f.apply(elem);
            ArrayBuilder bldr = (ArrayBuilder)m4.getOrElseUpdate(key, (Function0<ArrayBuilder> & Serializable)() -> {
                ClassTag make_evidence$1 = ClassTag$.MODULE$.apply($this.getClass().getComponentType());
                Class<?> clazz = make_evidence$1.runtimeClass();
                Class<Byte> clazz2 = Byte.TYPE;
                if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofByte();
                }
                Class<Short> clazz3 = Short.TYPE;
                if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofShort();
                }
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofChar();
                }
                Class<Integer> clazz5 = Integer.TYPE;
                if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofInt();
                }
                Class<Long> clazz6 = Long.TYPE;
                if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofLong();
                }
                Class<Float> clazz7 = Float.TYPE;
                if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofFloat();
                }
                Class<Double> clazz8 = Double.TYPE;
                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofDouble();
                }
                Class<Boolean> clazz9 = Boolean.TYPE;
                if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofBoolean();
                }
                Class<Void> clazz10 = Void.TYPE;
                if (!(clazz10 != null ? !clazz10.equals(clazz) : clazz != null)) {
                    return new ArrayBuilder.ofUnit();
                }
                return new ArrayBuilder.ofRef(make_evidence$1);
            });
            if (bldr == null) {
                throw null;
            }
            bldr.addOne(elem);
        }
        return m4.view().mapValues((Function1<ArrayBuilder, Object> & Serializable)x$3 -> x$3.result()).toMap($less$colon$less$.MODULE$.refl());
    }

    /*
     * WARNING - void declaration
     */
    public final <K, B, A> scala.collection.immutable.Map<K, Object> groupMap$extension(Object $this, Function1<A, K> key, Function1<A, B> f, ClassTag<B> evidence$28) {
        Map m4 = (Map)Map$.MODULE$.empty();
        int len = Array.getLength($this);
        for (int i = 0; i < len; ++i) {
            void $plus$eq_elem;
            Object elem = ScalaRunTime$.MODULE$.array_apply($this, i);
            K k = key.apply(elem);
            ArrayBuilder bldr = (ArrayBuilder)m4.getOrElseUpdate(k, (Function0<ArrayBuilder> & Serializable)() -> ArrayBuilder$.MODULE$.make(evidence$28));
            B b = f.apply(elem);
            if (bldr == null) {
                throw null;
            }
            bldr.addOne($plus$eq_elem);
            b = null;
        }
        return m4.view().mapValues((Function1<ArrayBuilder, Object> & Serializable)x$4 -> x$4.result()).toMap($less$colon$less$.MODULE$.refl());
    }

    public final <A> scala.collection.immutable.Seq<A> toSeq$extension(Object $this) {
        return this.toIndexedSeq$extension($this);
    }

    public final <A> IndexedSeq<A> toIndexedSeq$extension(Object $this) {
        return scala.collection.immutable.ArraySeq$.MODULE$.unsafeWrapArray(Array$.MODULE$.copyOf($this, Array.getLength($this)));
    }

    public final <B, A> int copyToArray$extension(Object $this, Object xs) {
        int copyToArray$extension_start = 0;
        return this.copyToArray$extension($this, xs, copyToArray$extension_start, Integer.MAX_VALUE);
    }

    public final <B, A> int copyToArray$extension(Object $this, Object xs, int start2) {
        return this.copyToArray$extension($this, xs, start2, Integer.MAX_VALUE);
    }

    /*
     * WARNING - void declaration
     */
    public final <B, A> int copyToArray$extension(Object $this, Object xs, int start2, int len) {
        void elemsToCopyToArray_destLen;
        int n = Array.getLength(xs);
        int elemsToCopyToArray_srcLen = Array.getLength($this);
        int copied = Math.max(Math.min(Math.min(len, elemsToCopyToArray_srcLen), (int)(elemsToCopyToArray_destLen - start2)), 0);
        if (copied > 0) {
            Array$.MODULE$.copy($this, 0, xs, start2, copied);
        }
        return copied;
    }

    public final <B, A> Object toArray$extension(Object $this, ClassTag<B> evidence$29) {
        Object destination = evidence$29.newArray(Array.getLength($this));
        int copyToArray$extension_start = 0;
        this.copyToArray$extension($this, destination, copyToArray$extension_start, Integer.MAX_VALUE);
        return destination;
    }

    public final <A> int count$extension(Object $this, Function1<A, Object> p) {
        int res = 0;
        int len = Array.getLength($this);
        for (int i = 0; i < len; ++i) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(ScalaRunTime$.MODULE$.array_apply($this, i)))) continue;
            ++res;
        }
        return res;
    }

    public final <B, A> boolean startsWith$extension(Object $this, Object that) {
        return this.startsWith$extension($this, that, 0);
    }

    public final <B, A> boolean startsWith$extension(Object $this, Object that, int offset) {
        int max$extension_that = 0;
        int safeOffset = Math.max(offset, max$extension_that);
        int thatl = Array.getLength(that);
        if (thatl > Array.getLength($this) - safeOffset) {
            return thatl == 0;
        }
        for (int i = 0; i < thatl; ++i) {
            if (BoxesRunTime.equals(ScalaRunTime$.MODULE$.array_apply($this, i + safeOffset), ScalaRunTime$.MODULE$.array_apply(that, i))) continue;
            return false;
        }
        return true;
    }

    public final <B, A> boolean endsWith$extension(Object $this, Object that) {
        int thatl = Array.getLength(that);
        int off = Array.getLength($this) - thatl;
        if (off < 0) {
            return false;
        }
        for (int i = 0; i < thatl; ++i) {
            if (BoxesRunTime.equals(ScalaRunTime$.MODULE$.array_apply($this, i + off), ScalaRunTime$.MODULE$.array_apply(that, i))) continue;
            return false;
        }
        return true;
    }

    public final <B, A> Object updated$extension(Object $this, int index, B elem, ClassTag<B> evidence$30) {
        if (index < 0 || index >= Array.getLength($this)) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(Array.getLength($this) - 1).append(")").toString());
        }
        Object dest = this.toArray$extension($this, evidence$30);
        ScalaRunTime$.MODULE$.array_update(dest, index, elem);
        return dest;
    }

    public final <A> IndexedSeqView<A> view$extension(Object $this) {
        return new ArrayOps.ArrayView($this);
    }

    public final <B, A> Object diff$extension(Object $this, Seq<B> that) {
        ArraySeq arraySeq = ArraySeq$.MODULE$.make($this);
        if (arraySeq == null) {
            throw null;
        }
        return ((IterableOnceOps)StrictOptimizedSeqOps.diff$(arraySeq, that)).toArray(ClassTag$.MODULE$.apply($this.getClass().getComponentType()));
    }

    public final <B, A> Object intersect$extension(Object $this, Seq<B> that) {
        ArraySeq arraySeq = ArraySeq$.MODULE$.make($this);
        if (arraySeq == null) {
            throw null;
        }
        return ((IterableOnceOps)StrictOptimizedSeqOps.intersect$(arraySeq, that)).toArray(ClassTag$.MODULE$.apply($this.getClass().getComponentType()));
    }

    public final <A> Iterator<Object> sliding$extension(Object $this, int size, int step) {
        return ArraySeq$.MODULE$.make($this).sliding(size, step).map((Function1<ArraySeq, Object> & Serializable)x$5 -> x$5.toArray(ClassTag$.MODULE$.apply($this.getClass().getComponentType())));
    }

    public final <A> int sliding$default$2$extension(Object $this) {
        return 1;
    }

    public final <A> Iterator<Object> combinations$extension(Object $this, int n) {
        return ArraySeq$.MODULE$.make($this).combinations(n).map((Function1<ArraySeq, Object> & Serializable)x$6 -> x$6.toArray(ClassTag$.MODULE$.apply($this.getClass().getComponentType())));
    }

    public final <A> Iterator<Object> permutations$extension(Object $this) {
        return ArraySeq$.MODULE$.make($this).permutations().map((Function1<ArraySeq, Object> & Serializable)x$7 -> x$7.toArray(ClassTag$.MODULE$.apply($this.getClass().getComponentType())));
    }

    public final <B, A> boolean startsWith$extension(Object $this, IterableOnce<B> that, int offset) {
        return ArraySeq$.MODULE$.make($this).startsWith(that, offset);
    }

    public final <B, A> int startsWith$default$2$extension(Object $this) {
        return 0;
    }

    public final <B, A> boolean endsWith$extension(Object $this, Iterable<B> that) {
        return ArraySeq$.MODULE$.make($this).endsWith(that);
    }

    public final <A> int hashCode$extension(Object $this) {
        return $this.hashCode();
    }

    public final <A> boolean equals$extension(Object $this, Object x$1) {
        if (x$1 instanceof ArrayOps) {
            Object object;
            Object object2 = object = x$1 == null ? null : ((ArrayOps)x$1).scala$collection$ArrayOps$$xs();
            if (BoxesRunTime.equals($this, object)) {
                return true;
            }
        }
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$iterateUntilEmpty$1(Object x) {
        return Array.getLength(x) != 0;
    }

    public static final /* synthetic */ boolean $anonfun$takeWhile$1(Function1 p$1, Object x) {
        return !BoxesRunTime.unboxToBoolean(p$1.apply(x));
    }

    public static final /* synthetic */ boolean $anonfun$dropWhile$1(Function1 p$2, Object x) {
        return !BoxesRunTime.unboxToBoolean(p$2.apply(x));
    }

    public static final /* synthetic */ boolean $anonfun$span$1(Function1 p$3, Object x) {
        return !BoxesRunTime.unboxToBoolean(p$3.apply(x));
    }

    public static final /* synthetic */ boolean $anonfun$filterNot$1(Function1 p$4, Object x) {
        return !BoxesRunTime.unboxToBoolean(p$4.apply(x));
    }

    private static final Object boxed$1(int len$1, Object $this$2, Ordering ord$1) {
        if (len$1 < 300) {
            Object a = ScalaRunTime$.MODULE$.array_clone($this$2);
            Sorting$.MODULE$.stableSort(a, 0, Array.getLength(a), ord$1);
            return a;
        }
        Object[] a = (Object[])Array$.MODULE$.copyAs($this$2, len$1, ClassTag$.MODULE$.AnyRef());
        Arrays.sort(a, ord$1);
        return Array$.MODULE$.copyAs(a, len$1, ClassTag$.MODULE$.apply($this$2.getClass().getComponentType()));
    }

    private static final Object f$mZc$sp$1(boolean[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, xs[i]);
        }
        return v;
    }

    private static final Object f$mBc$sp$1(byte[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, xs[i]);
        }
        return v;
    }

    private static final Object f$mCc$sp$1(char[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, Character.valueOf(xs[i]));
        }
        return v;
    }

    private static final Object f$mDc$sp$1(double[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, xs[i]);
        }
        return v;
    }

    private static final Object f$mFc$sp$1(float[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, Float.valueOf(xs[i]));
        }
        return v;
    }

    private static final Object f$mIc$sp$1(int[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, xs[i]);
        }
        return v;
    }

    private static final Object f$mJc$sp$1(long[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, xs[i]);
        }
        return v;
    }

    private static final Object f$mSc$sp$1(short[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, xs[i]);
        }
        return v;
    }

    private static final Object f$mVc$sp$1(BoxedUnit[] xs, Function2 op, Object z) {
        int length = xs.length;
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, xs[i]);
        }
        return v;
    }

    private static final Object f$2(Object xs, Function2 op, Object z) {
        int length = Array.getLength(xs);
        Object v = z;
        for (int i = 0; i < length; ++i) {
            v = op.apply(v, ScalaRunTime$.MODULE$.array_apply(xs, i));
        }
        return v;
    }

    private static final Object f$mZc$sp$2(boolean[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(xs[i], v);
        }
        return v;
    }

    private static final Object f$mBc$sp$2(byte[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(xs[i], v);
        }
        return v;
    }

    private static final Object f$mCc$sp$2(char[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(Character.valueOf(xs[i]), v);
        }
        return v;
    }

    private static final Object f$mDc$sp$2(double[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(xs[i], v);
        }
        return v;
    }

    private static final Object f$mFc$sp$2(float[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(Float.valueOf(xs[i]), v);
        }
        return v;
    }

    private static final Object f$mIc$sp$2(int[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(xs[i], v);
        }
        return v;
    }

    private static final Object f$mJc$sp$2(long[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(xs[i], v);
        }
        return v;
    }

    private static final Object f$mSc$sp$2(short[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(xs[i], v);
        }
        return v;
    }

    private static final Object f$mVc$sp$2(BoxedUnit[] xs, Function2 op, Object z) {
        Object v = z;
        for (int i = xs.length - 1; i >= 0; --i) {
            v = op.apply(xs[i], v);
        }
        return v;
    }

    private static final Object f$3(Object xs, Function2 op, Object z) {
        Object v = z;
        for (int i = Array.getLength(xs) - 1; i >= 0; --i) {
            v = op.apply(ScalaRunTime$.MODULE$.array_apply(xs, i), v);
        }
        return v;
    }

    public static final /* synthetic */ Iterable $anonfun$flatMap$2(Function1 asIterable$2, Function1 f$4, Object x) {
        return (Iterable)asIterable$2.apply(f$4.apply(x));
    }

    public static final /* synthetic */ boolean $anonfun$contains$1(Object elem$1, Object x$2) {
        return BoxesRunTime.equals(x$2, elem$1);
    }

    private static final ArrayBuilder mkRowBuilder$1(Class aClass$1) {
        ClassTag make_evidence$1 = ClassTag$.MODULE$.apply(aClass$1.getComponentType());
        Class<?> clazz = make_evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofByte();
        }
        Class<Short> clazz3 = Short.TYPE;
        if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofShort();
        }
        Class<Character> clazz4 = Character.TYPE;
        if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofChar();
        }
        Class<Integer> clazz5 = Integer.TYPE;
        if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofInt();
        }
        Class<Long> clazz6 = Long.TYPE;
        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofLong();
        }
        Class<Float> clazz7 = Float.TYPE;
        if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofFloat();
        }
        Class<Double> clazz8 = Double.TYPE;
        if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofDouble();
        }
        Class<Boolean> clazz9 = Boolean.TYPE;
        if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofBoolean();
        }
        Class<Void> clazz10 = Void.TYPE;
        if (!(clazz10 != null ? !clazz10.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofUnit();
        }
        return new ArrayBuilder.ofRef(make_evidence$1);
    }

    public static final /* synthetic */ ArrayBuilder $anonfun$transpose$1(Class aClass$1, Object x) {
        return ArrayOps$.mkRowBuilder$1(aClass$1);
    }

    public static final /* synthetic */ void $anonfun$transpose$3(ArrayBuilder[] bs$1, IntRef i$1, Object x) {
        bs$1[i$1.elem].$plus$eq(x);
        ++i$1.elem;
    }

    public static final /* synthetic */ void $anonfun$transpose$2(Function1 asArray$1, ArrayBuilder[] bs$1, Object xs) {
        int foreach$extension_i;
        int n = 0;
        Object foreach$extension_$this = asArray$1.apply(xs);
        int foreach$extension_len = Array.getLength(foreach$extension_$this);
        if (foreach$extension_$this instanceof Object[]) {
            Object[] objectArray = (Object[])foreach$extension_$this;
            for (foreach$extension_i = 0; foreach$extension_i < foreach$extension_len; ++foreach$extension_i) {
                Object object = objectArray[foreach$extension_i];
                bs$1[n].$plus$eq(object);
                ++n;
            }
        } else if (foreach$extension_$this instanceof int[]) {
            int[] nArray = (int[])foreach$extension_$this;
            while (foreach$extension_i < foreach$extension_len) {
                Integer n2 = nArray[foreach$extension_i];
                bs$1[n].$plus$eq(n2);
                ++n;
                ++foreach$extension_i;
            }
        } else if (foreach$extension_$this instanceof double[]) {
            double[] dArray = (double[])foreach$extension_$this;
            while (foreach$extension_i < foreach$extension_len) {
                Double d = dArray[foreach$extension_i];
                bs$1[n].$plus$eq(d);
                ++n;
                ++foreach$extension_i;
            }
        } else if (foreach$extension_$this instanceof long[]) {
            long[] lArray = (long[])foreach$extension_$this;
            while (foreach$extension_i < foreach$extension_len) {
                Long l = lArray[foreach$extension_i];
                bs$1[n].$plus$eq(l);
                ++n;
                ++foreach$extension_i;
            }
        } else if (foreach$extension_$this instanceof float[]) {
            float[] fArray = (float[])foreach$extension_$this;
            while (foreach$extension_i < foreach$extension_len) {
                Float f = Float.valueOf(fArray[foreach$extension_i]);
                bs$1[n].$plus$eq(f);
                ++n;
                ++foreach$extension_i;
            }
        } else if (foreach$extension_$this instanceof char[]) {
            char[] cArray = (char[])foreach$extension_$this;
            while (foreach$extension_i < foreach$extension_len) {
                Character c = Character.valueOf(cArray[foreach$extension_i]);
                bs$1[n].$plus$eq(c);
                ++n;
                ++foreach$extension_i;
            }
        } else if (foreach$extension_$this instanceof byte[]) {
            byte[] byArray = (byte[])foreach$extension_$this;
            while (foreach$extension_i < foreach$extension_len) {
                Byte by = byArray[foreach$extension_i];
                bs$1[n].$plus$eq(by);
                ++n;
                ++foreach$extension_i;
            }
        } else if (foreach$extension_$this instanceof short[]) {
            short[] sArray = (short[])foreach$extension_$this;
            while (foreach$extension_i < foreach$extension_len) {
                Short s2 = sArray[foreach$extension_i];
                bs$1[n].$plus$eq(s2);
                ++n;
                ++foreach$extension_i;
            }
        } else if (foreach$extension_$this instanceof boolean[]) {
            boolean[] blArray = (boolean[])foreach$extension_$this;
            while (foreach$extension_i < foreach$extension_len) {
                Boolean bl = blArray[foreach$extension_i];
                bs$1[n].$plus$eq(bl);
                ++n;
                ++foreach$extension_i;
            }
        } else {
            throw new MatchError(foreach$extension_$this);
        }
    }

    public static final /* synthetic */ ArrayBuilder.ofRef $anonfun$transpose$4(ArrayBuilder.ofRef bb$1, ArrayBuilder b) {
        Object $plus$eq_elem = b.result();
        return bb$1.addOne($plus$eq_elem);
    }

    private ArrayOps$() {
    }

    public static final /* synthetic */ Object $anonfun$takeWhile$1$adapted(Function1 p$1, Object x) {
        return BoxesRunTime.boxToBoolean(ArrayOps$.$anonfun$takeWhile$1(p$1, x));
    }

    public static final /* synthetic */ Object $anonfun$dropWhile$1$adapted(Function1 p$2, Object x) {
        return BoxesRunTime.boxToBoolean(ArrayOps$.$anonfun$dropWhile$1(p$2, x));
    }

    public static final /* synthetic */ Object $anonfun$span$1$adapted(Function1 p$3, Object x) {
        return BoxesRunTime.boxToBoolean(ArrayOps$.$anonfun$span$1(p$3, x));
    }

    public static final /* synthetic */ Object $anonfun$filterNot$1$adapted(Function1 p$4, Object x) {
        return BoxesRunTime.boxToBoolean(ArrayOps$.$anonfun$filterNot$1(p$4, x));
    }

    public static final /* synthetic */ Object $anonfun$contains$1$adapted(Object elem$1, Object x$2) {
        return BoxesRunTime.boxToBoolean(ArrayOps$.$anonfun$contains$1(elem$1, x$2));
    }

    public static final /* synthetic */ Object $anonfun$transpose$2$adapted(Function1 asArray$1, ArrayBuilder[] bs$1, Object xs) {
        ArrayOps$.$anonfun$transpose$2(asArray$1, bs$1, xs);
        return BoxedUnit.UNIT;
    }

    public static final /* synthetic */ Object $anonfun$transpose$3$adapted(ArrayBuilder[] bs$1, IntRef i$1, Object x) {
        ArrayOps$.$anonfun$transpose$3(bs$1, i$1, x);
        return BoxedUnit.UNIT;
    }
}

