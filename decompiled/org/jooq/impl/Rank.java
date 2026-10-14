/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.RowNumber;
import org.jooq.impl.SQLDataType;

final class Rank
extends AbstractWindowFunction<Integer>
implements QOM.Rank {
    Rank() {
        super(Names.N_RANK, SQLDataType.INTEGER.notNull());
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("rank()");
                this.acceptOverClause(ctx);
                break;
            }
            default: {
                ctx.visit(Names.N_RANK).sql("()");
                this.acceptOverClause(ctx);
            }
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

