/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Some;
import scala.collection.Seq;
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
import scala.runtime.Null$;
import scala.runtime.ScalaRunTime$;

public final class Sorting$ {
    public static final Sorting$ MODULE$ = new Sorting$();

    public void quickSort(double[] a) {
        Arrays.sort(a);
    }

    public void quickSort(int[] a) {
        Arrays.sort(a);
    }

    public void quickSort(float[] a) {
        Arrays.sort(a);
    }

    private final int qsortThreshold() {
        return 16;
    }

    public <K> void quickSort(Object a, Ordering<K> evidence$1) {
        this.inner$1(a, 0, Array.getLength(a), evidence$1);
    }

    private final int mergeThreshold() {
        return 32;
    }

    public <T> void scala$util$Sorting$$insertionSort(Object a, int i0, int iN, Ordering<T> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(ScalaRunTime$.MODULE$.array_apply(a, i0), ScalaRunTime$.MODULE$.array_apply(a, i0 + 1)) > 0) {
            Object temp = ScalaRunTime$.MODULE$.array_apply(a, i0);
            ScalaRunTime$.MODULE$.array_update(a, i0, ScalaRunTime$.MODULE$.array_apply(a, i0 + 1));
            ScalaRunTime$.MODULE$.array_update(a, i0 + 1, temp);
        }
        for (int m4 = 2; m4 < n; ++m4) {
            Object next = ScalaRunTime$.MODULE$.array_apply(a, i0 + m4);
            if (ord.compare(next, ScalaRunTime$.MODULE$.array_apply(a, i0 + m4 - 1)) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(next, ScalaRunTime$.MODULE$.array_apply(a, ix)) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(next, ScalaRunTime$.MODULE$.array_apply(a, iA)) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                ScalaRunTime$.MODULE$.array_update(a, i, ScalaRunTime$.MODULE$.array_apply(a, i - 1));
            }
            ScalaRunTime$.MODULE$.array_update(a, ix, next);
        }
    }

    public <T> void scala$util$Sorting$$mergeSort(Object a, int i0, int iN, Ordering<T> ord, Object scratch, ClassTag<T> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        Object sc = scratch == null ? evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    public <T> Null$ scala$util$Sorting$$mergeSort$default$5() {
        return null;
    }

    public <T> void scala$util$Sorting$$mergeSorted(Object a, int i0, int iK, int iN, Ordering<T> ord, Object scratch) {
        if (ord.compare(ScalaRunTime$.MODULE$.array_apply(a, iK - 1), ScalaRunTime$.MODULE$.array_apply(a, iK)) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                ScalaRunTime$.MODULE$.array_update(scratch, j, ScalaRunTime$.MODULE$.array_apply(a, i));
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(ScalaRunTime$.MODULE$.array_apply(a, i), ScalaRunTime$.MODULE$.array_apply(scratch, j)) < 0) {
                    ScalaRunTime$.MODULE$.array_update(a, k, ScalaRunTime$.MODULE$.array_apply(a, i));
                    ++i;
                } else {
                    ScalaRunTime$.MODULE$.array_update(a, k, ScalaRunTime$.MODULE$.array_apply(scratch, j));
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                ScalaRunTime$.MODULE$.array_update(a, k, ScalaRunTime$.MODULE$.array_apply(scratch, j));
                ++j;
                ++k;
            }
            return;
        }
    }

    public void scala$util$Sorting$$booleanSort(boolean[] a, int from, int until) {
        int i;
        int n = 0;
        for (i = from; i < until; ++i) {
            if (a[i]) continue;
            ++n;
        }
        for (i = 0; i < n; ++i) {
            a[from + i] = false;
        }
        while (from + i < until) {
            a[from + i] = true;
            ++i;
        }
    }

    private <T> void sort(Object a, int from, int until, Ordering<T> ord) {
        if (a instanceof Object[]) {
            if (Array.getLength(a) > 1 && ord == null) {
                throw new NullPointerException("Ordering");
            }
            Arrays.sort((Object[])a, from, until, ord);
            return;
        }
        if (a instanceof int[]) {
            int[] nArray = (int[])a;
            if (ord == Ordering$Int$.MODULE$) {
                Arrays.sort(nArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mIc$sp(nArray, from, until, ord, null, ClassTag$.MODULE$.Int());
            return;
        }
        if (a instanceof double[]) {
            double[] dArray = (double[])a;
            this.scala$util$Sorting$$mergeSort$mDc$sp(dArray, from, until, ord, null, ClassTag$.MODULE$.Double());
            return;
        }
        if (a instanceof long[]) {
            long[] lArray = (long[])a;
            if (ord == Ordering$Long$.MODULE$) {
                Arrays.sort(lArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mJc$sp(lArray, from, until, ord, null, ClassTag$.MODULE$.Long());
            return;
        }
        if (a instanceof float[]) {
            float[] fArray = (float[])a;
            this.scala$util$Sorting$$mergeSort$mFc$sp(fArray, from, until, ord, null, ClassTag$.MODULE$.Float());
            return;
        }
        if (a instanceof char[]) {
            char[] cArray = (char[])a;
            if (ord == Ordering$Char$.MODULE$) {
                Arrays.sort(cArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mCc$sp(cArray, from, until, ord, null, ClassTag$.MODULE$.Char());
            return;
        }
        if (a instanceof byte[]) {
            byte[] byArray = (byte[])a;
            if (ord == Ordering$Byte$.MODULE$) {
                Arrays.sort(byArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mBc$sp(byArray, from, until, ord, null, ClassTag$.MODULE$.Byte());
            return;
        }
        if (a instanceof short[]) {
            short[] sArray = (short[])a;
            if (ord == Ordering$Short$.MODULE$) {
                Arrays.sort(sArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mSc$sp(sArray, from, until, ord, null, ClassTag$.MODULE$.Short());
            return;
        }
        if (a instanceof boolean[]) {
            boolean[] blArray = (boolean[])a;
            if (ord == Ordering$Boolean$.MODULE$) {
                this.scala$util$Sorting$$booleanSort(blArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mZc$sp(blArray, from, until, ord, null, ClassTag$.MODULE$.Boolean());
            return;
        }
        if (a == null) {
            throw new NullPointerException();
        }
        throw new MatchError(a);
    }

    public <K> void stableSort(Object a, Ordering<K> evidence$3) {
        this.stableSort(a, 0, Array.getLength(a), evidence$3);
    }

    public <K> void stableSort(Object a, int from, int until, Ordering<K> evidence$4) {
        if (a instanceof Object[]) {
            if (Array.getLength(a) > 1 && evidence$4 == null) {
                throw new NullPointerException("Ordering");
            }
            Arrays.sort((Object[])a, from, until, evidence$4);
            return;
        }
        if (a instanceof int[]) {
            int[] nArray = (int[])a;
            if (evidence$4 == Ordering$Int$.MODULE$) {
                Arrays.sort(nArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mIc$sp(nArray, from, until, evidence$4, null, ClassTag$.MODULE$.Int());
            return;
        }
        if (a instanceof double[]) {
            double[] dArray = (double[])a;
            this.scala$util$Sorting$$mergeSort$mDc$sp(dArray, from, until, evidence$4, null, ClassTag$.MODULE$.Double());
            return;
        }
        if (a instanceof long[]) {
            long[] lArray = (long[])a;
            if (evidence$4 == Ordering$Long$.MODULE$) {
                Arrays.sort(lArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mJc$sp(lArray, from, until, evidence$4, null, ClassTag$.MODULE$.Long());
            return;
        }
        if (a instanceof float[]) {
            float[] fArray = (float[])a;
            this.scala$util$Sorting$$mergeSort$mFc$sp(fArray, from, until, evidence$4, null, ClassTag$.MODULE$.Float());
            return;
        }
        if (a instanceof char[]) {
            char[] cArray = (char[])a;
            if (evidence$4 == Ordering$Char$.MODULE$) {
                Arrays.sort(cArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mCc$sp(cArray, from, until, evidence$4, null, ClassTag$.MODULE$.Char());
            return;
        }
        if (a instanceof byte[]) {
            byte[] byArray = (byte[])a;
            if (evidence$4 == Ordering$Byte$.MODULE$) {
                Arrays.sort(byArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mBc$sp(byArray, from, until, evidence$4, null, ClassTag$.MODULE$.Byte());
            return;
        }
        if (a instanceof short[]) {
            short[] sArray = (short[])a;
            if (evidence$4 == Ordering$Short$.MODULE$) {
                Arrays.sort(sArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mSc$sp(sArray, from, until, evidence$4, null, ClassTag$.MODULE$.Short());
            return;
        }
        if (a instanceof boolean[]) {
            boolean[] blArray = (boolean[])a;
            if (evidence$4 == Ordering$Boolean$.MODULE$) {
                this.scala$util$Sorting$$booleanSort(blArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mZc$sp(blArray, from, until, evidence$4, null, ClassTag$.MODULE$.Boolean());
            return;
        }
        if (a == null) {
            throw new NullPointerException();
        }
        throw new MatchError(a);
    }

    /*
     * WARNING - void declaration
     */
    public <K> void stableSort(Object a, Function2<K, K, Object> f) {
        void stableSort_until;
        int n = Array.getLength(a);
        int stableSort_from = 0;
        Ordering stableSort_sort_ord = new Ordering<T>(f){
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
        };
        if (a instanceof Object[]) {
            Array.getLength(a);
            Arrays.sort((Object[])a, stableSort_from, (int)stableSort_until, stableSort_sort_ord);
            return;
        }
        if (a instanceof int[]) {
            int[] nArray = (int[])a;
            if (stableSort_sort_ord == Ordering$Int$.MODULE$) {
                Arrays.sort(nArray, stableSort_from, (int)stableSort_until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mIc$sp(nArray, stableSort_from, (int)stableSort_until, stableSort_sort_ord, null, ClassTag$.MODULE$.Int());
            return;
        }
        if (a instanceof double[]) {
            double[] dArray = (double[])a;
            this.scala$util$Sorting$$mergeSort$mDc$sp(dArray, stableSort_from, (int)stableSort_until, stableSort_sort_ord, null, ClassTag$.MODULE$.Double());
            return;
        }
        if (a instanceof long[]) {
            long[] lArray = (long[])a;
            if (stableSort_sort_ord == Ordering$Long$.MODULE$) {
                Arrays.sort(lArray, stableSort_from, (int)stableSort_until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mJc$sp(lArray, stableSort_from, (int)stableSort_until, stableSort_sort_ord, null, ClassTag$.MODULE$.Long());
            return;
        }
        if (a instanceof float[]) {
            float[] fArray = (float[])a;
            this.scala$util$Sorting$$mergeSort$mFc$sp(fArray, stableSort_from, (int)stableSort_until, stableSort_sort_ord, null, ClassTag$.MODULE$.Float());
            return;
        }
        if (a instanceof char[]) {
            char[] cArray = (char[])a;
            if (stableSort_sort_ord == Ordering$Char$.MODULE$) {
                Arrays.sort(cArray, stableSort_from, (int)stableSort_until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mCc$sp(cArray, stableSort_from, (int)stableSort_until, stableSort_sort_ord, null, ClassTag$.MODULE$.Char());
            return;
        }
        if (a instanceof byte[]) {
            byte[] byArray = (byte[])a;
            if (stableSort_sort_ord == Ordering$Byte$.MODULE$) {
                Arrays.sort(byArray, stableSort_from, (int)stableSort_until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mBc$sp(byArray, stableSort_from, (int)stableSort_until, stableSort_sort_ord, null, ClassTag$.MODULE$.Byte());
            return;
        }
        if (a instanceof short[]) {
            short[] sArray = (short[])a;
            if (stableSort_sort_ord == Ordering$Short$.MODULE$) {
                Arrays.sort(sArray, stableSort_from, (int)stableSort_until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mSc$sp(sArray, stableSort_from, (int)stableSort_until, stableSort_sort_ord, null, ClassTag$.MODULE$.Short());
            return;
        }
        if (a instanceof boolean[]) {
            boolean[] blArray = (boolean[])a;
            if (stableSort_sort_ord == Ordering$Boolean$.MODULE$) {
                this.scala$util$Sorting$$booleanSort(blArray, stableSort_from, (int)stableSort_until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mZc$sp(blArray, stableSort_from, (int)stableSort_until, stableSort_sort_ord, null, ClassTag$.MODULE$.Boolean());
            return;
        }
        throw new MatchError(a);
    }

    public <K> void stableSort(Object a, Function2<K, K, Object> f, int from, int until) {
        Ordering sort_ord = new /* invalid duplicate definition of identical inner class */;
        if (a instanceof Object[]) {
            Array.getLength(a);
            Arrays.sort((Object[])a, from, until, sort_ord);
            return;
        }
        if (a instanceof int[]) {
            int[] nArray = (int[])a;
            if (sort_ord == Ordering$Int$.MODULE$) {
                Arrays.sort(nArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mIc$sp(nArray, from, until, sort_ord, null, ClassTag$.MODULE$.Int());
            return;
        }
        if (a instanceof double[]) {
            double[] dArray = (double[])a;
            this.scala$util$Sorting$$mergeSort$mDc$sp(dArray, from, until, sort_ord, null, ClassTag$.MODULE$.Double());
            return;
        }
        if (a instanceof long[]) {
            long[] lArray = (long[])a;
            if (sort_ord == Ordering$Long$.MODULE$) {
                Arrays.sort(lArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mJc$sp(lArray, from, until, sort_ord, null, ClassTag$.MODULE$.Long());
            return;
        }
        if (a instanceof float[]) {
            float[] fArray = (float[])a;
            this.scala$util$Sorting$$mergeSort$mFc$sp(fArray, from, until, sort_ord, null, ClassTag$.MODULE$.Float());
            return;
        }
        if (a instanceof char[]) {
            char[] cArray = (char[])a;
            if (sort_ord == Ordering$Char$.MODULE$) {
                Arrays.sort(cArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mCc$sp(cArray, from, until, sort_ord, null, ClassTag$.MODULE$.Char());
            return;
        }
        if (a instanceof byte[]) {
            byte[] byArray = (byte[])a;
            if (sort_ord == Ordering$Byte$.MODULE$) {
                Arrays.sort(byArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mBc$sp(byArray, from, until, sort_ord, null, ClassTag$.MODULE$.Byte());
            return;
        }
        if (a instanceof short[]) {
            short[] sArray = (short[])a;
            if (sort_ord == Ordering$Short$.MODULE$) {
                Arrays.sort(sArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mSc$sp(sArray, from, until, sort_ord, null, ClassTag$.MODULE$.Short());
            return;
        }
        if (a instanceof boolean[]) {
            boolean[] blArray = (boolean[])a;
            if (sort_ord == Ordering$Boolean$.MODULE$) {
                this.scala$util$Sorting$$booleanSort(blArray, from, until);
                return;
            }
            this.scala$util$Sorting$$mergeSort$mZc$sp(blArray, from, until, sort_ord, null, ClassTag$.MODULE$.Boolean());
            return;
        }
        if (a == null) {
            throw new NullPointerException();
        }
        throw new MatchError(a);
    }

    /*
     * WARNING - void declaration
     */
    public <K> Object stableSort(Seq<K> a, ClassTag<K> evidence$5, Ordering<K> evidence$6) {
        void sort_until;
        Object ret = a.toArray(evidence$5);
        int n = Array.getLength(ret);
        int sort_from = 0;
        if (ret instanceof Object[]) {
            if (Array.getLength(ret) > 1 && evidence$6 == null) {
                throw new NullPointerException("Ordering");
            }
            Arrays.sort((Object[])ret, sort_from, (int)sort_until, evidence$6);
        } else if (ret instanceof int[]) {
            int[] nArray = (int[])ret;
            if (evidence$6 == Ordering$Int$.MODULE$) {
                Arrays.sort(nArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mIc$sp(nArray, sort_from, (int)sort_until, evidence$6, null, ClassTag$.MODULE$.Int());
            }
        } else if (ret instanceof double[]) {
            double[] dArray = (double[])ret;
            this.scala$util$Sorting$$mergeSort$mDc$sp(dArray, sort_from, (int)sort_until, evidence$6, null, ClassTag$.MODULE$.Double());
        } else if (ret instanceof long[]) {
            long[] lArray = (long[])ret;
            if (evidence$6 == Ordering$Long$.MODULE$) {
                Arrays.sort(lArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mJc$sp(lArray, sort_from, (int)sort_until, evidence$6, null, ClassTag$.MODULE$.Long());
            }
        } else if (ret instanceof float[]) {
            float[] fArray = (float[])ret;
            this.scala$util$Sorting$$mergeSort$mFc$sp(fArray, sort_from, (int)sort_until, evidence$6, null, ClassTag$.MODULE$.Float());
        } else if (ret instanceof char[]) {
            char[] cArray = (char[])ret;
            if (evidence$6 == Ordering$Char$.MODULE$) {
                Arrays.sort(cArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mCc$sp(cArray, sort_from, (int)sort_until, evidence$6, null, ClassTag$.MODULE$.Char());
            }
        } else if (ret instanceof byte[]) {
            byte[] byArray = (byte[])ret;
            if (evidence$6 == Ordering$Byte$.MODULE$) {
                Arrays.sort(byArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mBc$sp(byArray, sort_from, (int)sort_until, evidence$6, null, ClassTag$.MODULE$.Byte());
            }
        } else if (ret instanceof short[]) {
            short[] sArray = (short[])ret;
            if (evidence$6 == Ordering$Short$.MODULE$) {
                Arrays.sort(sArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mSc$sp(sArray, sort_from, (int)sort_until, evidence$6, null, ClassTag$.MODULE$.Short());
            }
        } else if (ret instanceof boolean[]) {
            boolean[] blArray = (boolean[])ret;
            if (evidence$6 == Ordering$Boolean$.MODULE$) {
                this.scala$util$Sorting$$booleanSort(blArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mZc$sp(blArray, sort_from, (int)sort_until, evidence$6, null, ClassTag$.MODULE$.Boolean());
            }
        } else {
            throw new MatchError(ret);
        }
        return ret;
    }

    /*
     * WARNING - void declaration
     */
    public <K> Object stableSort(Seq<K> a, Function2<K, K, Object> f, ClassTag<K> evidence$7) {
        void sort_ord;
        void sort_until;
        Object ret = a.toArray(evidence$7);
        Ordering ordering = new /* invalid duplicate definition of identical inner class */;
        int n = Array.getLength(ret);
        int sort_from = 0;
        if (ret instanceof Object[]) {
            Array.getLength(ret);
            Arrays.sort((Object[])ret, sort_from, (int)sort_until, sort_ord);
        } else if (ret instanceof int[]) {
            int[] nArray = (int[])ret;
            if (sort_ord == Ordering$Int$.MODULE$) {
                Arrays.sort(nArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mIc$sp(nArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Int());
            }
        } else if (ret instanceof double[]) {
            double[] dArray = (double[])ret;
            this.scala$util$Sorting$$mergeSort$mDc$sp(dArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Double());
        } else if (ret instanceof long[]) {
            long[] lArray = (long[])ret;
            if (sort_ord == Ordering$Long$.MODULE$) {
                Arrays.sort(lArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mJc$sp(lArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Long());
            }
        } else if (ret instanceof float[]) {
            float[] fArray = (float[])ret;
            this.scala$util$Sorting$$mergeSort$mFc$sp(fArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Float());
        } else if (ret instanceof char[]) {
            char[] cArray = (char[])ret;
            if (sort_ord == Ordering$Char$.MODULE$) {
                Arrays.sort(cArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mCc$sp(cArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Char());
            }
        } else if (ret instanceof byte[]) {
            byte[] byArray = (byte[])ret;
            if (sort_ord == Ordering$Byte$.MODULE$) {
                Arrays.sort(byArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mBc$sp(byArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Byte());
            }
        } else if (ret instanceof short[]) {
            short[] sArray = (short[])ret;
            if (sort_ord == Ordering$Short$.MODULE$) {
                Arrays.sort(sArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mSc$sp(sArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Short());
            }
        } else if (ret instanceof boolean[]) {
            boolean[] blArray = (boolean[])ret;
            if (sort_ord == Ordering$Boolean$.MODULE$) {
                this.scala$util$Sorting$$booleanSort(blArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mZc$sp(blArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Boolean());
            }
        } else {
            throw new MatchError(ret);
        }
        return ret;
    }

    /*
     * WARNING - void declaration
     */
    public <K, M> Object stableSort(Seq<K> a, Function1<K, M> f, ClassTag<K> evidence$8, Ordering<M> evidence$9) {
        void sort_until;
        void sort_ord;
        Object ret = a.toArray(evidence$8);
        Ordering<K> ordering = evidence$9.on(f);
        int n = Array.getLength(ret);
        int sort_from = 0;
        if (ret instanceof Object[]) {
            if (Array.getLength(ret) > 1 && sort_ord == null) {
                throw new NullPointerException("Ordering");
            }
            Arrays.sort((Object[])ret, sort_from, (int)sort_until, sort_ord);
        } else if (ret instanceof int[]) {
            int[] nArray = (int[])ret;
            if (sort_ord == Ordering$Int$.MODULE$) {
                Arrays.sort(nArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mIc$sp(nArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Int());
            }
        } else if (ret instanceof double[]) {
            double[] dArray = (double[])ret;
            this.scala$util$Sorting$$mergeSort$mDc$sp(dArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Double());
        } else if (ret instanceof long[]) {
            long[] lArray = (long[])ret;
            if (sort_ord == Ordering$Long$.MODULE$) {
                Arrays.sort(lArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mJc$sp(lArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Long());
            }
        } else if (ret instanceof float[]) {
            float[] fArray = (float[])ret;
            this.scala$util$Sorting$$mergeSort$mFc$sp(fArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Float());
        } else if (ret instanceof char[]) {
            char[] cArray = (char[])ret;
            if (sort_ord == Ordering$Char$.MODULE$) {
                Arrays.sort(cArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mCc$sp(cArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Char());
            }
        } else if (ret instanceof byte[]) {
            byte[] byArray = (byte[])ret;
            if (sort_ord == Ordering$Byte$.MODULE$) {
                Arrays.sort(byArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mBc$sp(byArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Byte());
            }
        } else if (ret instanceof short[]) {
            short[] sArray = (short[])ret;
            if (sort_ord == Ordering$Short$.MODULE$) {
                Arrays.sort(sArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mSc$sp(sArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Short());
            }
        } else if (ret instanceof boolean[]) {
            boolean[] blArray = (boolean[])ret;
            if (sort_ord == Ordering$Boolean$.MODULE$) {
                this.scala$util$Sorting$$booleanSort(blArray, sort_from, (int)sort_until);
            } else {
                this.scala$util$Sorting$$mergeSort$mZc$sp(blArray, sort_from, (int)sort_until, (Ordering<Object>)sort_ord, null, ClassTag$.MODULE$.Boolean());
            }
        } else {
            throw new MatchError(ret);
        }
        return ret;
    }

    private void insertionSort$mZc$sp(boolean[] a, int i0, int iN, Ordering<Object> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(a[i0], a[i0 + 1]) > 0) {
            boolean temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            boolean next = a[i0 + m4];
            if (ord.compare(next, a[i0 + m4 - 1]) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(next, a[ix]) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(next, a[iA]) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    private void insertionSort$mBc$sp(byte[] a, int i0, int iN, Ordering<Object> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(a[i0], a[i0 + 1]) > 0) {
            byte temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            byte next = a[i0 + m4];
            if (ord.compare(next, a[i0 + m4 - 1]) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(next, a[ix]) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(next, a[iA]) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    private void insertionSort$mCc$sp(char[] a, int i0, int iN, Ordering<Object> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(Character.valueOf(a[i0]), Character.valueOf(a[i0 + 1])) > 0) {
            char temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            char next = a[i0 + m4];
            if (ord.compare(Character.valueOf(next), Character.valueOf(a[i0 + m4 - 1])) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(Character.valueOf(next), Character.valueOf(a[ix])) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(Character.valueOf(next), Character.valueOf(a[iA])) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    private void insertionSort$mDc$sp(double[] a, int i0, int iN, Ordering<Object> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(a[i0], a[i0 + 1]) > 0) {
            double temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            double next = a[i0 + m4];
            if (ord.compare(next, a[i0 + m4 - 1]) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(next, a[ix]) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(next, a[iA]) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    private void insertionSort$mFc$sp(float[] a, int i0, int iN, Ordering<Object> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(Float.valueOf(a[i0]), Float.valueOf(a[i0 + 1])) > 0) {
            float temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            float next = a[i0 + m4];
            if (ord.compare(Float.valueOf(next), Float.valueOf(a[i0 + m4 - 1])) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(Float.valueOf(next), Float.valueOf(a[ix])) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(Float.valueOf(next), Float.valueOf(a[iA])) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    private void insertionSort$mIc$sp(int[] a, int i0, int iN, Ordering<Object> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(a[i0], a[i0 + 1]) > 0) {
            int temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            int next = a[i0 + m4];
            if (ord.compare(next, a[i0 + m4 - 1]) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(next, a[ix]) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(next, a[iA]) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    private void insertionSort$mJc$sp(long[] a, int i0, int iN, Ordering<Object> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(a[i0], a[i0 + 1]) > 0) {
            long temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            long next = a[i0 + m4];
            if (ord.compare(next, a[i0 + m4 - 1]) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(next, a[ix]) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(next, a[iA]) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    private void insertionSort$mSc$sp(short[] a, int i0, int iN, Ordering<Object> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(a[i0], a[i0 + 1]) > 0) {
            short temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            short next = a[i0 + m4];
            if (ord.compare(next, a[i0 + m4 - 1]) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(next, a[ix]) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(next, a[iA]) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    private void insertionSort$mVc$sp(BoxedUnit[] a, int i0, int iN, Ordering<BoxedUnit> ord) {
        int n = iN - i0;
        if (n < 2) {
            return;
        }
        if (ord.compare(a[i0], a[i0 + 1]) > 0) {
            BoxedUnit temp = a[i0];
            a[i0] = a[i0 + 1];
            a[i0 + 1] = temp;
        }
        for (int m4 = 2; m4 < n; ++m4) {
            BoxedUnit next = a[i0 + m4];
            if (ord.compare(next, a[i0 + m4 - 1]) >= 0) continue;
            int iA = i0;
            int iB = i0 + m4 - 1;
            while (iB - iA > 1) {
                int ix = iA + iB >>> 1;
                if (ord.compare(next, a[ix]) < 0) {
                    iB = ix;
                    continue;
                }
                iA = ix;
            }
            int ix = iA + (ord.compare(next, a[iA]) < 0 ? 0 : 1);
            for (int i = i0 + m4; i > ix; --i) {
                a[i] = a[i - 1];
            }
            a[ix] = next;
        }
    }

    public void scala$util$Sorting$$mergeSort$mZc$sp(boolean[] a, int i0, int iN, Ordering<Object> ord, boolean[] scratch, ClassTag<Object> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        boolean[] sc = scratch == null ? (boolean[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    public void scala$util$Sorting$$mergeSort$mBc$sp(byte[] a, int i0, int iN, Ordering<Object> ord, byte[] scratch, ClassTag<Object> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        byte[] sc = scratch == null ? (byte[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    public void scala$util$Sorting$$mergeSort$mCc$sp(char[] a, int i0, int iN, Ordering<Object> ord, char[] scratch, ClassTag<Object> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        char[] sc = scratch == null ? (char[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    public void scala$util$Sorting$$mergeSort$mDc$sp(double[] a, int i0, int iN, Ordering<Object> ord, double[] scratch, ClassTag<Object> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        double[] sc = scratch == null ? (double[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    public void scala$util$Sorting$$mergeSort$mFc$sp(float[] a, int i0, int iN, Ordering<Object> ord, float[] scratch, ClassTag<Object> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        float[] sc = scratch == null ? (float[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    public void scala$util$Sorting$$mergeSort$mIc$sp(int[] a, int i0, int iN, Ordering<Object> ord, int[] scratch, ClassTag<Object> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        int[] sc = scratch == null ? (int[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    public void scala$util$Sorting$$mergeSort$mJc$sp(long[] a, int i0, int iN, Ordering<Object> ord, long[] scratch, ClassTag<Object> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        long[] sc = scratch == null ? (long[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    public void scala$util$Sorting$$mergeSort$mSc$sp(short[] a, int i0, int iN, Ordering<Object> ord, short[] scratch, ClassTag<Object> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        short[] sc = scratch == null ? (short[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    private void mergeSort$mVc$sp(BoxedUnit[] a, int i0, int iN, Ordering<BoxedUnit> ord, BoxedUnit[] scratch, ClassTag<BoxedUnit> evidence$2) {
        if (iN - i0 < 32) {
            this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
            return;
        }
        int iK = i0 + iN >>> 1;
        BoxedUnit[] sc = scratch == null ? (BoxedUnit[])evidence$2.newArray(iK - i0) : scratch;
        this.scala$util$Sorting$$mergeSort(a, i0, iK, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSort(a, iK, iN, ord, sc, evidence$2);
        this.scala$util$Sorting$$mergeSorted(a, i0, iK, iN, ord, sc);
    }

    private void mergeSorted$mZc$sp(boolean[] a, int i0, int iK, int iN, Ordering<Object> ord, boolean[] scratch) {
        if (ord.compare(a[iK - 1], a[iK]) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(a[i], scratch[j]) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private void mergeSorted$mBc$sp(byte[] a, int i0, int iK, int iN, Ordering<Object> ord, byte[] scratch) {
        if (ord.compare(a[iK - 1], a[iK]) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(a[i], scratch[j]) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private void mergeSorted$mCc$sp(char[] a, int i0, int iK, int iN, Ordering<Object> ord, char[] scratch) {
        if (ord.compare(Character.valueOf(a[iK - 1]), Character.valueOf(a[iK])) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(Character.valueOf(a[i]), Character.valueOf(scratch[j])) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private void mergeSorted$mDc$sp(double[] a, int i0, int iK, int iN, Ordering<Object> ord, double[] scratch) {
        if (ord.compare(a[iK - 1], a[iK]) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(a[i], scratch[j]) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private void mergeSorted$mFc$sp(float[] a, int i0, int iK, int iN, Ordering<Object> ord, float[] scratch) {
        if (ord.compare(Float.valueOf(a[iK - 1]), Float.valueOf(a[iK])) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(Float.valueOf(a[i]), Float.valueOf(scratch[j])) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private void mergeSorted$mIc$sp(int[] a, int i0, int iK, int iN, Ordering<Object> ord, int[] scratch) {
        if (ord.compare(a[iK - 1], a[iK]) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(a[i], scratch[j]) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private void mergeSorted$mJc$sp(long[] a, int i0, int iK, int iN, Ordering<Object> ord, long[] scratch) {
        if (ord.compare(a[iK - 1], a[iK]) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(a[i], scratch[j]) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private void mergeSorted$mSc$sp(short[] a, int i0, int iK, int iN, Ordering<Object> ord, short[] scratch) {
        if (ord.compare(a[iK - 1], a[iK]) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(a[i], scratch[j]) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private void mergeSorted$mVc$sp(BoxedUnit[] a, int i0, int iK, int iN, Ordering<BoxedUnit> ord, BoxedUnit[] scratch) {
        if (ord.compare(a[iK - 1], a[iK]) > 0) {
            int i = i0;
            int jN = iK - i0;
            int j = 0;
            while (i < iK) {
                scratch[j] = a[i];
                ++i;
                ++j;
            }
            int k = i0;
            j = 0;
            while (i < iN && j < jN) {
                if (ord.compare(a[i], scratch[j]) < 0) {
                    a[k] = a[i];
                    ++i;
                } else {
                    a[k] = scratch[j];
                    ++j;
                }
                ++k;
            }
            while (j < jN) {
                a[k] = scratch[j];
                ++j;
                ++k;
            }
            return;
        }
    }

    private final void inner$1(Object a, int i0, int iN, Ordering ord) {
        while (true) {
            if (iN - i0 < 16) {
                this.scala$util$Sorting$$insertionSort(a, i0, iN, ord);
                return;
            }
            int iK = i0 + iN >>> 1;
            int pL = ord.compare(ScalaRunTime$.MODULE$.array_apply(a, i0), ScalaRunTime$.MODULE$.array_apply(a, iN - 1)) <= 0 ? (ord.compare(ScalaRunTime$.MODULE$.array_apply(a, i0), ScalaRunTime$.MODULE$.array_apply(a, iK)) < 0 ? (ord.compare(ScalaRunTime$.MODULE$.array_apply(a, iN - 1), ScalaRunTime$.MODULE$.array_apply(a, iK)) < 0 ? iN - 1 : iK) : i0) : (ord.compare(ScalaRunTime$.MODULE$.array_apply(a, i0), ScalaRunTime$.MODULE$.array_apply(a, iK)) < 0 ? i0 : (ord.compare(ScalaRunTime$.MODULE$.array_apply(a, iN - 1), ScalaRunTime$.MODULE$.array_apply(a, iK)) <= 0 ? iK : iN - 1));
            Object pivot = ScalaRunTime$.MODULE$.array_apply(a, pL);
            if (pL != iK) {
                ScalaRunTime$.MODULE$.array_update(a, pL, ScalaRunTime$.MODULE$.array_apply(a, iK));
                ScalaRunTime$.MODULE$.array_update(a, iK, pivot);
                pL = iK;
            }
            int pR = pL + 1;
            int iA = i0;
            int iB = iN;
            block7: while (pL - iA > 0) {
                Object current = ScalaRunTime$.MODULE$.array_apply(a, iA);
                int n = ord.compare(current, pivot);
                switch (n) {
                    case 0: {
                        ScalaRunTime$.MODULE$.array_update(a, iA, ScalaRunTime$.MODULE$.array_apply(a, pL - 1));
                        ScalaRunTime$.MODULE$.array_update(a, pL - 1, current);
                        --pL;
                        continue block7;
                    }
                }
                if (n < 0) {
                    ++iA;
                    continue;
                }
                if (iB > pR) {
                    ScalaRunTime$.MODULE$.array_update(a, iA, ScalaRunTime$.MODULE$.array_apply(a, iB - 1));
                    ScalaRunTime$.MODULE$.array_update(a, iB - 1, current);
                    --iB;
                    continue;
                }
                ScalaRunTime$.MODULE$.array_update(a, iA, ScalaRunTime$.MODULE$.array_apply(a, pL - 1));
                ScalaRunTime$.MODULE$.array_update(a, pL - 1, ScalaRunTime$.MODULE$.array_apply(a, pR - 1));
                ScalaRunTime$.MODULE$.array_update(a, pR - 1, current);
                --pL;
                --pR;
                --iB;
            }
            block8: while (iB - pR > 0) {
                Object current = ScalaRunTime$.MODULE$.array_apply(a, iB - 1);
                int n = ord.compare(current, pivot);
                switch (n) {
                    case 0: {
                        ScalaRunTime$.MODULE$.array_update(a, iB - 1, ScalaRunTime$.MODULE$.array_apply(a, pR));
                        ScalaRunTime$.MODULE$.array_update(a, pR, current);
                        ++pR;
                        continue block8;
                    }
                }
                if (n > 0) {
                    --iB;
                    continue;
                }
                ScalaRunTime$.MODULE$.array_update(a, iB - 1, ScalaRunTime$.MODULE$.array_apply(a, pR));
                ScalaRunTime$.MODULE$.array_update(a, pR, ScalaRunTime$.MODULE$.array_apply(a, pL));
                ScalaRunTime$.MODULE$.array_update(a, pL, current);
                ++iA;
                ++pL;
                ++pR;
            }
            if (iA - i0 < iN - iB) {
                this.inner$1(a, i0, iA, ord);
                i0 = iB;
                continue;
            }
            this.inner$1(a, iB, iN, ord);
            iN = iA;
        }
    }

    private Sorting$() {
    }
}

