/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.conf.TransformUnneededArithmeticExpressions;
import org.jooq.impl.AbstractTransformable;
import org.jooq.impl.Expression;
import org.jooq.impl.ExpressionOperator;
import org.jooq.impl.Mul;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class IMul<T>
extends AbstractTransformable<T>
implements QOM.Mul<T> {
    final Field<T> arg1;
    final Field<T> arg2;

    IMul(Field<T> arg1, Field<T> arg2) {
        super(Names.N_MUL, Tools.allNotNull(Tools.dataType(arg1), arg1, arg2));
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.OTHER);
        this.arg2 = Tools.nullSafeNotNull(arg2, SQLDataType.OTHER);
    }

    @Override
    public final void accept0(Context<?> ctx) {
        ctx.visit(new Mul(this.$arg1(), this.$arg2()));
    }

    @Override
    public final Field<?> transform(TransformUnneededArithmeticExpressions transform) {
        return Expression.transform(this, this.arg1, ExpressionOperator.MULTIPLY, this.arg2, true, transform);
    }

    @Override
    public final Field<T> $arg1() {
        return this.arg1;
    }

    @Override
    public final Field<T> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.Mul<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.Mul<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.Mul<T>> $constructor() {
        return (a1, a2) -> new IMul(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Mul) {
            QOM.Mul o = (QOM.Mul)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

