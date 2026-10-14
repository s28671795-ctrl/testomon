/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.RowCondition;
import org.jooq.impl.RowSubqueryCondition;
import org.jooq.impl.Tools;

final class RowIsDistinctFrom
extends AbstractCondition
implements QOM.UNotYetImplemented {
    private static final Set<SQLDialect> EMULATE_DISTINCT = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> EMULATE_DISTINCT_SELECT = SQLDialect.supportedBy(SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> SUPPORT_DISTINCT_WITH_ARROW = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MYSQL);
    private final Row lhs;
    private final Row rhsRow;
    private final Select<?> rhsSelect;
    private final boolean not;

    RowIsDistinctFrom(Row lhs, Row rhs, boolean not) {
        this.lhs = lhs;
        this.rhsRow = rhs;
        this.rhsSelect = null;
        this.not = not;
    }

    RowIsDistinctFrom(Row lhs, Select<?> rhs, boolean not) {
        this.lhs = lhs;
        this.rhsRow = null;
        this.rhsSelect = rhs;
        this.not = not;
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE_DISTINCT.contains((Object)ctx.dialect()) || this.rhsSelect != null && EMULATE_DISTINCT_SELECT.contains((Object)ctx.dialect())) {
            Select intersect = DSL.select(this.lhs.fields()).intersect(this.rhsSelect != null ? this.rhsSelect : DSL.select(this.rhsRow.fields()));
            ctx.visit(this.not ? DSL.exists(intersect) : DSL.notExists(intersect));
        } else if (SUPPORT_DISTINCT_WITH_ARROW.contains((Object)ctx.dialect())) {
            if (!this.not) {
                ctx.visit(Keywords.K_NOT).sql('(');
            }
            ctx.visit(this.lhs).sql(" <=> ");
            if (this.rhsRow != null) {
                ctx.visit(this.rhsRow);
            } else {
                Tools.visitSubquery(ctx, this.rhsSelect, 256);
            }
            if (!this.not) {
                ctx.sql(')');
            }
        } else if (SQLDialect.SQLITE == ctx.family()) {
            ctx.visit(this.lhs).sql(' ').visit(Keywords.K_IS).sql(' ');
            if (!this.not) {
                ctx.visit(Keywords.K_NOT).sql(' ');
            }
            if (this.rhsRow != null) {
                ctx.visit(this.rhsRow);
            } else {
                Tools.visitSubquery(ctx, this.rhsSelect, 256);
            }
        } else if (SQLDialect.REDSHIFT == ctx.family()) {
            ctx.visit(this.rhsRow != null ? (this.not ? new RowCondition(this.lhs, this.rhsRow, Comparator.IS_DISTINCT_FROM).not() : new RowCondition(this.lhs, this.rhsRow, Comparator.IS_DISTINCT_FROM)) : (this.not ? new RowSubqueryCondition(this.lhs, this.rhsSelect, Comparator.IS_DISTINCT_FROM).not() : new RowSubqueryCondition(this.lhs, this.rhsSelect, Comparator.IS_DISTINCT_FROM)));
        } else {
            ctx.visit(this.rhsRow != null ? new RowCondition(this.lhs, this.rhsRow, this.not ? Comparator.IS_NOT_DISTINCT_FROM : Comparator.IS_DISTINCT_FROM) : new RowSubqueryCondition(this.lhs, this.rhsSelect, this.not ? Comparator.IS_NOT_DISTINCT_FROM : Comparator.IS_DISTINCT_FROM));
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }
}

