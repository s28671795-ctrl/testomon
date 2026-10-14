/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class CurrentCatalog
extends AbstractField<String>
implements QOM.CurrentCatalog {
    CurrentCatalog() {
        super(Names.N_CURRENT_CATALOG, Tools.allNotNull(SQLDataType.VARCHAR));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case FIREBIRD: 
            case SQLITE: {
                ctx.visit(DSL.inline(""));
                break;
            }
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case INFORMIX: {
                ctx.visit(DSL.unquotedName("dbinfo")).sql('(').visit(DSL.inline("dbname")).sql(')');
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_DB_NAME).sql("()");
                break;
            }
            default: {
                ctx.visit(Names.N_CURRENT_DATABASE).sql("()");
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("currentCatalog()");
    }

    @Override
    public final Function0<? extends QOM.CurrentCatalog> $constructor() {
        return () -> new CurrentCatalog();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.CurrentCatalog) {
            QOM.CurrentCatalog o = (QOM.CurrentCatalog)that;
            return true;
        }
        return super.equals(that);
    }
}

