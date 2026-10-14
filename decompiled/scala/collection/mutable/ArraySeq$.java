/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.MatchError;
import scala.collection.ClassTagIterableFactory;
import scala.collection.ClassTagSeqFactory;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedClassTagSeqFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.reflect.ClassTag;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;

public final class ArraySeq$
implements StrictOptimizedClassTagSeqFactory<ArraySeq> {
    public static final ArraySeq$ MODULE$ = new ArraySeq$();
    private static final long serialVersionUID = 3L;
    private static final SeqFactory<ArraySeq> untagged = new ClassTagSeqFactory.AnySeqDelegate<ArraySeq>(MODULE$);
    private static final ArraySeq.ofRef<Object> EmptyArraySeq = new ArraySeq.ofRef<Object>(new Object[0]);

    @Override
    public SeqOps fill(int n, Function0 elem, ClassTag evidence$34) {
        return StrictOptimizedClassTagSeqFactory.fill$(this, n, elem, evidence$34);
    }

    @Override
    public SeqOps tabulate(int n, Function1 f, ClassTag evidence$35) {
        return StrictOptimizedClassTagSeqFactory.tabulate$(this, n, f, evidence$35);
    }

    @Override
    public final SeqOps unapplySeq(SeqOps x) {
        return ClassTagSeqFactory.unapplySeq$(this, x);
    }

    @Override
    public Object range(Object start2, Object end, Integral evidence$22, ClassTag evidence$23) {
        return ClassTagIterableFactory.range$(this, start2, end, evidence$22, evidence$23);
    }

    @Override
    public Object range(Object start2, Object end, Object step, Integral evidence$24, ClassTag evidence$25) {
        return ClassTagIterableFactory.range$(this, start2, end, step, evidence$24, evidence$25);
    }

    @Override
    public Object fill(int n1, int n2, Function0 elem, ClassTag evidence$26) {
        return ClassTagIterableFactory.fill$(this, n1, n2, elem, evidence$26);
    }

    @Override
    public Object fill(int n1, int n2, int n3, Function0 elem, ClassTag evidence$27) {
        return ClassTagIterableFactory.fill$(this, n1, n2, n3, elem, evidence$27);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, Function0 elem, ClassTag evidence$28) {
        return ClassTagIterableFactory.fill$(this, n1, n2, n3, n4, elem, evidence$28);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem, ClassTag evidence$29) {
        return ClassTagIterableFactory.fill$(this, n1, n2, n3, n4, n5, elem, evidence$29);
    }

    @Override
    public Object tabulate(int n1, int n2, Function2 f, ClassTag evidence$30) {
        return ClassTagIterableFactory.tabulate$(this, n1, n2, f, evidence$30);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, Function3 f, ClassTag evidence$31) {
        return ClassTagIterableFactory.tabulate$(this, n1, n2, n3, f, evidence$31);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, Function4 f, ClassTag evidence$32) {
        return ClassTagIterableFactory.tabulate$(this, n1, n2, n3, n4, f, evidence$32);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f, ClassTag evidence$33) {
        return ClassTagIterableFactory.tabulate$(this, n1, n2, n3, n4, n5, f, evidence$33);
    }

    @Override
    public Object apply(Seq xs, Object evidence$7) {
        return EvidenceIterableFactory.apply$(this, xs, evidence$7);
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

    public SeqFactory<ArraySeq> untagged() {
        return untagged;
    }

    @Override
    public <T> ArraySeq<T> empty(ClassTag<T> evidence$1) {
        return EmptyArraySeq;
    }

    @Override
    public <A> ArraySeq<A> from(IterableOnce<A> it, ClassTag<A> evidence$2) {
        return this.make(Array$.MODULE$.from(it, evidence$2));
    }

    @Override
    public <A> Builder<A, ArraySeq<A>> newBuilder(ClassTag<A> evidence$3) {
        ArrayBuilder arrayBuilder;
        Class<?> clazz = evidence$3.runtimeClass();
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
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<A>(evidence$3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        return arrayBuilder.mapResult((Function1<Object, ArraySeq> & Serializable)x -> MODULE$.make(x));
    }

    public <T> ArraySeq<T> make(Object x) {
        if (x == null) {
            return null;
        }
        if (x instanceof Object[]) {
            Object[] objectArray = (Object[])x;
            return new ArraySeq.ofRef<Object>(objectArray);
        }
        if (x instanceof int[]) {
            int[] nArray = (int[])x;
            return new ArraySeq.ofInt(nArray);
        }
        if (x instanceof double[]) {
            double[] dArray = (double[])x;
            return new ArraySeq.ofDouble(dArray);
        }
        if (x instanceof long[]) {
            long[] lArray = (long[])x;
            return new ArraySeq.ofLong(lArray);
        }
        if (x instanceof float[]) {
            float[] fArray = (float[])x;
            return new ArraySeq.ofFloat(fArray);
        }
        if (x instanceof char[]) {
            char[] cArray = (char[])x;
            return new ArraySeq.ofChar(cArray);
        }
        if (x instanceof byte[]) {
            byte[] byArray = (byte[])x;
            return new ArraySeq.ofByte(byArray);
        }
        if (x instanceof short[]) {
            short[] sArray = (short[])x;
            return new ArraySeq.ofShort(sArray);
        }
        if (x instanceof boolean[]) {
            boolean[] blArray = (boolean[])x;
            return new ArraySeq.ofBoolean(blArray);
        }
        if (x instanceof BoxedUnit[]) {
            BoxedUnit[] boxedUnitArray = (BoxedUnit[])x;
            return new ArraySeq.ofUnit(boxedUnitArray);
        }
        throw new MatchError(x);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ArraySeq$.class);
    }

    private ArraySeq$() {
    }
}

