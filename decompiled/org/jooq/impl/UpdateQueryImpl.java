/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.Operator;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.QueryPart;
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
import org.jooq.Replacer;
import org.jooq.Row;
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
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.UpdateQuery;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.AbstractStoreQuery;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldMapForUpdate;
import org.jooq.impl.Keywords;
import org.jooq.impl.NoField;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SortFieldList;
import org.jooq.impl.TableImpl;
import org.jooq.impl.TableList;
import org.jooq.impl.Tools;
import org.jooq.impl.WithImpl;

final class UpdateQueryImpl<R extends Record>
extends AbstractStoreQuery<R, FieldOrRow, FieldOrRowOrSelect>
implements UpdateQuery<R>,
QOM.Update<R> {
    private static final Clause[] CLAUSES = new Clause[]{Clause.UPDATE};
    @Pro
    private static final Set<SQLDialect> FROM_BEFORE = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.INGRES, SQLDialect.TERADATA);
    @Pro
    private static final Set<SQLDialect> REQUIRES_WHERE = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
    @Pro
    private static final Set<SQLDialect> REQUIRE_REPEAT_TARGET_IN_FROM = SQLDialect.supportedBy(SQLDialect.HANA);
    private static final Set<SQLDialect> EMULATE_FROM_WITH_MERGE = SQLDialect.supportedBy(SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> EMULATE_RETURNING_WITH_UPSERT = SQLDialect.supportedBy(SQLDialect.MARIADB_10_5);
    private static final Set<SQLDialect> NO_SUPPORT_LIMIT = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_10, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> NO_SUPPORT_ORDER_BY_LIMIT = SQLDialect.supportedBy(SQLDialect.IGNITE, SQLDialect.MEMSQL);
    static final Set<SQLDialect> NO_SUPPORT_UPDATE_JOIN = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private final FieldMapForUpdate updateMap;
    private final TableList from;
    private final ConditionProviderImpl condition;
    private final SortFieldList orderBy;
    private Field<? extends Number> limit;

    UpdateQueryImpl(Configuration configuration, WithImpl with, Table<R> table) {
        super(configuration, with, table);
        this.updateMap = new FieldMapForUpdate(table, FieldMapForUpdate.SetClause.UPDATE, Clause.UPDATE_SET_ASSIGNMENT);
        this.from = new TableList();
        this.condition = new ConditionProviderImpl();
        this.orderBy = new SortFieldList();
    }

    protected final FieldMapForUpdate getValues() {
        return this.updateMap;
    }

    @Override
    public final void addValues(RowN row, RowN value) {
        this.addValues0((Row)row, value);
    }

    @Override
    public final <T1> void addValues(Row1<T1> row, Row1<T1> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2> void addValues(Row2<T1, T2> row, Row2<T1, T2> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3> void addValues(Row3<T1, T2, T3> row, Row3<T1, T2, T3> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> row, Row4<T1, T2, T3, T4> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> row, Row5<T1, T2, T3, T4, T5> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> row, Row6<T1, T2, T3, T4, T5, T6> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> row, Row7<T1, T2, T3, T4, T5, T6, T7> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row, Row8<T1, T2, T3, T4, T5, T6, T7, T8> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> value) {
        this.addValues0(row, value);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> value) {
        this.addValues0(row, value);
    }

    @Override
    public final void addValues(RowN row, Select<? extends Record> select) {
        this.addValues0((Row)row, select);
    }

    @Override
    public final <T1> void addValues(Row1<T1> row, Select<? extends Record1<T1>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2> void addValues(Row2<T1, T2> row, Select<? extends Record2<T1, T2>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3> void addValues(Row3<T1, T2, T3> row, Select<? extends Record3<T1, T2, T3>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> row, Select<? extends Record4<T1, T2, T3, T4>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> row, Select<? extends Record5<T1, T2, T3, T4, T5>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> row, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> row, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row, Select<? extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row, Select<? extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row, Select<? extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row, Select<? extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row, Select<? extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row, Select<? extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row, Select<? extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row, Select<? extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row, Select<? extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select) {
        this.addValues0(row, select);
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row, Select<? extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select) {
        this.addValues0(row, select);
    }

    final void addValues0(Row row, Row value) {
        this.updateMap.put(row, value);
    }

    final void addValues0(Row row, Select<?> select) {
        this.updateMap.put(row, select);
    }

    @Override
    public final void addValues(Map<?, ?> map) {
        this.updateMap.set(map);
    }

    @Override
    public final void addFrom(Collection<? extends TableLike<?>> f) {
        for (TableLike<?> provider : f) {
            this.from.add(provider.asTable());
        }
    }

    @Override
    public final void addFrom(TableLike<?> f) {
        this.addFrom(Arrays.asList(f));
    }

    @Override
    public final void addFrom(TableLike<?> ... f) {
        this.addFrom(Arrays.asList(f));
    }

    @Override
    public final void addConditions(Collection<? extends Condition> conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Condition conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Condition ... conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition ... conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Override
    public final void addOrderBy(OrderField<?> ... fields) {
        this.addOrderBy(Arrays.asList(fields));
    }

    @Override
    public final void addOrderBy(Collection<? extends OrderField<?>> fields) {
        this.orderBy.addAll(Tools.sortFields(fields));
    }

    @Override
    public final void addLimit(Number l) {
        this.addLimit(DSL.val(l));
    }

    @Override
    public final void addLimit(Field<? extends Number> l) {
        if (l instanceof NoField) {
            return;
        }
        this.limit = l;
    }

    final Condition getWhere() {
        return this.condition.getWhere();
    }

    final boolean hasWhere() {
        return this.condition.hasWhere();
    }

    final TableList getFrom() {
        return this.from;
    }

    @Override
    final void accept0(Context<?> ctx) {
        if (!this.from.isEmpty() && EMULATE_FROM_WITH_MERGE.contains((Object)ctx.dialect())) {
            this.acceptFromAsMerge(ctx);
            return;
        }
        if (!this.returning.isEmpty() && EMULATE_RETURNING_WITH_UPSERT.contains((Object)ctx.dialect()) && this.table instanceof TableImpl && (((TableImpl)this.table).fields.fields.length == 0 || !this.table.getKeys().isEmpty())) {
            this.acceptReturningAsUpsert(ctx);
            return;
        }
        this.updateMap.checkReadonly(ctx);
        if (Tools.anyComputedOnClientStoredFields(ctx.configuration(), this.table)) {
            UpdateQueryImpl<R> u = this.copy(x -> x.updateMap.computeOnClientStoredReplace(ctx));
            u.accept1(ctx);
            return;
        }
        this.accept1(ctx);
    }

    private final void acceptReturningAsUpsert(Context<?> ctx) {
        ctx.visit(DSL.insertInto(this.table).select(DSL.selectFrom(this.table).where(this.hasWhere() ? this.getWhere() : DSL.noCondition()).orderBy(this.orderBy).limit(this.limit != null ? this.limit : DSL.noField(SQLDataType.INTEGER))).onDuplicateKeyUpdate().set(this.updateMap).returning(this.returning));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private final void acceptFromAsMerge(Context<?> ctx) {
        void var4_5;
        Table s2;
        boolean patchSource = true;
        ConditionProviderImpl conditionProviderImpl = this.condition;
        FieldMapForUpdate um = this.updateMap;
        if (this.orderBy.isEmpty() && this.limit == null) {
            if (this.from.size() == 1 && this.from.get(0) instanceof TableImpl) {
                patchSource = false;
                s2 = (Table)this.from.get(0);
            } else {
                s2 = DSL.select(new SelectFieldOrAsterisk[0]).from(this.from).asTable("s");
            }
        } else {
            s2 = DSL.select(this.from.fields()).from(this.from).join(this.table).on((Condition)this.condition).orderBy(this.orderBy).limit(this.limit).asTable("s");
        }
        if (patchSource && ctx.configuration().requireCommercial(() -> "The UPDATE .. FROM to MERGE transformation requires commercial only logic for non-trivial FROM clauses. Please upgrade to the jOOQ Professional Edition or jOOQ Enterprise Edition")) {
            Function<QueryPart, QueryPart> qualify = q -> {
                if (q instanceof TableField) {
                    TableField tf = (TableField)q;
                    return this.from.contains(tf.getTable()) ? Tools.qualify(s2, tf) : tf;
                }
                return q;
            };
            um = new FieldMapForUpdate(this.table, FieldMapForUpdate.SetClause.MERGE, Clause.MERGE_SET_ASSIGNMENT);
            Condition condition = (Condition)this.condition.$replace(qualify);
            for (Map.Entry e : this.updateMap.entrySet()) {
                um.put((FieldOrRow)e.getKey(), (FieldOrRowOrSelect)((FieldOrRowOrSelect)e.getValue()).$replace(qualify));
            }
        }
        ctx.visit(DSL.mergeInto(this.table).using(s2).on((Condition)var4_5).whenMatchedThenUpdate().set(um));
    }

    final UpdateQueryImpl<R> copy(Consumer<? super UpdateQueryImpl<R>> finisher) {
        return this.copy(finisher, this.table);
    }

    final <O extends Record> UpdateQueryImpl<O> copy(Consumer<? super UpdateQueryImpl<O>> finisher, Table<O> t2) {
        UpdateQueryImpl<O> u = new UpdateQueryImpl<O>(this.configuration(), this.with, t2);
        if (!this.returning.isEmpty()) {
            u.setReturning(this.returning);
        }
        u.updateMap.putAll(this.updateMap);
        u.from.addAll(this.from);
        u.condition.setWhere(this.condition.getWhere());
        u.orderBy.addAll(this.orderBy);
        u.limit = this.limit;
        finisher.accept(u);
        return u;
    }

    final void accept1(Context<?> ctx) {
        boolean declareTables = ctx.declareTables();
        ctx.start(Clause.UPDATE_UPDATE).visit(Keywords.K_UPDATE).sql(' ').declareTables(ctx.family() != SQLDialect.SQLSERVER && ctx.family() != SQLDialect.SQLDATAWAREHOUSE).visit(this.table(ctx)).declareTables(declareTables).end(Clause.UPDATE_UPDATE);
        if (FROM_BEFORE.contains((Object)ctx.dialect())) {
            this.acceptFrom(ctx);
        }
        ctx.formatSeparator().start(Clause.UPDATE_SET).visit(Keywords.K_SET).separatorRequired(true).formatIndentStart().formatSeparator().visit(this.updateMap).formatIndentEnd().end(Clause.UPDATE_SET);
        this.toSQLOutput(ctx);
        if (!FROM_BEFORE.contains((Object)ctx.dialect())) {
            this.acceptFrom(ctx);
        }
        if (this.limit != null && NO_SUPPORT_LIMIT.contains((Object)ctx.dialect()) || !this.orderBy.isEmpty() && NO_SUPPORT_ORDER_BY_LIMIT.contains((Object)ctx.dialect())) {
            SelectFieldOrAsterisk[] selectFieldOrAsteriskArray;
            if (this.table().getKeys().isEmpty()) {
                SelectFieldOrAsterisk[] selectFieldOrAsteriskArray2 = new Field[1];
                selectFieldOrAsteriskArray = selectFieldOrAsteriskArray2;
                selectFieldOrAsteriskArray2[0] = this.table().rowid();
            } else {
                selectFieldOrAsteriskArray = (this.table().getPrimaryKey() != null ? this.table().getPrimaryKey() : this.table().getKeys().get(0)).getFieldsArray();
            }
            SelectFieldOrAsterisk[] keyFields = selectFieldOrAsteriskArray;
            ctx.start(Clause.UPDATE_WHERE).formatSeparator().visit(Keywords.K_WHERE).sql(' ');
            if (keyFields.length == 1) {
                ctx.visit(keyFields[0].in(DSL.select(keyFields[0]).from((TableLike<?>)this.table()).where(this.getWhere()).orderBy(this.orderBy).limit(this.limit)));
            } else {
                ctx.visit(DSL.row(keyFields).in(DSL.select(keyFields).from((TableLike<?>)this.table()).where(this.getWhere()).orderBy(this.orderBy).limit(this.limit)));
            }
            ctx.end(Clause.UPDATE_WHERE);
        } else {
            ctx.start(Clause.UPDATE_WHERE);
            if (this.hasWhere()) {
                ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(this.getWhere());
            } else if (REQUIRES_WHERE.contains((Object)ctx.dialect())) {
                ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(DSL.trueCondition());
            }
            ctx.end(Clause.UPDATE_WHERE);
            if (!this.orderBy.isEmpty()) {
                ctx.formatSeparator().visit(Keywords.K_ORDER_BY).sql(' ').visit(this.orderBy);
            }
            if (this.limit != null) {
                ctx.formatSeparator().visit(Keywords.K_LIMIT).sql(' ').visit(this.limit);
            }
        }
        ctx.start(Clause.UPDATE_RETURNING);
        this.toSQLReturning(ctx);
        ctx.end(Clause.UPDATE_RETURNING);
    }

    private final void acceptFrom(Context<?> ctx) {
        Table<?> t2;
        ctx.start(Clause.UPDATE_FROM);
        TableList f = new TableList();
        if (REQUIRE_REPEAT_TARGET_IN_FROM.contains((Object)ctx.dialect()) && !Tools.containsDeclaredTable(this.from, t2 = this.table(ctx))) {
            f.add(t2);
        }
        f.addAll(this.from);
        Select s2 = (Select)Tools.findAny(this.updateMap.values(), v -> v instanceof Select);
        if (s2 != null && FieldMapForUpdate.EMULATE_RVE_SET_QUERY_UPDATE_FROM.contains((Object)ctx.dialect())) {
            f.add(s2.asTable(DSL.table(DSL.name("t")), Tools.fields(s2.getSelect().size())));
        }
        if (!f.isEmpty()) {
            ctx.formatSeparator().visit(Keywords.K_FROM).sql(' ').declareTables(true, c -> c.visit(f));
        }
        ctx.end(Clause.UPDATE_FROM);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final boolean isExecutable() {
        if (!this.condition.hasWhere()) {
            this.executeWithoutWhere("UPDATE without WHERE", SettingsTools.getExecuteUpdateWithoutWhere(this.configuration().settings()));
        }
        return this.updateMap.size() > 0;
    }

    @Override
    @Pro
    final int estimatedRowCount(Scope ctx) {
        return Integer.MAX_VALUE;
    }

    @Override
    public final WithImpl $with() {
        return this.with;
    }

    @Override
    public final Table<R> $table() {
        return this.table;
    }

    @Override
    public final QOM.Update<?> $table(Table<?> newTable) {
        if (this.$table() == newTable) {
            return this;
        }
        return this.copy(d -> {}, newTable);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Table<?>> $from() {
        return QOM.unmodifiable(this.from);
    }

    @Override
    public final QOM.Update<R> $from(Collection<? extends Table<?>> newFrom) {
        return this.copy(d -> {
            d.from.clear();
            d.from.addAll(newFrom);
        });
    }

    @Override
    public final QOM.UnmodifiableMap<? extends FieldOrRow, ? extends FieldOrRowOrSelect> $set() {
        return QOM.unmodifiable(this.updateMap);
    }

    @Override
    public final QOM.Update<R> $set(Map<? extends FieldOrRow, ? extends FieldOrRowOrSelect> newSet) {
        return this.copy(u -> {
            u.updateMap.clear();
            u.updateMap.putAll(newSet);
        });
    }

    @Override
    public final Condition $where() {
        return this.condition.getWhereOrNull();
    }

    @Override
    public final QOM.Update<R> $where(Condition newWhere) {
        if (this.$where() == newWhere) {
            return this;
        }
        return this.copy(u -> u.condition.setWhere(newWhere));
    }

    @Override
    public final QOM.UnmodifiableList<? extends SortField<?>> $orderBy() {
        return QOM.unmodifiable(this.orderBy);
    }

    @Override
    public final QOM.Update<R> $orderBy(Collection<? extends SortField<?>> newOrderBy) {
        return this.copy(u -> {
            u.orderBy.clear();
            u.orderBy.addAll(newOrderBy);
        });
    }

    @Override
    public final Field<? extends Number> $limit() {
        return this.limit;
    }

    @Override
    public final QOM.Update<R> $limit(Field<? extends Number> newLimit) {
        if (this.$limit() == newLimit) {
            return this;
        }
        return this.copy(s2 -> {
            s2.limit = newLimit;
        });
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$with(), this.$table(), this.$from(), this.$set(), this.$where(), this.$orderBy(), this.$limit());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$with(), this.$table(), this.$from(), this.$set(), this.$where(), this.$orderBy(), this.$limit(), (cte, t2, f, s2, w, o, l) -> {
            UpdateQueryImpl<R> r = new UpdateQueryImpl<R>(this.configuration(), (WithImpl)cte, t2);
            r.from.addAll(f);
            r.updateMap.putAll(s2);
            r.condition.addConditions(ConditionProviderImpl.extractCondition(w));
            r.orderBy.addAll(o);
            r.limit = l;
            r.setReturning(this.returning);
            return r;
        }, replacer);
    }
}

