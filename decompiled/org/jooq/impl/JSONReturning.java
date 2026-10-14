/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;

final class JSONReturning
extends AbstractQueryPart
implements SimpleQueryPart,
QOM.UTransient {
    static final Set<SQLDialect> NO_SUPPORT_RETURNING = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    final DataType<?> type;

    JSONReturning(DataType<?> type) {
        this.type = type;
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return !NO_SUPPORT_RETURNING.contains((Object)ctx.dialect()) && this.type != null;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.type == null) break;
                ctx.sql(".returning(").visit(this.type).sql(')');
                break;
            }
            default: {
                if (NO_SUPPORT_RETURNING.contains((Object)ctx.dialect())) break;
                ctx.visit(Keywords.K_RETURNING).sql(' ').sql(this.type.getCastTypeName(ctx.configuration()));
            }
        }
    }
}

