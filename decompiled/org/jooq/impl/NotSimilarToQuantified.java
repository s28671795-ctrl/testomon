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
import org.jooq.QuantifiedSelect;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.EqQuantified;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class NotSimilarToQuantified
extends AbstractCondition
implements QOM.NotSimilarToQuantified,
LikeEscapeStep {
    final Field<?> value;
    final QuantifiedSelect<? extends Record1<String>> pattern;
    Character escape;

    NotSimilarToQuantified(Field<?> value, QuantifiedSelect<? extends Record1<String>> pattern) {
        this(value, pattern, null);
    }

    NotSimilarToQuantified(Field<?> value, QuantifiedSelect<? extends Record1<String>> pattern, Character escape) {
        this.value = Tools.nullSafeNotNull(value, SQLDataType.OTHER);
        this.pattern = pattern;
        this.escape = escape;
    }

    @Override
    public final NotSimilarToQuantified escape(char escape) {
        this.escape = Character.valueOf(escape);
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        EqQuantified.acceptCompareCondition(ctx, this, this.value, Comparator.NOT_SIMILAR_TO, this.pattern, this.escape);
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.value).sql(".notSimilarTo(").visit(this.pattern).sql(')');
    }

    @Override
    public final Field<?> $arg1() {
        return this.value;
    }

    @Override
    public final QuantifiedSelect<? extends Record1<String>> $arg2() {
        return this.pattern;
    }

    @Override
    public final Character $arg3() {
        return this.escape;
    }

    @Override
    public final QOM.NotSimilarToQuantified $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue, (QuantifiedSelect<Record1<String>>)this.$arg2(), (Character)((Character)this.$arg3()));
    }

    @Override
    public final QOM.NotSimilarToQuantified $arg2(QuantifiedSelect<? extends Record1<String>> newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), (QuantifiedSelect<Record1<String>>)newValue, (Character)((Character)this.$arg3()));
    }

    @Override
    public final QOM.NotSimilarToQuantified $arg3(Character newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), (QuantifiedSelect<Record1<String>>)this.$arg2(), (Character)((Character)newValue));
    }

    @Override
    public final Function3<? super Field<?>, ? super QuantifiedSelect<? extends Record1<String>>, ? super Character, ? extends QOM.NotSimilarToQuantified> $constructor() {
        return (a1, a2, a3) -> new NotSimilarToQuantified((Field<?>)a1, (QuantifiedSelect<? extends Record1<String>>)a2, (Character)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.NotSimilarToQuantified) {
            QOM.NotSimilarToQuantified o = (QOM.NotSimilarToQuantified)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$pattern(), o.$pattern()) && StringUtils.equals(this.$escape(), o.$escape());
        }
        return super.equals(that);
    }
}

