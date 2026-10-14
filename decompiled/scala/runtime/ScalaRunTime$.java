/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import scala.Array$;
import scala.Function1;
import scala.MatchError;
import scala.Product;
import scala.Product1;
import scala.Tuple2;
import scala.collection.AbstractIterator;
import scala.collection.ArrayOps$;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.Seq;
import scala.collection.SortedOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StringOps;
import scala.collection.StringOps$;
import scala.collection.StringView;
import scala.collection.View;
import scala.collection.generic.IsIterable;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.ArraySeq$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.NumericRange;
import scala.collection.immutable.Range;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.package$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.hashing.MurmurHash3$;

public final class ScalaRunTime$ {
    public static final ScalaRunTime$ MODULE$ = new ScalaRunTime$();

    public boolean isArray(Object x, int atLevel) {
        return x != null && this.isArrayClass(x.getClass(), atLevel);
    }

    public int isArray$default$2() {
        return 1;
    }

    private boolean isArrayClass(Class<?> clazz, int atLevel) {
        while (clazz.isArray()) {
            if (atLevel != 1) {
                --atLevel;
                clazz = clazz.getComponentType();
                continue;
            }
            return true;
        }
        return false;
    }

    public <Repr> Repr drop(Repr coll, int num, IsIterable<Repr> iterable) {
        return (Repr)iterable.apply(coll).drop(num);
    }

