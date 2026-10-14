/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Function1;
import org.jooq.Function10;
import org.jooq.Function11;
import org.jooq.Function12;
import org.jooq.Function13;
import org.jooq.Function14;
import org.jooq.Function15;
import org.jooq.Function16;
import org.jooq.Function17;
import org.jooq.Function18;
import org.jooq.Function19;
import org.jooq.Function2;
import org.jooq.Function20;
import org.jooq.Function21;
import org.jooq.Function22;
import org.jooq.Function3;
import org.jooq.Function4;
import org.jooq.Function5;
import org.jooq.Function6;
import org.jooq.Function7;
import org.jooq.Function8;
import org.jooq.Function9;

public final class Functions {
    @NotNull
    public static final <T1, R> Function1<T1, R> nullOnAllNull(Function1<? super T1, ? extends R> function) {
        return t1 -> t1 == null ? null : function.apply((Object)t1);
    }

    @NotNull
    public static final <T1, T2, R> Function2<T1, T2, R> nullOnAllNull(Function2<? super T1, ? super T2, ? extends R> function) {
        return (t1, t2) -> t1 == null && t2 == null ? null : function.apply((Object)t1, (Object)t2);
    }

    @NotNull
    public static final <T1, T2, T3, R> Function3<T1, T2, T3, R> nullOnAllNull(Function3<? super T1, ? super T2, ? super T3, ? extends R> function) {
        return (t1, t2, t3) -> t1 == null && t2 == null && t3 == null ? null : function.apply(t1, t2, t3);
    }

    @NotNull
    public static final <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R> nullOnAllNull(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> function) {
        return (t1, t2, t3, t4) -> t1 == null && t2 == null && t3 == null && t4 == null ? null : function.apply(t1, t2, t3, t4);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R> nullOnAllNull(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> function) {
        return (t1, t2, t3, t4, t5) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null ? null : function.apply(t1, t2, t3, t4, t5);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, R> Function6<T1, T2, T3, T4, T5, T6, R> nullOnAllNull(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null ? null : function.apply(t1, t2, t3, t4, t5, t6);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, R> Function7<T1, T2, T3, T4, T5, T6, T7, R> nullOnAllNull(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, R> Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> nullOnAllNull(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> nullOnAllNull(Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> nullOnAllNull(Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> nullOnAllNull(Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> nullOnAllNull(Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> nullOnAllNull(Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> nullOnAllNull(Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> nullOnAllNull(Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null && t15 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> nullOnAllNull(Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null && t15 == null && t16 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R> Function17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R> nullOnAllNull(Function17<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null && t15 == null && t16 == null && t17 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R> Function18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R> nullOnAllNull(Function18<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null && t15 == null && t16 == null && t17 == null && t18 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R> Function19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R> nullOnAllNull(Function19<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null && t15 == null && t16 == null && t17 == null && t18 == null && t19 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R> Function20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R> nullOnAllNull(Function20<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null && t15 == null && t16 == null && t17 == null && t18 == null && t19 == null && t20 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R> Function21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R> nullOnAllNull(Function21<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? super T21, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null && t15 == null && t16 == null && t17 == null && t18 == null && t19 == null && t20 == null && t21 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R> Function22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R> nullOnAllNull(Function22<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? super T21, ? super T22, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22) -> t1 == null && t2 == null && t3 == null && t4 == null && t5 == null && t6 == null && t7 == null && t8 == null && t9 == null && t10 == null && t11 == null && t12 == null && t13 == null && t14 == null && t15 == null && t16 == null && t17 == null && t18 == null && t19 == null && t20 == null && t21 == null && t22 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
    }

    @NotNull
    public static final <T1, R> Function1<T1, R> nullOnAnyNull(Function1<? super T1, ? extends R> function) {
        return t1 -> t1 == null ? null : function.apply((Object)t1);
    }

    @NotNull
    public static final <T1, T2, R> Function2<T1, T2, R> nullOnAnyNull(Function2<? super T1, ? super T2, ? extends R> function) {
        return (t1, t2) -> t1 == null || t2 == null ? null : function.apply((Object)t1, (Object)t2);
    }

    @NotNull
    public static final <T1, T2, T3, R> Function3<T1, T2, T3, R> nullOnAnyNull(Function3<? super T1, ? super T2, ? super T3, ? extends R> function) {
        return (t1, t2, t3) -> t1 == null || t2 == null || t3 == null ? null : function.apply(t1, t2, t3);
    }

    @NotNull
    public static final <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R> nullOnAnyNull(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> function) {
        return (t1, t2, t3, t4) -> t1 == null || t2 == null || t3 == null || t4 == null ? null : function.apply(t1, t2, t3, t4);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R> nullOnAnyNull(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> function) {
        return (t1, t2, t3, t4, t5) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null ? null : function.apply(t1, t2, t3, t4, t5);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, R> Function6<T1, T2, T3, T4, T5, T6, R> nullOnAnyNull(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null ? null : function.apply(t1, t2, t3, t4, t5, t6);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, R> Function7<T1, T2, T3, T4, T5, T6, T7, R> nullOnAnyNull(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, R> Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> nullOnAnyNull(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> nullOnAnyNull(Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> nullOnAnyNull(Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R> nullOnAnyNull(Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> nullOnAnyNull(Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> nullOnAnyNull(Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> nullOnAnyNull(Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> nullOnAnyNull(Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null || t15 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> nullOnAnyNull(Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null || t15 == null || t16 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R> Function17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R> nullOnAnyNull(Function17<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null || t15 == null || t16 == null || t17 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R> Function18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R> nullOnAnyNull(Function18<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null || t15 == null || t16 == null || t17 == null || t18 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R> Function19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R> nullOnAnyNull(Function19<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null || t15 == null || t16 == null || t17 == null || t18 == null || t19 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R> Function20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R> nullOnAnyNull(Function20<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null || t15 == null || t16 == null || t17 == null || t18 == null || t19 == null || t20 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R> Function21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R> nullOnAnyNull(Function21<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? super T21, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null || t15 == null || t16 == null || t17 == null || t18 == null || t19 == null || t20 == null || t21 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
    }

    @NotNull
    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R> Function22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R> nullOnAnyNull(Function22<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? super T21, ? super T22, ? extends R> function) {
        return (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22) -> t1 == null || t2 == null || t3 == null || t4 == null || t5 == null || t6 == null || t7 == null || t8 == null || t9 == null || t10 == null || t11 == null || t12 == null || t13 == null || t14 == null || t15 == null || t16 == null || t17 == null || t18 == null || t19 == null || t20 == null || t21 == null || t22 == null ? null : function.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
    }

    private Functions() {
    }
}

