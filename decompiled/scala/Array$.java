/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import scala.Array;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.MatchError;
import scala.Predef$;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Range;
import scala.collection.immutable.Range$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuilder;
import scala.math.Numeric$IntIsIntegral$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;

public final class Array$
implements Serializable {
    public static final Array$ MODULE$ = new Array$();
    private static final boolean[] emptyBooleanArray = new boolean[0];
    private static final byte[] emptyByteArray = new byte[0];
    private static final char[] emptyCharArray = new char[0];
    private static final double[] emptyDoubleArray = new double[0];
    private static final float[] emptyFloatArray = new float[0];
    private static final int[] emptyIntArray = new int[0];
    private static final long[] emptyLongArray = new long[0];
    private static final short[] emptyShortArray = new short[0];
    private static final Object[] emptyObjectArray = new Object[0];

    public boolean[] emptyBooleanArray() {
        return emptyBooleanArray;
    }

    public byte[] emptyByteArray() {
        return emptyByteArray;
    }

    public char[] emptyCharArray() {
        return emptyCharArray;
    }

    public double[] emptyDoubleArray() {
        return emptyDoubleArray;
    }

    public float[] emptyFloatArray() {
        return emptyFloatArray;
    }

    public int[] emptyIntArray() {
        return emptyIntArray;
    }

    public long[] emptyLongArray() {
        return emptyLongArray;
    }

    public short[] emptyShortArray() {
        return emptyShortArray;
    }

    public Object[] emptyObjectArray() {
        return emptyObjectArray;
    }

    public <A> Factory<A, Object> toFactory(Array$ dummy, ClassTag<A> evidence$1) {
        return new Array.ArrayFactory<A>(dummy, evidence$1);
    }

    public <T> ArrayBuilder<T> newBuilder(ClassTag<T> t2) {
        Class<?> clazz = t2.runtimeClass();
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
        return new ArrayBuilder.ofRef<T>(t2);
    }

    public <A> Object from(IterableOnce<A> it, ClassTag<A> evidence$3) {
        if (it instanceof Iterable) {
            return ((Iterable)it).toArray(evidence$3);
        }
        return it.iterator().toArray(evidence$3);
    }

    private void slowcopy(Object src, int srcPos, Object dest, int destPos, int length) {
        int i = srcPos;
        int j = destPos;
        int srcUntil = srcPos + length;
        while (i < srcUntil) {
            ScalaRunTime$.MODULE$.array_update(dest, j, ScalaRunTime$.MODULE$.array_apply(src, i));
            ++i;
            ++j;
        }
    }

    public void copy(Object src, int srcPos, Object dest, int destPos, int length) {
        Class<?> srcClass = src.getClass();
        if (srcClass.isArray() && dest.getClass().isAssignableFrom(srcClass)) {
            System.arraycopy(src, srcPos, dest, destPos, length);
            return;
        }
        this.slowcopy(src, srcPos, dest, destPos, length);
    }

    /*
     * WARNING - void declaration
     */
    public <A> Object copyOf(Object original, int newLength) {
        if (original instanceof BoxedUnit[]) {
            void var3_3;
            Object[] newUnitArray_result = new BoxedUnit[newLength];
            Arrays.fill(newUnitArray_result, BoxedUnit.UNIT);
            return var3_3;
        }
        if (original instanceof Object[]) {
            return Arrays.copyOf((Object[])original, newLength);
        }
        if (original instanceof int[]) {
            return Arrays.copyOf((int[])original, newLength);
        }
        if (original instanceof double[]) {
            return Arrays.copyOf((double[])original, newLength);
        }
        if (original instanceof long[]) {
            return Arrays.copyOf((long[])original, newLength);
        }
        if (original instanceof float[]) {
            return Arrays.copyOf((float[])original, newLength);
        }
        if (original instanceof char[]) {
            return Arrays.copyOf((char[])original, newLength);
        }
        if (original instanceof byte[]) {
            return Arrays.copyOf((byte[])original, newLength);
        }
        if (original instanceof short[]) {
            return Arrays.copyOf((short[])original, newLength);
        }
        if (original instanceof boolean[]) {
            return Arrays.copyOf((boolean[])original, newLength);
        }
        throw new MatchError(original);
    }

    public <A> Object copyAs(Object original, int newLength, ClassTag<A> ct) {
        Class<?> runtimeClass;
        Class<?> clazz = runtimeClass = ct.runtimeClass();
        Class<Void> clazz2 = Void.TYPE;
        if (!(clazz != null ? !clazz.equals(clazz2) : clazz2 != null)) {
            Object[] newUnitArray_result = new BoxedUnit[newLength];
            Arrays.fill(newUnitArray_result, BoxedUnit.UNIT);
            return newUnitArray_result;
        }
        if (runtimeClass.isAssignableFrom(original.getClass().getComponentType())) {
            if (runtimeClass.isPrimitive()) {
                return this.copyOf(original, newLength);
            }
            Class<?> destArrayClass = Array.newInstance(runtimeClass, 0).getClass();
            return Arrays.copyOf((Object[])original, newLength, destArrayClass);
        }
        Object dest = ct.newArray(newLength);
        this.copy(original, 0, dest, 0, Array.getLength(original));
        return dest;
    }

    private BoxedUnit[] newUnitArray(int len) {
        Object[] result = new BoxedUnit[len];
        Arrays.fill(result, BoxedUnit.UNIT);
        return result;
    }

    public <T> Object empty(ClassTag<T> evidence$4) {
        return evidence$4.newArray(0);
    }

    public <T> Object apply(Seq<T> xs, ClassTag<T> evidence$5) {
        int len = xs.length();
        if (xs instanceof ArraySeq) {
            ArraySeq arraySeq = (ArraySeq)xs;
            Class<?> clazz = arraySeq.unsafeArray().getClass().getComponentType();
            Class<?> clazz2 = evidence$5.runtimeClass();
            if (!(clazz != null ? !clazz.equals(clazz2) : clazz2 != null)) {
                return ScalaRunTime$.MODULE$.array_clone(arraySeq.unsafeArray());
            }
        }
        Object array = evidence$5.newArray(len);
        Iterator iterator2 = xs.iterator();
        int i = 0;
        while (iterator2.hasNext()) {
            ScalaRunTime$.MODULE$.array_update(array, i, iterator2.next());
            ++i;
        }
        return array;
    }

    public boolean[] apply(boolean x, Seq<Object> xs) {
        boolean[] array = new boolean[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = BoxesRunTime.unboxToBoolean(iterator2.next());
            ++i;
        }
        return array;
    }

    public byte[] apply(byte x, Seq<Object> xs) {
        byte[] array = new byte[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = BoxesRunTime.unboxToByte(iterator2.next());
            ++i;
        }
        return array;
    }

    public short[] apply(short x, Seq<Object> xs) {
        short[] array = new short[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = BoxesRunTime.unboxToShort(iterator2.next());
            ++i;
        }
        return array;
    }

    public char[] apply(char x, Seq<Object> xs) {
        char[] array = new char[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = BoxesRunTime.unboxToChar(iterator2.next());
            ++i;
        }
        return array;
    }

    public int[] apply(int x, Seq<Object> xs) {
        int[] array = new int[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = BoxesRunTime.unboxToInt(iterator2.next());
            ++i;
        }
        return array;
    }

    public long[] apply(long x, Seq<Object> xs) {
        long[] array = new long[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = BoxesRunTime.unboxToLong(iterator2.next());
            ++i;
        }
        return array;
    }

    public float[] apply(float x, Seq<Object> xs) {
        float[] array = new float[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = BoxesRunTime.unboxToFloat(iterator2.next());
            ++i;
        }
        return array;
    }

    public double[] apply(double x, Seq<Object> xs) {
        double[] array = new double[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = BoxesRunTime.unboxToDouble(iterator2.next());
            ++i;
        }
        return array;
    }

    public BoxedUnit[] apply(BoxedUnit x, Seq<BoxedUnit> xs) {
        BoxedUnit[] array = new BoxedUnit[xs.length() + 1];
        array[0] = x;
        Iterator iterator2 = xs.iterator();
        int i = 1;
        while (iterator2.hasNext()) {
            array[i] = (BoxedUnit)iterator2.next();
            ++i;
        }
        return array;
    }

    public <T> Object ofDim(int n1, ClassTag<T> evidence$6) {
        return evidence$6.newArray(n1);
    }

    public <T> Object[] ofDim(int n1, int n2, ClassTag<T> evidence$7) {
        Object[] arr = (Object[])ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$7.runtimeClass())).newArray(n1);
        int n = 0;
        Range.Exclusive foreach$mVc$sp_this = new Range.Exclusive(n, n1, 1);
        if (!foreach$mVc$sp_this.isEmpty()) {
            int foreach$mVc$sp_i = foreach$mVc$sp_this.start();
            while (true) {
                arr[foreach$mVc$sp_i] = evidence$7.newArray(n2);
                if (foreach$mVc$sp_i == foreach$mVc$sp_this.scala$collection$immutable$Range$$lastElement) break;
                foreach$mVc$sp_i += foreach$mVc$sp_this.step();
            }
        }
        return arr;
    }

    public <T> Object[][] ofDim(int n1, int n2, int n3, ClassTag<T> evidence$8) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$8.runtimeClass())));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, Array$.MODULE$.ofDim(n2, n3, evidence$8));
            }
            object = tabulate_array;
        }
        return (Object[][])object;
    }

    public <T> Object[][][] ofDim(int n1, int n2, int n3, int n4, ClassTag<T> evidence$9) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$9.runtimeClass()))));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, Array$.MODULE$.ofDim(n2, n3, n4, evidence$9));
            }
            object = tabulate_array;
        }
        return (Object[][][])object;
    }

    public <T> Object[][][][] ofDim(int n1, int n2, int n3, int n4, int n5, ClassTag<T> evidence$10) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$10.runtimeClass())))));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, Array$.MODULE$.ofDim(n2, n3, n4, n5, evidence$10));
            }
            object = tabulate_array;
        }
        return (Object[][][][])object;
    }

    public <T> Object concat(Seq<Object> xss, ClassTag<T> evidence$11) {
        ArrayBuilder arrayBuilder;
        Class<?> clazz = evidence$11.runtimeClass();
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
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<T>(evidence$11);
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
        b.sizeHint(BoxesRunTime.unboxToInt(((IterableOnceOps)xss.map((Function1<Object, Object> & Serializable)x$4 -> BoxesRunTime.boxToInteger(ScalaRunTime$.MODULE$.array_length(x$4)))).sum(Numeric$IntIsIntegral$.MODULE$)));
        xss.foreach((Function1<Object, ArrayBuilder> & Serializable)xs -> {
            scala.collection.mutable.ArraySeq $plus$plus$eq_xs = Predef$.MODULE$.genericWrapArray(xs);
            return (ArrayBuilder)b.addAll($plus$plus$eq_xs);
        });
        return b.result();
    }

    public <T> Object fill(int n, Function0<T> elem, ClassTag<T> evidence$12) {
        if (n <= 0) {
            return evidence$12.newArray(0);
        }
        Object array = evidence$12.newArray(n);
        for (int i = 0; i < n; ++i) {
            ScalaRunTime$.MODULE$.array_update(array, i, elem.apply());
        }
        return array;
    }

    public <T> Object[] fill(int n1, int n2, Function0<T> elem, ClassTag<T> evidence$13) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$13.runtimeClass()));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                Object object2;
                if (n2 <= 0) {
                    object2 = evidence$13.newArray(0);
                } else {
                    Object fill_array = evidence$13.newArray(n2);
                    for (int fill_i = 0; fill_i < n2; ++fill_i) {
                        ScalaRunTime$.MODULE$.array_update(fill_array, fill_i, elem.apply());
                    }
                    object2 = fill_array;
                }
                Object var8_8 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, object2);
            }
            object = tabulate_array;
        }
        return (Object[])object;
    }

    public <T> Object[][] fill(int n1, int n2, int n3, Function0<T> elem, ClassTag<T> evidence$14) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$14.runtimeClass())));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                Object object2;
                ClassTag fill_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$14.runtimeClass()));
                if (n2 <= 0) {
                    object2 = fill_tabulate_evidence$17.newArray(0);
                } else {
                    Object fill_tabulate_array = fill_tabulate_evidence$17.newArray(n2);
                    for (int fill_tabulate_i = 0; fill_tabulate_i < n2; ++fill_tabulate_i) {
                        Object object3;
                        if (n3 <= 0) {
                            object3 = evidence$14.newArray(0);
                        } else {
                            Object fill_array = evidence$14.newArray(n3);
                            for (int fill_i = 0; fill_i < n3; ++fill_i) {
                                ScalaRunTime$.MODULE$.array_update(fill_array, fill_i, elem.apply());
                            }
                            object3 = fill_array;
                        }
                        Object var12_12 = null;
                        ScalaRunTime$.MODULE$.array_update(fill_tabulate_array, fill_tabulate_i, object3);
                    }
                    object2 = fill_tabulate_array;
                }
                Object var9_9 = null;
                Object var10_10 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, (Object[])object2);
            }
            object = tabulate_array;
        }
        return (Object[][])object;
    }

    public <T> Object[][][] fill(int n1, int n2, int n3, int n4, Function0<T> elem, ClassTag<T> evidence$15) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$15.runtimeClass()))));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                Object object2;
                ClassTag fill_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$15.runtimeClass())));
                if (n2 <= 0) {
                    object2 = fill_tabulate_evidence$17.newArray(0);
                } else {
                    Object fill_tabulate_array = fill_tabulate_evidence$17.newArray(n2);
                    for (int fill_tabulate_i = 0; fill_tabulate_i < n2; ++fill_tabulate_i) {
                        Object var16_16;
                        Object object3;
                        ClassTag fill_tabulate_evidence$172 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$15.runtimeClass()));
                        if (n3 <= 0) {
                            object3 = fill_tabulate_evidence$172.newArray(0);
                        } else {
                            Object fill_tabulate_array2 = fill_tabulate_evidence$172.newArray(n3);
                            for (int fill_tabulate_i2 = 0; fill_tabulate_i2 < n3; ++fill_tabulate_i2) {
                                Object object4;
                                if (n4 <= 0) {
                                    object4 = evidence$15.newArray(0);
                                } else {
                                    Object fill_fill_array = evidence$15.newArray(n4);
                                    for (int fill_fill_i = 0; fill_fill_i < n4; ++fill_fill_i) {
                                        ScalaRunTime$.MODULE$.array_update(fill_fill_array, fill_fill_i, elem.apply());
                                    }
                                    object4 = fill_fill_array;
                                }
                                var16_16 = null;
                                ScalaRunTime$.MODULE$.array_update(fill_tabulate_array2, fill_tabulate_i2, object4);
                            }
                            object3 = fill_tabulate_array2;
                        }
                        Object var13_13 = null;
                        Object var14_14 = null;
                        var16_16 = null;
                        ScalaRunTime$.MODULE$.array_update(fill_tabulate_array, fill_tabulate_i, (Object[])object3);
                    }
                    object2 = fill_tabulate_array;
                }
                Object var10_10 = null;
                Object var11_11 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, (Object[][])object2);
            }
            object = tabulate_array;
        }
        return (Object[][][])object;
    }

    public <T> Object[][][][] fill(int n1, int n2, int n3, int n4, int n5, Function0<T> elem, ClassTag<T> evidence$16) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$16.runtimeClass())))));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                Object object2;
                ClassTag fill_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$16.runtimeClass()))));
                if (n2 <= 0) {
                    object2 = fill_tabulate_evidence$17.newArray(0);
                } else {
                    Object fill_tabulate_array = fill_tabulate_evidence$17.newArray(n2);
                    for (int fill_tabulate_i = 0; fill_tabulate_i < n2; ++fill_tabulate_i) {
                        Object var18_18;
                        Object var17_17;
                        Object object3;
                        ClassTag fill_tabulate_evidence$172 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$16.runtimeClass())));
                        if (n3 <= 0) {
                            object3 = fill_tabulate_evidence$172.newArray(0);
                        } else {
                            Object fill_tabulate_array2 = fill_tabulate_evidence$172.newArray(n3);
                            for (int fill_tabulate_i2 = 0; fill_tabulate_i2 < n3; ++fill_tabulate_i2) {
                                Object object4;
                                ClassTag fill_fill_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$16.runtimeClass()));
                                if (n4 <= 0) {
                                    object4 = fill_fill_tabulate_evidence$17.newArray(0);
                                } else {
                                    Object fill_fill_tabulate_array = fill_fill_tabulate_evidence$17.newArray(n4);
                                    for (int fill_fill_tabulate_i = 0; fill_fill_tabulate_i < n4; ++fill_fill_tabulate_i) {
                                        Object object5;
                                        if (n5 <= 0) {
                                            object5 = evidence$16.newArray(0);
                                        } else {
                                            Object fill_array = evidence$16.newArray(n5);
                                            for (int fill_i = 0; fill_i < n5; ++fill_i) {
                                                ScalaRunTime$.MODULE$.array_update(fill_array, fill_i, elem.apply());
                                            }
                                            object5 = fill_array;
                                        }
                                        Object var20_20 = null;
                                        ScalaRunTime$.MODULE$.array_update(fill_fill_tabulate_array, fill_fill_tabulate_i, object5);
                                    }
                                    object4 = fill_fill_tabulate_array;
                                }
                                var17_17 = null;
                                var18_18 = null;
                                ScalaRunTime$.MODULE$.array_update(fill_tabulate_array2, fill_tabulate_i2, (Object[])object4);
                            }
                            object3 = fill_tabulate_array2;
                        }
                        Object var14_14 = null;
                        Object var15_15 = null;
                        var17_17 = null;
                        var18_18 = null;
                        ScalaRunTime$.MODULE$.array_update(fill_tabulate_array, fill_tabulate_i, (Object[][])object3);
                    }
                    object2 = fill_tabulate_array;
                }
                Object var11_11 = null;
                Object var12_12 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, (Object[][][])object2);
            }
            object = tabulate_array;
        }
        return (Object[][][][])object;
    }

    public <T> Object tabulate(int n, Function1<Object, T> f, ClassTag<T> evidence$17) {
        if (n <= 0) {
            return evidence$17.newArray(0);
        }
        Object array = evidence$17.newArray(n);
        for (int i = 0; i < n; ++i) {
            ScalaRunTime$.MODULE$.array_update(array, i, f.apply(i));
        }
        return array;
    }

    public <T> Object[] tabulate(int n1, int n2, Function2<Object, Object, T> f, ClassTag<T> evidence$18) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$18.runtimeClass()));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                Object object2;
                if (n2 <= 0) {
                    object2 = evidence$18.newArray(0);
                } else {
                    Object $anonfun$tabulate$1_tabulate_array = evidence$18.newArray(n2);
                    for (int $anonfun$tabulate$1_tabulate_i = 0; $anonfun$tabulate$1_tabulate_i < n2; ++$anonfun$tabulate$1_tabulate_i) {
                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$1_tabulate_array, $anonfun$tabulate$1_tabulate_i, f.apply(tabulate_i, $anonfun$tabulate$1_tabulate_i));
                    }
                    object2 = $anonfun$tabulate$1_tabulate_array;
                }
                Object var8_8 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, object2);
            }
            object = tabulate_array;
        }
        return (Object[])object;
    }

    public <T> Object[][] tabulate(int n1, int n2, int n3, Function3<Object, Object, Object, T> f, ClassTag<T> evidence$19) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$19.runtimeClass())));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                Object var12_12;
                Object object2;
                ClassTag $anonfun$tabulate$3_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$19.runtimeClass()));
                if (n2 <= 0) {
                    object2 = $anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(0);
                } else {
                    Object $anonfun$tabulate$3_tabulate_tabulate_array = $anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(n2);
                    for (int $anonfun$tabulate$3_tabulate_tabulate_i = 0; $anonfun$tabulate$3_tabulate_tabulate_i < n2; ++$anonfun$tabulate$3_tabulate_tabulate_i) {
                        Object object3;
                        if (n3 <= 0) {
                            object3 = evidence$19.newArray(0);
                        } else {
                            Object $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array = evidence$19.newArray(n3);
                            for (int $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i = 0; $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i < n3; ++$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i) {
                                ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array, $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i, f.apply(tabulate_i, $anonfun$tabulate$3_tabulate_tabulate_i, $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i));
                            }
                            object3 = $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array;
                        }
                        var12_12 = null;
                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$3_tabulate_tabulate_array, $anonfun$tabulate$3_tabulate_tabulate_i, object3);
                    }
                    object2 = $anonfun$tabulate$3_tabulate_tabulate_array;
                }
                Object var9_9 = null;
                Object var10_10 = null;
                var12_12 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, (Object[])object2);
            }
            object = tabulate_array;
        }
        return (Object[][])object;
    }

    public <T> Object[][][] tabulate(int n1, int n2, int n3, int n4, Function4<Object, Object, Object, Object, T> f, ClassTag<T> evidence$20) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$20.runtimeClass()))));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                Object var14_14;
                Object var13_13;
                Object var16_16;
                Object object2;
                ClassTag $anonfun$tabulate$5_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$20.runtimeClass())));
                if (n2 <= 0) {
                    object2 = $anonfun$tabulate$5_tabulate_tabulate_evidence$17.newArray(0);
                } else {
                    Object $anonfun$tabulate$5_tabulate_tabulate_array = $anonfun$tabulate$5_tabulate_tabulate_evidence$17.newArray(n2);
                    for (int $anonfun$tabulate$5_tabulate_tabulate_i = 0; $anonfun$tabulate$5_tabulate_tabulate_i < n2; ++$anonfun$tabulate$5_tabulate_tabulate_i) {
                        Object object3;
                        ClassTag $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$20.runtimeClass()));
                        if (n3 <= 0) {
                            object3 = $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(0);
                        } else {
                            Object $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array = $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(n3);
                            for (int $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i = 0; $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i < n3; ++$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i) {
                                Object object4;
                                if (n4 <= 0) {
                                    object4 = evidence$20.newArray(0);
                                } else {
                                    Object $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array = evidence$20.newArray(n4);
                                    for (int $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i = 0; $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i < n4; ++$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i) {
                                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array, $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i, f.apply(tabulate_i, $anonfun$tabulate$5_tabulate_tabulate_i, $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i, $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i));
                                    }
                                    object4 = $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array;
                                }
                                var16_16 = null;
                                ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array, $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i, object4);
                            }
                            object3 = $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array;
                        }
                        var13_13 = null;
                        var14_14 = null;
                        var16_16 = null;
                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$5_tabulate_tabulate_array, $anonfun$tabulate$5_tabulate_tabulate_i, (Object[])object3);
                    }
                    object2 = $anonfun$tabulate$5_tabulate_tabulate_array;
                }
                Object var10_10 = null;
                Object var11_11 = null;
                var13_13 = null;
                var14_14 = null;
                var16_16 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, (Object[][])object2);
            }
            object = tabulate_array;
        }
        return (Object[][][])object;
    }

    public <T> Object[][][][] tabulate(int n1, int n2, int n3, int n4, int n5, Function5<Object, Object, Object, Object, Object, T> f, ClassTag<T> evidence$21) {
        Object object;
        ClassTag tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$21.runtimeClass())))));
        if (n1 <= 0) {
            object = tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_array = tabulate_evidence$17.newArray(n1);
            for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
                Object var15_15;
                Object var14_14;
                Object var18_18;
                Object var17_17;
                Object var20_20;
                Object object2;
                ClassTag $anonfun$tabulate$7_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$21.runtimeClass()))));
                if (n2 <= 0) {
                    object2 = $anonfun$tabulate$7_tabulate_tabulate_evidence$17.newArray(0);
                } else {
                    Object $anonfun$tabulate$7_tabulate_tabulate_array = $anonfun$tabulate$7_tabulate_tabulate_evidence$17.newArray(n2);
                    for (int $anonfun$tabulate$7_tabulate_tabulate_i = 0; $anonfun$tabulate$7_tabulate_tabulate_i < n2; ++$anonfun$tabulate$7_tabulate_tabulate_i) {
                        Object object3;
                        ClassTag $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$21.runtimeClass())));
                        if (n3 <= 0) {
                            object3 = $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_evidence$17.newArray(0);
                        } else {
                            Object $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_array = $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_evidence$17.newArray(n3);
                            for (int $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_i = 0; $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_i < n3; ++$anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_i) {
                                Object object4;
                                ClassTag $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$21.runtimeClass()));
                                if (n4 <= 0) {
                                    object4 = $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(0);
                                } else {
                                    Object $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array = $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(n4);
                                    for (int $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i = 0; $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i < n4; ++$anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i) {
                                        Object object5;
                                        if (n5 <= 0) {
                                            object5 = evidence$21.newArray(0);
                                        } else {
                                            Object $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array = evidence$21.newArray(n5);
                                            for (int $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i = 0; $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i < n5; ++$anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i) {
                                                ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array, $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i, f.apply(tabulate_i, $anonfun$tabulate$7_tabulate_tabulate_i, $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_i, $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i, $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i));
                                            }
                                            object5 = $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array;
                                        }
                                        var20_20 = null;
                                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array, $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i, object5);
                                    }
                                    object4 = $anonfun$tabulate$7_$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array;
                                }
                                var17_17 = null;
                                var18_18 = null;
                                var20_20 = null;
                                ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_array, $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_i, (Object[])object4);
                            }
                            object3 = $anonfun$tabulate$7_$anonfun$tabulate$5_tabulate_tabulate_array;
                        }
                        var14_14 = null;
                        var15_15 = null;
                        var17_17 = null;
                        var18_18 = null;
                        var20_20 = null;
                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$7_tabulate_tabulate_array, $anonfun$tabulate$7_tabulate_tabulate_i, (Object[][])object3);
                    }
                    object2 = $anonfun$tabulate$7_tabulate_tabulate_array;
                }
                Object var11_11 = null;
                Object var12_12 = null;
                var14_14 = null;
                var15_15 = null;
                var17_17 = null;
                var18_18 = null;
                var20_20 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, (Object[][][])object2);
            }
            object = tabulate_array;
        }
        return (Object[][][][])object;
    }

    public int[] range(int start2, int end) {
        return this.range(start2, end, 1);
    }

    public int[] range(int start2, int end, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("zero step");
        }
        int[] array = new int[Range$.MODULE$.count(start2, end, step, false)];
        int n = 0;
        int i = start2;
        while (!(step >= 0 ? i >= end : end >= i)) {
            array[n] = i;
            i += step;
            ++n;
        }
        return array;
    }

    public <T> Object iterate(T start2, int len, Function1<T, T> f, ClassTag<T> evidence$22) {
        if (len > 0) {
            Object array = evidence$22.newArray(len);
            T acc = start2;
            ScalaRunTime$.MODULE$.array_update(array, 0, start2);
            for (int i = 1; i < len; ++i) {
                acc = f.apply(acc);
                ScalaRunTime$.MODULE$.array_update(array, i, acc);
            }
            return array;
        }
        return evidence$22.newArray(0);
    }

    public boolean equals(Object[] xs, Object[] ys) {
        block4: {
            block3: {
                int i;
                if (xs == ys) break block3;
                if (xs.length != ys.length) break block4;
                for (i = 0; i < xs.length && BoxesRunTime.equals(xs[i], ys[i]); ++i) {
                }
                if (i < xs.length) break block4;
            }
            return true;
        }
        return false;
    }

    public <T> Object unapplySeq(Object x) {
        return x;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Array$.class);
    }

    public static final /* synthetic */ void $anonfun$ofDim$1(Object[] arr$1, ClassTag evidence$7$1, int n2$1, int i) {
        arr$1[i] = evidence$7$1.newArray(n2$1);
    }

    public static final /* synthetic */ Object $anonfun$tabulate$1(int n2$9, Function2 f$1, ClassTag evidence$18$1, int i1) {
        if (n2$9 <= 0) {
            return evidence$18$1.newArray(0);
        }
        Object tabulate_array = evidence$18$1.newArray(n2$9);
        for (int tabulate_i = 0; tabulate_i < n2$9; ++tabulate_i) {
            ScalaRunTime$.MODULE$.array_update(tabulate_array, tabulate_i, f$1.apply(i1, tabulate_i));
        }
        return tabulate_array;
    }

    public static final /* synthetic */ Object[] $anonfun$tabulate$3(int n2$10, int n3$7, Function3 f$2, ClassTag evidence$19$1, int i1) {
        Object object;
        ClassTag tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$19$1.runtimeClass()));
        if (n2$10 <= 0) {
            object = tabulate_tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_tabulate_array = tabulate_tabulate_evidence$17.newArray(n2$10);
            for (int tabulate_tabulate_i = 0; tabulate_tabulate_i < n2$10; ++tabulate_tabulate_i) {
                Object object2;
                if (n3$7 <= 0) {
                    object2 = evidence$19$1.newArray(0);
                } else {
                    Object $anonfun$tabulate$1_tabulate_array = evidence$19$1.newArray(n3$7);
                    for (int $anonfun$tabulate$1_tabulate_i = 0; $anonfun$tabulate$1_tabulate_i < n3$7; ++$anonfun$tabulate$1_tabulate_i) {
                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$1_tabulate_array, $anonfun$tabulate$1_tabulate_i, f$2.apply(i1, tabulate_tabulate_i, $anonfun$tabulate$1_tabulate_i));
                    }
                    object2 = $anonfun$tabulate$1_tabulate_array;
                }
                Object var8_8 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_tabulate_array, tabulate_tabulate_i, object2);
            }
            object = tabulate_tabulate_array;
        }
        return (Object[])object;
    }

    public static final /* synthetic */ Object[][] $anonfun$tabulate$5(int n2$11, int n3$8, int n4$5, Function4 f$3, ClassTag evidence$20$1, int i1) {
        Object object;
        ClassTag tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$20$1.runtimeClass())));
        if (n2$11 <= 0) {
            object = tabulate_tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_tabulate_array = tabulate_tabulate_evidence$17.newArray(n2$11);
            for (int tabulate_tabulate_i = 0; tabulate_tabulate_i < n2$11; ++tabulate_tabulate_i) {
                Object var12_12;
                Object object2;
                ClassTag $anonfun$tabulate$3_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$20$1.runtimeClass()));
                if (n3$8 <= 0) {
                    object2 = $anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(0);
                } else {
                    Object $anonfun$tabulate$3_tabulate_tabulate_array = $anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(n3$8);
                    for (int $anonfun$tabulate$3_tabulate_tabulate_i = 0; $anonfun$tabulate$3_tabulate_tabulate_i < n3$8; ++$anonfun$tabulate$3_tabulate_tabulate_i) {
                        Object object3;
                        if (n4$5 <= 0) {
                            object3 = evidence$20$1.newArray(0);
                        } else {
                            Object $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array = evidence$20$1.newArray(n4$5);
                            for (int $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i = 0; $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i < n4$5; ++$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i) {
                                ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array, $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i, f$3.apply(i1, tabulate_tabulate_i, $anonfun$tabulate$3_tabulate_tabulate_i, $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i));
                            }
                            object3 = $anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array;
                        }
                        var12_12 = null;
                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$3_tabulate_tabulate_array, $anonfun$tabulate$3_tabulate_tabulate_i, object3);
                    }
                    object2 = $anonfun$tabulate$3_tabulate_tabulate_array;
                }
                Object var9_9 = null;
                Object var10_10 = null;
                var12_12 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_tabulate_array, tabulate_tabulate_i, (Object[])object2);
            }
            object = tabulate_tabulate_array;
        }
        return (Object[][])object;
    }

    public static final /* synthetic */ Object[][][] $anonfun$tabulate$7(int n2$12, int n3$9, int n4$6, int n5$3, Function5 f$4, ClassTag evidence$21$1, int i1) {
        Object object;
        ClassTag tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$21$1.runtimeClass()))));
        if (n2$12 <= 0) {
            object = tabulate_tabulate_evidence$17.newArray(0);
        } else {
            Object tabulate_tabulate_array = tabulate_tabulate_evidence$17.newArray(n2$12);
            for (int tabulate_tabulate_i = 0; tabulate_tabulate_i < n2$12; ++tabulate_tabulate_i) {
                Object var14_14;
                Object var13_13;
                Object var16_16;
                Object object2;
                ClassTag $anonfun$tabulate$5_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(ScalaRunTime$.MODULE$.arrayClass(evidence$21$1.runtimeClass())));
                if (n3$9 <= 0) {
                    object2 = $anonfun$tabulate$5_tabulate_tabulate_evidence$17.newArray(0);
                } else {
                    Object $anonfun$tabulate$5_tabulate_tabulate_array = $anonfun$tabulate$5_tabulate_tabulate_evidence$17.newArray(n3$9);
                    for (int $anonfun$tabulate$5_tabulate_tabulate_i = 0; $anonfun$tabulate$5_tabulate_tabulate_i < n3$9; ++$anonfun$tabulate$5_tabulate_tabulate_i) {
                        Object object3;
                        ClassTag $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17 = ClassTag$.MODULE$.apply(ScalaRunTime$.MODULE$.arrayClass(evidence$21$1.runtimeClass()));
                        if (n4$6 <= 0) {
                            object3 = $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(0);
                        } else {
                            Object $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array = $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_evidence$17.newArray(n4$6);
                            for (int $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i = 0; $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i < n4$6; ++$anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i) {
                                Object object4;
                                if (n5$3 <= 0) {
                                    object4 = evidence$21$1.newArray(0);
                                } else {
                                    Object $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array = evidence$21$1.newArray(n5$3);
                                    for (int $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i = 0; $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i < n5$3; ++$anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i) {
                                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array, $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i, f$4.apply(i1, tabulate_tabulate_i, $anonfun$tabulate$5_tabulate_tabulate_i, $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i, $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_i));
                                    }
                                    object4 = $anonfun$tabulate$5_$anonfun$tabulate$3_$anonfun$tabulate$1_tabulate_array;
                                }
                                var16_16 = null;
                                ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array, $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_i, object4);
                            }
                            object3 = $anonfun$tabulate$5_$anonfun$tabulate$3_tabulate_tabulate_array;
                        }
                        var13_13 = null;
                        var14_14 = null;
                        var16_16 = null;
                        ScalaRunTime$.MODULE$.array_update($anonfun$tabulate$5_tabulate_tabulate_array, $anonfun$tabulate$5_tabulate_tabulate_i, (Object[])object3);
                    }
                    object2 = $anonfun$tabulate$5_tabulate_tabulate_array;
                }
                Object var10_10 = null;
                Object var11_11 = null;
                var13_13 = null;
                var14_14 = null;
                var16_16 = null;
                ScalaRunTime$.MODULE$.array_update(tabulate_tabulate_array, tabulate_tabulate_i, (Object[][])object2);
            }
            object = tabulate_tabulate_array;
        }
        return (Object[][][])object;
    }

    private Array$() {
    }

    public static final /* synthetic */ Object[] $anonfun$ofDim$2$adapted(int n2$2, int n3$1, ClassTag evidence$8$1, Object x$1) {
        return Array$.MODULE$.ofDim(n2$2, n3$1, evidence$8$1);
    }

    public static final /* synthetic */ Object[][] $anonfun$ofDim$3$adapted(int n2$3, int n3$2, int n4$1, ClassTag evidence$9$1, Object x$2) {
        return Array$.MODULE$.ofDim(n2$3, n3$2, n4$1, evidence$9$1);
    }

    public static final /* synthetic */ Object[][][] $anonfun$ofDim$4$adapted(int n2$4, int n3$3, int n4$2, int n5$1, ClassTag evidence$10$1, Object x$3) {
        return Array$.MODULE$.ofDim(n2$4, n3$3, n4$2, n5$1, evidence$10$1);
    }

    public static final /* synthetic */ Object $anonfun$fill$1$adapted(int n2$5, Function0 elem$1, ClassTag evidence$13$1, Object x$5) {
        return Array$.MODULE$.fill(n2$5, elem$1, evidence$13$1);
    }

    public static final /* synthetic */ Object[] $anonfun$fill$2$adapted(int n2$6, int n3$4, Function0 elem$2, ClassTag evidence$14$1, Object x$6) {
        return Array$.MODULE$.fill(n2$6, n3$4, elem$2, evidence$14$1);
    }

    public static final /* synthetic */ Object[][] $anonfun$fill$3$adapted(int n2$7, int n3$5, int n4$3, Function0 elem$3, ClassTag evidence$15$1, Object x$7) {
        return Array$.MODULE$.fill(n2$7, n3$5, n4$3, elem$3, evidence$15$1);
    }

    public static final /* synthetic */ Object[][][] $anonfun$fill$4$adapted(int n2$8, int n3$6, int n4$4, int n5$2, Function0 elem$4, ClassTag evidence$16$1, Object x$8) {
        return Array$.MODULE$.fill(n2$8, n3$6, n4$4, n5$2, elem$4, evidence$16$1);
    }

    public static final /* synthetic */ Object $anonfun$tabulate$1$adapted(int n2$9, Function2 f$1, ClassTag evidence$18$1, Object i1) {
        return Array$.$anonfun$tabulate$1(n2$9, f$1, evidence$18$1, BoxesRunTime.unboxToInt(i1));
    }

    public static final /* synthetic */ Object[] $anonfun$tabulate$3$adapted(int n2$10, int n3$7, Function3 f$2, ClassTag evidence$19$1, Object i1) {
        return Array$.$anonfun$tabulate$3(n2$10, n3$7, f$2, evidence$19$1, BoxesRunTime.unboxToInt(i1));
    }

    public static final /* synthetic */ Object[][] $anonfun$tabulate$5$adapted(int n2$11, int n3$8, int n4$5, Function4 f$3, ClassTag evidence$20$1, Object i1) {
        return Array$.$anonfun$tabulate$5(n2$11, n3$8, n4$5, f$3, evidence$20$1, BoxesRunTime.unboxToInt(i1));
    }

    public static final /* synthetic */ Object[][][] $anonfun$tabulate$7$adapted(int n2$12, int n3$9, int n4$6, int n5$3, Function5 f$4, ClassTag evidence$21$1, Object i1) {
        return Array$.$anonfun$tabulate$7(n2$12, n3$9, n4$6, n5$3, f$4, evidence$21$1, BoxesRunTime.unboxToInt(i1));
    }

    public static final /* synthetic */ Object $anonfun$tabulate$2$adapted(Function2 f$1, int i1$1, Object x$9) {
        return f$1.apply(BoxesRunTime.boxToInteger(i1$1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$9)));
    }

    public static final /* synthetic */ Object $anonfun$tabulate$4$adapted(Function3 f$2, int i1$2, Object x$10, Object x$11) {
        return f$2.apply(BoxesRunTime.boxToInteger(i1$2), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$10)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$11)));
    }

    public static final /* synthetic */ Object $anonfun$tabulate$6$adapted(Function4 f$3, int i1$3, Object x$12, Object x$13, Object x$14) {
        return f$3.apply(BoxesRunTime.boxToInteger(i1$3), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$12)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$13)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$14)));
    }

    public static final /* synthetic */ Object $anonfun$tabulate$8$adapted(Function5 f$4, int i1$4, Object x$15, Object x$16, Object x$17, Object x$18) {
        return f$4.apply(BoxesRunTime.boxToInteger(i1$4), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$15)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$16)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$17)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$18)));
    }
}

