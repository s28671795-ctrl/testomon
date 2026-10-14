/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jooq.BetweenAndStep;
import org.jooq.BetweenAndStepR;
import org.jooq.CloseableResultQuery;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Cursor;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.GroupField;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.QuantifiedSelect;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Results;
import org.jooq.Row;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectConnectByAfterStartWithConditionStep;
import org.jooq.SelectConnectByAfterStartWithStep;
import org.jooq.SelectConnectByConditionStep;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectFinalStep;
import org.jooq.SelectForJSONCommonDirectivesStep;
import org.jooq.SelectForJSONStep;
import org.jooq.SelectForUpdateOfStep;
import org.jooq.SelectForXMLPathDirectivesStep;
import org.jooq.SelectForXMLRawDirectivesStep;
import org.jooq.SelectForXMLStep;
import org.jooq.SelectHavingConditionStep;
import org.jooq.SelectIntoStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitAfterOffsetStep;
import org.jooq.SelectLimitPercentAfterOffsetStep;
import org.jooq.SelectLimitPercentStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectOnStep;
import org.jooq.SelectOptionalOnStep;
import org.jooq.SelectQualifyConditionStep;
import org.jooq.SelectQuery;
import org.jooq.SelectSeekLimitStep;
import org.jooq.SelectSeekStep1;
import org.jooq.SelectSeekStep10;
import org.jooq.SelectSeekStep11;
import org.jooq.SelectSeekStep12;
import org.jooq.SelectSeekStep13;
import org.jooq.SelectSeekStep14;
import org.jooq.SelectSeekStep15;
import org.jooq.SelectSeekStep16;
import org.jooq.SelectSeekStep17;
import org.jooq.SelectSeekStep18;
import org.jooq.SelectSeekStep19;
import org.jooq.SelectSeekStep2;
import org.jooq.SelectSeekStep20;
import org.jooq.SelectSeekStep21;
import org.jooq.SelectSeekStep22;
import org.jooq.SelectSeekStep3;
import org.jooq.SelectSeekStep4;
import org.jooq.SelectSeekStep5;
import org.jooq.SelectSeekStep6;
import org.jooq.SelectSeekStep7;
import org.jooq.SelectSeekStep8;
import org.jooq.SelectSeekStep9;
import org.jooq.SelectSeekStepN;
import org.jooq.SelectSelectStep;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.Variable;
import org.jooq.WindowDefinition;
import org.jooq.impl.AbstractDelegatingQuery;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.JoinTable;
import org.jooq.impl.QOM;
import org.jooq.impl.ResultQueryTrait;
import org.jooq.impl.SelectIsNotNull;
import org.jooq.impl.SelectIsNull;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.Tools;
import org.jooq.impl.WithImpl;
import org.reactivestreams.Subscriber;

