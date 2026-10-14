/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Operator;
import org.jooq.OrderField;
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
import org.jooq.Result;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.StoreQuery;
import org.jooq.Support;
import org.jooq.TableLike;
import org.jooq.Update;

public interface UpdateQuery<R extends Record>
extends StoreQuery<R>,
ConditionProvider,
Update<R> {
    @Support
    public void addValues(RowN var1, RowN var2);

    @Support
    public <T1> void addValues(Row1<T1> var1, Row1<T1> var2);

    @Support
    public <T1, T2> void addValues(Row2<T1, T2> var1, Row2<T1, T2> var2);

    @Support
    public <T1, T2, T3> void addValues(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

    @Support
    public <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> var1, Row4<T1, T2, T3, T4> var2);

    @Support
    public <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> var1, Row5<T1, T2, T3, T4, T5> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> var1, Row6<T1, T2, T3, T4, T5, T6> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Row7<T1, T2, T3, T4, T5, T6, T7> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Row8<T1, T2, T3, T4, T5, T6, T7, T8> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var1, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var1, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var1, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var1, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var1, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var1, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var1, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var1, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var1, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var1, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var1, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var2);

    @Support
    public void addValues(RowN var1, Select<? extends Record> var2);

    @Support
    public <T1> void addValues(Row1<T1> var1, Select<? extends Record1<T1>> var2);

    @Support
    public <T1, T2> void addValues(Row2<T1, T2> var1, Select<? extends Record2<T1, T2>> var2);

    @Support
    public <T1, T2, T3> void addValues(Row3<T1, T2, T3> var1, Select<? extends Record3<T1, T2, T3>> var2);

    @Support
    public <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> var1, Select<? extends Record4<T1, T2, T3, T4>> var2);

    @Support
    public <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> var1, Select<? extends Record5<T1, T2, T3, T4, T5>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> var1, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> var1, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> var1, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> var1, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> var1, Select<? extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> var1, Select<? extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> var1, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> var1, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> var1, Select<? extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> var1, Select<? extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> var1, Select<? extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> var1, Select<? extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> var1, Select<? extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> var1, Select<? extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> var1, Select<? extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> var2);

    @Support
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> var1, Select<? extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> var2);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    public void addFrom(TableLike<?> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    public void addFrom(TableLike<?> ... var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE_3_38, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    public void addFrom(Collection<? extends TableLike<?>> var1);

    @Override
    @Support
    public void addConditions(Condition var1);

    @Override
    @Support
    public void addConditions(Condition ... var1);

    @Override
    @Support
    public void addConditions(Collection<? extends Condition> var1);

    @Override
    @Support
    public void addConditions(Operator var1, Condition var2);

    @Override
    @Support
    public void addConditions(Operator var1, Condition ... var2);

    @Override
    @Support
    public void addConditions(Operator var1, Collection<? extends Condition> var2);

    @Support
    public void addOrderBy(OrderField<?> ... var1);

    @Support
    public void addOrderBy(Collection<? extends OrderField<?>> var1);

    @Support
    public void addLimit(Number var1);

    @Support
    public void addLimit(Field<? extends Number> var1);

    @Override
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setReturning();

    @Override
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setReturning(Identity<R, ?> var1);

    @Override
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setReturning(SelectFieldOrAsterisk ... var1);

    @Override
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    public void setReturning(Collection<? extends SelectFieldOrAsterisk> var1);

    @Override
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @Nullable
    public R getReturnedRecord();

    @Override
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Result<R> getReturnedRecords();
}

