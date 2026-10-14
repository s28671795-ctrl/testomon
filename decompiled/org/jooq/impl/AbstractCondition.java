/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

abstract class AbstractCondition
extends AbstractField<Boolean>
implements Condition {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION};

    AbstractCondition() {
        super(DSL.name("condition"), SQLDataType.BOOLEAN);
    }

    @Override
    public Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public Condition and(Field<Boolean> other) {
        return this.and(DSL.condition(other));
    }

    @Override
    public final Condition or(Field<Boolean> other) {
        return this.or(DSL.condition(other));
    }

    @Override
    public final Condition xor(Field<Boolean> other) {
        return this.xor(DSL.condition(other));
    }

    @Override
    public final Condition and(SQL sql) {
        return this.and(DSL.condition(sql));
    }

    @Override
    public final Condition and(String sql) {
        return this.and(DSL.condition(sql));
    }

    @Override
    public final Condition and(String sql, Object ... bindings) {
        return this.and(DSL.condition(sql, bindings));
    }

    @Override
    public final Condition and(String sql, QueryPart ... parts) {
        return this.and(DSL.condition(sql, parts));
    }

    @Override
    public final Condition or(SQL sql) {
        return this.or(DSL.condition(sql));
    }

    @Override
    public final Condition or(String sql) {
        return this.or(DSL.condition(sql));
    }

    @Override
    public final Condition or(String sql, Object ... bindings) {
        return this.or(DSL.condition(sql, bindings));
    }

    @Override
    public final Condition or(String sql, QueryPart ... parts) {
        return this.or(DSL.condition(sql, parts));
    }

    @Override
    public final Condition xor(SQL sql) {
        return this.xor(DSL.condition(sql));
    }

    @Override
    public final Condition xor(String sql) {
        return this.xor(DSL.condition(sql));
    }

    @Override
    public final Condition xor(String sql, Object ... bindings) {
        return this.xor(DSL.condition(sql, bindings));
    }

    @Override
    public final Condition xor(String sql, QueryPart ... parts) {
        return this.xor(DSL.condition(sql, parts));
    }

    @Override
    public final Condition andNot(Condition other) {
        return this.and(other.not());
    }

    @Override
    public final Condition andNot(Field<Boolean> other) {
        return this.andNot(DSL.condition(other));
    }

    @Override
    public final Condition orNot(Condition other) {
        return this.or(other.not());
    }

    @Override
    public final Condition orNot(Field<Boolean> other) {
        return this.orNot(DSL.condition(other));
    }

    @Override
    public final Condition xorNot(Condition other) {
        return this.xor(other.not());
    }

    @Override
    public final Condition xorNot(Field<Boolean> other) {
        return this.xorNot(DSL.condition(other));
    }

    @Override
    public final Condition andExists(Select<?> select) {
        return this.and(DSL.exists(select));
    }

    @Override
    public final Condition andNotExists(Select<?> select) {
        return this.and(DSL.notExists(select));
    }

    @Override
    public final Condition orExists(Select<?> select) {
        return this.or(DSL.exists(select));
    }

    @Override
    public final Condition orNotExists(Select<?> select) {
        return this.or(DSL.notExists(select));
    }

    @Override
    public final Condition xorExists(Select<?> select) {
        return this.xor(DSL.exists(select));
    }

    @Override
    public final Condition xorNotExists(Select<?> select) {
        return this.xor(DSL.notExists(select));
    }

    @Override
    public final Condition and(Condition arg2) {
        return DSL.condition(Operator.AND, (Condition)this, arg2);
    }

    @Override
    public final Condition not() {
        return DSL.not(this);
    }

    @Override
    public final Condition or(Condition arg2) {
        return DSL.condition(Operator.OR, (Condition)this, arg2);
    }

    @Override
    public final Condition xor(Condition arg2) {
        return DSL.condition(Operator.XOR, (Condition)this, arg2);
    }
}

