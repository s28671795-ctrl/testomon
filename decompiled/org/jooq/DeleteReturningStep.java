/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.DeleteFinalStep;
import org.jooq.DeleteResultStep;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Support;

public interface DeleteReturningStep<R extends Record>
extends DeleteFinalStep<R> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DeleteResultStep<R> returning();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DeleteResultStep<R> returning(SelectFieldOrAsterisk ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DeleteResultStep<R> returning(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DeleteResultStep<Record> returningResult(SelectFieldOrAsterisk ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DeleteResultStep<Record> returningResult(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1> DeleteResultStep<Record1<T1>> returningResult(SelectField<T1> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2> DeleteResultStep<Record2<T1, T2>> returningResult(SelectField<T1> var1, SelectField<T2> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3> DeleteResultStep<Record3<T1, T2, T3>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4> DeleteResultStep<Record4<T1, T2, T3, T4>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5> DeleteResultStep<Record5<T1, T2, T3, T4, T5>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6> DeleteResultStep<Record6<T1, T2, T3, T4, T5, T6>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7> DeleteResultStep<Record7<T1, T2, T3, T4, T5, T6, T7>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8> DeleteResultStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> DeleteResultStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> DeleteResultStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> DeleteResultStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> DeleteResultStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> DeleteResultStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> DeleteResultStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> DeleteResultStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> DeleteResultStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> DeleteResultStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> DeleteResultStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> DeleteResultStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> DeleteResultStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> DeleteResultStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.MARIADB_10_1, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> DeleteResultStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> returningResult(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21, SelectField<T22> var22);
}

