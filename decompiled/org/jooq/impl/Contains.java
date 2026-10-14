/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.JSONB;
import org.jooq.Pro;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Contains<T>
extends AbstractCondition
implements QOM.Contains<T> {
    final Field<T> value;
    final Field<T> content;

    Contains(Field<T> value, Field<T> content) {
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
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (this.value.getDataType().isArray() || this.content.getDataType().isArray()) {
                    ctx.visit(this.value).sql(" @> ").visit(this.content);
                    break;
                }
                if (this.value.getDataType().getType() == JSONB.class || this.content.getDataType().getType() == JSONB.class) {
                    ctx.visit(this.value).sql(" @> ").visit(this.content);
                    break;
                }
                this.acceptDefault(ctx);
                break;
            }
            default: {
                this.acceptDefault(ctx);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.value).sql(".contains(").visit(Val.getJavaValue(this.content)).sql(')');
    }

    private final void acceptDefault(Context<?> ctx) {
        ctx.visit(this.value.like(DSL.concat(DSL.inline("%"), Tools.escapeForLike(this.content, ctx.configuration()), DSL.inline("%")), '!'));
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
    public final QOM.Contains<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.Contains<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.Contains<T>> $constructor() {
        return (a1, a2) -> new Contains(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Contains) {
            QOM.Contains o = (QOM.Contains)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$content(), o.$content());
        }
        return super.equals(that);
    }
}

