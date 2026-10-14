/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Statement;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class StartTransaction
extends AbstractRowCountQuery
implements QOM.StartTransaction {
    StartTransaction(Configuration configuration) {
        super(configuration);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (ctx.data(Tools.SimpleDataKey.DATA_BLOCK_NESTING) != null) break;
                ctx.visit(Keywords.K_START).sql(' ').visit(Keywords.K_TRANSACTION);
                break;
            }
            case EXASOL: {
                ctx.visit(Keywords.K_COMMIT);
                break;
            }
            case DB2: 
            case HANA: 
            case ORACLE: 
            case FIREBIRD: {
                if (ctx.data(Tools.SimpleDataKey.DATA_BLOCK_NESTING) != null) break;
                ctx.visit(DSL.begin(new Statement[0]));
                break;
            }
            case INFORMIX: {
                ctx.visit(Keywords.K_BEGIN).sql(' ').visit(Keywords.K_WORK);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case TERADATA: 
            case H2: 
            case SQLITE: {
                ctx.visit(Keywords.K_BEGIN).sql(' ').visit(Keywords.K_TRANSACTION);
                break;
            }
            case HSQLDB: {
                ctx.visit(Keywords.K_START).sql(' ').visit(Keywords.K_TRANSACTION).sql(' ').visit(Keywords.K_READ).sql(' ').visit(Keywords.K_WRITE);
                break;
            }
            default: {
                ctx.visit(Keywords.K_START).sql(' ').visit(Keywords.K_TRANSACTION);
            }
        }
    }
}

