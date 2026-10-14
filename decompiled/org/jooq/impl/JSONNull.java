/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SimpleQueryPart;

final class JSONNull
extends AbstractQueryPart
implements SimpleQueryPart,
QOM.UTransient {
    static final Set<SQLDialect> NO_SUPPORT_ABSENT_ON_NULL = SQLDialect.supportedBy(SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE);
    final QOM.JSONOnNull type;

    JSONNull(QOM.JSONOnNull type) {
        this.type = type;
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return !NO_SUPPORT_ABSENT_ON_NULL.contains((Object)ctx.dialect()) && this.type != null;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.type == QOM.JSONOnNull.NULL_ON_NULL) {
                    ctx.sql(".nullOnNull()");
                    break;
                }
                if (this.type != QOM.JSONOnNull.ABSENT_ON_NULL) break;
                ctx.sql(".absentOnNull()");
                break;
            }
            default: {
                if (NO_SUPPORT_ABSENT_ON_NULL.contains((Object)ctx.dialect())) break;
                if (this.type == QOM.JSONOnNull.NULL_ON_NULL) {
                    ctx.visit(Keywords.K_NULL).sql(' ').visit(Keywords.K_ON).sql(' ').visit(Keywords.K_NULL);
                    break;
                }
                if (this.type != QOM.JSONOnNull.ABSENT_ON_NULL) break;
                ctx.visit(Keywords.K_ABSENT).sql(' ').visit(Keywords.K_ON).sql(' ').visit(Keywords.K_NULL);
            }
        }
    }
}

