/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Condition;
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
import org.jooq.tools.StringUtils;

final class BoolOr
extends AbstractAggregateFunction<Boolean>
implements QOM.BoolOr {
    private static final Set<SQLDialect> EMULATE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);

    BoolOr(Condition condition) {
        super(false, Names.N_BOOL_OR, SQLDataType.BOOLEAN, DSL.field(condition));
    }

    @Override
    final void acceptFunctionName(Context<?> ctx) {
        switch (ctx.family()) {
            case BIGQUERY: {
                ctx.visit(Names.N_LOGICAL_OR);
                break;
            }
            case EXASOL: {
                ctx.visit(Names.N_ANY);
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(Names.N_BOOLOR_AGG);
                break;
            }
            default: {
                super.acceptFunctionName(ctx);
            }
        }
    }

    final Condition condition() {
        return DSL.condition((Field)this.getArguments().get(0));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.field(this.fo(DSL.max(DSL.when(this.condition(), DSL.one()).otherwise(DSL.zero()))).eq(DSL.one())));
        } else {
            super.accept(ctx);
        }
    }

    @Override
    public final Condition $condition() {
        return DSL.condition((Field)this.getArguments().get(0));
    }

    @Override
    public final QOM.BoolOr $condition(Condition newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Condition, ? extends QOM.BoolOr> $constructor() {
        return a1 -> new BoolOr((Condition)a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$condition(), a1 -> this.$constructor().apply((Condition)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$condition());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BoolOr) {
            QOM.BoolOr o = (QOM.BoolOr)that;
            return StringUtils.equals(this.$condition(), o.$condition());
        }
        return super.equals(that);
    }
}

