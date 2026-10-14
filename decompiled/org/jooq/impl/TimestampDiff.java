/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.types.DayToSecond;

final class TimestampDiff<T>
extends AbstractField<DayToSecond>
implements QOM.TimestampDiff<T> {
    private final Field<T> timestamp1;
    private final Field<T> timestamp2;

    TimestampDiff(Field<T> timestamp1, Field<T> timestamp2) {
        super(Names.N_TIMESTAMPDIFF, SQLDataType.INTERVALDAYTOSECOND);
        this.timestamp1 = timestamp1;
        this.timestamp2 = timestamp2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(this.timestamp1.getType() == Timestamp.class ? "timestampDiff(" : "localDateTimeDiff(").visit(Val.getJavaValue(this.timestamp1)).sql(", ").visit(Val.getJavaValue(this.timestamp2)).sql(')');
                return;
            }
            case ACCESS: {
                ctx.visit(Names.N_DATEDIFF).sql("('s', ").visit(this.timestamp2).sql(", ").visit(this.timestamp1).sql(") * 1000");
                break;
            }
            case ASE: {
                Field<Integer> days = DSL.field("{datediff}(day, {0}, {1})", SQLDataType.INTEGER, new QueryPart[]{this.timestamp2, this.timestamp1});
                Field<Integer> milli = DSL.field("{datediff}(ms, {0}, {1})", SQLDataType.INTEGER, new QueryPart[]{this.timestamp2.add(days), this.timestamp1});
                ctx.visit(days.mul(86400000).add(milli));
                break;
            }
            case DB2: {
                ctx.visit(DSL.function(Names.N_DAYS, SQLDataType.INTEGER, this.timestamp1).sub(DSL.function(Names.N_DAYS, SQLDataType.INTEGER, this.timestamp2)).mul(86400000).add(DSL.function(DSL.systemName("midnight_seconds"), SQLDataType.INTEGER, this.timestamp1).sub(DSL.function(DSL.systemName("midnight_seconds"), SQLDataType.INTEGER, this.timestamp2)).mul(1000)));
                break;
            }
            case HANA: {
                ctx.visit(Names.N_NANO100_BETWEEN).sql('(').visit(this.timestamp2).sql(", ").visit(this.timestamp1).sql(") / 10000");
                break;
            }
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: {
                ctx.visit(Names.N_DATEDIFF).sql("(ms, ").visit(this.timestamp2).sql(", ").visit(this.timestamp1).sql(')');
                break;
            }
            case BIGQUERY: {
                ctx.visit(Names.N_TIMESTAMP_DIFF).sql('(').visit(this.timestamp1).sql(", ").visit(this.timestamp2).sql(", ").visit(Keywords.K_MILLISECOND).sql(')');
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case ORACLE: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.sql('(').visit(this.timestamp1).sql(" - ").visit(this.timestamp2).sql(')');
                break;
            }
            case CUBRID: {
                ctx.visit(this.timestamp1.sub(this.timestamp2));
                break;
            }
            case DERBY: {
                ctx.sql("1000 * {fn ").visit(Names.N_TIMESTAMPDIFF).sql('(').visit(DSL.keyword("sql_tsi_second")).sql(", ").visit(this.timestamp2).sql(", ").visit(this.timestamp1).sql(") }");
                break;
            }
            case VERTICA: 
            case FIREBIRD: {
                ctx.visit(Names.N_DATEDIFF).sql('(').visit(Keywords.K_MILLISECOND).sql(", ").visit(this.timestamp2).sql(", ").visit(this.timestamp1).sql(')');
                break;
            }
            case H2: 
            case HSQLDB: {
                ctx.visit(Names.N_DATEDIFF).sql("('ms', ").visit(this.timestamp2).sql(", ").visit(this.timestamp1).sql(')');
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Names.N_TIMESTAMPDIFF).sql('(').visit(DSL.keyword("microsecond")).sql(", ").visit(this.timestamp2).sql(", ").visit(this.timestamp1).sql(") / 1000");
                break;
            }
            case SQLITE: {
                ctx.sql('(').visit(Names.N_STRFTIME).sql("('%s', ").visit(this.timestamp1).sql(") - ").visit(Names.N_STRFTIME).sql("('%s', ").visit(this.timestamp2).sql(")) * 1000");
                break;
            }
            default: {
                ctx.visit(Tools.castIfNeeded(this.timestamp1.sub(this.timestamp2), DayToSecond.class));
            }
        }
    }

    @Override
    public final Field<T> $arg1() {
        return this.timestamp1;
    }

    @Override
    public final Field<T> $arg2() {
        return this.timestamp2;
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.TimestampDiff<T>> $constructor() {
        return (t1, t2) -> new TimestampDiff(t1, t2);
    }
}

