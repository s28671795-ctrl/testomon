/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.lang.reflect.Array;
import java.util.Arrays;
import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.immutable.Iterable;

public final class VectorStatics$ {
    public static final VectorStatics$ MODULE$ = new VectorStatics$();
    private static final Object[] empty1 = new Object[0];
    private static final Object[][] empty2 = new Object[0][];
    private static final Object[][][] empty3 = new Object[0][][];
    private static final Object[][][][] empty4 = new Object[0][][][];
    private static final Object[][][][][] empty5 = new Object[0][][][][];
    private static final Object[][][][][][] empty6 = new Object[0][][][][][];

    public final Object[] copyAppend1(Object[] a, Object elem) {
        int alen = a.length;
        Object[] ac = new Object[alen + 1];
        System.arraycopy(a, 0, ac, 0, alen);
        ac[alen] = elem;
        return ac;
    }

    public final <T> T[] copyAppend(T[] a, T elem) {
        T[] ac = Arrays.copyOf(a, a.length + 1);
        ac[ac.length - 1] = elem;
        return ac;
    }

    public final Object[] copyPrepend1(Object elem, Object[] a) {
        Object[] ac = new Object[a.length + 1];
        System.arraycopy(a, 0, ac, 1, a.length);
        ac[0] = elem;
        return ac;
    }

    public final <T> T[] copyPrepend(T elem, T[] a) {
        Object[] ac = (Object[])Array.newInstance(a.getClass().getComponentType(), a.length + 1);
        System.arraycopy(a, 0, ac, 1, a.length);
        ac[0] = elem;
        return ac;
    }

    public final Object[] empty1() {
        return empty1;
    }

    public final Object[][] empty2() {
        return empty2;
    }

    public final Object[][][] empty3() {
        return empty3;
    }

    public final Object[][][][] empty4() {
        return empty4;
    }

    public final Object[][][][][] empty5() {
        return empty5;
    }

    public final Object[][][][][][] empty6() {
        return empty6;
    }

    public final <T, A, U> void foreachRec(int level, T[] a, Function1<A, U> f) {
        int i;
        int len = a.length;
        if (level == 0) {
            for (i = 0; i < len; ++i) {
                f.apply(a[i]);
            }
            return;
        }
        int l = level - 1;
        while (i < len) {
            this.foreachRec(l, (Object[])a[i], f);
            ++i;
        }
    }

    public final <A, B> Object[] mapElems1(Object[] a, Function1<A, B> f) {
        for (int i = 0; i < a.length; ++i) {
            Object v1 = a[i];
            B v2 = f.apply(v1);
            if (v1 == v2) continue;
            Object[] mapElems1Rest_ac = new Object[a.length];
            if (i > 0) {
                System.arraycopy(a, 0, mapElems1Rest_ac, 0, i);
            }
            mapElems1Rest_ac[i] = v2;
            for (int mapElems1Rest_i = i + 1; mapElems1Rest_i < a.length; ++mapElems1Rest_i) {
                mapElems1Rest_ac[mapElems1Rest_i] = f.apply(a[mapElems1Rest_i]);
            }
            return mapElems1Rest_ac;
        }
        return a;
    }

    public final <A, B> Object[] mapElems1Rest(Object[] a, Function1<A, B> f, int at, Object v2) {
        Object[] ac = new Object[a.length];
        if (at > 0) {
            System.arraycopy(a, 0, ac, 0, at);
        }
        ac[at] = v2;
        for (int i = at + 1; i < a.length; ++i) {
            ac[i] = f.apply(a[i]);
        }
        return ac;
    }

