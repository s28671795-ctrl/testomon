/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class NullStatement
extends AbstractStatement
implements QOM.NullStatement,
QOM.UTransient {
    static final NullStatement INSTANCE = new NullStatement();

    NullStatement() {
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Keywords.K_IF).sql(' ').visit(DSL.falseCondition()).sql(' ').visit(DSL.select(DSL.one()));
                if (ctx.family() == SQLDialect.ASE) break;
                ctx.sql(';');
                break;
            }
            default: {
                ctx.visit(Keywords.K_NULL).sql(';');
            }
        }
    }
}

