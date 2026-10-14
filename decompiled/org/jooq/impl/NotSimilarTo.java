/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.LikeEscapeStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Like;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class NotSimilarTo
extends AbstractCondition
implements QOM.NotSimilarTo,
LikeEscapeStep {
    final Field<?> value;
    final Field<String> pattern;
    Character escape;

    NotSimilarTo(Field<?> value, Field<String> pattern) {
        this(value, pattern, null);
    }

    NotSimilarTo(Field<?> value, Field<String> pattern, Character escape) {
        this.value = Tools.nullableIf(false, Tools.nullSafe(value, pattern.getDataType()));
        this.pattern = Tools.nullableIf(false, Tools.nullSafe(pattern, value.getDataType()));
    }

    @Override
    public final NotSimilarTo escape(char escape) {
        this.escape = Character.valueOf(escape);
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        Like.accept0(ctx, this.value, Comparator.NOT_SIMILAR_TO, this.pattern, this.escape);
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.value).sql(".notSimilarTo(").visit(Val.getJavaValue(this.pattern)).sql(')');
    }

    @Override
    public final Field<?> $arg1() {
        return this.value;
    }

    @Override
    public final Field<String> $arg2() {
        return this.pattern;
    }

    @Override
    public final Character $arg3() {
        return this.escape;
    }

    @Override
    public final QOM.NotSimilarTo $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue, (Field<String>)this.$arg2(), (Character)((Character)this.$arg3()));
    }

    @Override
    public final QOM.NotSimilarTo $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), newValue, (Character)((Character)this.$arg3()));
    }

    @Override
    public final QOM.NotSimilarTo $arg3(Character newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), (Field<String>)this.$arg2(), (Character)((Character)newValue));
    }

    @Override
    public final Function3<? super Field<?>, ? super Field<String>, ? super Character, ? extends QOM.NotSimilarTo> $constructor() {
        return (a1, a2, a3) -> new NotSimilarTo((Field<?>)a1, (Field<String>)a2, (Character)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.NotSimilarTo) {
            QOM.NotSimilarTo o = (QOM.NotSimilarTo)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$pattern(), o.$pattern()) && StringUtils.equals(this.$escape(), o.$escape());
        }
        return super.equals(that);
    }
}

