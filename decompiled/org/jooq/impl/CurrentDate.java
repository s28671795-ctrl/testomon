/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class CurrentDate<T>
extends AbstractField<T>
implements QOM.CurrentDate<T> {
    CurrentDate(DataType<T> type) {
        super(Names.N_CURRENT_DATE, type);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.getType() == Date.class) {
                    ctx.sql("currentDate()");
                    break;
                }
                ctx.sql("currentLocalTime()");
                break;
            }
            case ACCESS: {
                ctx.visit(Keywords.K_DATE).sql("()");
                break;
            }
            case ORACLE: {
                ctx.visit(Names.N_TRUNC).sql('(').visit(Names.N_CURRENT_DATE).sql(')');
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(Names.N_CONVERT).sql('(').visit(Keywords.K_DATE).sql(", ").visit(Names.N_CURRENT_TIMESTAMP).sql(')');
                break;
            }
            case SYBASE: {
                ctx.visit(Keywords.K_CURRENT).sql(' ').visit(Keywords.K_DATE);
                break;
            }
            case INFORMIX: {
                ctx.visit(Keywords.K_CURRENT).sql(' ').visit(Keywords.K_YEAR_TO_DAY);
                break;
            }
            case ASE: 
            case AURORA_MYSQL: 
            case MEMSQL: 
            case SNOWFLAKE: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Names.N_CURRENT_DATE).sql("()");
                break;
            }
            default: {
                ctx.visit(Keywords.K_CURRENT).sql('_').visit(Keywords.K_DATE);
            }
        }
    }
}

