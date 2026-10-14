/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.BiFunction;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Function3;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.TableLike;
import org.jooq.TransformProvider;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Ge;
import org.jooq.impl.Gt;
import org.jooq.impl.Le;
import org.jooq.impl.Lt;
import org.jooq.impl.Ne;
import org.jooq.impl.QOM;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.Transformations;
import org.jooq.tools.StringUtils;

final class Eq<T>
extends AbstractCondition
implements QOM.Eq<T> {
    final Field<T> arg1;
    final Field<T> arg2;
    static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};

    Eq(Field<T> arg1, Field<T> arg2) {
        this.arg1 = Tools.nullableIf(false, Tools.nullSafe(arg1, arg2.getDataType()));
        this.arg2 = Tools.nullableIf(false, Tools.nullSafe(arg2, arg1.getDataType()));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        Eq.acceptCompareCondition(ctx, this, this.arg1, Comparator.EQUALS, this.arg2, RowN::eq, RowN::eq, (c, a1, a2) -> c.visit((Field<?>)a1).sql(" = ").visit((Field<?>)a2));
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Deprecated
    static final <T> void acceptCompareCondition(Context<?> ctx, AbstractCondition condition, Field<T> arg1, Comparator op, Field<T> arg2, BiFunction<RowN, Select<?>, Condition> compareRowSubquery, BiFunction<RowN, RowN, Condition> compareRowRow, Function3<? super Context<?>, ? super Field<T>, ? super Field<T>, ? extends Context<?>> acceptDefault) {
        SelectQueryImpl<?> s2;
        boolean field1Embeddable = arg1.getDataType().isEmbeddable();
        if (field1Embeddable && arg2 instanceof ScalarSubquery) {
            ctx.visit(compareRowSubquery.apply(DSL.row(Tools.embeddedFields(arg1)), ((ScalarSubquery)arg2).query));
        } else if (field1Embeddable && arg2.getDataType().isEmbeddable()) {
            ctx.visit(compareRowRow.apply(DSL.row(Tools.embeddedFields(arg1)), DSL.row(Tools.embeddedFields(arg2))));
        } else if ((op == Comparator.IN || op == Comparator.NOT_IN) && (s2 = Transformations.subqueryWithLimit(arg2)) != null && Transformations.NO_SUPPORT_IN_LIMIT.contains((Object)ctx.dialect())) {
            ctx.visit(arg1.compare(op, DSL.select(DSL.asterisk()).from((TableLike<?>)s2.asTable("t"))));
        } else if (arg1.getDataType().isMultiset() && arg2.getDataType().isMultiset() && !Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONDITION))) {
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONDITION, true, c -> c.visit(condition));
        } else if (Boolean.TRUE.equals(ctx.settings().isTransformInlineBindValuesForFieldComparisons())) {
            TransformProvider tp = ctx.configuration().transformProvider();
            if (tp.inlineBindValuesForFieldComparisons(arg1) && arg2 instanceof Param && !((Param)arg2).$inline()) {
                acceptDefault.apply(ctx, arg1, ((Param)arg2).$inline(true));
            } else if (tp.inlineBindValuesForFieldComparisons(arg2) && arg1 instanceof Param && !((Param)arg1).$inline()) {
                acceptDefault.apply(ctx, ((Param)arg1).$inline(true), arg2);
            } else {
                acceptDefault.apply(ctx, arg1, arg2);
            }
        } else {
            acceptDefault.apply(ctx, arg1, arg2);
        }
    }

    @Deprecated
    static final Comparator comparator(Condition condition) {
        if (condition instanceof Eq) {
            return Comparator.EQUALS;
        }
        if (condition instanceof Ne) {
            return Comparator.NOT_EQUALS;
        }
        if (condition instanceof Gt) {
            return Comparator.GREATER;
        }
        if (condition instanceof Ge) {
            return Comparator.GREATER_OR_EQUAL;
        }
        if (condition instanceof Lt) {
            return Comparator.LESS;
        }
        if (condition instanceof Le) {
            return Comparator.LESS_OR_EQUAL;
        }
        return null;
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.arg1).sql(".eq(").visit(this.arg2).sql(')');
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
    public final QOM.Eq<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.Eq<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.Eq<T>> $constructor() {
        return (a1, a2) -> new Eq(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Eq) {
            QOM.Eq o = (QOM.Eq)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

