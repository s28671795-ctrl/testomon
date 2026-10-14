/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.CaseValueStep;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.impl.CaseSearched;
import org.jooq.impl.CaseSimpleValueStep;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class CaseImpl
implements Case,
QOM.UTransient {
    CaseImpl() {
    }

    @Override
    public final <V> CaseValueStep<V> value(V value) {
        return this.value(Tools.field(value));
    }

    @Override
    public final <V> CaseValueStep<V> value(Field<V> value) {
        return new CaseSimpleValueStep<V>(value);
    }

    @Override
    public final <T> CaseConditionStep<T> when(Condition condition, T result) {
        return this.when(condition, Tools.field(result));
    }

    @Override
    public final <T> CaseConditionStep<T> when(Condition condition, Field<T> result) {
        return new CaseSearched<T>(condition, result);
    }

    @Override
    public final <T> CaseConditionStep<T> when(Condition condition, Select<? extends Record1<T>> result) {
        return this.when(condition, DSL.field(result));
    }

    @Override
    public final <T> CaseConditionStep<T> when(Field<Boolean> condition, T result) {
        return this.when(DSL.condition(condition), result);
    }

    @Override
    public final <T> CaseConditionStep<T> when(Field<Boolean> condition, Field<T> result) {
        return this.when(DSL.condition(condition), result);
    }

    @Override
    public final <T> CaseConditionStep<T> when(Field<Boolean> condition, Select<? extends Record1<T>> result) {
        return this.when(DSL.condition(condition), result);
    }
}

