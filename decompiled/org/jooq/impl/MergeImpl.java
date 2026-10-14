/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeMatchedDeleteStep;
import org.jooq.MergeMatchedSetMoreStep;
import org.jooq.MergeMatchedThenStep;
import org.jooq.MergeNotMatchedSetMoreStep;
import org.jooq.MergeNotMatchedValuesStep1;
import org.jooq.MergeNotMatchedValuesStep10;
import org.jooq.MergeNotMatchedValuesStep11;
import org.jooq.MergeNotMatchedValuesStep12;
import org.jooq.MergeNotMatchedValuesStep13;
import org.jooq.MergeNotMatchedValuesStep14;
import org.jooq.MergeNotMatchedValuesStep15;
import org.jooq.MergeNotMatchedValuesStep16;
import org.jooq.MergeNotMatchedValuesStep17;
import org.jooq.MergeNotMatchedValuesStep18;
import org.jooq.MergeNotMatchedValuesStep19;
import org.jooq.MergeNotMatchedValuesStep2;
import org.jooq.MergeNotMatchedValuesStep20;
import org.jooq.MergeNotMatchedValuesStep21;
import org.jooq.MergeNotMatchedValuesStep22;
import org.jooq.MergeNotMatchedValuesStep3;
import org.jooq.MergeNotMatchedValuesStep4;
import org.jooq.MergeNotMatchedValuesStep5;
import org.jooq.MergeNotMatchedValuesStep6;
import org.jooq.MergeNotMatchedValuesStep7;
import org.jooq.MergeNotMatchedValuesStep8;
import org.jooq.MergeNotMatchedValuesStep9;
import org.jooq.MergeNotMatchedValuesStepN;
import org.jooq.MergeOnConditionStep;
import org.jooq.MergeOnStep;
import org.jooq.MergeUsingStep;
import org.jooq.Operator;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.UniqueKey;
import org.jooq.conf.WriteIfReadonly;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractDMLQuery;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.CaseSearched;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Dual;
import org.jooq.impl.FieldMapForUpdate;
import org.jooq.impl.FieldMapsForInsert;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Internal;
import org.jooq.impl.Keywords;
import org.jooq.impl.NoCondition;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;
import org.jooq.impl.WithImpl;
import org.jooq.tools.StringUtils;

