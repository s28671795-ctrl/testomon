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

final class ContainsIgnoreCase<T>
extends AbstractCondition
implements QOM.ContainsIgnoreCase<T> {
    final Field<T> value;
    final Field<T> content;

    ContainsIgnoreCase(Field<T> value, Field<T> content) {
        this.value = Tools.nullableIf(false, Tools.nullSafe(value, content.getDataType()));
        this.content = Tools.nullableIf(false, Tools.nullSafe(content, value.getDataType()));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(this.value.likeIgnoreCase(DSL.concat(DSL.inline("%"), Tools.escapeForLike(this.content, ctx.configuration()), DSL.inline("%")), '!'));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.value).sql(".containsIgnoreCase(").visit(Val.getJavaValue(this.content)).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final Field<T> $arg2() {
        return this.content;
    }

    @Override
    public final QOM.ContainsIgnoreCase<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.ContainsIgnoreCase<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.ContainsIgnoreCase<T>> $constructor() {
        return (a1, a2) -> new ContainsIgnoreCase(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ContainsIgnoreCase) {
            QOM.ContainsIgnoreCase o = (QOM.ContainsIgnoreCase)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$content(), o.$content());
        }
        return super.equals(that);
    }
}

