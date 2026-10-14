/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.SQLDialect;
import org.jooq.True;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;

final class TrueCondition
extends AbstractCondition
implements True,
QOM.True,
QOM.UEmpty,
SimpleQueryPart {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
    static final TrueCondition INSTANCE = new TrueCondition();
    static final Set<SQLDialect> NO_SUPPORT_BOOLEAN = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return !NO_SUPPORT_BOOLEAN.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_BOOLEAN.contains((Object)ctx.dialect())) {
            ctx.sql("1 = 1");
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("trueCondition()");
        } else {
            ctx.visit(Keywords.K_TRUE);
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    private TrueCondition() {
    }
}

