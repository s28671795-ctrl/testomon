/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class Commit
extends AbstractRowCountQuery
implements QOM.Commit {
    Commit(Configuration configuration) {
        super(configuration);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case TERADATA: {
                ctx.visit(Keywords.K_END).sql(' ').visit(Keywords.K_TRANSACTION);
                break;
            }
            default: {
                ctx.visit(Keywords.K_COMMIT);
            }
        }
    }
}

