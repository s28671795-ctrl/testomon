/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.Map;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

abstract class AbstractCaseSimple<V, T, CS extends AbstractCaseSimple<V, T, CS>>
extends AbstractField<T> {
    final Field<V> value;
    final List<QOM.Tuple2<Field<V>, Field<T>>> when;
    Field<T> else_;

    AbstractCaseSimple(Name name, Field<V> value, Field<V> compareValue, Field<T> result) {
        this(name, value, result.getDataType());
        this.when(compareValue, result);
    }

    AbstractCaseSimple(Name name, Field<V> value, Map<? extends Field<V>, ? extends Field<T>> map) {
        this(name, value, AbstractCaseSimple.dataType(map));
        this.mapFields(map);
    }

    AbstractCaseSimple(Name name, Field<V> value, DataType<T> type) {
        super(name, type);
        this.value = value;
        this.when = new QueryPartList<QOM.Tuple2<Field<V>, Field<T>>>();
    }

    private static final <T> DataType<T> dataType(Map<? extends Field<?>, ? extends Field<T>> map) {
        if (map.isEmpty()) {
            return SQLDataType.OTHER;
        }
        return map.entrySet().iterator().next().getValue().getDataType();
    }

    public final CS when(V compareValue, T result) {
        return this.when(Tools.field(compareValue, this.value), Tools.field(result));
    }

    public final CS when(V compareValue, Field<T> result) {
        return this.when(Tools.field(compareValue, this.value), result);
    }

    public final CS when(Field<V> compareValue, T result) {
        return this.when(compareValue, Tools.field(result));
    }

    public final CS when(Field<V> compareValue, Field<T> result) {
        this.when.add(QOM.tuple(compareValue, result));
        return (CS)this;
    }

    public final CS mapValues(Map<V, T> values2) {
        values2.forEach((k, v) -> this.when(k, v));
        return (CS)this;
    }

    public final CS mapFields(Map<? extends Field<V>, ? extends Field<T>> fields) {
        fields.forEach((k, v) -> this.when((Field<V>)k, (Field<T>)v));
        return (CS)this;
    }

    public final Field<T> else_(T result) {
        return this.else_(Tools.field(result));
    }

    public final Field<T> else_(Field<T> result) {
        this.else_ = result;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.when.isEmpty()) {
            if (this.else_ != null) {
                ctx.visit(this.else_);
            } else {
                ctx.visit(DSL.inline(null, this.getDataType()));
            }
        } else {
            this.accept0(ctx);
        }
    }

    abstract void accept0(Context<?> var1);

    abstract CS construct(Field<V> var1, DataType<T> var2);

    public final Function3<? super Field<V>, ? super QOM.UnmodifiableList<? extends QOM.Tuple2<Field<V>, Field<T>>>, ? super Field<T>, ? extends CS> $constructor() {
        return (v, w, e) -> {
            Object r = this.construct((Field<V>)v, this.getDataType());
            w.forEach(t2 -> r.when((Field)t2.$1(), (Field)t2.$2()));
            ((AbstractCaseSimple)r).else_((Field<T>)e);
            return r;
        };
    }

    public final Field<V> $arg1() {
        return this.value;
    }

    public final CS $arg1(Field<V> newArg1) {
        return (CS)((AbstractCaseSimple)this.$constructor().apply(newArg1, this.$arg2(), this.$arg3()));
    }

    public final QOM.UnmodifiableList<? extends QOM.Tuple2<Field<V>, Field<T>>> $arg2() {
        return QOM.unmodifiable(this.when);
    }

    public final CS $arg2(QOM.UnmodifiableList<? extends QOM.Tuple2<Field<V>, Field<T>>> w) {
        return (CS)((AbstractCaseSimple)this.$constructor().apply(this.$arg1(), w, this.$arg3()));
    }

    public final Field<T> $arg3() {
        return this.else_;
    }

    public final CS $arg3(Field<T> e) {
        return (CS)((AbstractCaseSimple)this.$constructor().apply(this.$arg1(), this.$arg2(), e));
    }
}

