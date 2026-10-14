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

final class BoolAnd
extends AbstractAggregateFunction<Boolean>
implements QOM.BoolAnd {
    private static final Set<SQLDialect> EMULATE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);

    BoolAnd(Condition condition) {
        super(false, Names.N_BOOL_AND, SQLDataType.BOOLEAN, DSL.field(condition));
    }

    @Override
    final void acceptFunctionName(Context<?> ctx) {
        switch (ctx.family()) {
            case BIGQUERY: {
                ctx.visit(Names.N_LOGICAL_AND);
                break;
            }
            case EXASOL: {
                ctx.visit(Names.N_EVERY);
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(Names.N_BOOLAND_AGG);
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
            ctx.visit(DSL.field(this.fo(DSL.min(DSL.when(this.condition(), DSL.one()).otherwise(DSL.zero()))).eq(DSL.one())));
        } else {
            super.accept(ctx);
        }
    }

    @Override
    public final Condition $condition() {
        return DSL.condition((Field)this.getArguments().get(0));
    }

    @Override
    public final QOM.BoolAnd $condition(Condition newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Condition, ? extends QOM.BoolAnd> $constructor() {
        return a1 -> new BoolAnd((Condition)a1);
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
        if (that instanceof QOM.BoolAnd) {
            QOM.BoolAnd o = (QOM.BoolAnd)that;
            return StringUtils.equals(this.$condition(), o.$condition());
        }
        return super.equals(that);
    }
}

