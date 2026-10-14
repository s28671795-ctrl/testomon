/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.NoCondition;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

final class ConditionProviderImpl
extends AbstractField<Boolean>
implements ConditionProvider,
Condition,
QOM.UProxy<Condition> {
    private Condition condition;

    ConditionProviderImpl() {
        this(null);
    }

    ConditionProviderImpl(Condition condition) {
        super(DSL.name("condition"), SQLDataType.BOOLEAN);
        this.condition = condition;
    }

    static final Condition extractCondition(Condition c) {
        Condition condition;
        if (c instanceof ConditionProviderImpl) {
            ConditionProviderImpl cp = (ConditionProviderImpl)c;
            condition = cp.getWhere();
        } else {
            condition = c;
        }
        return condition;
    }

    @Nullable
    final Condition getWhereOrNull() {
        return this.hasWhere() ? this.condition : null;
    }

    @NotNull
    final Condition getWhere() {
        return this.hasWhere() ? this.condition : DSL.noCondition();
    }

    final void setWhere(Condition newCondition) {
        this.condition = newCondition;
    }

    final boolean hasWhere() {
        return this.condition != null && !(this.condition instanceof NoCondition);
    }

    @Override
    public final void addConditions(Condition conditions) {
        this.addConditions(Operator.AND, conditions);
    }

    @Override
    public final void addConditions(Condition ... conditions) {
        this.addConditions(Operator.AND, conditions);
    }

    @Override
    public final void addConditions(Collection<? extends Condition> conditions) {
        this.addConditions(Operator.AND, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition conditions) {
        if (this.hasWhere()) {
            this.setWhere(DSL.condition(operator, this.getWhere(), conditions));
        } else {
            this.setWhere(conditions);
        }
    }

    @Override
    public final void addConditions(Operator operator, Condition ... conditions) {
        this.addConditions(operator, Arrays.asList(conditions));
    }

    @Override
    public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
        if (!conditions.isEmpty()) {
            Condition c = conditions.size() == 1 ? conditions.iterator().next() : DSL.condition(operator, conditions);
            this.addConditions(operator, c);
        }
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.getWhere());
    }

    @Override
    public final Condition and(Condition other) {
        return this.getWhere().and(other);
    }

    @Override
    public final Condition and(Field<Boolean> other) {
        return this.getWhere().and(other);
    }

    @Override
    public final Condition and(SQL sql) {
        return this.getWhere().and(sql);
    }

    @Override
    public final Condition and(String sql) {
        return this.getWhere().and(sql);
    }

    @Override
    public final Condition and(String sql, Object ... bindings) {
        return this.getWhere().and(sql, bindings);
    }

    @Override
    public final Condition and(String sql, QueryPart ... parts) {
        return this.getWhere().and(sql, parts);
    }

    @Override
    public final Condition andNot(Condition other) {
        return this.getWhere().andNot(other);
    }

    @Override
    public final Condition andNot(Field<Boolean> other) {
        return this.getWhere().andNot(other);
    }

    @Override
    public final Condition andExists(Select<?> select) {
        return this.getWhere().andExists(select);
    }

    @Override
    public final Condition andNotExists(Select<?> select) {
        return this.getWhere().andNotExists(select);
    }

    @Override
    public final Condition or(Condition other) {
        return this.getWhere().or(other);
    }

    @Override
    public final Condition or(Field<Boolean> other) {
        return this.getWhere().or(other);
    }

    @Override
    public final Condition or(SQL sql) {
        return this.getWhere().or(sql);
    }

    @Override
    public final Condition or(String sql) {
        return this.getWhere().or(sql);
    }

    @Override
    public final Condition or(String sql, Object ... bindings) {
        return this.getWhere().or(sql, bindings);
    }

    @Override
    public final Condition or(String sql, QueryPart ... parts) {
        return this.getWhere().or(sql, parts);
    }

    @Override
    public final Condition orNot(Condition other) {
        return this.getWhere().orNot(other);
    }

    @Override
    public final Condition orNot(Field<Boolean> other) {
        return this.getWhere().orNot(other);
    }

    @Override
    public final Condition orExists(Select<?> select) {
        return this.getWhere().orExists(select);
    }

    @Override
    public final Condition orNotExists(Select<?> select) {
        return this.getWhere().orNotExists(select);
    }

    @Override
    public final Condition xor(Condition other) {
        return this.getWhere().xor(other);
    }

    @Override
    public final Condition xor(Field<Boolean> other) {
        return this.getWhere().xor(other);
    }

    @Override
    public final Condition xor(SQL sql) {
        return this.getWhere().xor(sql);
    }

    @Override
    public final Condition xor(String sql) {
        return this.getWhere().xor(sql);
    }

    @Override
    public final Condition xor(String sql, Object ... bindings) {
        return this.getWhere().xor(sql, bindings);
    }

    @Override
    public final Condition xor(String sql, QueryPart ... parts) {
        return this.getWhere().xor(sql, parts);
    }

    @Override
    public final Condition xorNot(Condition other) {
        return this.getWhere().xorNot(other);
    }

    @Override
    public final Condition xorNot(Field<Boolean> other) {
        return this.getWhere().xorNot(other);
    }

    @Override
    public final Condition xorExists(Select<?> select) {
        return this.getWhere().xorExists(select);
    }

    @Override
    public final Condition xorNotExists(Select<?> select) {
        return this.getWhere().xorNotExists(select);
    }

    @Override
    public final Condition not() {
        return this.getWhere().not();
    }

    @Override
    public final Condition $delegate() {
        return this.getWhere();
    }
}

