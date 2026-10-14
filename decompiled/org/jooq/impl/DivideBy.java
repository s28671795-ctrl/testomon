/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.DivideByOnConditionStep;
import org.jooq.DivideByOnStep;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.QueryPartList;

final class DivideBy
implements DivideByOnStep,
DivideByOnConditionStep {
    private final Table<?> dividend;
    private final Table<?> divisor;
    private final ConditionProviderImpl condition;
    private final QueryPartList<Field<?>> returning;

    DivideBy(Table<?> dividend, Table<?> divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
        this.condition = new ConditionProviderImpl();
        this.returning = new QueryPartList();
    }

    private final Table<Record> table() {
        ConditionProviderImpl selfJoin = new ConditionProviderImpl();
        ArrayList select = new ArrayList(this.returning.size());
        SelectField outer = this.dividend.as("dividend");
        for (Field field2 : this.returning) {
            Field outerField = outer.field(field2);
            if (outerField == null) {
                select.add(field2);
                continue;
            }
            select.add(outerField);
            this.selfJoin(selfJoin, (Table<?>)outer, this.dividend, field2);
        }
        return DSL.selectDistinct(select).from((TableLike<?>)((Object)outer)).whereNotExists(DSL.selectOne().from((TableLike<?>)this.divisor).whereNotExists(DSL.selectOne().from((TableLike<?>)this.dividend).where((Condition)selfJoin).and(this.condition))).asTable();
    }

    private final <T> void selfJoin(ConditionProvider selfJoin, Table<?> outer, Table<?> inner, Field<T> field2) {
        Field<T> outerField = outer.field(field2);
        Field<T> innerField = inner.field(field2);
        if (outerField != null && innerField != null) {
            selfJoin.addConditions(outerField.equal(innerField));
        }
    }

    @Override
    public final DivideByOnConditionStep on(Condition conditions) {
        this.condition.addConditions(conditions);
        return this;
    }

    @Override
    public final DivideByOnConditionStep on(Condition ... conditions) {
        this.condition.addConditions(conditions);
        return this;
    }

    @Override
    public final DivideByOnConditionStep on(Field<Boolean> c) {
        return this.on(DSL.condition(c));
    }

    @Override
    public final DivideByOnConditionStep on(SQL sql) {
        this.and(sql);
        return this;
    }

    @Override
    public final DivideByOnConditionStep on(String sql) {
        this.and(sql);
        return this;
    }

    @Override
    public final DivideByOnConditionStep on(String sql, Object ... bindings) {
        this.and(sql, bindings);
        return this;
    }

    @Override
    public final DivideByOnConditionStep on(String sql, QueryPart ... parts) {
        this.and(sql, parts);
        return this;
    }

    @Override
    public final Table<Record> returning(Field<?> ... fields) {
        return this.returning(Arrays.asList(fields));
    }

    @Override
    public final Table<Record> returning(Collection<? extends Field<?>> fields) {
        this.returning.addAll((Collection<Field<?>>)fields);
        return this.table();
    }

    @Override
    public final DivideByOnConditionStep and(Condition c) {
        this.condition.addConditions(c);
        return this;
    }

    @Override
    public final DivideByOnConditionStep and(Field<Boolean> c) {
        return this.and(DSL.condition(c));
    }

    @Override
    public final DivideByOnConditionStep and(SQL sql) {
        return this.and(DSL.condition(sql));
    }

    @Override
    public final DivideByOnConditionStep and(String sql) {
        return this.and(DSL.condition(sql));
    }

    @Override
    public final DivideByOnConditionStep and(String sql, Object ... bindings) {
        return this.and(DSL.condition(sql, bindings));
    }

    @Override
    public final DivideByOnConditionStep and(String sql, QueryPart ... parts) {
        return this.and(DSL.condition(sql, parts));
    }

    @Override
    public final DivideByOnConditionStep andNot(Condition c) {
        return this.and(c.not());
    }

    @Override
    public final DivideByOnConditionStep andNot(Field<Boolean> c) {
        return this.andNot(DSL.condition(c));
    }

    @Override
    public final DivideByOnConditionStep andExists(Select<?> select) {
        return this.and(DSL.exists(select));
    }

    @Override
    public final DivideByOnConditionStep andNotExists(Select<?> select) {
        return this.and(DSL.notExists(select));
    }

    @Override
    public final DivideByOnConditionStep or(Condition c) {
        this.condition.addConditions(Operator.OR, c);
        return this;
    }

    @Override
    public final DivideByOnConditionStep or(Field<Boolean> c) {
        return this.or(DSL.condition(c));
    }

    @Override
    public final DivideByOnConditionStep or(SQL sql) {
        return this.or(DSL.condition(sql));
    }

    @Override
    public final DivideByOnConditionStep or(String sql) {
        return this.or(DSL.condition(sql));
    }

    @Override
    public final DivideByOnConditionStep or(String sql, Object ... bindings) {
        return this.or(DSL.condition(sql, bindings));
    }

    @Override
    public final DivideByOnConditionStep or(String sql, QueryPart ... parts) {
        return this.or(DSL.condition(sql, parts));
    }

    @Override
    public final DivideByOnConditionStep orNot(Condition c) {
        return this.or(c.not());
    }

    @Override
    public final DivideByOnConditionStep orNot(Field<Boolean> c) {
        return this.orNot(DSL.condition(c));
    }

    @Override
    public final DivideByOnConditionStep orExists(Select<?> select) {
        return this.or(DSL.exists(select));
    }

    @Override
    public final DivideByOnConditionStep orNotExists(Select<?> select) {
        return this.or(DSL.notExists(select));
    }
}

