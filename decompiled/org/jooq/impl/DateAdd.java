/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.jooq.Context;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class DateAdd<T>
extends AbstractField<T>
implements QOM.DateAdd<T> {
    final Field<T> date;
    final Field<? extends Number> interval;
    final DatePart datePart;

    DateAdd(Field<T> date, Field<? extends Number> interval) {
        super(Names.N_DATE_ADD, Tools.allNotNull(Tools.dataType(date), date, interval));
        this.date = Tools.nullSafeNotNull(date, SQLDataType.OTHER);
        this.interval = Tools.nullSafeNotNull(interval, SQLDataType.INTEGER);
        this.datePart = null;
    }

    DateAdd(Field<T> date, Field<? extends Number> interval, DatePart datePart) {
        super(Names.N_DATE_ADD, Tools.allNotNull(Tools.dataType(date), date, interval));
        this.date = Tools.nullSafeNotNull(date, SQLDataType.OTHER);
        this.interval = Tools.nullSafeNotNull(interval, SQLDataType.INTEGER);
        this.datePart = datePart;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.datePart == null) {
            ctx.visit(this.date.add(this.interval));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        Keyword keyword = null;
        Name name = null;
        String string = null;
        block0 : switch (ctx.family()) {
            case AURORA_MYSQL: 
            case BIGQUERY: 
            case MEMSQL: 
            case CUBRID: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Names.N_DATE_ADD).sql('(').visit(this.date).sql(", ").visit(Keywords.K_INTERVAL).sql(' ').visit(this.interval).sql(' ').visit(this.standardKeyword()).sql(')');
                break;
            }
            case DERBY: 
            case HSQLDB: {
                switch (this.datePart) {
                    case YEAR: {
                        keyword = DSL.keyword("sql_tsi_year");
                        break;
                    }
                    case MONTH: {
                        keyword = DSL.keyword("sql_tsi_month");
                        break;
                    }
                    case DAY: {
                        keyword = DSL.keyword("sql_tsi_day");
                        break;
                    }
                    case HOUR: {
                        keyword = DSL.keyword("sql_tsi_hour");
                        break;
                    }
                    case MINUTE: {
                        keyword = DSL.keyword("sql_tsi_minute");
                        break;
                    }
                    case SECOND: {
                        keyword = DSL.keyword("sql_tsi_second");
                        break;
                    }
                    default: {
                        throw this.unsupported();
                    }
                }
                ctx.sql("{fn ").visit(Names.N_TIMESTAMPADD).sql('(').visit(keyword).sql(", ").visit(this.interval).sql(", ").visit(this.date).sql(") }");
                break;
            }
            case H2: {
                switch (this.datePart) {
                    case YEAR: {
                        string = "year";
                        break;
                    }
                    case MONTH: {
                        string = "month";
                        break;
                    }
                    case DAY: {
                        string = "day";
                        break;
                    }
                    case HOUR: {
                        string = "hour";
                        break;
                    }
                    case MINUTE: {
                        string = "minute";
                        break;
                    }
                    case SECOND: {
                        string = "second";
                        break;
                    }
                    default: {
                        throw this.unsupported();
                    }
                }
                ctx.visit(Names.N_DATEADD).sql('(').visit(DSL.inline(string)).sql(", ").visit(this.interval).sql(", ").visit(this.date).sql(')');
                break;
            }
            case JAVA: {
                if (this.date.getType() == LocalDateTime.class) {
                    ctx.sql("localDateTimeAdd(");
                } else if (this.date.getType() == Timestamp.class) {
                    ctx.sql("timestampAdd(");
                } else if (this.date.getType() == LocalDate.class) {
                    ctx.sql("localDateAdd(");
                } else {
                    ctx.sql("dateAdd(");
                }
                ctx.visit(Val.getJavaValue(this.date)).sql(", ").visit(Val.getJavaValue(this.interval));
                if (this.datePart != null) {
                    ctx.sql(", ").sql("DatePart.").sql(this.datePart.name());
                }
                ctx.sql(')');
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case REDSHIFT: 
            case POSTGRES: 
            case YUGABYTEDB: {
                switch (this.datePart) {
                    case YEAR: {
                        string = "1 year";
                        break;
                    }
                    case MONTH: {
                        string = "1 month";
                        break;
                    }
                    case DAY: {
                        string = "1 day";
                        break;
                    }
                    case HOUR: {
                        string = "1 hour";
                        break;
                    }
                    case MINUTE: {
                        string = "1 minute";
                        break;
                    }
                    case SECOND: {
                        string = "1 second";
                        break;
                    }
                    default: {
                        throw this.unsupported();
                    }
                }
                if (((AbstractField)this.interval).getExpressionDataType().isInterval()) {
                    ctx.sql('(').visit(this.date).sql(" + ").visit(this.interval).sql(')');
                    break;
                }
                if (this.getDataType().isDate()) {
                    if (this.datePart == DatePart.DAY) {
                        ctx.sql('(').visit(this.date).sql(" + ").visit(this.interval).sql(')');
                        break;
                    }
                    ctx.sql("cast((").visit(this.date).sql(" + ").visit(this.interval).sql(" * ").visit(Keywords.K_INTERVAL).sql(' ').visit(DSL.inline(string)).sql(") as date)");
                    break;
                }
                ctx.sql('(').visit(this.date).sql(" + ").visit(this.interval).sql(" * ").visit(Keywords.K_INTERVAL).sql(' ').visit(DSL.inline(string)).sql(")");
                break;
            }
            case SQLITE: {
                switch (this.datePart) {
                    case YEAR: {
                        string = " year";
                        break;
                    }
                    case MONTH: {
                        string = " month";
                        break;
                    }
                    case DAY: {
                        string = " day";
                        break;
                    }
                    case HOUR: {
                        string = " hour";
                        break;
                    }
                    case MINUTE: {
                        string = " minute";
                        break;
                    }
                    case SECOND: {
                        string = " second";
                        break;
                    }
                    default: {
                        throw this.unsupported();
                    }
                }
                ctx.visit(Names.N_STRFTIME).sql("('%Y-%m-%d %H:%M:%f', ").visit(this.date).sql(", ").visit(this.interval.concat(DSL.inline(string))).sql(')');
                break;
            }
            case ACCESS: {
                switch (this.datePart) {
                    case YEAR: {
                        string = "yyyy";
                        break;
                    }
                    case MONTH: {
                        string = "m";
                        break;
                    }
                    case DAY: {
                        string = "d";
                        break;
                    }
                    case HOUR: {
                        string = "h";
                        break;
                    }
                    case MINUTE: {
                        string = "n";
                        break;
                    }
                    case SECOND: {
                        string = "s";
                        break;
                    }
                    default: {
                        throw this.unsupported();
                    }
                }
                ctx.visit(Names.N_DATEADD).sql('(').visit(DSL.inline(string)).sql(", ").visit(this.interval).sql(", ").visit(this.date).sql(')');
                break;
            }
            case ASE: 
            case SYBASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                switch (this.datePart) {
                    case YEAR: {
                        keyword = DSL.keyword("yy");
                        break;
                    }
                    case MONTH: {
                        keyword = DSL.keyword("mm");
                        break;
                    }
                    case DAY: {
                        keyword = DSL.keyword("dd");
                        break;
                    }
                    case HOUR: {
                        keyword = DSL.keyword("hh");
                        break;
                    }
                    case MINUTE: {
                        keyword = DSL.keyword("mi");
                        break;
                    }
                    case SECOND: {
                        keyword = DSL.keyword("ss");
                        break;
                    }
                    default: {
                        throw this.unsupported();
                    }
                }
                ctx.visit(Names.N_DATEADD).sql('(').visit(keyword).sql(", ").visit(this.interval).sql(", ").visit(this.date).sql(')');
                break;
            }
            case EXASOL: 
            case HANA: {
                boolean needsMultiplier = ctx.family() == SQLDialect.HANA;
                String multiplier = null;
                switch (this.datePart) {
                    case YEAR: {
                        name = Names.N_ADD_YEARS;
                        break;
                    }
                    case MONTH: {
                        name = Names.N_ADD_MONTHS;
                        break;
                    }
                    case DAY: {
                        name = Names.N_ADD_DAYS;
                        break;
                    }
                    case HOUR: {
                        if (needsMultiplier) {
                            name = Names.N_ADD_SECONDS;
                            multiplier = "3600";
                            break;
                        }
                        name = Names.N_ADD_HOURS;
                        break;
                    }
                    case MINUTE: {
                        if (needsMultiplier) {
                            name = Names.N_ADD_SECONDS;
                            multiplier = "60";
                            break;
                        }
                        name = Names.N_ADD_MINUTES;
                        break;
                    }
                    case SECOND: {
                        name = Names.N_ADD_SECONDS;
                        break;
                    }
                    default: {
                        throw this.unsupported();
                    }
                }
                if (multiplier == null) {
                    ctx.visit(name).sql('(').visit(this.date).sql(", ").visit(this.interval).sql(')');
                    break;
                }
                ctx.visit(name).sql('(').visit(this.date).sql(", ").visit(this.interval).sql(" * ").visit(DSL.sql(multiplier)).sql(')');
                break;
            }
            case INFORMIX: {
                ctx.visit(this.date.add(DSL.field("{0} {units} {1}", this.interval, this.standardKeyword())));
                break;
            }
            case DB2: {
                ctx.visit(this.date.add(DSL.field("{0} {1}", this.interval, this.standardKeyword())));
                break;
            }
            case ORACLE: {
                switch (this.datePart) {
                    case YEAR: {
                        ctx.visit(Names.N_ADD_MONTHS).sql('(').visit(this.date).sql(", ").visit(this.interval.mul(DSL.inline(12))).sql(')');
                        break block0;
                    }
                    case MONTH: {
                        ctx.visit(Names.N_ADD_MONTHS).sql('(').visit(this.date).sql(", ").visit(this.interval).sql(')');
                        break block0;
                    }
                    case DAY: {
                        ctx.visit(this.date.add(DSL.function("numtodsinterval", SQLDataType.INTERVAL, this.interval, DSL.inline("day"))));
                        break block0;
                    }
                    case HOUR: {
                        ctx.visit(this.date.add(DSL.function("numtodsinterval", SQLDataType.INTERVAL, this.interval, DSL.inline("hour"))));
                        break block0;
                    }
                    case MINUTE: {
                        ctx.visit(this.date.add(DSL.function("numtodsinterval", SQLDataType.INTERVAL, this.interval, DSL.inline("minute"))));
                        break block0;
                    }
                    case SECOND: {
                        ctx.visit(this.date.add(DSL.function("numtodsinterval", SQLDataType.INTERVAL, this.interval, DSL.inline("second"))));
                        break block0;
                    }
                }
                throw this.unsupported();
            }
            case TERADATA: {
                switch (this.datePart) {
                    case YEAR: {
                        ctx.visit(Names.N_ADD_MONTHS).sql('(').visit(this.date).sql(", ").visit(this.interval.mul(DSL.inline(12))).sql(')');
                        break block0;
                    }
                    case MONTH: {
                        ctx.visit(Names.N_ADD_MONTHS).sql('(').visit(this.date).sql(", ").visit(this.interval).sql(')');
                        break block0;
                    }
                    case DAY: {
                        ctx.sql('(').visit(this.date).sql(" + ").visit(Keywords.K_CAST).sql('(').visit(this.interval).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_INTERVAL).sql(' ').visit(Keywords.K_DAY).sql(" (4)))");
                        break block0;
                    }
                    case HOUR: {
                        ctx.sql('(').visit(this.date).sql(" + ").visit(Keywords.K_CAST).sql('(').visit(this.interval).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_INTERVAL).sql(' ').visit(Keywords.K_HOUR).sql(" (4)))");
                        break block0;
                    }
                    case MINUTE: {
                        ctx.sql('(').visit(this.date).sql(" + ").visit(Keywords.K_CAST).sql('(').visit(this.interval).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_INTERVAL).sql(' ').visit(Keywords.K_MINUTE).sql(" (4)))");
                        break block0;
                    }
                    case SECOND: {
                        ctx.sql('(').visit(this.date).sql(" + ").visit(Keywords.K_CAST).sql('(').visit(this.interval).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_INTERVAL).sql(' ').visit(Keywords.K_SECOND).sql(" (4, 6)))");
                        break block0;
                    }
                }
                throw this.unsupported();
            }
            case VERTICA: {
                ctx.visit(this.date.add(DSL.field("{cast}({0} {as} {interval} {1})", this.interval, this.standardKeyword())));
                break;
            }
            default: {
                ctx.visit(Names.N_DATEADD).sql('(').visit(this.standardKeyword()).sql(", ").visit(this.interval).sql(", ").visit(this.date).sql(')');
            }
        }
    }

    private final Keyword standardKeyword() {
        switch (this.datePart) {
            case YEAR: 
            case MONTH: 
            case DAY: 
            case HOUR: 
            case MINUTE: 
            case SECOND: {
                return this.datePart.toKeyword();
            }
        }
        throw this.unsupported();
    }

    private final UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Unknown date part : " + String.valueOf((Object)this.datePart));
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("dateAdd(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.date));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.interval));
        s2 = ", ";
        if (this.datePart != null) {
            ctx.sql(s2).sql("DatePart.").sql(this.datePart.name());
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.date;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.interval;
    }

    @Override
    public final DatePart $arg3() {
        return this.datePart;
    }

    @Override
    public final QOM.DateAdd<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<Number>)this.$arg2(), this.$arg3());
    }

    @Override
    public final QOM.DateAdd<T> $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<Number>)newValue, this.$arg3());
    }

    @Override
    public final QOM.DateAdd<T> $arg3(DatePart newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<Number>)this.$arg2(), newValue);
    }

    @Override
    public final Function3<? super Field<T>, ? super Field<? extends Number>, ? super DatePart, ? extends QOM.DateAdd<T>> $constructor() {
        return (a1, a2, a3) -> new DateAdd(a1, (Field<? extends Number>)((Field<Number>)a2), (DatePart)((Object)a3));
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.DateAdd) {
            QOM.DateAdd o = (QOM.DateAdd)that;
            return StringUtils.equals(this.$date(), o.$date()) && StringUtils.equals(this.$interval(), o.$interval()) && StringUtils.equals((Object)this.$datePart(), (Object)o.$datePart());
        }
        return super.equals(that);
    }
}

