/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import org.jooq.Context;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;

final class DateDiff<T>
extends AbstractField<Integer>
implements QOM.UNotYetImplemented {
    private final DatePart part;
    private final Field<T> startDate;
    private final Field<T> endDate;

    DateDiff(DatePart part, Field<T> startDate, Field<T> endDate) {
        super(Names.N_DATEDIFF, SQLDataType.INTEGER);
        this.part = part;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public final void accept(Context<?> ctx) {
        DatePart p = this.part == null ? DatePart.DAY : this.part;
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                switch (p) {
                    case MILLENNIUM: 
                    case CENTURY: 
                    case DECADE: 
                    case YEAR: {
                        ctx.visit(this.partDiff(p));
                        return;
                    }
                    case QUARTER: 
                    case MONTH: {
                        ctx.visit(this.monthDiff(p));
                        return;
                    }
                    case DAY: {
                        ctx.visit(Names.N_DATEDIFF).sql('(').visit(this.endDate).sql(", ").visit(this.startDate).sql(')');
                        return;
                    }
                    case MILLISECOND: {
                        ctx.visit(new DateDiff<T>(DatePart.MICROSECOND, this.startDate, this.endDate).div(DSL.inline(1000)));
                        return;
                    }
                    case NANOSECOND: {
                        ctx.visit(new DateDiff<T>(DatePart.MICROSECOND, this.startDate, this.endDate).times(DSL.inline(1000)));
                        return;
                    }
                }
                ctx.visit(Names.N_TIMESTAMPDIFF).sql('(').visit(p.toName()).sql(", ").visit(this.startDate).sql(", ").visit(this.endDate).sql(')');
                return;
            }
            case DERBY: {
                Name name = Names.N_SQL_TSI_DAY;
                switch (p) {
                    case MILLENNIUM: 
                    case CENTURY: 
                    case DECADE: 
                    case YEAR: {
                        ctx.visit(this.partDiff(p));
                        return;
                    }
                    case QUARTER: 
                    case MONTH: {
                        ctx.visit(this.monthDiff(p));
                        return;
                    }
                    case DAY: {
                        name = Names.N_SQL_TSI_DAY;
                        break;
                    }
                    case HOUR: {
                        name = Names.N_SQL_TSI_HOUR;
                        break;
                    }
                    case MINUTE: {
                        name = Names.N_SQL_TSI_MINUTE;
                        break;
                    }
                    case SECOND: {
                        name = Names.N_SQL_TSI_SECOND;
                        break;
                    }
                    case NANOSECOND: {
                        name = Names.N_SQL_TSI_FRAC_SECOND;
                        break;
                    }
                    case MILLISECOND: {
                        ctx.visit(new DateDiff<T>(DatePart.NANOSECOND, this.startDate, this.endDate).div(DSL.inline(1000000L)));
                        return;
                    }
                    case MICROSECOND: {
                        ctx.visit(new DateDiff<T>(DatePart.NANOSECOND, this.startDate, this.endDate).div(DSL.inline(1000L)));
                        return;
                    }
                }
                ctx.sql("{fn ").visit(Names.N_TIMESTAMPDIFF).sql('(').visit(name).sql(", ").visit(this.startDate).sql(", ").visit(this.endDate).sql(") }");
                return;
            }
            case ASE: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case VERTICA: 
            case FIREBIRD: 
            case H2: 
            case HSQLDB: {
                switch (p) {
                    case MILLENNIUM: 
                    case CENTURY: 
                    case DECADE: {
                        ctx.visit(this.partDiff(p));
                        return;
                    }
                    case QUARTER: {
                        if (ctx.family() != SQLDialect.FIREBIRD) break;
                        ctx.visit(this.monthDiff(DatePart.QUARTER));
                        return;
                    }
                    case MILLISECOND: 
                    case NANOSECOND: 
                    case HOUR: 
                    case MINUTE: 
                    case SECOND: 
                    case MICROSECOND: {
                        if (ctx.family() != SQLDialect.HSQLDB) break;
                        ctx.visit(Names.N_DATEDIFF).sql('(').visit(p.toKeyword()).sql(", ").visit(this.startDate.cast(SQLDataType.TIMESTAMP)).sql(", ").visit(this.endDate.cast(SQLDataType.TIMESTAMP)).sql(')');
                        return;
                    }
                }
                ctx.visit(Names.N_DATEDIFF).sql('(').visit(p.toKeyword()).sql(", ").visit(this.startDate).sql(", ").visit(this.endDate).sql(')');
                return;
            }
            case JAVA: {
                ctx.sql(this.startDate.getType() == Date.class ? "dateDiff(DatePart." : "localDateDiff(DatePart.").sql(p.name()).sql(", ").visit(Val.getJavaValue(this.startDate)).sql(", ").visit(Val.getJavaValue(this.endDate)).sql(')');
                return;
            }
            case REDSHIFT: {
                ctx.visit(Names.N_DATEDIFF).sql('(').visit(DSL.inline(p.name().toLowerCase())).sql(", ").visit(this.startDate).sql(", ").visit(this.endDate).sql(')');
                return;
            }
            case BIGQUERY: {
                ctx.visit(Names.N_DATE_DIFF).sql('(').visit(this.endDate).sql(", ").visit(this.startDate).sql(", ").visit(p.toKeyword()).sql(')');
                return;
            }
            case SQLITE: {
                ctx.sql('(').visit(Names.N_STRFTIME).sql("('%s', ").visit(this.endDate).sql(") - ").visit(Names.N_STRFTIME).sql("('%s', ").visit(this.startDate).sql(")) / 86400");
                return;
            }
            case DB2: 
            case HANA: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: 
            case ORACLE: 
            case CUBRID: {
                switch (p) {
                    case MILLENNIUM: 
                    case CENTURY: 
                    case DECADE: 
                    case YEAR: {
                        ctx.visit(this.partDiff(p));
                        return;
                    }
                    case QUARTER: 
                    case MONTH: {
                        ctx.visit(this.monthDiff(p));
                        return;
                    }
                    case DAY: {
                        switch (ctx.family()) {
                            case DB2: {
                                ctx.visit(DSL.function(Names.N_DAYS, this.getDataType(), this.endDate).minus(DSL.function(Names.N_DAYS, this.getDataType(), this.startDate)));
                                return;
                            }
                            case HANA: {
                                ctx.visit(Names.N_DAYS_BETWEEN).sql('(').visit(this.startDate).sql(", ").visit(this.endDate).sql(')');
                                return;
                            }
                            case AURORA_POSTGRES: 
                            case COCKROACHDB: 
                            case POSTGRES: 
                            case YUGABYTEDB: {
                                if (this.endDate.getDataType().isDate() && this.startDate.getDataType().isDate()) {
                                    ctx.sql('(').visit(this.endDate).sql(" - ").visit(this.startDate).sql(')');
                                } else {
                                    ctx.visit(Names.N_EXTRACT).sql('(').visit(Keywords.K_DAY).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.endDate).sql(" - ").visit(this.startDate).sql(')');
                                }
                                return;
                            }
                        }
                        ctx.sql('(').visit(this.endDate).sql(" - ").visit(this.startDate).sql(')');
                        return;
                    }
                    case HOUR: 
                    case MINUTE: {
                        ctx.visit(this.partDiff(DatePart.EPOCH).div(p == DatePart.HOUR ? DSL.inline(3600) : DSL.inline(60)));
                        return;
                    }
                    case SECOND: {
                        ctx.visit(this.partDiff(DatePart.EPOCH));
                        return;
                    }
                    case MILLISECOND: 
                    case NANOSECOND: 
                    case MICROSECOND: {
                        ctx.visit(this.partDiff(DatePart.EPOCH).times(p == DatePart.MILLISECOND ? DSL.inline(1000) : (p == DatePart.MICROSECOND ? DSL.inline(1000000) : DSL.inline(1000000000))));
                        return;
                    }
                }
                break;
            }
            case ACCESS: {
                ctx.visit(Names.N_DATEDIFF).sql("('d', ").visit(this.startDate).sql(", ").visit(this.endDate).sql(')');
                return;
            }
        }
        ctx.visit(Tools.castIfNeeded(this.endDate.minus(this.startDate), Integer.class));
    }

    private final Field<Integer> partDiff(DatePart p) {
        return DSL.extract(this.endDate, p).minus(DSL.extract(this.startDate, p));
    }

    private final Field<Integer> monthDiff(DatePart p) {
        return this.partDiff(DatePart.YEAR).times(p == DatePart.QUARTER ? DSL.inline(4) : DSL.inline(12)).plus(this.partDiff(p));
    }
}

