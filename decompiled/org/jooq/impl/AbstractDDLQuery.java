/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DDLQuery;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

abstract class AbstractDDLQuery
extends AbstractRowCountQuery
implements DDLQuery {
    AbstractDDLQuery(Configuration configuration) {
        super(configuration);
    }

    static final void acceptCascade(Context<?> ctx, QOM.Cascade cascade) {
        if (cascade == QOM.Cascade.CASCADE) {
            ctx.sql(' ').visit(Keywords.K_CASCADE);
        } else if (cascade == QOM.Cascade.RESTRICT) {
            ctx.sql(' ').visit(Keywords.K_RESTRICT);
        }
    }
}

