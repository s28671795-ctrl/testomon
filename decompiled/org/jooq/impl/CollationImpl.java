/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Collation;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;

final class CollationImpl
extends AbstractQueryPart
implements Collation,
QOM.UNotYetImplemented {
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_NAME_QUOTING = SQLDialect.supportedBy(SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE);
    private final Name name;

    CollationImpl(Name name) {
        this.name = name;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_NAME_QUOTING.contains((Object)ctx.dialect())) {
            ctx.visit(this.name.unquotedName());
        } else {
            ctx.visit(this.name);
        }
    }

    @Override
    public final String getName() {
        return this.name.last();
    }
}

