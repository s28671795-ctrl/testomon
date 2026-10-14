/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.impl.CaseSimple;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class CaseSimpleValueStep<V>
implements CaseValueStep<V>,
QOM.UTransient {
    private final Field<V> value;

    CaseSimpleValueStep(Field<V> value) {
        this.value = value;
    }

    @Override
    public final <T> CaseWhenStep<V, T> when(V compareValue, T result) {
        return this.when(Tools.field(compareValue, this.value), Tools.field(result));
    }

    @Override
    public final <T> CaseWhenStep<V, T> when(V compareValue, Field<T> result) {
        return this.when(Tools.field(compareValue, this.value), result);
    }

    @Override
    public final <T> CaseWhenStep<V, T> when(V compareValue, Select<? extends Record1<T>> result) {
        return this.when(Tools.field(compareValue, this.value), DSL.field(result));
    }

    @Override
    public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, T result) {
        return this.when(compareValue, Tools.field(result));
    }

    @Override
    public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, Field<T> result) {
        return new CaseSimple<V, T>(this.value, compareValue, result);
    }

    @Override
    public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, Select<? extends Record1<T>> result) {
        return this.when(compareValue, DSL.field(result));
    }

    @Override
    public final <T> CaseWhenStep<V, T> mapValues(Map<V, T> values2) {
        LinkedHashMap fields = new LinkedHashMap();
        values2.forEach((k, v) -> fields.put(Tools.field(k, this.value), Tools.field(v)));
        return this.mapFields(fields);
    }

    @Override
    public final <T> CaseWhenStep<V, T> mapFields(Map<? extends Field<V>, ? extends Field<T>> fields) {
        return new CaseSimple(this.value, fields);
    }
}

