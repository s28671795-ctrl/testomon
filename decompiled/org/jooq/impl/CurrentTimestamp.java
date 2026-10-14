/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class CurrentTimestamp<T>
extends AbstractField<T>
implements QOM.CurrentTimestamp<T> {
    private static final Set<SQLDialect> NO_SUPPORT_PRECISION = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE);
    private final Field<Integer> precision;

    CurrentTimestamp(DataType<T> type) {
        this(type, null);
    }

    CurrentTimestamp(DataType<T> type, Field<Integer> precision) {
        super(Names.N_CURRENT_TIMESTAMP, type);
        this.precision = precision;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.precision != null) {
                    ctx.sql(this.getType() == Timestamp.class ? "currentTimestamp(" : "currentLocalDateTime(").visit(this.precision).sql(')');
                    break;
                }
                ctx.sql(this.getType() == Timestamp.class ? "currentTimestamp()" : "currentLocalDateTime()");
                break;
            }
            case ACCESS: {
                ctx.visit(Names.N_NOW).sql("()");
                break;
            }
            case ASE: {
                if (SQLDialect.ASE_15_5.precedes(ctx.dialect())) {
                    ctx.visit(Names.N_CURRENT_BIGDATETIME).sql("()");
                    break;
                }
                ctx.visit(Names.N_GETDATE).sql("()");
                break;
            }
            case SYBASE: {
                ctx.visit(Keywords.K_CURRENT).sql(' ').visit(Keywords.K_TIMESTAMP);
                break;
            }
            case INFORMIX: {
                ctx.visit(Keywords.K_CURRENT).sql(' ').visit(Keywords.K_YEAR).sql(' ').visit(Keywords.K_TO).sql(' ').visit(Keywords.K_FRACTION).sql(" (5)");
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case SNOWFLAKE: 
            case MARIADB: 
            case MYSQL: {
                if (this.precision != null && !NO_SUPPORT_PRECISION.contains((Object)ctx.dialect())) {
                    ctx.visit(Names.N_CURRENT_TIMESTAMP).sql('(').visit(this.precision).sql(')');
                    break;
                }
                ctx.visit(Names.N_CURRENT_TIMESTAMP).sql("()");
                break;
            }
            default: {
                if (this.precision != null && !NO_SUPPORT_PRECISION.contains((Object)ctx.dialect())) {
                    ctx.visit(Keywords.K_CURRENT).sql('_').visit(Keywords.K_TIMESTAMP).sql('(').visit(this.precision).sql(')');
                    break;
                }
                ctx.visit(Keywords.K_CURRENT).sql('_').visit(Keywords.K_TIMESTAMP);
            }
        }
    }
}