final class SelectImpl<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>
extends AbstractDelegatingQuery<R, SelectQueryImpl<R>>
implements ResultQueryTrait<R>,
SelectSelectStep<R>,
SelectOptionalOnStep<R>,
SelectOnConditionStep<R>,
SelectConditionStep<R>,
SelectConnectByConditionStep<R>,
SelectConnectByAfterStartWithConditionStep<R>,
SelectConnectByAfterStartWithStep<R>,
SelectHavingConditionStep<R>,
SelectQualifyConditionStep<R>,
SelectSeekStep1<R, T1>,
SelectSeekStep2<R, T1, T2>,
SelectSeekStep3<R, T1, T2, T3>,
SelectSeekStep4<R, T1, T2, T3, T4>,
SelectSeekStep5<R, T1, T2, T3, T4, T5>,
SelectSeekStep6<R, T1, T2, T3, T4, T5, T6>,
SelectSeekStep7<R, T1, T2, T3, T4, T5, T6, T7>,
SelectSeekStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>,
SelectSeekStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>,
SelectSeekStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>,
SelectSeekStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>,
SelectSeekStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>,
SelectSeekStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>,
SelectSeekStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>,
SelectSeekStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>,
SelectSeekStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>,
SelectSeekStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>,
SelectSeekStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>,
SelectSeekStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>,
SelectSeekStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>,
SelectSeekStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>,
SelectSeekStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>,
SelectForXMLStep<R>,
SelectForXMLRawDirectivesStep<R>,
SelectForXMLPathDirectivesStep<R>,
SelectForJSONStep<R>,
SelectForJSONCommonDirectivesStep<R>,
SelectSeekStepN<R>,
SelectSeekLimitStep<R>,
SelectLimitPercentStep<R>,
SelectLimitAfterOffsetStep<R>,
SelectLimitPercentAfterOffsetStep<R>,
SelectForUpdateOfStep<R> {
    private transient TableLike<?> joinTable;
    private transient Field<?>[] joinPartitionBy;
    private transient JoinType joinType;
    private transient ConditionProviderImpl joinConditions;
    private transient ConditionStep conditionStep;
    private transient Number limit;
    private transient Field<? extends Number> limitParam;
    private transient Number offset;
    private transient Field<? extends Number> offsetParam;
    @Pro
    private transient boolean forXML;
    @Pro
    private transient boolean forJSON;

    SelectImpl(Configuration configuration, WithImpl with) {
        this(configuration, with, false);
    }

    SelectImpl(Configuration configuration, WithImpl with, boolean distinct2) {
        this(new SelectQueryImpl(configuration, with, distinct2));
    }

    SelectImpl(SelectQueryImpl<R> query) {
        super(query);
    }

    @Override
    public final SelectQuery<R> getQuery() {
        return (SelectQuery)this.getDelegate();
    }

    public final SelectImpl select(SelectFieldOrAsterisk ... fields) {
        this.getQuery().addSelect(fields);
        return this;
    }

    public final SelectImpl select(Collection<? extends SelectFieldOrAsterisk> fields) {
        this.getQuery().addSelect(fields);
        return this;
    }

    @Override
    public final SelectIntoStep<R> on(SelectFieldOrAsterisk ... fields) {
        return this.distinctOn(Arrays.asList(fields));
    }

    @Override
    public final SelectIntoStep<R> on(Collection<? extends SelectFieldOrAsterisk> fields) {
        return this.distinctOn(fields);
    }

    @Override
    public final SelectIntoStep<R> distinctOn(SelectFieldOrAsterisk ... fields) {
        this.getQuery().addDistinctOn(fields);
        return this;
    }

    @Override
    public final SelectIntoStep<R> distinctOn(Collection<? extends SelectFieldOrAsterisk> fields) {
        this.getQuery().addDistinctOn(fields);
        return this;
    }

    public final SelectImpl into(Table<?> table) {
        this.getQuery().setInto(table);
        return this;
    }

    public final SelectImpl into(Variable<?> ... variables) {
        return this.into(Arrays.asList(variables));
    }

    public final SelectImpl into(Collection<? extends Variable<?>> variables) {
        this.getQuery().setInto(variables);
        return this;
    }

    public final SelectImpl hint(String hint) {
        this.getQuery().addHint(hint);
        return this;
    }

    public final SelectImpl option(String hint) {
        this.getQuery().addOption(hint);
        return this;
    }

    public final SelectImpl from(TableLike<?> table) {
        this.getQuery().addFrom(table);
        return this;
    }

    public final SelectImpl from(TableLike<?> ... tables) {
        this.getQuery().addFrom(tables);
        return this;
    }

    public final SelectImpl from(Collection<? extends TableLike<?>> tables) {
        this.getQuery().addFrom(tables);
        return this;
    }

    public final SelectImpl from(SQL sql) {
        return this.from(DSL.table(sql));
    }

    public final SelectImpl from(String sql) {
        return this.from(DSL.table(sql));
    }

    public final SelectImpl from(String sql, Object ... bindings) {
        return this.from(DSL.table(sql, bindings));
    }

    public final SelectImpl from(String sql, QueryPart ... parts) {
        return this.from(DSL.table(sql, parts));
    }

    @Override
    public final SelectJoinStep<R> from(Name name) {
        return this.from(DSL.table(name));
    }

    public final SelectImpl where(Condition conditions) {
        this.conditionStep = ConditionStep.WHERE;
        this.getQuery().addConditions(conditions);
        return this;
    }

    public final SelectImpl where(Condition ... conditions) {
        this.conditionStep = ConditionStep.WHERE;
        this.getQuery().addConditions(conditions);
        return this;
    }

    public final SelectImpl where(Collection<? extends Condition> conditions) {
        this.conditionStep = ConditionStep.WHERE;
        this.getQuery().addConditions(conditions);
        return this;
    }

    public final SelectImpl where(Field<Boolean> condition) {
        return this.where(DSL.condition(condition));
    }

    public final SelectImpl where(SQL sql) {
        return this.where(DSL.condition(sql));
    }

    public final SelectImpl where(String sql) {
        return this.where(DSL.condition(sql));
    }

    public final SelectImpl where(String sql, Object ... bindings) {
        return this.where(DSL.condition(sql, bindings));
    }

    public final SelectImpl where(String sql, QueryPart ... parts) {
        return this.where(DSL.condition(sql, parts));
    }

    public final SelectImpl whereExists(Select<?> select) {
        this.conditionStep = ConditionStep.WHERE;
        return this.andExists((Select)select);
    }

    public final SelectImpl whereNotExists(Select<?> select) {
        this.conditionStep = ConditionStep.WHERE;
        return this.andNotExists((Select)select);
    }

    public final SelectImpl and(Condition condition) {
        switch (this.conditionStep) {
            case WHERE: {
                this.getQuery().addConditions(condition);
                break;
            }
            case CONNECT_BY: {
                this.getQuery().addConnectBy(condition);
                break;
            }
            case HAVING: {
                this.getQuery().addHaving(condition);
                break;
            }
            case ON: {
                this.joinConditions.addConditions(condition);
                break;
            }
            case QUALIFY: {
                this.getQuery().addQualify(condition);
            }
        }
        return this;
    }

    public final SelectImpl and(Field<Boolean> condition) {
        return this.and(DSL.condition(condition));
    }

    public final SelectImpl and(SQL sql) {
        return this.and(DSL.condition(sql));
    }

    public final SelectImpl and(String sql) {
        return this.and(DSL.condition(sql));
    }

    public final SelectImpl and(String sql, Object ... bindings) {
        return this.and(DSL.condition(sql, bindings));
    }

    public final SelectImpl and(String sql, QueryPart ... parts) {
        return this.and(DSL.condition(sql, parts));
    }

    public final SelectImpl andNot(Condition condition) {
        return this.and(condition.not());
    }

    public final SelectImpl andNot(Field<Boolean> condition) {
        return this.andNot(DSL.condition(condition));
    }

    public final SelectImpl andExists(Select<?> select) {
        return this.and(DSL.exists(select));
    }

    public final SelectImpl andNotExists(Select<?> select) {
        return this.and(DSL.notExists(select));
    }

    public final SelectImpl or(Condition condition) {
        switch (this.conditionStep) {
            case WHERE: {
                this.getQuery().addConditions(Operator.OR, condition);
                break;
            }
            case CONNECT_BY: {
                throw new IllegalStateException("Cannot connect conditions for the CONNECT BY clause using the OR operator");
            }
            case HAVING: {
                this.getQuery().addHaving(Operator.OR, condition);
                break;
            }
            case ON: {
                this.joinConditions.addConditions(Operator.OR, condition);
                break;
            }
            case QUALIFY: {
                this.getQuery().addQualify(Operator.OR, condition);
            }
        }
        return this;
    }

    public final SelectImpl or(Field<Boolean> condition) {
        return this.or(DSL.condition(condition));
    }

    public final SelectImpl or(SQL sql) {
        return this.or(DSL.condition(sql));
    }

    public final SelectImpl or(String sql) {
        return this.or(DSL.condition(sql));
    }

    public final SelectImpl or(String sql, Object ... bindings) {
        return this.or(DSL.condition(sql, bindings));
    }

    public final SelectImpl or(String sql, QueryPart ... parts) {
        return this.or(DSL.condition(sql, parts));
    }

    public final SelectImpl orNot(Condition condition) {
        return this.or(condition.not());
    }

    public final SelectImpl orNot(Field<Boolean> condition) {
        return this.orNot(DSL.condition(condition));
    }

    public final SelectImpl orExists(Select<?> select) {
        return this.or(DSL.exists(select));
    }

    public final SelectImpl orNotExists(Select<?> select) {
        return this.or(DSL.notExists(select));
    }

    @Pro
    public final SelectImpl connectBy(Condition condition) {
        this.conditionStep = ConditionStep.CONNECT_BY;
        this.getQuery().addConnectBy(condition);
        return this;
    }

    @Pro
    public final SelectImpl connectBy(Field<Boolean> condition) {
        return this.connectBy(DSL.condition(condition));
    }

    @Pro
    public final SelectImpl connectBy(SQL sql) {
        return this.connectBy(DSL.condition(sql));
    }

    @Pro
    public final SelectImpl connectBy(String sql) {
        return this.connectBy(DSL.condition(sql));
    }

    @Pro
    public final SelectImpl connectBy(String sql, Object ... bindings) {
        return this.connectBy(DSL.condition(sql, bindings));
    }

    @Pro
    public final SelectImpl connectBy(String sql, QueryPart ... parts) {
        return this.connectBy(DSL.condition(sql, parts));
    }

    @Pro
    public final SelectImpl connectByNoCycle(Condition condition) {
        this.conditionStep = ConditionStep.CONNECT_BY;
        this.getQuery().addConnectByNoCycle(condition);
        return this;
    }

    @Pro
    public final SelectImpl connectByNoCycle(Field<Boolean> condition) {
        return this.connectByNoCycle(DSL.condition(condition));
    }

    @Pro
    public final SelectImpl connectByNoCycle(SQL sql) {
        return this.connectByNoCycle(DSL.condition(sql));
    }

    @Pro
    public final SelectImpl connectByNoCycle(String sql) {
        return this.connectByNoCycle(DSL.condition(sql));
    }

    @Pro
    public final SelectImpl connectByNoCycle(String sql, Object ... bindings) {
        return this.connectByNoCycle(DSL.condition(sql, bindings));
    }

    @Pro
    public final SelectImpl connectByNoCycle(String sql, QueryPart ... parts) {
        return this.connectByNoCycle(DSL.condition(sql, parts));
    }

    @Pro
    public final SelectImpl startWith(Condition condition) {
        this.getQuery().setConnectByStartWith(condition);
        return this;
    }

    @Pro
    public final SelectImpl startWith(Field<Boolean> condition) {
        return this.startWith(DSL.condition(condition));
    }

    @Pro
    public final SelectImpl startWith(SQL sql) {
        return this.startWith(DSL.condition(sql));
    }

    @Pro
    public final SelectImpl startWith(String sql) {
        return this.startWith(DSL.condition(sql));
    }

    @Pro
    public final SelectImpl startWith(String sql, Object ... bindings) {
        return this.startWith(DSL.condition(sql, bindings));
    }

    @Pro
    public final SelectImpl startWith(String sql, QueryPart ... parts) {
        return this.startWith(DSL.condition(sql, parts));
    }

    public final SelectImpl groupBy(GroupField ... fields) {
        this.getQuery().addGroupBy(fields);
        return this;
    }

    public final SelectImpl groupBy(Collection<? extends GroupField> fields) {
        this.getQuery().addGroupBy(fields);
        return this;
    }

    public final SelectImpl groupByDistinct(GroupField ... fields) {
        this.getQuery().addGroupBy(fields);
        this.getQuery().setGroupByDistinct(true);
        return this;
    }

    public final SelectImpl groupByDistinct(Collection<? extends GroupField> fields) {
        this.getQuery().addGroupBy(fields);
        this.getQuery().setGroupByDistinct(true);
        return this;
    }

    @Override
    public final SelectSeekStep1 orderBy(OrderField t1) {
        return this.orderBy(new OrderField[]{t1});
    }

    @Override
    public final SelectSeekStep2 orderBy(OrderField t1, OrderField t2) {
        return this.orderBy(new OrderField[]{t1, t2});
    }

    @Override
    public final SelectSeekStep3 orderBy(OrderField t1, OrderField t2, OrderField t3) {
        return this.orderBy(new OrderField[]{t1, t2, t3});
    }

    @Override
    public final SelectSeekStep4 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4});
    }

    @Override
    public final SelectSeekStep5 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5});
    }

    @Override
    public final SelectSeekStep6 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6});
    }

    @Override
    public final SelectSeekStep7 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7});
    }

    @Override
    public final SelectSeekStep8 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    @Override
    public final SelectSeekStep9 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    @Override
    public final SelectSeekStep10 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    @Override
    public final SelectSeekStep11 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
    }

    @Override
    public final SelectSeekStep12 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
    }

    @Override
    public final SelectSeekStep13 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
    }

    @Override
    public final SelectSeekStep14 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
    }

    @Override
    public final SelectSeekStep15 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14, OrderField t15) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
    }

    @Override
    public final SelectSeekStep16 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14, OrderField t15, OrderField t16) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
    }

    @Override
    public final SelectSeekStep17 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14, OrderField t15, OrderField t16, OrderField t17) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
    }

    @Override
    public final SelectSeekStep18 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14, OrderField t15, OrderField t16, OrderField t17, OrderField t18) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
    }

    @Override
    public final SelectSeekStep19 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14, OrderField t15, OrderField t16, OrderField t17, OrderField t18, OrderField t19) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
    }

    @Override
    public final SelectSeekStep20 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14, OrderField t15, OrderField t16, OrderField t17, OrderField t18, OrderField t19, OrderField t20) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
    }

    @Override
    public final SelectSeekStep21 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14, OrderField t15, OrderField t16, OrderField t17, OrderField t18, OrderField t19, OrderField t20, OrderField t21) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
    }

    @Override
    public final SelectSeekStep22 orderBy(OrderField t1, OrderField t2, OrderField t3, OrderField t4, OrderField t5, OrderField t6, OrderField t7, OrderField t8, OrderField t9, OrderField t10, OrderField t11, OrderField t12, OrderField t13, OrderField t14, OrderField t15, OrderField t16, OrderField t17, OrderField t18, OrderField t19, OrderField t20, OrderField t21, OrderField t22) {
        return this.orderBy(new OrderField[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
    }

    public final SelectImpl orderBy(OrderField<?> ... fields) {
        this.getQuery().addOrderBy(fields);
        return this;
    }

    public final SelectImpl orderBy(Collection<? extends OrderField<?>> fields) {
        this.getQuery().addOrderBy(fields);
        return this;
    }

    public final SelectImpl orderBy(int ... fieldIndexes) {
        this.getQuery().addOrderBy(fieldIndexes);
        return this;
    }

    @Pro
    public final SelectImpl orderSiblingsBy(OrderField<?> ... fields) {
        this.getQuery().addOrderBy(fields);
        this.getQuery().setOrderBySiblings(true);
        return this;
    }

    @Pro
    public final SelectImpl orderSiblingsBy(Collection<? extends OrderField<?>> fields) {
        this.getQuery().addOrderBy(fields);
        this.getQuery().setOrderBySiblings(true);
        return this;
    }

    @Pro
    public final SelectImpl orderSiblingsBy(int ... fieldIndexes) {
        this.getQuery().addOrderBy(fieldIndexes);
        this.getQuery().setOrderBySiblings(true);
        return this;
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1) {
        return this.seek(new Object[]{t1});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1) {
        return this.seekBefore(new Object[]{t1});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1) {
        return this.seekAfter(new Object[]{t1});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2) {
        return this.seek(new Object[]{t1, t2});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2) {
        return this.seekBefore(new Object[]{t1, t2});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2) {
        return this.seekAfter(new Object[]{t1, t2});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3) {
        return this.seek(new Object[]{t1, t2, t3});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3) {
        return this.seekBefore(new Object[]{t1, t2, t3});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3) {
        return this.seekAfter(new Object[]{t1, t2, t3});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4) {
        return this.seek(new Object[]{t1, t2, t3, t4});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22) {
        return this.seek(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22) {
        return this.seekBefore(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22) {
        return this.seekAfter(new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1) {
        return this.seek(new Field[]{t1});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1) {
        return this.seekBefore(new Field[]{t1});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1) {
        return this.seekAfter(new Field[]{t1});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2) {
        return this.seek(new Field[]{t1, t2});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2) {
        return this.seekBefore(new Field[]{t1, t2});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2) {
        return this.seekAfter(new Field[]{t1, t2});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3) {
        return this.seek(new Field[]{t1, t2, t3});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3) {
        return this.seekBefore(new Field[]{t1, t2, t3});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3) {
        return this.seekAfter(new Field[]{t1, t2, t3});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4) {
        return this.seek(new Field[]{t1, t2, t3, t4});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
        return this.seek(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
    }

    @Override
    @Deprecated
    public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
        return this.seekBefore(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
        return this.seekAfter(new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
    }

    private final List<? extends Field<?>> seekValues(Object[] values2) {
        SelectQuery<R> selectQuery = this.getQuery();
        if (selectQuery instanceof SelectQueryImpl) {
            SelectQueryImpl s2 = (SelectQueryImpl)selectQuery;
            return Tools.fields(values2, Tools.map(s2.getOrderBy().fields(), f -> f.getDataType(), DataType[]::new));
        }
        return Tools.fields(values2);
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Object ... values2) {
        this.getQuery().addSeekAfter(this.seekValues(values2));
        return this;
    }

    @Override
    public final SelectSeekLimitStep<R> seek(Field<?> ... fields) {
        this.getQuery().addSeekAfter(fields);
        return this;
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Object ... values2) {
        this.getQuery().addSeekAfter(this.seekValues(values2));
        return this;
    }

    @Override
    public final SelectSeekLimitStep<R> seekAfter(Field<?> ... fields) {
        this.getQuery().addSeekAfter(fields);
        return this;
    }

    @Override
    public final SelectSeekLimitStep<R> seekBefore(Object ... values2) {
        this.getQuery().addSeekBefore(Tools.fields(values2));
        return this;
    }

    @Override
    public final SelectSeekLimitStep<R> seekBefore(Field<?> ... fields) {
        this.getQuery().addSeekBefore(fields);
        return this;
    }

    public final SelectImpl limit(Number l) {
        this.limit = l;
        this.limitParam = null;
        return this.limitOffset();
    }

    public final SelectImpl limit(Field<? extends Number> l) {
        this.limit = null;
        this.limitParam = l;
        return this.limitOffset();
    }

    public final SelectImpl limit(Number o, Number l) {
        this.offset = o;
        this.offsetParam = null;
        this.limit = l;
        this.limitParam = null;
        return this.limitOffset();
    }

    public final SelectImpl limit(Number o, Field<? extends Number> l) {
        this.offset = o;
        this.offsetParam = null;
        this.limit = null;
        this.limitParam = l;
        return this.limitOffset();
    }

    public final SelectImpl limit(Field<? extends Number> o, Number l) {
        this.offset = null;
        this.offsetParam = o;
        this.limit = l;
        this.limitParam = null;
        return this.limitOffset();
    }

    public final SelectImpl limit(Field<? extends Number> o, Field<? extends Number> l) {
        this.offset = null;
        this.offsetParam = o;
        this.limit = null;
        this.limitParam = l;
        return this.limitOffset();
    }

    public final SelectImpl offset(Number o) {
        this.offset = o;
        this.offsetParam = null;
        return this.limitOffset();
    }

    public final SelectImpl offset(Field<? extends Number> o) {
        this.offset = null;
        this.offsetParam = o;
        return this.limitOffset();
    }

    private final SelectImpl limitOffset() {
        if (this.limit != null) {
            if (this.offset != null) {
                this.getQuery().addLimit(this.offset, this.limit);
            } else if (this.offsetParam != null) {
                this.getQuery().addLimit(this.offsetParam, this.limit);
            } else {
                this.getQuery().addLimit(this.limit);
            }
        } else if (this.limitParam != null) {
            if (this.offset != null) {
                this.getQuery().addLimit(this.offset, this.limitParam);
            } else if (this.offsetParam != null) {
                this.getQuery().addLimit(this.offsetParam, this.limitParam);
            } else {
                this.getQuery().addLimit(this.limitParam);
            }
        } else if (this.offset != null) {
            this.getQuery().addOffset(this.offset);
        } else if (this.offsetParam != null) {
            this.getQuery().addOffset(this.offsetParam);
        }
        return this;
    }

    public final SelectImpl percent() {
        this.getQuery().setLimitPercent(true);
        return this;
    }

    public final SelectImpl withTies() {
        this.getQuery().setWithTies(true);
        return this;
    }

    public final SelectImpl forUpdate() {
        this.getQuery().setForUpdate(true);
        return this;
    }

    public final SelectImpl forNoKeyUpdate() {
        this.getQuery().setForNoKeyUpdate(true);
        return this;
    }

    public final SelectImpl of(Field<?> ... fields) {
        this.getQuery().setForLockModeOf(fields);
        return this;
    }

    public final SelectImpl of(Collection<? extends Field<?>> fields) {
        this.getQuery().setForLockModeOf(fields);
        return this;
    }

    public final SelectImpl of(Table<?> ... tables) {
        this.getQuery().setForLockModeOf(tables);
        return this;
    }

    public final SelectImpl wait(int seconds) {
        this.getQuery().setForLockModeWait(seconds);
        return this;
    }

    public final SelectImpl noWait() {
        this.getQuery().setForLockModeNoWait();
        return this;
    }

    public final SelectImpl skipLocked() {
        this.getQuery().setForLockModeSkipLocked();
        return this;
    }

    public final SelectImpl forShare() {
        this.getQuery().setForShare(true);
        return this;
    }

    public final SelectImpl forKeyShare() {
        this.getQuery().setForKeyShare(true);
        return this;
    }

    public final SelectImpl forXML() {
        this.forXML = true;
        return this;
    }

    public final SelectImpl forJSON() {
        this.forJSON = true;
        return this;
    }

    public final SelectImpl forJSONB() {
        this.forJSON = true;
        this.getQuery().setForJSONB(true);
        return this;
    }

    public final SelectImpl raw() {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLRaw(true);
        return this;
    }

    public final SelectImpl raw(String elementName) {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLRaw(true, elementName);
        return this;
    }

    public final SelectImpl auto() {
        if (this.forXML) {
            this.getQuery().setForXMLAuto(true);
        } else if (this.forJSON) {
            this.getQuery().setForJSONAuto(true);
        } else {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    public final SelectImpl path() {
        if (this.forXML) {
            this.getQuery().setForXMLPath(true);
        } else if (this.forJSON) {
            this.getQuery().setForJSONPath(true);
        } else {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    public final SelectImpl path(String elementName) {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLPath(true, elementName);
        return this;
    }

    public final SelectImpl explicit() {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLExplicit(true);
        return this;
    }

    public final SelectImpl binaryBase64() {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLBinaryBase64(true);
        return this;
    }

    public final SelectImpl type() {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLType(true);
        return this;
    }

    public final SelectImpl root() {
        if (this.forXML) {
            this.getQuery().setForXMLRoot(true);
        } else if (this.forJSON) {
            this.getQuery().setForJSONRoot(true);
        } else {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    public final SelectImpl root(String rootName) {
        if (this.forXML) {
            this.getQuery().setForXMLRoot(true, rootName);
        } else if (this.forJSON) {
            this.getQuery().setForJSONRoot(true, rootName);
        } else {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    public final SelectImpl elements() {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLElements(true);
        return this;
    }

    public final SelectImpl elementsXsinil() {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLElementsXsinil(true);
        return this;
    }

    public final SelectImpl elementsAbsent() {
        if (!this.forXML) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForXMLElementsAbsent(true);
        return this;
    }

    public final SelectImpl includeNullValues() {
        if (!this.forJSON) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForJSONIncludeNullValues(true);
        return this;
    }

    public final SelectImpl withoutArrayWrapper() {
        if (!this.forJSON) {
            throw new UnsupportedOperationException();
        }
        this.getQuery().setForJSONWithoutArrayWrapper(true);
        return this;
    }

    @Override
    public final SelectFinalStep<R> withCheckOption() {
        this.getQuery().setWithCheckOption();
        return this;
    }

    @Override
    public final SelectFinalStep<R> withReadOnly() {
        this.getQuery().setWithReadOnly();
        return this;
    }

    public final SelectImpl union(Select<? extends R> select) {
        return new SelectImpl<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(((SelectQueryImpl)this.getDelegate()).union((Select)select));
    }

    public final SelectImpl unionAll(Select<? extends R> select) {
        return new SelectImpl<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(((SelectQueryImpl)this.getDelegate()).unionAll((Select)select));
    }

    public final SelectImpl except(Select<? extends R> select) {
        return new SelectImpl<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(((SelectQueryImpl)this.getDelegate()).except((Select)select));
    }

    public final SelectImpl exceptAll(Select<? extends R> select) {
        return new SelectImpl<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(((SelectQueryImpl)this.getDelegate()).exceptAll((Select)select));
    }

    public final SelectImpl intersect(Select<? extends R> select) {
        return new SelectImpl<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(((SelectQueryImpl)this.getDelegate()).intersect((Select)select));
    }

    public final SelectImpl intersectAll(Select<? extends R> select) {
        return new SelectImpl<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(((SelectQueryImpl)this.getDelegate()).intersectAll((Select)select));
    }

    public final SelectImpl having(Condition conditions) {
        this.conditionStep = ConditionStep.HAVING;
        this.getQuery().addHaving(conditions);
        return this;
    }

    public final SelectImpl having(Condition ... conditions) {
        this.conditionStep = ConditionStep.HAVING;
        this.getQuery().addHaving(conditions);
        return this;
    }

    public final SelectImpl having(Collection<? extends Condition> conditions) {
        this.conditionStep = ConditionStep.HAVING;
        this.getQuery().addHaving(conditions);
        return this;
    }

    public final SelectImpl having(Field<Boolean> condition) {
        return this.having(DSL.condition(condition));
    }

    public final SelectImpl having(SQL sql) {
        return this.having(DSL.condition(sql));
    }

    public final SelectImpl having(String sql) {
        return this.having(DSL.condition(sql));
    }

    public final SelectImpl having(String sql, Object ... bindings) {
        return this.having(DSL.condition(sql, bindings));
    }

    public final SelectImpl having(String sql, QueryPart ... parts) {
        return this.having(DSL.condition(sql, parts));
    }

    public final SelectImpl window(WindowDefinition ... definitions) {
        this.getQuery().addWindow(definitions);
        return this;
    }

    public final SelectImpl window(Collection<? extends WindowDefinition> definitions) {
        this.getQuery().addWindow(definitions);
        return this;
    }

    public final SelectImpl qualify(Condition conditions) {
        this.conditionStep = ConditionStep.QUALIFY;
        this.getQuery().addQualify(conditions);
        return this;
    }

    public final SelectImpl qualify(Condition ... conditions) {
        this.conditionStep = ConditionStep.QUALIFY;
        this.getQuery().addQualify(conditions);
        return this;
    }

    public final SelectImpl qualify(Collection<? extends Condition> conditions) {
        this.conditionStep = ConditionStep.QUALIFY;
        this.getQuery().addQualify(conditions);
        return this;
    }

    public final SelectImpl qualify(Field<Boolean> condition) {
        return this.qualify(DSL.condition(condition));
    }

    public final SelectImpl qualify(SQL sql) {
        return this.qualify(DSL.condition(sql));
    }

    public final SelectImpl qualify(String sql) {
        return this.qualify(DSL.condition(sql));
    }

    public final SelectImpl qualify(String sql, Object ... bindings) {
        return this.qualify(DSL.condition(sql, bindings));
    }

    public final SelectImpl qualify(String sql, QueryPart ... parts) {
        return this.qualify(DSL.condition(sql, parts));
    }

    public final SelectImpl on(Condition conditions) {
        this.conditionStep = ConditionStep.ON;
        this.joinConditions = new ConditionProviderImpl();
        this.joinConditions.addConditions(conditions);
        if (this.joinPartitionBy != null && this.joinPartitionBy.length > 0) {
            this.getQuery().addJoin(this.joinTable, this.joinType, (Condition)this.joinConditions, this.joinPartitionBy);
        } else {
            this.getQuery().addJoin(this.joinTable, this.joinType, (Condition)this.joinConditions);
        }
        this.joinTable = null;
        this.joinPartitionBy = null;
        this.joinType = null;
        return this;
    }

    public final SelectImpl on(Condition ... conditions) {
        this.conditionStep = ConditionStep.ON;
        this.joinConditions = new ConditionProviderImpl();
        this.joinConditions.addConditions(conditions);
        if (this.joinPartitionBy != null && this.joinPartitionBy.length > 0) {
            this.getQuery().addJoin(this.joinTable, this.joinType, new Condition[]{this.joinConditions}, this.joinPartitionBy);
        } else {
            this.getQuery().addJoin(this.joinTable, this.joinType, new Condition[]{this.joinConditions});
        }
        this.joinTable = null;
        this.joinPartitionBy = null;
        this.joinType = null;
        return this;
    }

    public final SelectImpl on(Field<Boolean> condition) {
        return this.on(DSL.condition(condition));
    }

    public final SelectImpl on(SQL sql) {
        return this.on(DSL.condition(sql));
    }

    public final SelectImpl on(String sql) {
        return this.on(DSL.condition(sql));
    }

    public final SelectImpl on(String sql, Object ... bindings) {
        return this.on(DSL.condition(sql, bindings));
    }

    public final SelectImpl on(String sql, QueryPart ... parts) {
        return this.on(DSL.condition(sql, parts));
    }

    public final SelectImpl onKey() {
        this.conditionStep = ConditionStep.ON;
        this.getQuery().addJoinOnKey(this.joinTable, this.joinType);
        this.joinConditions = ((JoinTable)((SelectQueryImpl)this.getDelegate()).getFrom().get((int)(((SelectQueryImpl)this.getDelegate()).getFrom().size() - 1))).condition;
        this.joinTable = null;
        this.joinPartitionBy = null;
        this.joinType = null;
        return this;
    }

    public final SelectImpl onKey(TableField<?, ?> ... keyFields) {
        this.conditionStep = ConditionStep.ON;
        this.getQuery().addJoinOnKey(this.joinTable, this.joinType, keyFields);
        this.joinConditions = ((JoinTable)((SelectQueryImpl)this.getDelegate()).getFrom().get((int)(((SelectQueryImpl)this.getDelegate()).getFrom().size() - 1))).condition;
        this.joinTable = null;
        this.joinPartitionBy = null;
        this.joinType = null;
        return this;
    }

    public final SelectImpl onKey(ForeignKey<?, ?> key) {
        this.conditionStep = ConditionStep.ON;
        this.getQuery().addJoinOnKey(this.joinTable, this.joinType, key);
        this.joinConditions = ((JoinTable)((SelectQueryImpl)this.getDelegate()).getFrom().get((int)(((SelectQueryImpl)this.getDelegate()).getFrom().size() - 1))).condition;
        this.joinTable = null;
        this.joinPartitionBy = null;
        this.joinType = null;
        return this;
    }

    public final SelectImpl using(Field<?> ... fields) {
        return this.using(Arrays.asList(fields));
    }

    public final SelectImpl using(Collection<? extends Field<?>> fields) {
        this.getQuery().addJoinUsing(this.joinTable, this.joinType, fields);
        this.joinTable = null;
        this.joinPartitionBy = null;
        this.joinType = null;
        return this;
    }

    public final SelectImpl join(TableLike<?> table) {
        return this.innerJoin((TableLike)table);
    }

    public final SelectImpl innerJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.JOIN);
    }

    public final SelectImpl leftJoin(TableLike<?> table) {
        return this.leftOuterJoin((TableLike)table);
    }

    public final SelectImpl leftOuterJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.LEFT_OUTER_JOIN);
    }

    public final SelectImpl rightJoin(TableLike<?> table) {
        return this.rightOuterJoin((TableLike)table);
    }

    public final SelectImpl rightOuterJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.RIGHT_OUTER_JOIN);
    }

    @Override
    public final SelectOnStep<R> fullJoin(TableLike<?> table) {
        return this.fullOuterJoin((TableLike)table);
    }

    public final SelectImpl fullOuterJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.FULL_OUTER_JOIN);
    }

    public final SelectImpl join(TableLike<?> table, JoinType type) {
        switch (type) {
            case CROSS_JOIN: 
            case NATURAL_JOIN: 
            case NATURAL_LEFT_OUTER_JOIN: 
            case NATURAL_RIGHT_OUTER_JOIN: 
            case NATURAL_FULL_OUTER_JOIN: 
            case CROSS_APPLY: 
            case OUTER_APPLY: {
                this.getQuery().addJoin(table, type, new Condition[0]);
                this.joinTable = null;
                this.joinPartitionBy = null;
                this.joinType = null;
                return this;
            }
        }
        this.conditionStep = ConditionStep.ON;
        this.joinTable = table;
        this.joinType = type;
        this.joinPartitionBy = null;
        this.joinConditions = null;
        return this;
    }

    public final SelectImpl crossJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.CROSS_JOIN);
    }

    public final SelectImpl naturalJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.NATURAL_JOIN);
    }

    public final SelectImpl naturalLeftOuterJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.NATURAL_LEFT_OUTER_JOIN);
    }

    public final SelectImpl naturalRightOuterJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.NATURAL_RIGHT_OUTER_JOIN);
    }

    public final SelectImpl naturalFullOuterJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.NATURAL_FULL_OUTER_JOIN);
    }

    public final SelectImpl leftSemiJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.LEFT_SEMI_JOIN);
    }

    public final SelectImpl leftAntiJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.LEFT_ANTI_JOIN);
    }

    public final SelectImpl crossApply(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.CROSS_APPLY);
    }

    public final SelectImpl outerApply(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.OUTER_APPLY);
    }

    public final SelectImpl straightJoin(TableLike<?> table) {
        return this.join((TableLike)table, JoinType.STRAIGHT_JOIN);
    }

    public final SelectImpl join(SQL sql) {
        return this.innerJoin(sql);
    }

    public final SelectImpl join(String sql) {
        return this.innerJoin(sql);
    }

    public final SelectImpl join(String sql, Object ... bindings) {
        return this.innerJoin(sql, bindings);
    }

    public final SelectImpl join(String sql, QueryPart ... parts) {
        return this.innerJoin(sql, parts);
    }

    public final SelectImpl join(Name name) {
        return this.innerJoin(DSL.table(name));
    }

    public final SelectImpl innerJoin(SQL sql) {
        return this.innerJoin(DSL.table(sql));
    }

    public final SelectImpl innerJoin(String sql) {
        return this.innerJoin(DSL.table(sql));
    }

    public final SelectImpl innerJoin(String sql, Object ... bindings) {
        return this.innerJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl innerJoin(String sql, QueryPart ... parts) {
        return this.innerJoin(DSL.table(sql, parts));
    }

    public final SelectImpl innerJoin(Name name) {
        return this.innerJoin(DSL.table(name));
    }

    public final SelectImpl leftJoin(SQL sql) {
        return this.leftOuterJoin(sql);
    }

    public final SelectImpl leftJoin(String sql) {
        return this.leftOuterJoin(sql);
    }

    public final SelectImpl leftJoin(String sql, Object ... bindings) {
        return this.leftOuterJoin(sql, bindings);
    }

    public final SelectImpl leftJoin(String sql, QueryPart ... parts) {
        return this.leftOuterJoin(sql, parts);
    }

    public final SelectImpl leftJoin(Name name) {
        return this.leftOuterJoin(DSL.table(name));
    }

    public final SelectImpl leftOuterJoin(SQL sql) {
        return this.leftOuterJoin(DSL.table(sql));
    }

    public final SelectImpl leftOuterJoin(String sql) {
        return this.leftOuterJoin(DSL.table(sql));
    }

    public final SelectImpl leftOuterJoin(String sql, Object ... bindings) {
        return this.leftOuterJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl leftOuterJoin(String sql, QueryPart ... parts) {
        return this.leftOuterJoin(DSL.table(sql, parts));
    }

    public final SelectImpl leftOuterJoin(Name name) {
        return this.leftOuterJoin(DSL.table(name));
    }

    public final SelectImpl rightJoin(SQL sql) {
        return this.rightOuterJoin(sql);
    }

    public final SelectImpl rightJoin(String sql) {
        return this.rightOuterJoin(sql);
    }

    public final SelectImpl rightJoin(String sql, Object ... bindings) {
        return this.rightOuterJoin(sql, bindings);
    }

    public final SelectImpl rightJoin(String sql, QueryPart ... parts) {
        return this.rightOuterJoin(sql, parts);
    }

    public final SelectImpl rightJoin(Name name) {
        return this.rightOuterJoin(DSL.table(name));
    }

    public final SelectImpl rightOuterJoin(SQL sql) {
        return this.rightOuterJoin(DSL.table(sql));
    }

    public final SelectImpl rightOuterJoin(String sql) {
        return this.rightOuterJoin(DSL.table(sql));
    }

    public final SelectImpl rightOuterJoin(String sql, Object ... bindings) {
        return this.rightOuterJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl rightOuterJoin(String sql, QueryPart ... parts) {
        return this.rightOuterJoin(DSL.table(sql, parts));
    }

    public final SelectImpl rightOuterJoin(Name name) {
        return this.rightOuterJoin(DSL.table(name));
    }

    public final SelectImpl fullJoin(SQL sql) {
        return this.fullOuterJoin(sql);
    }

    public final SelectImpl fullJoin(String sql) {
        return this.fullOuterJoin(sql);
    }

    public final SelectImpl fullJoin(String sql, Object ... bindings) {
        return this.fullOuterJoin(sql, bindings);
    }

    public final SelectImpl fullJoin(String sql, QueryPart ... parts) {
        return this.fullOuterJoin(sql, parts);
    }

    public final SelectImpl fullJoin(Name name) {
        return this.fullOuterJoin(name);
    }

    public final SelectImpl fullOuterJoin(SQL sql) {
        return this.fullOuterJoin(DSL.table(sql));
    }

    public final SelectImpl fullOuterJoin(String sql) {
        return this.fullOuterJoin(DSL.table(sql));
    }

    public final SelectImpl fullOuterJoin(String sql, Object ... bindings) {
        return this.fullOuterJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl fullOuterJoin(String sql, QueryPart ... parts) {
        return this.fullOuterJoin(DSL.table(sql, parts));
    }

    public final SelectImpl fullOuterJoin(Name name) {
        return this.fullOuterJoin(DSL.table(name));
    }

    @Override
    public final SelectJoinStep<R> crossJoin(SQL sql) {
        return this.crossJoin(DSL.table(sql));
    }

    @Override
    public final SelectJoinStep<R> crossJoin(String sql) {
        return this.crossJoin(DSL.table(sql));
    }

    @Override
    public final SelectJoinStep<R> crossJoin(String sql, Object ... bindings) {
        return this.crossJoin(DSL.table(sql, bindings));
    }

    @Override
    public final SelectJoinStep<R> crossJoin(String sql, QueryPart ... parts) {
        return this.crossJoin(DSL.table(sql, parts));
    }

    public final SelectImpl crossJoin(Name name) {
        return this.crossJoin(DSL.table(name));
    }

    public final SelectImpl naturalJoin(SQL sql) {
        return this.naturalJoin(DSL.table(sql));
    }

    public final SelectImpl naturalJoin(String sql) {
        return this.naturalJoin(DSL.table(sql));
    }

    public final SelectImpl naturalJoin(String sql, Object ... bindings) {
        return this.naturalJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl naturalJoin(String sql, QueryPart ... parts) {
        return this.naturalJoin(DSL.table(sql, parts));
    }

    public final SelectImpl naturalJoin(Name name) {
        return this.naturalJoin(DSL.table(name));
    }

    public final SelectImpl naturalLeftOuterJoin(SQL sql) {
        return this.naturalLeftOuterJoin(DSL.table(sql));
    }

    public final SelectImpl naturalLeftOuterJoin(String sql) {
        return this.naturalLeftOuterJoin(DSL.table(sql));
    }

    public final SelectImpl naturalLeftOuterJoin(String sql, Object ... bindings) {
        return this.naturalLeftOuterJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl naturalLeftOuterJoin(String sql, QueryPart ... parts) {
        return this.naturalLeftOuterJoin(DSL.table(sql, parts));
    }

    public final SelectImpl naturalLeftOuterJoin(Name name) {
        return this.naturalLeftOuterJoin(DSL.table(name));
    }

    public final SelectImpl naturalRightOuterJoin(SQL sql) {
        return this.naturalRightOuterJoin(DSL.table(sql));
    }

    public final SelectImpl naturalRightOuterJoin(String sql) {
        return this.naturalRightOuterJoin(DSL.table(sql));
    }

    public final SelectImpl naturalRightOuterJoin(String sql, Object ... bindings) {
        return this.naturalRightOuterJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl naturalRightOuterJoin(String sql, QueryPart ... parts) {
        return this.naturalRightOuterJoin(DSL.table(sql, parts));
    }

    public final SelectImpl naturalRightOuterJoin(Name name) {
        return this.naturalRightOuterJoin(DSL.table(name));
    }

    public final SelectImpl naturalFullOuterJoin(SQL sql) {
        return this.naturalFullOuterJoin(DSL.table(sql));
    }

    public final SelectImpl naturalFullOuterJoin(String sql) {
        return this.naturalFullOuterJoin(DSL.table(sql));
    }

    public final SelectImpl naturalFullOuterJoin(String sql, Object ... bindings) {
        return this.naturalFullOuterJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl naturalFullOuterJoin(String sql, QueryPart ... parts) {
        return this.naturalFullOuterJoin(DSL.table(sql, parts));
    }

    public final SelectImpl naturalFullOuterJoin(Name name) {
        return this.naturalFullOuterJoin(DSL.table(name));
    }

    public final SelectImpl crossApply(SQL sql) {
        return this.crossApply(DSL.table(sql));
    }

    public final SelectImpl crossApply(String sql) {
        return this.crossApply(DSL.table(sql));
    }

    public final SelectImpl crossApply(String sql, Object ... bindings) {
        return this.crossApply(DSL.table(sql, bindings));
    }

    public final SelectImpl crossApply(String sql, QueryPart ... parts) {
        return this.crossApply(DSL.table(sql, parts));
    }

    public final SelectImpl crossApply(Name name) {
        return this.crossApply(DSL.table(name));
    }

    public final SelectImpl outerApply(SQL sql) {
        return this.outerApply(DSL.table(sql));
    }

    public final SelectImpl outerApply(String sql) {
        return this.outerApply(DSL.table(sql));
    }

    public final SelectImpl outerApply(String sql, Object ... bindings) {
        return this.outerApply(DSL.table(sql, bindings));
    }

    public final SelectImpl outerApply(String sql, QueryPart ... parts) {
        return this.outerApply(DSL.table(sql, parts));
    }

    public final SelectImpl outerApply(Name name) {
        return this.outerApply(DSL.table(name));
    }

    @Pro
    public final SelectImpl partitionBy(Field<?> ... fields) {
        this.joinPartitionBy = fields;
        return this;
    }

    @Pro
    public final SelectImpl partitionBy(Collection<? extends Field<?>> fields) {
        return this.partitionBy((Field[])fields.toArray(Tools.EMPTY_FIELD));
    }

    public final SelectImpl straightJoin(SQL sql) {
        return this.straightJoin(DSL.table(sql));
    }

    public final SelectImpl straightJoin(String sql) {
        return this.straightJoin(DSL.table(sql));
    }

    public final SelectImpl straightJoin(String sql, Object ... bindings) {
        return this.straightJoin(DSL.table(sql, bindings));
    }

    public final SelectImpl straightJoin(String sql, QueryPart ... parts) {
        return this.straightJoin(DSL.table(sql, parts));
    }

    public final SelectImpl straightJoin(Name name) {
        return this.straightJoin(DSL.table(name));
    }

    @Override
    public final CloseableResultQuery<R> maxRows(int rows) {
        return ((SelectQueryImpl)this.getDelegate()).maxRows(rows);
    }

    @Override
    public final CloseableResultQuery<R> fetchSize(int rows) {
        return ((SelectQueryImpl)this.getDelegate()).fetchSize(rows);
    }

    @Override
    public final CloseableResultQuery<R> resultSetConcurrency(int resultSetConcurrency) {
        return ((SelectQueryImpl)this.getDelegate()).resultSetConcurrency(resultSetConcurrency);
    }

    @Override
    public final CloseableResultQuery<R> resultSetType(int resultSetType) {
        return ((SelectQueryImpl)this.getDelegate()).resultSetType(resultSetType);
    }

    @Override
    public final CloseableResultQuery<R> resultSetHoldability(int resultSetHoldability) {
        return ((SelectQueryImpl)this.getDelegate()).resultSetHoldability(resultSetHoldability);
    }

    @Override
    public final CloseableResultQuery<R> intern(Field<?> ... fields) {
        return ((SelectQueryImpl)this.getDelegate()).intern((Field[])fields);
    }

    @Override
    public final CloseableResultQuery<R> intern(int ... fieldIndexes) {
        return ((SelectQueryImpl)this.getDelegate()).intern(fieldIndexes);
    }

    @Override
    public final CloseableResultQuery<R> intern(String ... fieldNames) {
        return ((SelectQueryImpl)this.getDelegate()).intern(fieldNames);
    }

    @Override
    public final CloseableResultQuery<R> intern(Name ... fieldNames) {
        return ((SelectQueryImpl)this.getDelegate()).intern(fieldNames);
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return ((SelectQueryImpl)this.getDelegate()).getRecordType();
    }

    @Override
    public final List<Field<?>> getSelect() {
        return ((SelectQueryImpl)this.getDelegate()).getSelect();
    }

    @Override
    public final Result<R> getResult() {
        return ((SelectQueryImpl)this.getDelegate()).getResult();
    }

    @Override
    public final Result<R> fetch() {
        return ((SelectQueryImpl)this.getDelegate()).fetch();
    }

    @Override
    public final void subscribe(Flow.Subscriber<? super R> subscriber) {
        ((SelectQueryImpl)this.getDelegate()).subscribe(subscriber);
    }

    @Override
    public final void subscribe(Subscriber<? super R> subscriber) {
        ((SelectQueryImpl)this.getDelegate()).subscribe(subscriber);
    }

    @Override
    public final Cursor<R> fetchLazy() {
        return ((SelectQueryImpl)this.getDelegate()).fetchLazy();
    }

    @Override
    public final Cursor<R> fetchLazyNonAutoClosing() {
        return ((SelectQueryImpl)this.getDelegate()).fetchLazyNonAutoClosing();
    }

    @Override
    public final Results fetchMany() {
        return ((SelectQueryImpl)this.getDelegate()).fetchMany();
    }

    @Override
    public final Field<Result<R>> asMultiset() {
        return ((SelectQueryImpl)this.getDelegate()).asMultiset();
    }

    @Override
    public final Field<Result<R>> asMultiset(String alias) {
        return ((SelectQueryImpl)this.getDelegate()).asMultiset(alias);
    }

    @Override
    public final Field<Result<R>> asMultiset(Name alias) {
        return ((SelectQueryImpl)this.getDelegate()).asMultiset(alias);
    }

    @Override
    public final Field<Result<R>> asMultiset(Field<?> alias) {
        return ((SelectQueryImpl)this.getDelegate()).asMultiset(alias);
    }

    @Override
    public final Table<R> asTable() {
        return ((SelectQueryImpl)this.getDelegate()).asTable();
    }

    @Override
    public final Table<R> asTable(String alias) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias);
    }

    @Override
    public final Table<R> asTable(String alias, String ... fieldAliases) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(String alias, Collection<? extends String> fieldAliases) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Name alias) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias);
    }

    @Override
    public final Table<R> asTable(Name alias, Name ... fieldAliases) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Name alias, Collection<? extends Name> fieldAliases) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Table<?> alias) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias);
    }

    @Override
    public final Table<R> asTable(Table<?> alias, Field<?> ... fieldAliases) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(Table<?> alias, Collection<? extends Field<?>> fieldAliases) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias, fieldAliases);
    }

    @Override
    public final Table<R> asTable(String alias, Function<? super Field<?>, ? extends String> aliasFunction) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias, aliasFunction);
    }

    @Override
    public final Table<R> asTable(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> aliasFunction) {
        return ((SelectQueryImpl)this.getDelegate()).asTable(alias, aliasFunction);
    }

    @Override
    public final <T> Field<T> asField() {
        return ((SelectQueryImpl)this.getDelegate()).asField();
    }

    @Override
    public final <T> Field<T> asField(String alias) {
        return ((SelectQueryImpl)this.getDelegate()).asField(alias);
    }

    @Override
    public final <T> Field<T> asField(Function<? super Field<T>, ? extends String> aliasFunction) {
        return ((SelectQueryImpl)this.getDelegate()).asField(aliasFunction);
    }

    @Override
    public final Row fieldsRow() {
        return ((SelectQueryImpl)this.getDelegate()).fieldsRow();
    }

    @Override
    public final <X extends Record> CloseableResultQuery<X> coerce(Table<X> table) {
        return ((SelectQueryImpl)this.getDelegate()).coerce((Table)table);
    }

    @Override
    public final CloseableResultQuery<Record> coerce(Collection<? extends Field<?>> fields) {
        return ((SelectQueryImpl)this.getDelegate()).coerce((Collection)fields);
    }

    @Override
    public final Condition compare(Comparator comparator, R record) {
        if (Tools.degree(this) == 1) {
            return DSL.field(this).compare(comparator, record.get(0, this.field(0).getType()));
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition compare(Comparator comparator, Select<? extends R> select) {
        if (Tools.degree(this) == 1) {
            return DSL.field(this).compare(comparator, select);
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition compare(Comparator comparator, QuantifiedSelect<? extends R> select) {
        if (Tools.degree(this) == 1) {
            return DSL.field(this).compare(comparator, select);
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition eq(R record) {
        return this.compare(Comparator.EQUALS, record);
    }

    @Override
    public final Condition eq(Select<? extends R> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition eq(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition equal(R record) {
        return this.compare(Comparator.EQUALS, record);
    }

    @Override
    public final Condition equal(Select<? extends R> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition equal(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.EQUALS, select);
    }

    @Override
    public final Condition ne(R record) {
        return this.compare(Comparator.NOT_EQUALS, record);
    }

    @Override
    public final Condition ne(Select<? extends R> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition ne(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition notEqual(R record) {
        return this.compare(Comparator.NOT_EQUALS, record);
    }

    @Override
    public final Condition notEqual(Select<? extends R> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition notEqual(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.NOT_EQUALS, select);
    }

    @Override
    public final Condition lt(R record) {
        return this.compare(Comparator.LESS, record);
    }

    @Override
    public final Condition lt(Select<? extends R> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lt(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lessThan(R record) {
        return this.compare(Comparator.LESS, record);
    }

    @Override
    public final Condition lessThan(Select<? extends R> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition lessThan(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.LESS, select);
    }

    @Override
    public final Condition le(R record) {
        return this.compare(Comparator.LESS_OR_EQUAL, record);
    }

    @Override
    public final Condition le(Select<? extends R> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition le(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition lessOrEqual(R record) {
        return this.compare(Comparator.LESS_OR_EQUAL, record);
    }

    @Override
    public final Condition lessOrEqual(Select<? extends R> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition lessOrEqual(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.LESS_OR_EQUAL, select);
    }

    @Override
    public final Condition gt(R record) {
        return this.compare(Comparator.GREATER, record);
    }

    @Override
    public final Condition gt(Select<? extends R> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition gt(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition greaterThan(R record) {
        return this.compare(Comparator.GREATER, record);
    }

    @Override
    public final Condition greaterThan(Select<? extends R> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition greaterThan(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.GREATER, select);
    }

    @Override
    public final Condition ge(R record) {
        return this.compare(Comparator.GREATER_OR_EQUAL, record);
    }

    @Override
    public final Condition ge(Select<? extends R> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition ge(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition greaterOrEqual(R record) {
        return this.compare(Comparator.GREATER_OR_EQUAL, record);
    }

    @Override
    public final Condition greaterOrEqual(Select<? extends R> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    @Override
    public final Condition greaterOrEqual(QuantifiedSelect<? extends R> select) {
        return this.compare(Comparator.GREATER_OR_EQUAL, select);
    }

    private final Object[] values(int index, R ... records) {
        Class type = this.field(0).getType();
        return Tools.map(records, r -> r.get(index, type), Object[]::new);
    }

    @Override
    public final Condition in(R ... records) {
        if (Tools.degree(this) == 1) {
            return DSL.field(this).in(this.values(0, (Record[])records));
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition in(Select<? extends R> select) {
        if (Tools.degree(this) == 1) {
            return DSL.field(this).in(select);
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition notIn(R ... records) {
        if (Tools.degree(this) == 1) {
            return DSL.field(this).notIn(this.values(0, (Record[])records));
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition notIn(Select<? extends R> select) {
        if (Tools.degree(this) == 1) {
            return DSL.field(this).notIn(select);
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition isDistinctFrom(R record) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition isDistinctFrom(Select<? extends R> select) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition isDistinctFrom(QuantifiedSelect<? extends R> select) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition isNotDistinctFrom(R record) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition isNotDistinctFrom(Select<? extends R> select) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition isNotDistinctFrom(QuantifiedSelect<? extends R> select) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final BetweenAndStep<R> between(R minValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition between(R minValue, R maxValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final BetweenAndStep<R> between(Select<? extends R> minValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition between(Select<? extends R> minValue, Select<? extends R> maxValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final BetweenAndStepR<R> betweenSymmetric(R minValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition betweenSymmetric(R minValue, R maxValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final BetweenAndStepR<R> betweenSymmetric(Select<? extends R> minValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition betweenSymmetric(Select<? extends R> minValue, Select<? extends R> maxValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final BetweenAndStepR<R> notBetween(R minValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition notBetween(R minValue, R maxValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final BetweenAndStepR<R> notBetween(Select<? extends R> minValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition notBetween(Select<? extends R> minValue, Select<? extends R> maxValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final BetweenAndStepR<R> notBetweenSymmetric(R minValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition notBetweenSymmetric(R minValue, R maxValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final BetweenAndStepR<R> notBetweenSymmetric(Select<? extends R> minValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition notBetweenSymmetric(Select<? extends R> minValue, Select<? extends R> maxValue) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Condition isNull() {
        return new SelectIsNull(this);
    }

    @Override
    public final Condition isNotNull() {
        return new SelectIsNotNull(this);
    }

    @Override
    public final Field<?>[] getFields(ThrowingSupplier<? extends ResultSetMetaData, SQLException> rs) throws SQLException {
        return ((SelectQueryImpl)this.getDelegate()).getFields(rs);
    }

    @Override
    public final Field<?>[] getFields() {
        return ((SelectQueryImpl)this.getDelegate()).getFields();
    }

    @Override
    public final QOM.With $with() {
        return ((SelectQueryImpl)this.getDelegate()).$with();
    }

    @Override
    public final Select<?> $with(QOM.With newWith) {
        return ((SelectQueryImpl)this.getDelegate()).$with(newWith);
    }

    @Override
    public final QOM.UnmodifiableList<? extends SelectFieldOrAsterisk> $select() {
        return ((SelectQueryImpl)this.getDelegate()).$select();
    }

    @Override
    public final Select<?> $select(Collection<? extends SelectFieldOrAsterisk> newSelect) {
        return ((SelectQueryImpl)this.getDelegate()).$select((Collection)newSelect);
    }

    @Override
    public final boolean $distinct() {
        return ((SelectQueryImpl)this.getDelegate()).$distinct();
    }

    @Override
    public final Select<R> $distinct(boolean newDistinct) {
        return ((SelectQueryImpl)this.getDelegate()).$distinct(newDistinct);
    }

    @Override
    public final QOM.UnmodifiableList<? extends SelectFieldOrAsterisk> $distinctOn() {
        return ((SelectQueryImpl)this.getDelegate()).$distinctOn();
    }

    @Override
    public final Select<R> $distinctOn(Collection<? extends SelectFieldOrAsterisk> newDistinctOn) {
        return ((SelectQueryImpl)this.getDelegate()).$distinctOn(newDistinctOn);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Table<?>> $from() {
        return ((SelectQueryImpl)this.getDelegate()).$from();
    }

    @Override
    public final Select<R> $from(Collection<? extends Table<?>> newFrom) {
        return ((SelectQueryImpl)this.getDelegate()).$from((Collection)newFrom);
    }

    @Override
    public final Condition $where() {
        return ((SelectQueryImpl)this.getDelegate()).$where();
    }

    @Override
    public final Select<R> $where(Condition newWhere) {
        return ((SelectQueryImpl)this.getDelegate()).$where(newWhere);
    }

    @Override
    public final QOM.UnmodifiableList<? extends GroupField> $groupBy() {
        return ((SelectQueryImpl)this.getDelegate()).$groupBy();
    }

    @Override
    public final Select<R> $groupBy(Collection<? extends GroupField> newGroupBy) {
        return ((SelectQueryImpl)this.getDelegate()).$groupBy(newGroupBy);
    }

    @Override
    public final boolean $groupByDistinct() {
        return ((SelectQueryImpl)this.getDelegate()).$groupByDistinct();
    }

    @Override
    public final Select<R> $groupByDistinct(boolean newGroupByDistinct) {
        return ((SelectQueryImpl)this.getDelegate()).$groupByDistinct(newGroupByDistinct);
    }

    @Override
    public final Condition $having() {
        return ((SelectQueryImpl)this.getDelegate()).$having();
    }

    @Override
    public final Select<R> $having(Condition newHaving) {
        return ((SelectQueryImpl)this.getDelegate()).$having(newHaving);
    }

    @Override
    public final QOM.UnmodifiableList<? extends WindowDefinition> $window() {
        return ((SelectQueryImpl)this.getDelegate()).$window();
    }

    @Override
    public final Select<R> $window(Collection<? extends WindowDefinition> newWindow) {
        return ((SelectQueryImpl)this.getDelegate()).$window(newWindow);
    }

    @Override
    public final Condition $qualify() {
        return ((SelectQueryImpl)this.getDelegate()).$qualify();
    }

    @Override
    public final Select<R> $qualify(Condition newQualify) {
        return ((SelectQueryImpl)this.getDelegate()).$qualify(newQualify);
    }

    @Override
    public QOM.UnmodifiableList<? extends SortField<?>> $orderBy() {
        return ((SelectQueryImpl)this.getDelegate()).$orderBy();
    }

    @Override
    public final Select<R> $orderBy(Collection<? extends SortField<?>> newOrderBy) {
        return ((SelectQueryImpl)this.getDelegate()).$orderBy(newOrderBy);
    }

    @Override
    public final Field<? extends Number> $limit() {
        return ((SelectQueryImpl)this.getDelegate()).$limit();
    }

    @Override
    public final Select<R> $limit(Field<? extends Number> newLimit) {
        return ((SelectQueryImpl)this.getDelegate()).$limit(newLimit);
    }

    @Override
    public final boolean $limitPercent() {
        return ((SelectQueryImpl)this.getDelegate()).$limitPercent();
    }

    @Override
    public final Select<R> $limitPercent(boolean newLimitPercent) {
        return ((SelectQueryImpl)this.getDelegate()).$limitPercent(newLimitPercent);
    }

    @Override
    public final boolean $limitWithTies() {
        return ((SelectQueryImpl)this.getDelegate()).$limitWithTies();
    }

    @Override
    public final Select<R> $limitWithTies(boolean newLimitWithTies) {
        return ((SelectQueryImpl)this.getDelegate()).$limitWithTies(newLimitWithTies);
    }

    @Override
    public final Field<? extends Number> $offset() {
        return ((SelectQueryImpl)this.getDelegate()).$offset();
    }

    @Override
    public final Select<R> $offset(Field<? extends Number> newOffset) {
        return ((SelectQueryImpl)this.getDelegate()).$offset(newOffset);
    }

    @Override
    public final Condition $connectBy() {
        return ((SelectQueryImpl)this.getDelegate()).$connectBy();
    }

    @Override
    public final Select<R> $connectBy(Condition newConnectBy) {
        return ((SelectQueryImpl)this.getDelegate()).$connectBy(newConnectBy);
    }

    @Override
    public final boolean $connectByNoCycle() {
        return ((SelectQueryImpl)this.getDelegate()).$connectByNoCycle();
    }

    @Override
    public final Select<R> $connectByNoCycle(boolean newConnectByNoCycle) {
        return ((SelectQueryImpl)this.getDelegate()).$connectByNoCycle(newConnectByNoCycle);
    }

    @Override
    public final Condition $connectByStartWith() {
        return ((SelectQueryImpl)this.getDelegate()).$connectByStartWith();
    }

    @Override
    public final Select<R> $connectByStartWith(Condition newConnectByStartWith) {
        return ((SelectQueryImpl)this.getDelegate()).$connectByStartWith(newConnectByStartWith);
    }

    private static enum ConditionStep {
        ON,
        WHERE,
        CONNECT_BY,
        HAVING,
        QUALIFY;

    }
}

