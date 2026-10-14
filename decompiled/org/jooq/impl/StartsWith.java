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

final class StartsWith<T>
extends AbstractCondition
implements QOM.StartsWith<T> {
    final Field<T> string;
    final Field<T> prefix;

    StartsWith(Field<T> string, Field<T> prefix) {
        this.string = Tools.nullableIf(false, Tools.nullSafe(string, prefix.getDataType()));
        this.prefix = Tools.nullableIf(false, Tools.nullSafe(prefix, string.getDataType()));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(this.string.like(DSL.concat(Tools.escapeForLike(this.prefix, ctx.configuration()), DSL.inline("%")), '!'));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.string).sql(".startsWith(").visit(Val.getJavaValue(this.prefix)).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.string;
    }

    @Override
    public final Field<T> $arg2() {
        return this.prefix;
    }

    @Override
    public final QOM.StartsWith<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.StartsWith<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.StartsWith<T>> $constructor() {
        return (a1, a2) -> new StartsWith(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StartsWith) {
            QOM.StartsWith o = (QOM.StartsWith)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$prefix(), o.$prefix());
        }
        return super.equals(that);
    }
}

