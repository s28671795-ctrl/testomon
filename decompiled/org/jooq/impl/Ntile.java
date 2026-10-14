/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Val;

final class Ntile
extends AbstractWindowFunction<Integer>
implements QOM.Ntile {
    private final Field<Integer> tiles;

    Ntile(Field<Integer> tiles) {
        super(Names.N_NTILE, SQLDataType.INTEGER.notNull());
        this.tiles = tiles;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("ntile(").visit(Val.getJavaValue(this.tiles)).sql(')');
                break;
            }
            default: {
                ctx.visit(Names.N_NTILE).sql('(').visit(this.tiles).sql(')');
            }
        }
        this.acceptOverClause(ctx);
    }

    @Override
    public final Field<Integer> $tiles() {
        return this.tiles;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.tiles, this.$windowSpecification() != null ? this.$windowSpecification() : this.$windowDefinition());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.tiles, this.$windowSpecification(), this.$windowDefinition(), (t2, s2, d) -> new Ntile((Field<Integer>)t2).$windowSpecification((WindowSpecification)s2).$windowDefinition((WindowDefinition)d), replacer);
    }
}

