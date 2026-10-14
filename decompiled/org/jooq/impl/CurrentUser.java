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

final class CurrentUser
extends AbstractField<String>
implements QOM.CurrentUser {
    CurrentUser() {
        super(Names.N_CURRENT_USER, Tools.allNotNull(SQLDataType.VARCHAR));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ACCESS: {
                ctx.visit(Names.N_CURRENTUSER).sql("()");
                break;
            }
            case ASE: 
            case INFORMIX: 
            case ORACLE: {
                ctx.visit(Names.N_USER);
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case DB2: 
            case HANA: 
            case INGRES: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case DERBY: 
            case FIREBIRD: 
            case HSQLDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(Names.N_CURRENT_USER);
                break;
            }
            case SQLITE: {
                ctx.visit(DSL.inline(""));
                break;
            }
            default: {
                ctx.visit(Names.N_CURRENT_USER).sql("()");
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("currentUser()");
    }

    @Override
    public final Function0<? extends QOM.CurrentUser> $constructor() {
        return () -> new CurrentUser();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.CurrentUser) {
            QOM.CurrentUser o = (QOM.CurrentUser)that;
            return true;
        }
        return super.equals(that);
    }
}