    public Class<?> arrayClass(Class<?> clazz) {
        Class<?> clazz2 = clazz;
        Class<Void> clazz3 = Void.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz3) : clazz3 != null)) {
            return BoxedUnit[].class;
        }
        return Array.newInstance(clazz, 0).getClass();
    }

    public <T> Class<T> anyValClass(T value, ClassTag<T> evidence$1) {
        return evidence$1.runtimeClass();
    }

    public Object array_apply(Object xs, int idx) {
        if (xs instanceof Object[]) {
            return ((Object[])xs)[idx];
        }
        if (xs instanceof int[]) {
            return ((int[])xs)[idx];
        }
        if (xs instanceof double[]) {
            return ((double[])xs)[idx];
        }
        if (xs instanceof long[]) {
            return ((long[])xs)[idx];
        }
        if (xs instanceof float[]) {
            return Float.valueOf(((float[])xs)[idx]);
        }
        if (xs instanceof char[]) {
            return Character.valueOf(((char[])xs)[idx]);
        }
        if (xs instanceof byte[]) {
            return ((byte[])xs)[idx];
        }
        if (xs instanceof short[]) {
            return ((short[])xs)[idx];
        }
        if (xs instanceof boolean[]) {
            return ((boolean[])xs)[idx];
        }
        if (xs == null) {
            throw new NullPointerException();
        }
        throw new MatchError(xs);
    }

    public void array_update(Object xs, int idx, Object value) {
        if (xs instanceof Object[]) {
            ((Object[])xs)[idx] = value;
            return;
        }
        if (xs instanceof int[]) {
            ((int[])xs)[idx] = BoxesRunTime.unboxToInt(value);
            return;
        }
        if (xs instanceof double[]) {
            ((double[])xs)[idx] = BoxesRunTime.unboxToDouble(value);
            return;
        }
        if (xs instanceof long[]) {
            ((long[])xs)[idx] = BoxesRunTime.unboxToLong(value);
            return;
        }
        if (xs instanceof float[]) {
            ((float[])xs)[idx] = BoxesRunTime.unboxToFloat(value);
            return;
        }
        if (xs instanceof char[]) {
            ((char[])xs)[idx] = BoxesRunTime.unboxToChar(value);
            return;
        }
        if (xs instanceof byte[]) {
            ((byte[])xs)[idx] = BoxesRunTime.unboxToByte(value);
            return;
        }
        if (xs instanceof short[]) {
            ((short[])xs)[idx] = BoxesRunTime.unboxToShort(value);
            return;
        }
        if (xs instanceof boolean[]) {
            ((boolean[])xs)[idx] = BoxesRunTime.unboxToBoolean(value);
            return;
        }
        if (xs == null) {
            throw new NullPointerException();
        }
        throw new MatchError(xs);
    }

    public int array_length(Object xs) {
        return Array.getLength(xs);
    }

    public Object array_clone(Object xs) {
        if (xs instanceof Object[]) {
            return ((Object[])xs).clone();
        }
        if (xs instanceof int[]) {
            return ((int[])xs).clone();
        }
        if (xs instanceof double[]) {
            return ((double[])xs).clone();
        }
        if (xs instanceof long[]) {
            return ((long[])xs).clone();
        }
        if (xs instanceof float[]) {
            return ((float[])xs).clone();
        }
        if (xs instanceof char[]) {
            return ((char[])xs).clone();
        }
        if (xs instanceof byte[]) {
            return ((byte[])xs).clone();
        }
        if (xs instanceof short[]) {
            return ((short[])xs).clone();
        }
        if (xs instanceof boolean[]) {
            return ((boolean[])xs).clone();
        }
        if (xs == null) {
            throw new NullPointerException();
        }
        throw new MatchError(xs);
    }

    public Object[] toObjectArray(Object src) {
        if (src instanceof Object[]) {
            return (Object[])src;
        }
        if (src instanceof int[]) {
            return ScalaRunTime$.copy$mIc$sp$1((int[])src);
        }
        if (src instanceof double[]) {
            return ScalaRunTime$.copy$mDc$sp$1((double[])src);
        }
        if (src instanceof long[]) {
            return ScalaRunTime$.copy$mJc$sp$1((long[])src);
        }
        if (src instanceof float[]) {
            return ScalaRunTime$.copy$mFc$sp$1((float[])src);
        }
        if (src instanceof char[]) {
            return ScalaRunTime$.copy$mCc$sp$1((char[])src);
        }
        if (src instanceof byte[]) {
            return ScalaRunTime$.copy$mBc$sp$1((byte[])src);
        }
        if (src instanceof short[]) {
            return ScalaRunTime$.copy$mSc$sp$1((short[])src);
        }
        if (src instanceof boolean[]) {
            return ScalaRunTime$.copy$mZc$sp$1((boolean[])src);
        }
        if (src == null) {
            throw new NullPointerException();
        }
        throw new MatchError(src);
    }

    public <T> Object[] toArray(Seq<T> xs) {
        if (xs.isEmpty()) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] arr = new Object[xs.length()];
        Iterator it = xs.iterator();
        int i = 0;
        while (it.hasNext()) {
            arr[i] = it.next();
            ++i;
        }
        return arr;
    }

    public Method ensureAccessible(Method m4) {
        return package$.MODULE$.ensureAccessible(m4);
    }

    public String _toString(Product x) {
        return x.productIterator().mkString(new java.lang.StringBuilder(1).append(x.productPrefix()).append("(").toString(), ",", ")");
    }

    public int _hashCode(Product x) {
        return MurmurHash3$.MODULE$.productHash(x);
    }

    public <T> Iterator<T> typedProductIterator(Product x) {
        return new AbstractIterator<T>(x){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    public String stringOf(Object arg) {
        return this.stringOf(arg, Integer.MAX_VALUE);
    }

    public String stringOf(Object arg, int maxElements) {
        try {
            return this.inner$1(arg, maxElements);
        }
        catch (Throwable throwable) {
            if (throwable instanceof UnsupportedOperationException ? true : throwable instanceof AssertionError) {
                return String.valueOf(arg);
            }
            throw throwable;
        }
    }

    public String replStringOf(Object arg, int maxElements) {
        String string = this.stringOf(arg, maxElements);
        switch (string == null ? 0 : string.hashCode()) {
            case 0: {
                if (string != null) break;
                return "null toString";
            }
        }
        if (string.indexOf(10) >= 0) {
            return new java.lang.StringBuilder(2).append("\n").append(string).append("\n").toString();
        }
        return new java.lang.StringBuilder(1).append(string).append("\n").toString();
    }

    public <T> ArraySeq<T> genericWrapArray(Object xs) {
        if (xs == null) {
            return null;
        }
        return ArraySeq$.MODULE$.unsafeWrapArray(xs);
    }

    public <T> ArraySeq<T> wrapRefArray(T[] xs) {
        if (xs == null) {
            return null;
        }
        if (xs.length == 0) {
            return ArraySeq$.MODULE$.empty(ClassTag$.MODULE$.AnyRef());
        }
        return new ArraySeq.ofRef<T>(xs);
    }

    public ArraySeq<Object> wrapIntArray(int[] xs) {
        if (xs != null) {
            return new ArraySeq.ofInt(xs);
        }
        return null;
    }

    public ArraySeq<Object> wrapDoubleArray(double[] xs) {
        if (xs != null) {
            return new ArraySeq.ofDouble(xs);
        }
        return null;
    }

    public ArraySeq<Object> wrapLongArray(long[] xs) {
        if (xs != null) {
            return new ArraySeq.ofLong(xs);
        }
        return null;
    }

    public ArraySeq<Object> wrapFloatArray(float[] xs) {
        if (xs != null) {
            return new ArraySeq.ofFloat(xs);
        }
        return null;
    }

    public ArraySeq<Object> wrapCharArray(char[] xs) {
        if (xs != null) {
            return new ArraySeq.ofChar(xs);
        }
        return null;
    }

    public ArraySeq<Object> wrapByteArray(byte[] xs) {
        if (xs != null) {
            return new ArraySeq.ofByte(xs);
        }
        return null;
    }

    public ArraySeq<Object> wrapShortArray(short[] xs) {
        if (xs != null) {
            return new ArraySeq.ofShort(xs);
        }
        return null;
    }

    public ArraySeq<Object> wrapBooleanArray(boolean[] xs) {
        if (xs != null) {
            return new ArraySeq.ofBoolean(xs);
        }
        return null;
    }

    public ArraySeq<BoxedUnit> wrapUnitArray(BoxedUnit[] xs) {
        if (xs != null) {
            return new ArraySeq.ofUnit(xs);
        }
        return null;
    }

    private static final Object[] copy$mZc$sp$1(boolean[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = src[i];
        }
        return dest;
    }

    private static final Object[] copy$mBc$sp$1(byte[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = src[i];
        }
        return dest;
    }

    private static final Object[] copy$mCc$sp$1(char[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = Character.valueOf(src[i]);
        }
        return dest;
    }

    private static final Object[] copy$mDc$sp$1(double[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = src[i];
        }
        return dest;
    }

    private static final Object[] copy$mFc$sp$1(float[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = Float.valueOf(src[i]);
        }
        return dest;
    }

    private static final Object[] copy$mIc$sp$1(int[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = src[i];
        }
        return dest;
    }

    private static final Object[] copy$mJc$sp$1(long[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = src[i];
        }
        return dest;
    }

    private static final Object[] copy$mSc$sp$1(short[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = src[i];
        }
        return dest;
    }

    private static final Object[] copy$mVc$sp$1(BoxedUnit[] src) {
        int length = src.length;
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = src[i];
        }
        return dest;
    }

    private static final Object[] copy$1(Object src) {
        int length = Array.getLength(src);
        if (length == 0) {
            return Array$.MODULE$.emptyObjectArray();
        }
        Object[] dest = new Object[length];
        for (int i = 0; i < length; ++i) {
            dest[i] = MODULE$.array_apply(src, i);
        }
        return dest;
    }

    private static final String packageOf$1(Object x) {
        Package package_ = x.getClass().getPackage();
        if (package_ == null) {
            return "";
        }
        return package_.getName();
    }

    private static final boolean isScalaClass$1(Object x) {
        return ScalaRunTime$.packageOf$1(x).startsWith("scala.");
    }

    private static final boolean isScalaCompilerClass$1(Object x) {
        return ScalaRunTime$.packageOf$1(x).startsWith("scala.tools.nsc.");
    }

    private static final boolean isTuple$1(Object x) {
        return x != null && x.getClass().getName().startsWith("scala.Tuple");
    }

    private static final boolean isSubClassOf$1(Class potentialSubClass, String ofClass) {
        try {
            ClassLoader classLoader = potentialSubClass.getClassLoader();
            return Class.forName(ofClass, false, classLoader).isAssignableFrom(potentialSubClass);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    private static final boolean isXmlNode$1(Class potentialSubClass) {
        return ScalaRunTime$.isSubClassOf$1(potentialSubClass, "scala.xml.Node");
    }

    private static final boolean isXmlMetaData$1(Class potentialSubClass) {
        return ScalaRunTime$.isSubClassOf$1(potentialSubClass, "scala.xml.MetaData");
    }

    private static final boolean useOwnToString$1(Object x) {
        if (x instanceof Range ? true : x instanceof NumericRange) {
            return true;
        }
        if (x instanceof SortedOps) {
            return true;
        }
        if (x instanceof StringView ? true : (x instanceof StringOps ? true : x instanceof StringBuilder)) {
            return true;
        }
        if (x instanceof View) {
            return true;
        }
        if (x instanceof Iterable) {
            Iterable iterable = (Iterable)x;
            return !(iterable instanceof StrictOptimizedIterableOps) || !ScalaRunTime$.isScalaClass$1(iterable) || ScalaRunTime$.isScalaCompilerClass$1(iterable) || ScalaRunTime$.isSubClassOf$1(iterable.getClass(), "scala.xml.Node") || ScalaRunTime$.isSubClassOf$1(iterable.getClass(), "scala.xml.MetaData");
        }
        return false;
    }

    private final String mapInner$1(Object arg, int maxElements$1) {
        if (arg instanceof Tuple2) {
            Tuple2 tuple2 = (Tuple2)arg;
            Object k = tuple2._1();
            Object v = tuple2._2();
            return new java.lang.StringBuilder(4).append(this.inner$1(k, maxElements$1)).append(" -> ").append(this.inner$1(v, maxElements$1)).toString();
        }
        return this.inner$1(arg, maxElements$1);
    }

    public static final /* synthetic */ String $anonfun$stringOf$1(int x$1) {
        return "()";
    }

    /*
     * WARNING - void declaration
     */
    private final String arrayToString$1(Object x, int maxElements$1) {
        Class<?> clazz = x.getClass().getComponentType();
        Class<BoxedUnit> clazz2 = BoxedUnit.class;
        if (clazz != null && clazz.equals(clazz2)) {
            String string;
            int n = 0;
            int until$extension_end = Math.min(Array.getLength(x), maxElements$1);
            Range.Exclusive map_this = new Range.Exclusive(n, until$extension_end, 1);
            map_this.scala$collection$immutable$Range$$validateMaxLength();
            Builder strictOptimizedMap_b = IndexedSeq$.MODULE$.newBuilder();
            Iterator strictOptimizedMap_it = map_this.iterator();
            while (strictOptimizedMap_it.hasNext()) {
                void strictOptimizedMap_$plus$eq_elem;
                Integer cfr_ignored_0 = (Integer)strictOptimizedMap_it.next();
                string = "()";
                if (strictOptimizedMap_b == null) {
                    throw null;
                }
                strictOptimizedMap_b.addOne(strictOptimizedMap_$plus$eq_elem);
                string = null;
            }
            Object var8_8 = null;
            string = null;
            Object var7_7 = null;
            Object var5_6 = null;
            return ((IndexedSeq)strictOptimizedMap_b.result()).mkString("Array(", ", ", ")");
        }
        return ArrayOps$.MODULE$.iterator$extension(x).take(maxElements$1).map((Function1<Object, String> & Serializable)arg -> this.inner$1(arg, maxElements$1)).mkString("Array(", ", ", ")");
    }

    private final String inner$1(Object arg2, int maxElements$1) {
        Product product;
        Product1 product1;
        block11: {
            String string;
            block13: {
                block12: {
                    if (arg2 == null) {
                        return "null";
                    }
                    if ("".equals(arg2)) {
                        return "\"\"";
                    }
                    if (!(arg2 instanceof String)) break block11;
                    string = (String)arg2;
                    if (Character.isWhitespace(StringOps$.MODULE$.head$extension(string))) break block12;
                    if (!Character.isWhitespace(StringOps$.MODULE$.last$extension(string))) break block13;
                }
                return new java.lang.StringBuilder(2).append("\"").append(string).append("\"").toString();
            }
            return string;
        }
        if (ScalaRunTime$.useOwnToString$1(arg2)) {
            return arg2.toString();
        }
        if (this.isArray(arg2, 1)) {
            return this.arrayToString$1(arg2, maxElements$1);
        }
        if (arg2 instanceof Map) {
            Map map = (Map)arg2;
            return map.iterator().take(maxElements$1).map((Function1<Object, String> & Serializable)arg -> this.mapInner$1(arg, maxElements$1)).mkString(new java.lang.StringBuilder(1).append(map.className()).append("(").toString(), ", ", ")");
        }
        if (arg2 instanceof Iterable) {
            Iterable iterable = (Iterable)arg2;
            return iterable.iterator().take(maxElements$1).map((Function1<Object, String> & Serializable)arg -> this.inner$1(arg, maxElements$1)).mkString(new java.lang.StringBuilder(1).append(iterable.className()).append("(").toString(), ", ", ")");
        }
        if (arg2 instanceof Product1 && ScalaRunTime$.isTuple$1(product1 = (Product1)arg2)) {
            return new java.lang.StringBuilder(3).append("(").append(this.inner$1(product1._1(), maxElements$1)).append(",)").toString();
        }
        if (arg2 instanceof Product && ScalaRunTime$.isTuple$1(product = (Product)arg2)) {
            return product.productIterator().map((Function1<Object, String> & Serializable)arg -> this.inner$1(arg, maxElements$1)).mkString("(", ",", ")");
        }
        return arg2.toString();
    }

    private ScalaRunTime$() {
    }

    public static final /* synthetic */ String $anonfun$stringOf$1$adapted(Object x$1) {
        return ScalaRunTime$.$anonfun$stringOf$1(BoxesRunTime.unboxToInt(x$1));
    }
}

