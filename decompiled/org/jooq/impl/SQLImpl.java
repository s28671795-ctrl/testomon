/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.Objects;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class SQLImpl
extends AbstractQueryPart
implements SQL,
QOM.UEmpty {
    private static final Clause[] CLAUSES = new Clause[]{Clause.TEMPLATE};
    final String sql;
    final boolean isName;
    final boolean raw;
    final List<QueryPart> substitutes;

    SQLImpl(String sql, boolean raw, Object ... input) {
        this.sql = Objects.requireNonNull(sql);
        this.raw = raw;
        this.substitutes = Tools.queryParts(input);
        this.isName = this.substitutes.isEmpty() && SQLImpl.isName(sql);
    }

    static final boolean isName(String sql) {
        int l = sql.length();
        if (l == 0 || !Character.isJavaIdentifierStart(sql.charAt(0))) {
            return false;
        }
        for (int i = 1; i < l; ++i) {
            if (Character.isJavaIdentifierPart(sql.charAt(i))) continue;
            return false;
        }
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.raw) {
                    ctx.sql("raw(");
                }
                ctx.sql(Tools.stringLiteral(this.sql));
                if (!this.substitutes.isEmpty()) {
                    ctx.sql(", ").visit(DSL.list(this.substitutes));
                }
                if (!this.raw) break;
                ctx.sql(')');
                break;
            }
            default: {
                if (this.raw) {
                    ctx.sql(this.sql);
                    break;
                }
                Tools.renderAndBind(ctx, this.sql, this.substitutes);
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public String toString() {
        return this.sql;
    }
}

