/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.util.Set;
import org.jooq.Context;
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

final class CumeDist
extends AbstractWindowFunction<BigDecimal>
implements QOM.CumeDist {
    @Pro
    private static final Set<SQLDialect> EMULATE_CUME_DIST = SQLDialect.supportedUntil(SQLDialect.DB2_10, SQLDialect.MEMSQL);

    CumeDist() {
        super(Names.N_CUME_DIST, SQLDataType.NUMERIC.notNull());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("cumeDist()");
            this.acceptOverClause(ctx);
        } else if (EMULATE_CUME_DIST.contains((Object)ctx.dialect())) {
            WindowSpecificationImpl spec = this.spec();
            WindowSpecificationExcludeStep spec1 = spec.copy().rangeUnboundedPreceding();
            WindowSpecificationExcludeStep spec2 = spec.copy().rangeBetweenUnboundedPreceding().andUnboundedFollowing();
            ctx.visit(Internal.idiv(DSL.cast(DSL.count().over(spec1), SQLDataType.NUMERIC), DSL.count().over(spec2)));
        } else {
            ctx.visit(Names.N_CUME_DIST).sql("()");
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

