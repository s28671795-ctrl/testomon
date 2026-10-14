/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class EndsWithIgnoreCase<T>
extends AbstractCondition
implements QOM.EndsWithIgnoreCase<T> {
    final Field<T> string;
    final Field<T> suffix;

    EndsWithIgnoreCase(Field<T> string, Field<T> suffix) {
        this.string = Tools.nullableIf(false, Tools.nullSafe(string, suffix.getDataType()));
        this.suffix = Tools.nullableIf(false, Tools.nullSafe(suffix, string.getDataType()));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(this.string.likeIgnoreCase(DSL.concat(DSL.inline("%"), Tools.escapeForLike(this.suffix, ctx.configuration())), '!'));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.string).sql(".endsWithIgnoreCase(").visit(Val.getJavaValue(this.suffix)).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.string;
    }

    @Override
    public final Field<T> $arg2() {
        return this.suffix;
    }

    @Override
    public final QOM.EndsWithIgnoreCase<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.EndsWithIgnoreCase<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.EndsWithIgnoreCase<T>> $constructor() {
        return (a1, a2) -> new EndsWithIgnoreCase(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.EndsWithIgnoreCase) {
            QOM.EndsWithIgnoreCase o = (QOM.EndsWithIgnoreCase)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$suffix(), o.$suffix());
        }
        return super.equals(that);
    }
}