    /*
     * WARNING - void declaration
     */
    public final <A, B, T> T[] mapElems(int n, T[] a, Function1<A, B> f) {
        if (n == 1) {
            for (int mapElems1_i = 0; mapElems1_i < a.length; ++mapElems1_i) {
                T mapElems1_v1 = a[mapElems1_i];
                B mapElems1_v2 = f.apply(mapElems1_v1);
                if (mapElems1_v1 == mapElems1_v2) continue;
                Object[] mapElems1_mapElems1Rest_ac = new Object[a.length];
                if (mapElems1_i > 0) {
                    System.arraycopy(a, 0, mapElems1_mapElems1Rest_ac, 0, mapElems1_i);
                }
                mapElems1_mapElems1Rest_ac[mapElems1_i] = mapElems1_v2;
                for (int mapElems1_mapElems1Rest_i = mapElems1_i + 1; mapElems1_mapElems1Rest_i < a.length; ++mapElems1_mapElems1Rest_i) {
                    mapElems1_mapElems1Rest_ac[mapElems1_mapElems1Rest_i] = f.apply(a[mapElems1_mapElems1Rest_i]);
                }
                return mapElems1_mapElems1Rest_ac;
            }
            return a;
        }
        for (int i = 0; i < a.length; ++i) {
            T v1 = a[i];
            Object[] v2 = this.mapElems(n - 1, (Object[])v1, f);
            if (v1 == v2) continue;
            Object[] mapElemsRest_ac = (Object[])Array.newInstance(a.getClass().getComponentType(), a.length);
            if (i > 0) {
                System.arraycopy(a, 0, mapElemsRest_ac, 0, i);
            }
            mapElemsRest_ac[i] = v2;
            for (int mapElemsRest_i = i + 1; mapElemsRest_i < a.length; ++mapElemsRest_i) {
                Object var21_21;
                Object var20_20;
                Object var22_22;
                Object[] objectArray;
                Object[] objectArray2;
                block14: {
                    void mapElemsRest_mapElems_a;
                    objectArray2 = (Object[])a[mapElemsRest_i];
                    int mapElemsRest_mapElems_n = n - 1;
                    if (mapElemsRest_mapElems_n == 1) {
                        block13: {
                            for (int mapElemsRest_mapElems_mapElems1_i = 0; mapElemsRest_mapElems_mapElems1_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_mapElems1_i) {
                                void mapElemsRest_mapElems_mapElems1_v1 = mapElemsRest_mapElems_a[mapElemsRest_mapElems_mapElems1_i];
                                B mapElemsRest_mapElems_mapElems1_v2 = f.apply(mapElemsRest_mapElems_mapElems1_v1);
                                if (mapElemsRest_mapElems_mapElems1_v1 == mapElemsRest_mapElems_mapElems1_v2) continue;
                                Object[] mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElemsRest_mapElems_a).length];
                                if (mapElemsRest_mapElems_mapElems1_i > 0) {
                                    System.arraycopy(mapElemsRest_mapElems_a, 0, mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac, 0, mapElemsRest_mapElems_mapElems1_i);
                                }
                                mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac[mapElemsRest_mapElems_mapElems1_i] = mapElemsRest_mapElems_mapElems1_v2;
                                for (int mapElemsRest_mapElems_mapElems1_mapElems1Rest_i = mapElemsRest_mapElems_mapElems1_i + 1; mapElemsRest_mapElems_mapElems1_mapElems1Rest_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_mapElems1_mapElems1Rest_i) {
                                    mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac[mapElemsRest_mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElemsRest_mapElems_a[mapElemsRest_mapElems_mapElems1_mapElems1Rest_i]);
                                }
                                objectArray = mapElemsRest_mapElems_mapElems1_mapElems1Rest_ac;
                                var22_22 = null;
                                break block13;
                            }
                            objectArray = mapElemsRest_mapElems_a;
                        }
                        var20_20 = null;
                        var21_21 = null;
                        var22_22 = null;
                    } else {
                        for (int mapElemsRest_mapElems_i = 0; mapElemsRest_mapElems_i < ((void)mapElemsRest_mapElems_a).length; ++mapElemsRest_mapElems_i) {
                            void mapElemsRest_mapElems_v1 = mapElemsRest_mapElems_a[mapElemsRest_mapElems_i];
                            Object[] mapElemsRest_mapElems_v2 = this.mapElems(mapElemsRest_mapElems_n - 1, (Object[])mapElemsRest_mapElems_v1, f);
                            if (mapElemsRest_mapElems_v1 == mapElemsRest_mapElems_v2) continue;
                            objectArray = this.mapElemsRest(mapElemsRest_mapElems_n, (T[])mapElemsRest_mapElems_a, f, mapElemsRest_mapElems_i, mapElemsRest_mapElems_v2);
                            break block14;
                        }
                        objectArray = mapElemsRest_mapElems_a;
                    }
                }
                objectArray2 = null;
                Object var17_17 = null;
                Object var18_18 = null;
                var20_20 = null;
                var21_21 = null;
                var22_22 = null;
                mapElemsRest_ac[mapElemsRest_i] = objectArray;
            }
            return mapElemsRest_ac;
        }
        return a;
    }

    /*
     * WARNING - void declaration
     */
    public final <A, B, T> T[] mapElemsRest(int n, T[] a, Function1<A, B> f, int at, Object v2) {
        Object[] ac = (Object[])Array.newInstance(a.getClass().getComponentType(), a.length);
        if (at > 0) {
            System.arraycopy(a, 0, ac, 0, at);
        }
        ac[at] = v2;
        for (int i = at + 1; i < a.length; ++i) {
            Object var15_15;
            Object var14_14;
            Object var16_16;
            Object[] objectArray;
            Object[] objectArray2;
            block9: {
                void mapElems_a;
                objectArray2 = (Object[])a[i];
                int mapElems_n = n - 1;
                if (mapElems_n == 1) {
                    block8: {
                        for (int mapElems_mapElems1_i = 0; mapElems_mapElems1_i < ((void)mapElems_a).length; ++mapElems_mapElems1_i) {
                            void mapElems_mapElems1_v1 = mapElems_a[mapElems_mapElems1_i];
                            B mapElems_mapElems1_v2 = f.apply(mapElems_mapElems1_v1);
                            if (mapElems_mapElems1_v1 == mapElems_mapElems1_v2) continue;
                            Object[] mapElems_mapElems1_mapElems1Rest_ac = new Object[((void)mapElems_a).length];
                            if (mapElems_mapElems1_i > 0) {
                                System.arraycopy(mapElems_a, 0, mapElems_mapElems1_mapElems1Rest_ac, 0, mapElems_mapElems1_i);
                            }
                            mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_i] = mapElems_mapElems1_v2;
                            for (int mapElems_mapElems1_mapElems1Rest_i = mapElems_mapElems1_i + 1; mapElems_mapElems1_mapElems1Rest_i < ((void)mapElems_a).length; ++mapElems_mapElems1_mapElems1Rest_i) {
                                mapElems_mapElems1_mapElems1Rest_ac[mapElems_mapElems1_mapElems1Rest_i] = f.apply(mapElems_a[mapElems_mapElems1_mapElems1Rest_i]);
                            }
                            objectArray = mapElems_mapElems1_mapElems1Rest_ac;
                            var16_16 = null;
                            break block8;
                        }
                        objectArray = mapElems_a;
                    }
                    var14_14 = null;
                    var15_15 = null;
                    var16_16 = null;
                } else {
                    for (int mapElems_i = 0; mapElems_i < ((void)mapElems_a).length; ++mapElems_i) {
                        void mapElems_v1 = mapElems_a[mapElems_i];
                        Object[] mapElems_v2 = this.mapElems(mapElems_n - 1, (Object[])mapElems_v1, f);
                        if (mapElems_v1 == mapElems_v2) continue;
                        objectArray = this.mapElemsRest(mapElems_n, (T[])mapElems_a, f, mapElems_i, mapElems_v2);
                        break block9;
                    }
                    objectArray = mapElems_a;
                }
            }
            objectArray2 = null;
            Object var11_11 = null;
            Object var12_12 = null;
            var14_14 = null;
            var15_15 = null;
            var16_16 = null;
            ac[i] = objectArray;
        }
        return ac;
    }

    public final Object[] prepend1IfSpace(Object[] prefix1, IterableOnce<?> xs) {
        if (xs instanceof Iterable) {
            Iterable iterable = (Iterable)xs;
            if (iterable.sizeCompare(32 - prefix1.length) <= 0) {
                int n = iterable.size();
                switch (n) {
                    case 0: {
                        return null;
                    }
                    case 1: {
                        return this.copyPrepend(iterable.head(), prefix1);
                    }
                }
                Object[] prefix1b = new Object[prefix1.length + n];
                System.arraycopy(prefix1, 0, prefix1b, n, prefix1.length);
                iterable.copyToArray(prefix1b, 0);
                return prefix1b;
            }
            return null;
        }
        int s2 = xs.knownSize();
        if (s2 > 0 && s2 <= 32 - prefix1.length) {
            Object[] prefix1b = new Object[prefix1.length + s2];
            System.arraycopy(prefix1, 0, prefix1b, s2, prefix1.length);
            xs.iterator().copyToArray(prefix1b, 0);
            return prefix1b;
        }
        return null;
    }

    public final Object[] append1IfSpace(Object[] suffix1, IterableOnce<?> xs) {
        if (xs instanceof Iterable) {
            Iterable iterable = (Iterable)xs;
            if (iterable.sizeCompare(32 - suffix1.length) <= 0) {
                int n = iterable.size();
                switch (n) {
                    case 0: {
                        return null;
                    }
                    case 1: {
                        return this.copyAppend(suffix1, iterable.head());
                    }
                }
                Object[] suffix1b = Arrays.copyOf(suffix1, suffix1.length + n);
                iterable.copyToArray(suffix1b, suffix1.length);
                return suffix1b;
            }
            return null;
        }
        int s2 = xs.knownSize();
        if (s2 > 0 && s2 <= 32 - suffix1.length) {
            Object[] suffix1b = Arrays.copyOf(suffix1, suffix1.length + s2);
            xs.iterator().copyToArray(suffix1b, suffix1.length);
            return suffix1b;
        }
        return null;
    }

    private VectorStatics$() {
    }
}

