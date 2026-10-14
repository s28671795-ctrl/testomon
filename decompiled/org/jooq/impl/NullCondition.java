/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Null;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SimpleQueryPart;
import org.jooq.impl.TrueCondition;

final class NullCondition
extends AbstractCondition
implements Null,
QOM.Null,
QOM.UEmpty,
SimpleQueryPart {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
    static final NullCondition INSTANCE = new NullCondition();
    static final Set<SQLDialect> NO_SUPPORT_UNTYPED_NULL = SQLDialect.supportedBy(SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.INFORMIX);

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return !NO_SUPPORT_UNTYPED_NULL.contains((Object)ctx.dialect()) && !TrueCondition.NO_SUPPORT_BOOLEAN.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_UNTYPED_NULL.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.castNull(SQLDataType.BOOLEAN));
        } else if (TrueCondition.NO_SUPPORT_BOOLEAN.contains((Object)ctx.dialect())) {
            ctx.visit(Keywords.K_NULL).sql(" = ").visit(Keywords.K_NULL);
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("nullCondition()");
        } else {
            ctx.visit(Keywords.K_NULL);
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    private NullCondition() {
    }
}