final class MergeImpl<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>
extends AbstractRowCountQuery
implements QOM.UNotYetImplemented,
MergeUsingStep<R>,
MergeKeyStep1<R, T1>,
MergeKeyStep2<R, T1, T2>,
MergeKeyStep3<R, T1, T2, T3>,
MergeKeyStep4<R, T1, T2, T3, T4>,
MergeKeyStep5<R, T1, T2, T3, T4, T5>,
MergeKeyStep6<R, T1, T2, T3, T4, T5, T6>,
MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7>,
MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>,
MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>,
MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>,
MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>,
MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>,
MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>,
MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>,
MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>,
MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>,
MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>,
MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>,
MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>,
MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>,
MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>,
MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>,
MergeOnStep<R>,
MergeOnConditionStep<R>,
MergeMatchedSetMoreStep<R>,
MergeMatchedThenStep<R>,
MergeNotMatchedSetMoreStep<R>,
MergeNotMatchedValuesStep1<R, T1>,
MergeNotMatchedValuesStep2<R, T1, T2>,
MergeNotMatchedValuesStep3<R, T1, T2, T3>,
MergeNotMatchedValuesStep4<R, T1, T2, T3, T4>,
MergeNotMatchedValuesStep5<R, T1, T2, T3, T4, T5>,
MergeNotMatchedValuesStep6<R, T1, T2, T3, T4, T5, T6>,
MergeNotMatchedValuesStep7<R, T1, T2, T3, T4, T5, T6, T7>,
MergeNotMatchedValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>,
MergeNotMatchedValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>,
MergeNotMatchedValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>,
MergeNotMatchedValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>,
MergeNotMatchedValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>,
MergeNotMatchedValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>,
MergeNotMatchedValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>,
MergeNotMatchedValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>,
MergeNotMatchedValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>,
MergeNotMatchedValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>,
MergeNotMatchedValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>,
MergeNotMatchedValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>,
MergeNotMatchedValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>,
MergeNotMatchedValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>,
MergeNotMatchedValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>,
MergeNotMatchedValuesStepN<R> {
    private static final Clause[] CLAUSES = new Clause[]{Clause.MERGE};
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_AND = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.ORACLE, SQLDialect.TERADATA);
    private static final Set<SQLDialect> NO_SUPPORT_MULTI = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SQLSERVER, SQLDialect.TERADATA);
    private static final Set<SQLDialect> REQUIRE_NEGATION = SQLDialect.supportedBy(SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_CONDITION_AFTER_NO_CONDITION = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER);
    private final WithImpl with;
    private final Table<R> table;
    private final ConditionProviderImpl on;
    private TableLike<?> using;
    private boolean usingDual;
    private boolean matchedClause;
    private final List<MatchedClause> matched;
    private boolean notMatchedClause;
    private final List<NotMatchedClause> notMatched;
    private boolean upsertStyle;
    private QueryPartList<Field<?>> upsertFields;
    private QueryPartList<Field<?>> upsertKeys;
    private QueryPartList<Field<?>> upsertValues;
    private Select<?> upsertSelect;

    MergeImpl(Configuration configuration, WithImpl with, Table<R> table) {
        this(configuration, with, table, null);
    }

    MergeImpl(Configuration configuration, WithImpl with, Table<R> table, Collection<? extends Field<?>> fields) {
        super(configuration);
        this.with = with;
        this.table = table;
        this.on = new ConditionProviderImpl();
        this.matched = new ArrayList<MatchedClause>();
        this.notMatched = new ArrayList<NotMatchedClause>();
        if (fields != null) {
            this.columns((Collection)fields);
        }
    }

    final Table<R> table() {
        return this.table;
    }

    final QueryPartList<Field<?>> getUpsertFields() {
        if (this.upsertFields == null) {
            this.upsertFields = new QueryPartList((QueryPart[])this.table.fields());
        }
        return this.upsertFields;
    }

    final QueryPartList<Field<?>> getUpsertKeys() {
        if (this.upsertKeys == null) {
            this.upsertKeys = new QueryPartList();
        }
        return this.upsertKeys;
    }

    final QueryPartList<Field<?>> getUpsertValues() {
        if (this.upsertValues == null) {
            this.upsertValues = new QueryPartList();
        }
        return this.upsertValues;
    }

    final MatchedClause getLastMatched() {
        return this.matched.get(this.matched.size() - 1);
    }

    final NotMatchedClause getLastNotMatched() {
        return this.notMatched.get(this.notMatched.size() - 1);
    }

    public final MergeImpl columns(Field<?> ... fields) {
        return this.columns(Arrays.asList(fields));
    }

    public final MergeImpl columns(Collection<? extends Field<?>> fields) {
        this.upsertStyle = true;
        this.upsertFields = new QueryPartList((Iterable<Field<?>>)fields);
        return this;
    }

    public final <T1> MergeImpl columns(Field<T1> field1) {
        return this.columns(Arrays.asList(field1));
    }

    public final <T1, T2> MergeImpl columns(Field<T1> field1, Field<T2> field2) {
        return this.columns(Arrays.asList(field1, field2));
    }

    public final <T1, T2, T3> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return this.columns(Arrays.asList(field1, field2, field3));
    }

    public final <T1, T2, T3, T4> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return this.columns(Arrays.asList(field1, field2, field3, field4));
    }

    public final <T1, T2, T3, T4, T5> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5));
    }

    public final <T1, T2, T3, T4, T5, T6> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6));
    }

    public final <T1, T2, T3, T4, T5, T6, T7> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeImpl columns(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return this.columns(Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
    }

    public final MergeImpl select(Select select) {
        this.upsertStyle = true;
        this.upsertSelect = select;
        return this;
    }

    public final MergeImpl key(Field<?> ... k) {
        return this.key(Arrays.asList(k));
    }

    public final MergeImpl key(Collection<? extends Field<?>> keys2) {
        this.upsertStyle = true;
        this.getUpsertKeys().addAll((Collection<Field<?>>)keys2);
        return this;
    }

    public final MergeImpl values(T1 value1) {
        return this.values(new Object[]{value1});
    }

    public final MergeImpl values(T1 value1, T2 value2) {
        return this.values(new Object[]{value1, value2});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3) {
        return this.values(new Object[]{value1, value2, value3});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4) {
        return this.values(new Object[]{value1, value2, value3, value4});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5) {
        return this.values(new Object[]{value1, value2, value3, value4, value5});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20, T21 value21) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21});
    }

    public final MergeImpl values(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, T7 value7, T8 value8, T9 value9, T10 value10, T11 value11, T12 value12, T13 value13, T14 value14, T15 value15, T16 value16, T17 value17, T18 value18, T19 value19, T20 value20, T21 value21, T22 value22) {
        return this.values(new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22});
    }

    public final MergeImpl values(Field<T1> value1) {
        return this.values(new Field[]{value1});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2) {
        return this.values(new Field[]{value1, value2});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3) {
        return this.values(new Field[]{value1, value2, value3});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4) {
        return this.values(new Field[]{value1, value2, value3, value4});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5) {
        return this.values(new Field[]{value1, value2, value3, value4, value5});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20, Field<T21> value21) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21});
    }

    public final MergeImpl values(Field<T1> value1, Field<T2> value2, Field<T3> value3, Field<T4> value4, Field<T5> value5, Field<T6> value6, Field<T7> value7, Field<T8> value8, Field<T9> value9, Field<T10> value10, Field<T11> value11, Field<T12> value12, Field<T13> value13, Field<T14> value14, Field<T15> value15, Field<T16> value16, Field<T17> value17, Field<T18> value18, Field<T19> value19, Field<T20> value20, Field<T21> value21, Field<T22> value22) {
        return this.values(new Field[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17, value18, value19, value20, value21, value22});
    }

    public final MergeImpl values(Object ... values2) {
        if (this.using == null && !this.usingDual) {
            this.upsertStyle = true;
            this.getUpsertValues().addAll((Collection<Field<?>>)Tools.fields(values2, this.getUpsertFields().toArray((E[])Tools.EMPTY_FIELD)));
        } else {
            this.getLastNotMatched().insertMap.set(Tools.fields(values2, this.getLastNotMatched().insertMap.values.keySet().toArray(Tools.EMPTY_FIELD)));
        }
        return this;
    }

    public final MergeImpl values(Field<?> ... values2) {
        return this.values((Object[])values2);
    }

    public final MergeImpl values(Collection<?> values2) {
        return this.values(values2.toArray());
    }

    public final MergeImpl using(TableLike<?> u) {
        this.using = u;
        return this;
    }

    public final MergeImpl usingDual() {
        this.usingDual = true;
        return this;
    }

    public final MergeImpl on(Condition conditions) {
        this.on.addConditions(conditions);
        return this;
    }

    public final MergeImpl on(Condition ... conditions) {
        this.on.addConditions(conditions);
        return this;
    }

    @Override
    public final MergeOnConditionStep<R> on(Field<Boolean> condition) {
        return this.on(DSL.condition(condition));
    }

    public final MergeImpl on(SQL sql) {
        return this.on(DSL.condition(sql));
    }

    public final MergeImpl on(String sql) {
        return this.on(DSL.condition(sql));
    }

    public final MergeImpl on(String sql, Object ... bindings) {
        return this.on(DSL.condition(sql, bindings));
    }

    public final MergeImpl on(String sql, QueryPart ... parts) {
        return this.on(DSL.condition(sql, parts));
    }

    public final MergeImpl and(Condition condition) {
        this.on.addConditions(condition);
        return this;
    }

    public final MergeImpl and(Field<Boolean> condition) {
        return this.and(DSL.condition(condition));
    }

    public final MergeImpl and(SQL sql) {
        return this.and(DSL.condition(sql));
    }

    public final MergeImpl and(String sql) {
        return this.and(DSL.condition(sql));
    }

    public final MergeImpl and(String sql, Object ... bindings) {
        return this.and(DSL.condition(sql, bindings));
    }

    public final MergeImpl and(String sql, QueryPart ... parts) {
        return this.and(DSL.condition(sql, parts));
    }

    public final MergeImpl andNot(Condition condition) {
        return this.and(condition.not());
    }

    public final MergeImpl andNot(Field<Boolean> condition) {
        return this.andNot(DSL.condition(condition));
    }

    public final MergeImpl andExists(Select<?> select) {
        return this.and(DSL.exists(select));
    }

    public final MergeImpl andNotExists(Select<?> select) {
        return this.and(DSL.notExists(select));
    }

    public final MergeImpl or(Condition condition) {
        this.on.addConditions(Operator.OR, condition);
        return this;
    }

    public final MergeImpl or(Field<Boolean> condition) {
        return this.and(DSL.condition(condition));
    }

    public final MergeImpl or(SQL sql) {
        return this.or(DSL.condition(sql));
    }

    public final MergeImpl or(String sql) {
        return this.or(DSL.condition(sql));
    }

    public final MergeImpl or(String sql, Object ... bindings) {
        return this.or(DSL.condition(sql, bindings));
    }

    public final MergeImpl or(String sql, QueryPart ... parts) {
        return this.or(DSL.condition(sql, parts));
    }

    public final MergeImpl orNot(Condition condition) {
        return this.or(condition.not());
    }

    public final MergeImpl orNot(Field<Boolean> condition) {
        return this.orNot(DSL.condition(condition));
    }

    public final MergeImpl orExists(Select<?> select) {
        return this.or(DSL.exists(select));
    }

    public final MergeImpl orNotExists(Select<?> select) {
        return this.or(DSL.notExists(select));
    }

    public final MergeImpl whenMatchedThenUpdate() {
        return this.whenMatchedAnd(DSL.noCondition()).thenUpdate();
    }

    public final MergeImpl whenMatchedThenDelete() {
        return this.whenMatchedAnd(DSL.noCondition()).thenDelete();
    }

    public final MergeImpl whenMatchedAnd(Condition condition) {
        this.matchedClause = true;
        this.matched.add(new MatchedClause(condition));
        this.notMatchedClause = false;
        return this;
    }

    public final MergeImpl whenMatchedAnd(Field<Boolean> condition) {
        return this.whenMatchedAnd(DSL.condition(condition));
    }

    public final MergeImpl whenMatchedAnd(SQL sql) {
        return this.whenMatchedAnd(DSL.condition(sql));
    }

    public final MergeImpl whenMatchedAnd(String sql) {
        return this.whenMatchedAnd(DSL.condition(sql));
    }

    public final MergeImpl whenMatchedAnd(String sql, Object ... bindings) {
        return this.whenMatchedAnd(DSL.condition(sql, bindings));
    }

    public final MergeImpl whenMatchedAnd(String sql, QueryPart ... parts) {
        return this.whenMatchedAnd(DSL.condition(sql, parts));
    }

    public final MergeImpl thenUpdate() {
        return this;
    }

    public final MergeImpl thenDelete() {
        this.getLastMatched().delete = true;
        return this;
    }

    public final <T> MergeImpl set(Field<T> field2, T value) {
        return this.set((Field)field2, (Field)Tools.field(value, field2));
    }

    public final <T> MergeImpl set(Field<T> field2, Field<T> value) {
        if (this.matchedClause) {
            this.getLastMatched().updateMap.put(field2, Tools.nullSafe(value));
        } else if (this.notMatchedClause) {
            this.getLastNotMatched().insertMap.set(field2, Tools.nullSafe(value));
        } else {
            throw new IllegalStateException("Cannot call where() on the current state of the MERGE statement");
        }
        return this;
    }

    public final <T> MergeImpl setNull(Field<T> field2) {
        return this.set((Field)field2, (Object)null);
    }

    public final <T> MergeImpl set(Field<T> field2, Select<? extends Record1<T>> value) {
        if (value == null) {
            return this.set((Field)field2, (Object)null);
        }
        return this.set((Field)field2, value.asField());
    }

    public final MergeImpl set(Map<?, ?> map) {
        if (this.matchedClause) {
            this.getLastMatched().updateMap.set(map);
        } else if (this.notMatchedClause) {
            this.getLastNotMatched().insertMap.set(map);
        } else {
            throw new IllegalStateException("Cannot call where() on the current state of the MERGE statement");
        }
        return this;
    }

    public final MergeImpl set(Record record) {
        return this.set((Map)Tools.mapOfChangedValues(record));
    }

    public final MergeImpl whenNotMatchedThenInsert() {
        return this.whenNotMatchedThenInsert((Collection)Collections.emptyList());
    }

    public final <T1> MergeImpl whenNotMatchedThenInsert(Field<T1> field1) {
        return this.whenNotMatchedThenInsert(new Field[]{field1});
    }

    public final <T1, T2> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2});
    }

    public final <T1, T2, T3> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3});
    }

    public final <T1, T2, T3, T4> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4});
    }

    public final <T1, T2, T3, T4, T5> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5});
    }

    public final <T1, T2, T3, T4, T5, T6> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6});
    }

    public final <T1, T2, T3, T4, T5, T6, T7> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeImpl whenNotMatchedThenInsert(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return this.whenNotMatchedThenInsert(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    public final MergeImpl whenNotMatchedThenInsert(Field<?> ... fields) {
        return this.whenNotMatchedThenInsert(Arrays.asList(fields));
    }

    public final MergeImpl whenNotMatchedThenInsert(Collection<? extends Field<?>> fields) {
        this.notMatchedClause = true;
        this.notMatched.add(new NotMatchedClause(DSL.noCondition()));
        this.getLastNotMatched().insertMap.addFields(fields);
        this.matchedClause = false;
        return this;
    }

    public final MergeImpl where(Condition condition) {
        if (this.matchedClause) {
            this.getLastMatched().condition = condition;
        } else if (this.notMatchedClause) {
            this.getLastNotMatched().condition = condition;
        } else {
            throw new IllegalStateException("Cannot call where() on the current state of the MERGE statement");
        }
        return this;
    }

    @Override
    public final MergeMatchedDeleteStep<R> where(Field<Boolean> condition) {
        return this.where(DSL.condition(condition));
    }

    public final MergeImpl deleteWhere(Condition condition) {
        if (!this.matchedClause) {
            throw new IllegalStateException("Cannot call where() on the current state of the MERGE statement");
        }
        this.matched.add(this.matched.size() - 1, new MatchedClause(condition, true));
        return this;
    }

    public final MergeImpl deleteWhere(Field<Boolean> condition) {
        return this.deleteWhere(DSL.condition(condition));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final QueryPart getStandardMerge(boolean usingSubqueries) {
        List<Field> srcFields;
        Table<Record> src;
        if (this.upsertSelect != null) {
            Table s2 = this.upsertSelect.asTable("s");
            src = DSL.select(Tools.map(s2.fieldsRow().fields(), (f, i) -> f.as("s" + (i + 1)))).from((TableLike<?>)s2).asTable("src");
            srcFields = Arrays.asList(src.fields());
        } else if (usingSubqueries) {
            src = DSL.select(Tools.map(this.getUpsertValues(), (f, i) -> f.as("s" + (i + 1)))).asTable("src");
            srcFields = Arrays.asList(src.fields());
        } else {
            src = new Dual();
            srcFields = Tools.map(this.getUpsertValues(), f -> f);
        }
        HashSet<Field<Object>> onFields = new HashSet<Field<Object>>();
        Condition condition = null;
        if (this.getUpsertKeys().isEmpty()) {
            UniqueKey<R> key = this.table.getPrimaryKey();
            if (key == null) throw new IllegalStateException("Cannot omit KEY() clause on a non-Updatable Table");
            onFields.addAll(key.getFields());
            for (int i2 = 0; i2 < key.getFields().size(); ++i2) {
                rhs = key.getFields().get(i2).equal(srcFields.get(i2));
                condition = condition == null ? rhs : condition.and(rhs);
            }
        } else {
            for (int i3 = 0; i3 < this.getUpsertKeys().size(); ++i3) {
                int matchIndex = this.getUpsertFields().indexOf(this.getUpsertKeys().get(i3));
                if (matchIndex == -1) {
                    throw new IllegalStateException("Fields in KEY() clause must be part of the fields specified in MERGE INTO table (...)");
                }
                onFields.addAll(this.getUpsertKeys());
                rhs = ((Field)this.getUpsertKeys().get(i3)).equal(srcFields.get(matchIndex));
                condition = condition == null ? rhs : condition.and(rhs);
            }
        }
        LinkedHashMap<Field, Field> update = new LinkedHashMap<Field, Field>();
        LinkedHashMap<Field, Field> insert = new LinkedHashMap<Field, Field>();
        for (int i4 = 0; i4 < srcFields.size(); ++i4) {
            if (!onFields.contains(this.getUpsertFields().get(i4))) {
                update.put((Field)this.getUpsertFields().get(i4), srcFields.get(i4));
            }
            insert.put((Field)this.getUpsertFields().get(i4), srcFields.get(i4));
        }
        return DSL.mergeInto(this.table).using(src).on(condition).whenMatchedThenUpdate().set(update).whenNotMatchedThenInsert().set(insert);
    }

    @Override
    public final void accept(Context<?> ctx) {
        for (NotMatchedClause notMatchedClause : this.notMatched) {
            notMatchedClause.insertMap.checkReadonly(ctx);
        }
        for (MatchedClause matchedClause : this.matched) {
            matchedClause.updateMap.checkReadonly(ctx);
        }
        if (this.with != null) {
            ctx.visit(this.with);
        }
        if (this.upsertStyle) {
            switch (ctx.family()) {
                case H2: {
                    this.toSQLH2Merge(ctx);
                    break;
                }
                case AURORA_MYSQL: 
                case MEMSQL: 
                case MARIADB: 
                case MYSQL: {
                    this.toSQLMySQLOnDuplicateKeyUpdate(ctx);
                    break;
                }
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    this.toPostgresInsertOnConflict(ctx);
                    break;
                }
                case HANA: {
                    this.toSQLHanaUpsert(ctx);
                    break;
                }
                case DERBY: {
                    ctx.visit(this.getStandardMerge(false));
                    break;
                }
                default: {
                    ctx.visit(this.getStandardMerge(true));
                    break;
                }
            }
        } else {
            this.toSQLStandard(ctx);
        }
    }

    private final void toSQLMySQLOnDuplicateKeyUpdate(Context<?> ctx) {
        FieldsImpl fields = new FieldsImpl(this.getUpsertFields());
        LinkedHashMap map = new LinkedHashMap();
        for (Field<?> field2 : fields.fields) {
            map.put(field2, (Field)this.getUpsertValues().get(fields.indexOf(field2)));
        }
        if (this.upsertSelect != null) {
            ctx.sql("[ merge with select is not supported in MySQL / MariaDB ]");
        } else {
            ctx.visit(DSL.insertInto(this.table, this.getUpsertFields()).values(this.getUpsertValues()).onDuplicateKeyUpdate().set(map));
        }
    }

    private final void toPostgresInsertOnConflict(Context<?> ctx) {
        if (this.upsertSelect != null) {
            ctx.visit(this.getStandardMerge(true));
        } else {
            FieldsImpl fields = new FieldsImpl(this.getUpsertFields());
            LinkedHashMap map = new LinkedHashMap();
            for (Field<?> field2 : fields.fields) {
                int i = fields.indexOf(field2);
                if (i <= -1 || i >= this.getUpsertValues().size()) continue;
                map.put(field2, (Field)this.getUpsertValues().get(i));
            }
            ctx.visit(DSL.insertInto(this.table, this.getUpsertFields()).values(this.getUpsertValues()).onConflict(this.getUpsertKeys()).doUpdate().set(map));
        }
    }

    private final void toSQLH2Merge(Context<?> ctx) {
        this.checkReadonlyUpsertFields(ctx);
        ctx.visit(Keywords.K_MERGE_INTO).sql(' ').declareTables(true, c -> c.visit(this.table)).formatSeparator();
        ctx.sql('(').visit(QueryPartListView.wrap(Tools.collect(MergeImpl.removeReadonly(ctx, this.getUpsertFields()))).qualify(false)).sql(')');
        if (!this.getUpsertKeys().isEmpty()) {
            ctx.formatSeparator().visit(Keywords.K_KEY).sql(" (").visit(QueryPartListView.wrap(this.getUpsertKeys()).qualify(false)).sql(')');
        }
        if (this.upsertSelect != null) {
            ctx.formatSeparator().visit(this.upsertSelect);
        } else {
            ctx.formatSeparator().visit(Keywords.K_VALUES).sql(" (").visit(QueryPartListView.wrap(Tools.collect(MergeImpl.removeReadonly(ctx, this.getUpsertFields(), this.getUpsertValues())))).sql(')');
        }
    }

    static final Iterable<Field<?>> removeReadonly(Context<?> ctx, List<Field<?>> it) {
        return MergeImpl.removeReadonly(ctx, it, it);
    }

    static final Iterable<Field<?>> removeReadonly(Context<?> ctx, List<Field<?>> checkIt, List<Field<?>> removeIt) {
        if (ctx.settings().getReadonlyInsert() == WriteIfReadonly.IGNORE || ctx.settings().getReadonlyUpdate() == WriteIfReadonly.IGNORE) {
            return Tools.filter(removeIt, (e, i) -> !((Field)checkIt.get(i)).getDataType().readonly());
        }
        return removeIt;
    }

    @Pro
    private final void checkReadonlyUpsertFields(Context<?> ctx) {
        if (WriteIfReadonly.THROW.equals((Object)ctx.settings().getReadonlyInsert()) || WriteIfReadonly.THROW.equals((Object)ctx.settings().getReadonlyUpdate())) {
            for (Field field2 : this.getUpsertFields()) {
                if (!field2.getDataType().readonly()) continue;
                throw new DataTypeException("Cannot upsert readonly column: " + String.valueOf(field2));
            }
        }
    }

    @Pro
    private final void toSQLHanaUpsert(Context<?> ctx) {
        this.checkReadonlyUpsertFields(ctx);
        FieldsImpl fields = new FieldsImpl(Tools.collect(MergeImpl.removeReadonly(ctx, this.getUpsertFields())));
        ctx.visit(Keywords.K_UPSERT).sql(' ').declareTables(true, c -> ctx.visit(this.table)).sql(" (").visit(QueryPartListView.wrap((QueryPart[])fields.fields).qualify(false)).sql(')');
        if (this.upsertSelect != null) {
            ctx.sql(' ').visit(this.upsertSelect);
        } else {
            List<Field<?>> values2 = Tools.collect(MergeImpl.removeReadonly(ctx, this.getUpsertFields(), this.getUpsertValues()));
            ctx.formatSeparator().visit(Keywords.K_VALUES).sql(" (").visit(QueryPartListView.wrap(values2)).sql(')');
            if (this.getUpsertKeys().isEmpty()) {
                ctx.formatSeparator().visit(Keywords.K_WITH_PRIMARY_KEY);
            } else {
                ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ');
                ConditionProviderImpl c2 = new ConditionProviderImpl();
                for (Field field2 : this.getUpsertKeys()) {
                    c2.addConditions(field2.eq(values2.get(fields.indexOf(field2))));
                }
                ctx.visit(c2);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void toSQLStandard(Context<?> ctx) {
        ctx.start(Clause.MERGE_MERGE_INTO).visit(Keywords.K_MERGE_INTO).sql(' ').declareTables(true, c -> c.visit(this.table)).end(Clause.MERGE_MERGE_INTO).formatSeparator().start(Clause.MERGE_USING).visit(Keywords.K_USING).sql(' ');
        ctx.declareTables(true, c1 -> c1.data(Tools.BooleanDataKey.DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES, true, c2 -> {
            if (this.usingDual) {
                switch (c2.family()) {
                    case DERBY: 
                    case TERADATA: {
                        c2.visit(new Dual());
                        break;
                    }
                    default: {
                        c2.visit(DSL.selectOne());
                        break;
                    }
                }
            } else {
                c2.visit(this.using);
            }
        }));
        switch (ctx.family()) {
            case POSTGRES: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case VERTICA: {
                if (this.using instanceof Select) {
                    int hash = Internal.hash(this.using);
                    ctx.sql(' ').visit(Keywords.K_AS).sql(' ').sql("dummy_").sql(hash).sql('(');
                    String separator = "";
                    for (Field<?> field2 : this.using.asTable("t").fields()) {
                        String name = StringUtils.isBlank(field2.getName()) ? "dummy_" + hash + "_" + Internal.hash(field2) : field2.getName();
                        ctx.sql(separator).literal(name);
                        separator = ", ";
                    }
                    ctx.sql(')');
                    break;
                }
                if (!this.usingDual) break;
                ctx.sql(" t");
            }
        }
        boolean onParentheses = ctx.family() == SQLDialect.ORACLE;
        ctx.end(Clause.MERGE_USING).formatSeparator().start(Clause.MERGE_ON).visit(Keywords.K_ON).sql(onParentheses ? " (" : " ").visit(this.on).sql(onParentheses ? ")" : "").end(Clause.MERGE_ON).start(Clause.MERGE_WHEN_MATCHED_THEN_UPDATE).start(Clause.MERGE_SET);
        boolean emulate = false;
        boolean requireMatchedConditions = false;
        if (NO_SUPPORT_CONDITION_AFTER_NO_CONDITION.contains((Object)ctx.dialect())) {
            boolean withoutMatchedConditionFound = false;
            for (MatchedClause matchedClause : this.matched) {
                if (requireMatchedConditions |= withoutMatchedConditionFound) break;
                withoutMatchedConditionFound |= matchedClause.condition instanceof NoCondition;
            }
        }
        if (NO_SUPPORT_MULTI.contains((Object)ctx.dialect()) && this.matched.size() > 1 || NO_SUPPORT_AND.contains((Object)ctx.dialect())) {
            boolean matchUpdate = false;
            boolean matchDelete = false;
            for (MatchedClause m5 : this.matched) {
                if (m5.delete) {
                    if (emulate |= matchDelete || NO_SUPPORT_AND.contains((Object)ctx.dialect())) break;
                    matchDelete = true;
                    continue;
                }
                if (emulate |= matchUpdate) break;
                matchUpdate = true;
            }
        }
        if (emulate) {
            MatchedClause update = null;
            MatchedClause delete = null;
            Condition condition = DSL.noCondition();
            for (MatchedClause m6 : this.matched) {
                void var7_21;
                Condition condition2 = var7_21.and(m6.condition);
                if (m6.delete) {
                    if (delete == null) {
                        delete = new MatchedClause(DSL.noCondition(), true);
                    }
                    delete.condition = delete.condition.or(condition2);
                } else {
                    if (update == null) {
                        update = new MatchedClause(DSL.noCondition());
                    }
                    for (Map.Entry e : m6.updateMap.entrySet()) {
                        FieldOrRowOrSelect exp = (FieldOrRowOrSelect)update.updateMap.get(e.getKey());
                        if (exp instanceof CaseSearched) {
                            CaseSearched c2 = (CaseSearched)exp;
                            c2.when(var7_21.and(condition2), e.getValue());
                            continue;
                        }
                        update.updateMap.put((FieldOrRow)e.getKey(), DSL.when(var7_21.and(condition2), (Field)e.getValue()).else_(e.getKey()));
                    }
                    update.condition = update.condition.or(condition2);
                }
                if (!REQUIRE_NEGATION.contains((Object)ctx.dialect())) continue;
                Condition condition3 = var7_21.andNot(m6.condition instanceof NoCondition ? DSL.trueCondition() : m6.condition);
            }
            if (NO_SUPPORT_AND.contains((Object)ctx.dialect())) {
                if (update == null && delete != null && this.table.fields().length > 0) {
                    update = new MatchedClause(delete.condition);
                    update.updateMap.put(this.table.field(0), this.table.field(0));
                }
                this.toSQLMatched(ctx, update, delete, requireMatchedConditions);
            } else {
                if (delete != null) {
                    this.toSQLMatched(ctx, delete, requireMatchedConditions);
                }
                if (update != null) {
                    this.toSQLMatched(ctx, update, requireMatchedConditions);
                }
            }
        } else if (REQUIRE_NEGATION.contains((Object)ctx.dialect())) {
            Object negate = DSL.noCondition();
            for (MatchedClause matchedClause : this.matched) {
                this.toSQLMatched(ctx, new MatchedClause(negate.and(matchedClause.condition), matchedClause.delete, matchedClause.updateMap), requireMatchedConditions);
                negate = negate.andNot(matchedClause.condition instanceof NoCondition ? DSL.trueCondition() : matchedClause.condition);
            }
        } else {
            for (MatchedClause m7 : this.matched) {
                this.toSQLMatched(ctx, m7, requireMatchedConditions);
            }
        }
        ctx.end(Clause.MERGE_SET).end(Clause.MERGE_WHEN_MATCHED_THEN_UPDATE).start(Clause.MERGE_WHEN_NOT_MATCHED_THEN_INSERT);
        for (NotMatchedClause m8 : this.notMatched) {
            this.toSQLNotMatched(ctx, m8);
        }
        ctx.end(Clause.MERGE_WHEN_NOT_MATCHED_THEN_INSERT);
        switch (ctx.family()) {
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                AbstractDMLQuery insertOnDuplicateKeyUpdate;
                if (!Boolean.FALSE.equals(ctx.settings().isRenderOutputForSQLServerReturningClause()) && (insertOnDuplicateKeyUpdate = (AbstractDMLQuery)ctx.data(Tools.ExtendedDataKey.DATA_INSERT_ON_DUPLICATE_KEY_UPDATE)) != null) {
                    insertOnDuplicateKeyUpdate.toSQLOutput(ctx);
                }
                ctx.sql(';');
            }
        }
    }

    private final void toSQLMatched(Context<?> ctx, MatchedClause m4, boolean requireMatchedConditions) {
        if (m4.delete) {
            this.toSQLMatched(ctx, null, m4, requireMatchedConditions);
        } else {
            this.toSQLMatched(ctx, m4, null, requireMatchedConditions);
        }
    }

    private final void toSQLMatched(Context<?> ctx, MatchedClause update, MatchedClause delete, boolean requireMatchedConditions) {
        MatchedClause m4;
        ctx.formatSeparator().visit(Keywords.K_WHEN).sql(' ').visit(Keywords.K_MATCHED);
        MatchedClause matchedClause = m4 = update != null ? update : delete;
        if (!(!requireMatchedConditions && m4.condition instanceof NoCondition || NO_SUPPORT_AND.contains((Object)ctx.dialect()))) {
            ctx.sql(' ').visit(Keywords.K_AND).sql(' ').visit(m4.condition);
        }
        ctx.sql(' ').visit(Keywords.K_THEN);
        if (update != null) {
            ctx.sql(' ').visit(Keywords.K_UPDATE).sql(' ').visit(Keywords.K_SET).formatIndentStart().formatSeparator().visit(update.updateMap).formatIndentEnd();
            if ((requireMatchedConditions || !(update.condition instanceof NoCondition)) && NO_SUPPORT_AND.contains((Object)ctx.dialect())) {
                ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(update.condition);
            }
        }
        if (delete != null) {
            if ((requireMatchedConditions || !(delete.condition instanceof NoCondition)) && NO_SUPPORT_AND.contains((Object)ctx.dialect())) {
                ctx.formatSeparator().visit(Keywords.K_DELETE).sql(' ').visit(Keywords.K_WHERE).sql(' ').visit(delete.condition);
            } else {
                ctx.sql(' ').visit(Keywords.K_DELETE);
            }
        }
    }

    private final void toSQLNotMatched(Context<?> ctx, NotMatchedClause m4) {
        ctx.formatSeparator().visit(Keywords.K_WHEN).sql(' ').visit(Keywords.K_NOT).sql(' ').visit(Keywords.K_MATCHED).sql(' ');
        if (!(m4.condition instanceof NoCondition) && !NO_SUPPORT_AND.contains((Object)ctx.dialect())) {
            ctx.visit(Keywords.K_AND).sql(' ').visit(m4.condition).sql(' ');
        }
        ctx.visit(Keywords.K_THEN).sql(' ').visit(Keywords.K_INSERT);
        m4.insertMap.toSQLReferenceKeys(ctx);
        ctx.formatSeparator().start(Clause.MERGE_VALUES).visit(Keywords.K_VALUES).sql(' ');
        m4.insertMap.toSQL92Values(ctx);
        ctx.end(Clause.MERGE_VALUES);
        if (!(m4.condition instanceof NoCondition) && NO_SUPPORT_AND.contains((Object)ctx.dialect())) {
            ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(m4.condition);
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    private final class MatchedClause
    implements Serializable {
        FieldMapForUpdate updateMap;
        boolean delete;
        Condition condition;

        MatchedClause(Condition condition) {
            this(condition, false);
        }

        MatchedClause(Condition condition, boolean delete) {
            this(condition, delete, new FieldMapForUpdate(mergeImpl.table, FieldMapForUpdate.SetClause.MERGE, Clause.MERGE_SET_ASSIGNMENT));
        }

        MatchedClause(Condition condition, boolean delete, FieldMapForUpdate updateMap) {
            this.updateMap = updateMap;
            this.condition = condition == null ? DSL.noCondition() : condition;
            this.delete = delete;
        }
    }

    private final class NotMatchedClause
    implements Serializable {
        FieldMapsForInsert insertMap;
        Condition condition;

        NotMatchedClause(Condition condition) {
            this.insertMap = new FieldMapsForInsert(MergeImpl.this.table);
            this.condition = condition == null ? DSL.noCondition() : condition;
        }
    }
}

