/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Param;
import org.jooq.SQLDialect;
import org.jooq.conf.TransformUnneededArithmeticExpressions;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractTransformable;
import org.jooq.impl.Convert;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Transformable;

final class Neg<T>
extends AbstractTransformable<T>
implements QOM.Neg<T> {
    private final Field<T> field;
    private final boolean internal;

    Neg(Field<T> field2, boolean internal) {
        super(Names.N_NEG, field2.getDataType());
        this.field = field2;
        this.internal = internal;
    }

    @Override
    public final Field<?> transform(TransformUnneededArithmeticExpressions transform) {
        Param param2;
        Field<T> newField;
        Field<T> field2 = this.field;
        if (field2 instanceof Transformable) {
            Transformable t2 = (Transformable)field2;
            v0 = t2.transform(transform);
        } else {
            v0 = newField = this.field;
        }
        if ((this.internal || transform == TransformUnneededArithmeticExpressions.ALWAYS) && newField instanceof Param && (param2 = (Param)newField).isInline()) {
            if (param2.getValue() == null) {
                return param2;
            }
            return DSL.inline((Object)(-Convert.convert(param2.getValue(), Double.TYPE).doubleValue()), param2.getDataType());
        }
        if (this.field != newField) {
            return new Neg<T>(newField, this.internal);
        }
        return this;
    }

    @Override
    public final void accept0(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(this.field).sql(".neg()");
        } else if (this.field instanceof AbstractField && ((AbstractField)this.field).parenthesised(ctx)) {
            ctx.sql('-').visit(this.field);
        } else {
            ctx.sql("-(").visit(this.field).sql(')');
        }
    }

    @Override
    public final Field<T> $arg1() {
        return this.field;
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Neg<T>> $constructor() {
        return f -> new Neg<T>(f, this.internal);
    }
}

