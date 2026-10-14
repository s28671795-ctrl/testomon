/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class VarPop
extends AbstractAggregateFunction<BigDecimal>
implements QOM.VarPop {
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.FIREBIRD_2_5, SQLDialect.IGNITE, SQLDialect.SQLITE);

    VarPop(Field<? extends Number> field2) {
        super(false, Names.N_VAR_POP, SQLDataType.NUMERIC, Tools.nullSafeNotNull(field2, SQLDataType.INTEGER));
    }

    @Override
    public void accept(Context<?> ctx) {
        if (NO_SUPPORT_NATIVE.contains((Object)ctx.dialect())) {
            Field x = (Field)this.getArguments().get(0);
            ctx.visit(this.fo(DSL.avg(DSL.square(x).cast(this.d(ctx)))).minus(DSL.square(this.fo(DSL.avg(x.cast(this.d(ctx)))))));
        } else {
            super.accept(ctx);
        }
    }

    @Override
    void acceptFunctionName(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: 
            case INFORMIX: {
                ctx.visit(Names.N_VARIANCE);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_VARP);
                break;
            }
            default: {
                super.acceptFunctionName(ctx);
            }
        }
    }

    @Override
    public final Field<? extends Number> $field() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.VarPop $field(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    public final Function1<? super Field<? extends Number>, ? extends QOM.VarPop> $constructor() {
        return a1 -> new VarPop((Field<? extends Number>)a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), a1 -> this.$constructor().apply((Field<? extends Number>)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$field());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.VarPop) {
            QOM.VarPop o = (QOM.VarPop)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

