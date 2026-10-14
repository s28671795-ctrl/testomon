/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.WindowSpecificationExcludeStep;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.RowNumber;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.WindowSpecificationImpl;

final class PercentRank
extends AbstractWindowFunction<BigDecimal>
implements QOM.PercentRank {
    @Pro
    private static final Set<SQLDialect> EMULATE_PERCENT_RANK = SQLDialect.supportedUntil(SQLDialect.DB2_10);

    PercentRank() {
        super(Names.N_PERCENT_RANK, SQLDataType.NUMERIC.notNull());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("percentRank()");
            this.acceptOverClause(ctx);
        } else if (EMULATE_PERCENT_RANK.contains((Object)ctx.dialect())) {
            WindowSpecificationImpl spec1 = this.spec();
            WindowSpecificationExcludeStep spec2 = spec1.copy().rangeBetweenUnboundedPreceding().andUnboundedFollowing();
            ctx.visit(DSL.case_(DSL.count().over(spec2)).when((Field<BigDecimal>)DSL.inline(1), DSL.cast(DSL.inline(0), SQLDataType.NUMERIC)).else_(Internal.idiv(Internal.isub(DSL.cast(DSL.rank().over(spec1), SQLDataType.NUMERIC), DSL.inline(1)), Internal.isub(DSL.count().over(spec2), DSL.inline(1)))));
        } else {
            ctx.visit(Names.N_PERCENT_RANK).sql("()");
            this.acceptOverClause(ctx);
        }
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$windowSpecification() != null ? this.$windowSpecification() : this.$windowDefinition());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$windowSpecification(), this.$windowDefinition(), (s2, d) -> new RowNumber().$windowSpecification((WindowSpecification)s2).$windowDefinition((WindowDefinition)d), replacer);
    }
}

