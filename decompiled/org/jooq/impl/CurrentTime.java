/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Time;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class CurrentTime<T>
extends AbstractField<T>
implements QOM.CurrentTime<T> {
    CurrentTime(DataType<T> type) {
        super(Names.N_CURRENT_TIME, type);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.getType() == Time.class) {
                    ctx.sql("currentTime()");
                    break;
                }
                ctx.sql("currentLocalTime()");
                break;
            }
            case ACCESS: {
                ctx.visit(Keywords.K_TIME).sql("()");
                break;
            }
            case EXASOL: 
            case ORACLE: {
                ctx.visit(Names.N_CURRENT_TIMESTAMP);
                break;
            }
            case INFORMIX: {
                ctx.visit(Keywords.K_CURRENT).sql(' ').visit(Keywords.K_HOUR_TO_SECOND);
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_CONVERT).sql('(').visit(Keywords.K_TIME).sql(", ").visit(Names.N_CURRENT_TIMESTAMP).sql(')');
                break;
            }
            case SYBASE: {
                ctx.visit(Keywords.K_CURRENT).sql(' ').visit(Keywords.K_TIME);
                break;
            }
            case ASE: 
            case AURORA_MYSQL: 
            case MEMSQL: 
            case SNOWFLAKE: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Names.N_CURRENT_TIME).sql("()");
                break;
            }
            default: {
                ctx.visit(Keywords.K_CURRENT).sql('_').visit(Keywords.K_TIME);
            }
        }
    }
}

