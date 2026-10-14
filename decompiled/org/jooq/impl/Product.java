/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.CaseConditionStep;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Product
extends AbstractAggregateFunction<BigDecimal>
implements QOM.Product {
    Product(Field<? extends Number> field2, boolean distinct2) {
        super(distinct2, Names.N_PRODUCT, SQLDataType.NUMERIC, Tools.nullSafeNotNull(field2, SQLDataType.INTEGER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: 
            case EXASOL: {
                this.acceptNative(ctx);
                break;
            }
            default: {
                this.acceptEmulation(ctx);
            }
        }
    }

    @Pro
    private final void acceptNative(Context<?> ctx) {
        Name name = ctx.family() == SQLDialect.JAVA ? Names.N_PRODUCT : Names.N_MUL;
        ctx.visit(CustomField.of(name, SQLDataType.NUMERIC, c -> {
            c.visit(this.distinct ? DSL.aggregateDistinct(name, SQLDataType.NUMERIC, this.arguments.toArray((E[])Tools.EMPTY_FIELD)) : DSL.aggregate(name, SQLDataType.NUMERIC, this.arguments.toArray((E[])Tools.EMPTY_FIELD)));
            this.acceptFilterClause((Context<?>)c);
            this.acceptOverClause((Context<?>)c);
        }));
    }

    private final void acceptEmulation(Context<?> ctx) {
        Field f = (Field)this.arguments.get(0);
        CaseConditionStep<Integer> negatives = DSL.when(f.lt(DSL.zero()), DSL.inline(-1));
        CustomField<BigDecimal> negativesSum = CustomField.of("sum", SQLDataType.NUMERIC, c -> {
            c.visit(this.distinct ? DSL.sumDistinct(negatives) : DSL.sum(negatives));
            this.acceptFilterClause((Context<?>)c);
            this.acceptOverClause((Context<?>)c);
        });
        CustomField<BigDecimal> zerosSum = CustomField.of("sum", SQLDataType.NUMERIC, c -> {
            c.visit(DSL.sum(DSL.choose(f).when((Field<Integer>)DSL.zero(), DSL.one())));
            this.acceptFilterClause((Context<?>)c);
            this.acceptOverClause((Context<?>)c);
        });
        CustomField<BigDecimal> logarithmsSum = CustomField.of("sum", SQLDataType.NUMERIC, c -> {
            Field<Integer> abs = DSL.abs(DSL.nullif(f, DSL.zero()));
            Field<BigDecimal> ln = c.family() == SQLDialect.COCKROACHDB ? DSL.ln(DSL.cast(abs, SQLDataType.NUMERIC)) : DSL.ln(abs);
            c.visit(this.distinct ? DSL.sumDistinct(ln) : DSL.sum(ln));
            this.acceptFilterClause((Context<?>)c);
            this.acceptOverClause((Context<?>)c);
        });
        ctx.visit(Internal.imul(DSL.when(zerosSum.gt(DSL.inline(BigDecimal.ZERO)), DSL.zero()).when(negativesSum.mod(DSL.inline(2)).lt(DSL.inline(BigDecimal.ZERO)), (Field<Integer>)DSL.inline(-1)).otherwise(DSL.one()), DSL.exp(logarithmsSum)));
    }

    @Override
    public final Field<? extends Number> $field() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.Product $field(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue, (Boolean)this.$distinct());
    }

    @Override
    public final QOM.Product $distinct(boolean newValue) {
        return this.$constructor().apply((Field<? extends Number>)this.$field(), (Boolean)newValue);
    }

    public final Function2<? super Field<? extends Number>, ? super Boolean, ? extends QOM.Product> $constructor() {
        return (a1, a2) -> new Product((Field<? extends Number>)a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), this.$distinct(), (a1, a2) -> this.$constructor().apply((Field<? extends Number>)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$field());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Product) {
            QOM.Product o = (QOM.Product)that;
            return StringUtils.equals(this.$field(), o.$field()) && this.$distinct() == o.$distinct();
        }
        return super.equals(that);
    }
}

