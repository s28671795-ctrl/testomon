/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.conf.TransformUnneededArithmeticExpressions;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractTransformable;
import org.jooq.impl.Expression;
import org.jooq.impl.ExpressionOperator;
import org.jooq.impl.Names;
import org.jooq.impl.Operators;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Add<T>
extends AbstractTransformable<T>
implements QOM.Add<T> {
    final Field<T> arg1;
    final Field<T> arg2;

    Add(Field<T> arg1, Field<T> arg2) {
        super(Names.N_ADD, Tools.allNotNull(Tools.dataType(arg1), arg1, arg2));
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.OTHER);
        this.arg2 = Tools.nullSafeNotNull(arg2, SQLDataType.OTHER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept0(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        ctx.sql('(');
        Expression.acceptAssociative(ctx, this, q -> new Expression.Expr(q.arg1, Operators.OP_PLUS, q.arg2), c -> c.sql(' '));
        ctx.sql(')');
    }

    @Override
    public final Field<?> transform(TransformUnneededArithmeticExpressions transform) {
        return Expression.transform(this, this.arg1, ExpressionOperator.ADD, this.arg2, false, transform);
    }

    @Override
    final DataType<?> getExpressionDataType() {
        return Expression.getExpressionDataType((AbstractField)this.arg1, ExpressionOperator.ADD, (AbstractField)this.arg2);
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(Val.getJavaValue(this.arg1)).sql(".add(").visit(Val.getJavaValue(this.arg2)).sql(')');
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
    public final QOM.Add<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.Add<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.Add<T>> $constructor() {
        return (a1, a2) -> new Add(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Add) {
            QOM.Add o = (QOM.Add)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

