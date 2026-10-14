/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DeleteConditionStep;
import org.jooq.DeleteResultStep;
import org.jooq.DeleteUsingStep;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.OrderField;
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
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDelegatingDMLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.DeleteAsResultQuery;
import org.jooq.impl.DeleteQueryImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.WithImpl;

final class DeleteImpl<R extends Record>
extends AbstractDelegatingDMLQuery<R, DeleteQueryImpl<R>>
implements DeleteUsingStep<R>,
DeleteConditionStep<R>,
QOM.Delete<R> {
    private boolean returningResult;

    DeleteImpl(Configuration configuration, WithImpl with, Table<R> table) {
        super(new DeleteQueryImpl<R>(configuration, with, table));
    }

    @Override
    public final DeleteImpl<R> using(TableLike<?> table) {
        ((DeleteQueryImpl)this.getDelegate()).addUsing(table);
        return this;
    }

    @Override
    public final DeleteImpl<R> using(TableLike<?> ... tables) {
        ((DeleteQueryImpl)this.getDelegate()).addUsing(tables);
        return this;
    }

    @Override
    public final DeleteImpl<R> using(Collection<? extends TableLike<?>> tables) {
        ((DeleteQueryImpl)this.getDelegate()).addUsing(tables);
        return this;
    }

    @Override
    public final DeleteImpl<R> using(SQL sql) {
        return this.using(DSL.table(sql));
    }

    @Override
    public final DeleteImpl<R> using(String sql) {
        return this.using(DSL.table(sql));
    }

    @Override
    public final DeleteImpl<R> using(String sql, Object ... bindings) {
        return this.using(DSL.table(sql, bindings));
    }

    @Override
    public final DeleteImpl<R> using(String sql, QueryPart ... parts) {
        return this.using(DSL.table(sql, parts));
    }

    @Override
    public final DeleteImpl<R> using(Name name) {
        return this.using(DSL.table(name));
    }

    @Override
    public final DeleteImpl<R> where(Condition conditions) {
        ((DeleteQueryImpl)this.getDelegate()).addConditions(conditions);
        return this;
    }

    @Override
    public final DeleteImpl<R> where(Condition ... conditions) {
        ((DeleteQueryImpl)this.getDelegate()).addConditions(conditions);
        return this;
    }

    @Override
    public final DeleteImpl<R> where(Collection<? extends Condition> conditions) {
        ((DeleteQueryImpl)this.getDelegate()).addConditions(conditions);
        return this;
    }

    @Override
    public final DeleteImpl<R> where(Field<Boolean> condition) {
        return this.where(DSL.condition(condition));
    }

    @Override
    public final DeleteImpl<R> where(SQL sql) {
        return this.where(DSL.condition(sql));
    }

    @Override
    public final DeleteImpl<R> where(String sql) {
        return this.where(DSL.condition(sql));
    }

    @Override
    public final DeleteImpl<R> where(String sql, Object ... bindings) {
        return this.where(DSL.condition(sql, bindings));
    }

    @Override
    public final DeleteImpl<R> where(String sql, QueryPart ... parts) {
        return this.where(DSL.condition(sql, parts));
    }

    @Override
    public final DeleteImpl<R> whereExists(Select<?> select) {
        return this.andExists((Select)select);
    }

    @Override
    public final DeleteImpl<R> whereNotExists(Select<?> select) {
        return this.andNotExists((Select)select);
    }

    @Override
    public final DeleteImpl<R> and(Condition condition) {
        ((DeleteQueryImpl)this.getDelegate()).addConditions(condition);
        return this;
    }

    @Override
    public final DeleteImpl<R> and(Field<Boolean> condition) {
        return this.and(DSL.condition(condition));
    }

    @Override
    public final DeleteImpl<R> and(SQL sql) {
        return this.and(DSL.condition(sql));
    }

    @Override
    public final DeleteImpl<R> and(String sql) {
        return this.and(DSL.condition(sql));
    }

    @Override
    public final DeleteImpl<R> and(String sql, Object ... bindings) {
        return this.and(DSL.condition(sql, bindings));
    }

    @Override
    public final DeleteImpl<R> and(String sql, QueryPart ... parts) {
        return this.and(DSL.condition(sql, parts));
    }

    @Override
    public final DeleteImpl<R> andNot(Condition condition) {
        return this.and(condition.not());
    }

    @Override
    public final DeleteImpl<R> andNot(Field<Boolean> condition) {
        return this.andNot(DSL.condition(condition));
    }

    @Override
    public final DeleteImpl<R> andExists(Select<?> select) {
        return this.and(DSL.exists(select));
    }

    @Override
    public final DeleteImpl<R> andNotExists(Select<?> select) {
        return this.and(DSL.notExists(select));
    }

    @Override
    public final DeleteImpl<R> or(Condition condition) {
        ((DeleteQueryImpl)this.getDelegate()).addConditions(Operator.OR, condition);
        return this;
    }

    @Override
    public final DeleteImpl<R> or(Field<Boolean> condition) {
        return this.or(DSL.condition(condition));
    }

    @Override
    public final DeleteImpl<R> or(SQL sql) {
        return this.or(DSL.condition(sql));
    }

    @Override
    public final DeleteImpl<R> or(String sql) {
        return this.or(DSL.condition(sql));
    }

    @Override
    public final DeleteImpl<R> or(String sql, Object ... bindings) {
        return this.or(DSL.condition(sql, bindings));
    }

    @Override
    public final DeleteImpl<R> or(String sql, QueryPart ... parts) {
        return this.or(DSL.condition(sql, parts));
    }

    @Override
    public final DeleteImpl<R> orNot(Condition condition) {
        return this.or(condition.not());
    }

    @Override
    public final DeleteImpl<R> orNot(Field<Boolean> condition) {
        return this.orNot(DSL.condition(condition));
    }

    @Override
    public final DeleteImpl<R> orExists(Select<?> select) {
        return this.or(DSL.exists(select));
    }

    @Override
    public final DeleteImpl<R> orNotExists(Select<?> select) {
        return this.or(DSL.notExists(select));
    }

    @Override
    public final DeleteImpl<R> orderBy(OrderField<?> ... fields) {
        ((DeleteQueryImpl)this.getDelegate()).addOrderBy(fields);
        return this;
    }

    @Override
    public final DeleteImpl<R> orderBy(Collection<? extends OrderField<?>> fields) {
        ((DeleteQueryImpl)this.getDelegate()).addOrderBy(fields);
        return this;
    }

    @Override
    public final DeleteImpl<R> orderBy(int ... fieldIndexes) {
        return this.orderBy(Tools.map(fieldIndexes, v -> DSL.inline(v)));
    }

    @Override
    public final DeleteImpl<R> limit(Number numberOfRows) {
        ((DeleteQueryImpl)this.getDelegate()).addLimit(numberOfRows);
        return this;
    }

    @Override
    public final DeleteImpl<R> limit(Field<? extends Number> numberOfRows) {
        ((DeleteQueryImpl)this.getDelegate()).addLimit(numberOfRows);
        return this;
    }

    @Override
    public final DeleteResultStep<R> returning() {
        ((DeleteQueryImpl)this.getDelegate()).setReturning();
        return new DeleteAsResultQuery((DeleteQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final DeleteResultStep<R> returning(SelectFieldOrAsterisk ... f) {
        ((DeleteQueryImpl)this.getDelegate()).setReturning(f);
        return new DeleteAsResultQuery((DeleteQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final DeleteResultStep<R> returning(Collection<? extends SelectFieldOrAsterisk> f) {
        ((DeleteQueryImpl)this.getDelegate()).setReturning(f);
        return new DeleteAsResultQuery((DeleteQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final DeleteResultStep<Record> returningResult(SelectFieldOrAsterisk ... f) {
        this.returningResult = true;
        ((DeleteQueryImpl)this.getDelegate()).setReturning(f);
        return new DeleteAsResultQuery<Record>((DeleteQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final DeleteResultStep<Record> returningResult(Collection<? extends SelectFieldOrAsterisk> f) {
        this.returningResult = true;
        ((DeleteQueryImpl)this.getDelegate()).setReturning(f);
        return new DeleteAsResultQuery<Record>((DeleteQueryImpl)this.getDelegate(), this.returningResult);
    }

    @Override
    public final <T1> DeleteResultStep<Record1<T1>> returningResult(SelectField<T1> field1) {
        return this.returningResult(new SelectField[]{field1});
    }

    @Override
    public final <T1, T2> DeleteResultStep<Record2<T1, T2>> returningResult(SelectField<T1> field1, SelectField<T2> field2) {
        return this.returningResult(new SelectField[]{field1, field2});
    }

    @Override
    public final <T1, T2, T3> DeleteResultStep<Record3<T1, T2, T3>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return this.returningResult(new SelectField[]{field1, field2, field3});
    }

    @Override
    public final <T1, T2, T3, T4> DeleteResultStep<Record4<T1, T2, T3, T4>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4});
    }

    @Override
    public final <T1, T2, T3, T4, T5> DeleteResultStep<Record5<T1, T2, T3, T4, T5>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> DeleteResultStep<Record6<T1, T2, T3, T4, T5, T6>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> DeleteResultStep<Record7<T1, T2, T3, T4, T5, T6, T7>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> DeleteResultStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> DeleteResultStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> DeleteResultStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> DeleteResultStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> DeleteResultStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> DeleteResultStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> DeleteResultStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> DeleteResultStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> DeleteResultStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> DeleteResultStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> DeleteResultStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> DeleteResultStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> DeleteResultStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> DeleteResultStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> DeleteResultStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> returningResult(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return this.returningResult(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public final QOM.With $with() {
        return ((DeleteQueryImpl)this.getDelegate()).$with();
    }

    @Override
    public final Table<R> $from() {
        return ((DeleteQueryImpl)this.getDelegate()).$from();
    }

    @Override
    public final QOM.Delete<?> $from(Table<?> from) {
        return ((DeleteQueryImpl)this.getDelegate()).$from(from);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Table<?>> $using() {
        return ((DeleteQueryImpl)this.getDelegate()).$using();
    }

    @Override
    public final QOM.Delete<R> $using(Collection<? extends Table<?>> using2) {
        return ((DeleteQueryImpl)this.getDelegate()).$using(using2);
    }

    @Override
    public final Condition $where() {
        return ((DeleteQueryImpl)this.getDelegate()).$where();
    }

    @Override
    public final QOM.Delete<R> $where(Condition condition) {
        return ((DeleteQueryImpl)this.getDelegate()).$where(condition);
    }

    @Override
    public final QOM.UnmodifiableList<? extends SortField<?>> $orderBy() {
        return ((DeleteQueryImpl)this.getDelegate()).$orderBy();
    }

    @Override
    public final QOM.Delete<R> $orderBy(Collection<? extends SortField<?>> orderBy) {
        return ((DeleteQueryImpl)this.getDelegate()).$orderBy(orderBy);
    }

    @Override
    public final Field<? extends Number> $limit() {
        return ((DeleteQueryImpl)this.getDelegate()).$limit();
    }

    @Override
    public final QOM.Delete<R> $limit(Field<? extends Number> limit) {
        return ((DeleteQueryImpl)this.getDelegate()).$limit(limit);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return ((DeleteQueryImpl)this.getDelegate()).$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return ((DeleteQueryImpl)this.getDelegate()).$replace(replacer);
    }
}

