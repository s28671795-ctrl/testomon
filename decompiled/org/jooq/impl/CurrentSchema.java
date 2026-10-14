/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class CurrentSchema
extends AbstractField<String>
implements QOM.CurrentSchema {
    CurrentSchema() {
        super(Names.N_CURRENT_SCHEMA, Tools.allNotNull(SQLDataType.VARCHAR));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ORACLE: {
                ctx.visit(Names.N_USER);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_SCHEMA_NAME).sql("()");
                break;
            }
            case TERADATA: {
                ctx.visit(Keywords.K_DATABASE);
                break;
            }
            case CUBRID: 
            case FIREBIRD: 
            case SQLITE: {
                ctx.visit(DSL.inline(""));
                break;
            }
            case DERBY: {
                ctx.visit(Keywords.K_CURRENT).sql(' ').visit(Keywords.K_SCHEMA);
                break;
            }
            case H2: {
                ctx.visit(Keywords.K_SCHEMA).sql("()");
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Keywords.K_DATABASE).sql("()");
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case DB2: 
            case HSQLDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(Keywords.K_CURRENT_SCHEMA);
                break;
            }
            default: {
                ctx.visit(Keywords.K_CURRENT_SCHEMA).sql("()");
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("currentSchema()");
    }

    @Override
    public final Function0<? extends QOM.CurrentSchema> $constructor() {
        return () -> new CurrentSchema();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.CurrentSchema) {
            QOM.CurrentSchema o = (QOM.CurrentSchema)that;
            return true;
        }
        return super.equals(that);
    }
}

