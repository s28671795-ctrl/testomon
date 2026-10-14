/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import java.sql.Timestamp;
import org.jooq.Context;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.Keyword;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Cast;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Extract
extends AbstractField<Integer>
implements QOM.Extract {
    private final Field<?> field;
    private final DatePart datePart;

    Extract(Field<?> field2, DatePart datePart) {
        super(Names.N_EXTRACT, SQLDataType.INTEGER);
        this.field = field2;
        this.datePart = datePart;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLITE: {
                switch (this.datePart) {
                    case YEAR: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%Y"), this.field).cast(SQLDataType.INTEGER));
                        return;
                    }
                    case MONTH: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%m"), this.field).cast(SQLDataType.INTEGER));
                        return;
                    }
                    case DAY: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%d"), this.field).cast(SQLDataType.INTEGER));
                        return;
                    }
                    case HOUR: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%H"), this.field).cast(SQLDataType.INTEGER));
                        return;
                    }
                    case MINUTE: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%M"), this.field).cast(SQLDataType.INTEGER));
                        return;
                    }
                    case SECOND: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%S"), this.field).cast(SQLDataType.INTEGER));
                        return;
                    }
                    case EPOCH: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%s"), this.field).cast(SQLDataType.INTEGER));
                        return;
                    }
                    case ISO_DAY_OF_WEEK: {
                        ctx.visit(Extract.dowSun0ToISO(DSL.function(DSL.systemName("strftime"), SQLDataType.INTEGER, DSL.inline("%w"), this.field).cast(SQLDataType.INTEGER)));
                        return;
                    }
                    case DAY_OF_WEEK: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%w"), this.field).cast(SQLDataType.INTEGER).plus(DSL.one()));
                        return;
                    }
                    case DAY_OF_YEAR: {
                        ctx.visit(DSL.function(Names.N_STRFTIME, SQLDataType.VARCHAR, DSL.inline("%j"), this.field).cast(SQLDataType.INTEGER));
                        return;
                    }
                }
                break;
            }
            case JAVA: {
                ctx.sql("extract(").visit(this.field).sql(", DatePart.").sql(this.datePart.name()).sql(")");
                return;
            }
            case INFORMIX: {
                switch (this.datePart) {
                    case YEAR: {
                        ctx.visit(Keywords.K_YEAR).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case MONTH: {
                        ctx.visit(Keywords.K_MONTH).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case DAY: {
                        ctx.visit(Keywords.K_DAY).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case HOUR: {
                        Cast.renderCast(ctx, c1 -> c1.visit(this.field), c1 -> Cast.renderCast(c1, c2 -> c2.visit(DSL.keyword("datetime hour to hour")), c2 -> Cast.renderCast(c2, c3 -> c3.visit(DSL.keyword("char(2)")), c3 -> c3.visit(Keywords.K_INT))));
                        return;
                    }
                    case MINUTE: {
                        Cast.renderCast(ctx, c1 -> c1.visit(this.field), c1 -> Cast.renderCast(c1, c2 -> c2.visit(DSL.keyword("datetime minute to minute")), c2 -> Cast.renderCast(c2, c3 -> c3.visit(DSL.keyword("char(2)")), c3 -> c3.visit(Keywords.K_INT))));
                        return;
                    }
                    case SECOND: {
                        Cast.renderCast(ctx, c1 -> c1.visit(this.field), c1 -> Cast.renderCast(c1, c2 -> c2.visit(DSL.keyword("datetime second to second")), c2 -> Cast.renderCast(c2, c3 -> c3.visit(DSL.keyword("char(2)")), c3 -> c3.visit(Keywords.K_INT))));
                        return;
                    }
                }
                break;
            }
            case DB2: {
                switch (this.datePart) {
                    case QUARTER: {
                        ctx.visit(this.datePart.toName()).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case ISO_DAY_OF_WEEK: {
                        ctx.visit(DSL.keyword("dayofweek_iso")).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case DAY_OF_WEEK: {
                        ctx.visit(Names.N_DAYOFWEEK).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case DAY_OF_YEAR: {
                        ctx.visit(Names.N_DAYOFYEAR).sql('(').visit(this.field).sql(')');
                        return;
                    }
                }
            }
            case DERBY: {
                switch (this.datePart) {
                    case YEAR: {
                        ctx.visit(Keywords.K_YEAR).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case MONTH: {
                        ctx.visit(Keywords.K_MONTH).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case DAY: {
                        ctx.visit(Keywords.K_DAY).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case HOUR: {
                        ctx.visit(Keywords.K_HOUR).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case MINUTE: {
                        ctx.visit(Keywords.K_MINUTE).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case SECOND: {
                        ctx.visit(Keywords.K_SECOND).sql('(').visit(this.field).sql(')');
                        return;
                    }
                }
                break;
            }
            case ACCESS: {
                switch (this.datePart) {
                    case YEAR: {
                        ctx.visit(Names.N_DATEPART).sql("('yyyy', ").visit(this.field).sql(')');
                        return;
                    }
                    case MONTH: {
                        ctx.visit(Names.N_DATEPART).sql("('m', ").visit(this.field).sql(')');
                        return;
                    }
                    case DAY: {
                        ctx.visit(Names.N_DATEPART).sql("('d', ").visit(this.field).sql(')');
                        return;
                    }
                    case HOUR: {
                        ctx.visit(Names.N_DATEPART).sql("('h', ").visit(this.field).sql(')');
                        return;
                    }
                    case MINUTE: {
                        ctx.visit(Names.N_DATEPART).sql("('n', ").visit(this.field).sql(')');
                        return;
                    }
                    case SECOND: {
                        ctx.visit(Names.N_DATEPART).sql("('s', ").visit(this.field).sql(')');
                        return;
                    }
                }
                break;
            }
            case HANA: {
                switch (this.datePart) {
                    case ISO_DAY_OF_WEEK: {
                        ctx.sql('(').visit(Names.N_WEEKDAY).sql('(').visit(this.field).sql(") + 1)");
                        return;
                    }
                    case DAY_OF_WEEK: {
                        ctx.visit(Extract.dowISOToSun1(DSL.isoDayOfWeek(this.field)));
                        return;
                    }
                    case DAY_OF_YEAR: {
                        ctx.visit(Names.N_DAYOFYEAR).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case EPOCH: {
                        ctx.visit(Names.N_SECONDS_BETWEEN).sql("('1970-01-01', ").visit(this.field).sql(')');
                        return;
                    }
                    case WEEK: {
                        ctx.visit(this.datePart.toName()).sql('(').visit(this.field).sql(')');
                        return;
                    }
                }
                break;
            }
            case ORACLE: {
                switch (this.datePart) {
                    case HOUR: 
                    case MINUTE: 
                    case SECOND: {
                        ctx.visit(Names.N_EXTRACT).sql('(').visit(this.datePart.toKeyword()).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(Tools.castIfNeeded(this.field, Timestamp.class)).sql(')');
                        return;
                    }
                    case EPOCH: {
                        ctx.visit(Names.N_TRUNC).sql("((").visit(Tools.castIfNeeded(this.field, Date.class)).sql(" - ").visit(Keywords.K_DATE).sql(" '1970-01-01') * 86400)");
                        return;
                    }
                    case QUARTER: {
                        ctx.visit(Names.N_TO_NUMBER).sql('(').visit(Names.N_TO_CHAR).sql('(').visit(this.field).sql(", 'Q'))");
                        return;
                    }
                    case ISO_DAY_OF_WEEK: {
                        ctx.visit(DSL.function(Names.N_TO_NUMBER, SQLDataType.INTEGER, DSL.function(Names.N_TO_CHAR, SQLDataType.VARCHAR, this.field, DSL.inline("D"))));
                        return;
                    }
                    case WEEK: {
                        ctx.visit(Names.N_TO_NUMBER).sql('(').visit(Names.N_TO_CHAR).sql('(').visit(this.field).sql(", 'IW'))");
                        return;
                    }
                    case DAY_OF_WEEK: {
                        ctx.visit(Extract.dowISOToSun1(DSL.function(Names.N_TO_NUMBER, SQLDataType.INTEGER, DSL.function(Names.N_TO_CHAR, SQLDataType.VARCHAR, this.field, DSL.inline("D")))));
                        return;
                    }
                    case DAY_OF_YEAR: {
                        ctx.visit(Names.N_TO_NUMBER).sql('(').visit(Names.N_TO_CHAR).sql('(').visit(this.field).sql(", 'DDD'))");
                        return;
                    }
                }
                break;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: {
                switch (this.datePart) {
                    case YEAR: {
                        ctx.visit(Names.N_DATEPART).sql("(yy, ").visit(this.field).sql(')');
                        return;
                    }
                    case MONTH: {
                        ctx.visit(Names.N_DATEPART).sql("(mm, ").visit(this.field).sql(')');
                        return;
                    }
                    case DAY: {
                        ctx.visit(Names.N_DATEPART).sql("(dd, ").visit(this.field).sql(')');
                        return;
                    }
                    case HOUR: {
                        ctx.visit(Names.N_DATEPART).sql("(hh, ").visit(this.field).sql(')');
                        return;
                    }
                    case MINUTE: {
                        ctx.visit(Names.N_DATEPART).sql("(mi, ").visit(this.field).sql(')');
                        return;
                    }
                    case SECOND: {
                        ctx.visit(Names.N_DATEPART).sql("(ss, ").visit(this.field).sql(')');
                        return;
                    }
                    case EPOCH: {
                        ctx.visit(Names.N_DATEDIFF).sql("(ss, '1970-01-01 00:00:00', ").visit(this.field).sql(')');
                        return;
                    }
                    case QUARTER: {
                        ctx.visit(Names.N_DATEPART).sql("(qq, ").visit(this.field).sql(')');
                        return;
                    }
                    case ISO_DAY_OF_WEEK: {
                        ctx.visit(Extract.dowSun1ToISO(DSL.field("{datepart}(dw, {0})", SQLDataType.INTEGER, new QueryPart[]{this.field}).add(DSL.field("@@datefirst", SQLDataType.INTEGER))));
                        return;
                    }
                    case DAY_OF_WEEK: {
                        ctx.visit(Extract.dowSun1ToISO(DSL.field("{datepart}(dw, {0})", SQLDataType.INTEGER, new QueryPart[]{this.field}).add(DSL.field("@@datefirst - 1", SQLDataType.INTEGER))));
                        return;
                    }
                    case DAY_OF_YEAR: {
                        ctx.visit(Names.N_DATEPART).sql("(dy, ").visit(this.field).sql(')');
                        return;
                    }
                }
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                switch (this.datePart) {
                    case DAY_OF_WEEK: {
                        ctx.visit(Names.N_DAYOFWEEK).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case DAY_OF_YEAR: {
                        ctx.visit(Names.N_DAYOFYEAR).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case EPOCH: {
                        ctx.visit(DSL.keyword("unix_timestamp")).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case ISO_DAY_OF_WEEK: {
                        ctx.visit(Names.N_WEEKDAY).sql('(').visit(this.field).sql(") + 1");
                        return;
                    }
                    case QUARTER: {
                        ctx.visit(this.datePart.toName()).sql('(').visit(this.field).sql(')');
                        return;
                    }
                }
                break;
            }
            case COCKROACHDB: 
            case SNOWFLAKE: {
                switch (this.datePart) {
                    case DECADE: 
                    case CENTURY: 
                    case MILLENNIUM: {
                        this.acceptDefaultEmulation(ctx);
                        return;
                    }
                }
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                switch (this.datePart) {
                    case DAY_OF_WEEK: {
                        ctx.sql('(');
                        this.acceptNativeFunction(ctx, DSL.keyword("dow"));
                        ctx.sql(" + 1)");
                        return;
                    }
                    case DAY_OF_YEAR: {
                        this.acceptNativeFunction(ctx, DSL.keyword("doy"));
                        return;
                    }
                    case ISO_DAY_OF_WEEK: {
                        this.acceptNativeFunction(ctx, DSL.keyword("isodow"));
                        return;
                    }
                    case MILLISECOND: {
                        this.acceptNativeFunction(ctx, DSL.keyword("milliseconds"));
                        return;
                    }
                    case MICROSECOND: {
                        this.acceptNativeFunction(ctx, DSL.keyword("microseconds"));
                        return;
                    }
                    case QUARTER: 
                    case DECADE: 
                    case CENTURY: 
                    case MILLENNIUM: 
                    case TIMEZONE: {
                        this.acceptNativeFunction(ctx);
                        return;
                    }
                }
                break;
            }
            case HSQLDB: {
                switch (this.datePart) {
                    case EPOCH: {
                        ctx.visit(DSL.keyword("unix_timestamp")).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case ISO_DAY_OF_WEEK: {
                        ctx.visit(Extract.dowSun1ToISO(DSL.field("{extract}({day_of_week from} {0})", SQLDataType.INTEGER, new QueryPart[]{this.field})));
                        return;
                    }
                    case QUARTER: 
                    case WEEK: {
                        ctx.visit(this.datePart.toName()).sql('(').visit(this.field).sql(')');
                        return;
                    }
                }
                break;
            }
            case H2: {
                switch (this.datePart) {
                    case QUARTER: {
                        ctx.visit(this.datePart.toName()).sql('(').visit(this.field).sql(')');
                        return;
                    }
                    case WEEK: {
                        ctx.visit(DSL.keyword("iso_week")).sql('(').visit(this.field).sql(')');
                        return;
                    }
                }
            }
        }
        this.acceptDefaultEmulation(ctx);
    }

    private static final Field<Integer> dowISOToSun1(Field<Integer> dow) {
        return Internal.iadd(dow.mod(DSL.inline(7)), DSL.one());
    }

    private static final Field<Integer> dowSun1ToISO(Field<Integer> dow) {
        return Internal.iadd(Internal.iadd(dow, DSL.inline(5)).mod(DSL.inline(7)), DSL.one());
    }

    private static final Field<Integer> dowSun0ToISO(Field<Integer> dow) {
        return Internal.iadd(Internal.iadd(dow, DSL.inline(6)).mod(DSL.inline(7)), DSL.one());
    }

    private final void acceptDefaultEmulation(Context<?> ctx) {
        switch (this.datePart) {
            case DECADE: {
                ctx.visit(DSL.floor(Internal.idiv(DSL.year(this.field), DSL.inline(10))));
                break;
            }
            case CENTURY: {
                ctx.visit(DSL.floor(Internal.idiv(Internal.imul(DSL.sign(DSL.year(this.field)), Internal.iadd(DSL.abs(DSL.year(this.field)), DSL.inline(99))), DSL.inline(100))));
                break;
            }
            case MILLENNIUM: {
                ctx.visit(DSL.floor(Internal.idiv(Internal.imul(DSL.sign(DSL.year(this.field)), Internal.iadd(DSL.abs(DSL.year(this.field)), DSL.inline(999))), DSL.inline(1000))));
                break;
            }
            case QUARTER: {
                ctx.visit(DSL.floor(Internal.idiv(Internal.iadd(DSL.month(this.field), DSL.inline(2)), DSL.inline(3))));
                break;
            }
            case TIMEZONE: {
                ctx.visit(Internal.iadd(Internal.imul(DSL.extract(this.field, DatePart.TIMEZONE_HOUR), DSL.inline(3600)), Internal.imul(DSL.extract(this.field, DatePart.TIMEZONE_MINUTE), DSL.inline(60))));
                break;
            }
            default: {
                this.acceptNativeFunction(ctx);
            }
        }
    }

    private final void acceptNativeFunction(Context<?> ctx) {
        this.acceptNativeFunction(ctx, this.datePart.toKeyword());
    }

    private final void acceptNativeFunction(Context<?> ctx, Keyword keyword) {
        ctx.visit(Names.N_EXTRACT).sql('(').visit(keyword).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.field).sql(')');
    }

    @Override
    public final Field<?> $field() {
        return this.field;
    }

    @Override
    public final DatePart $datePart() {
        return this.datePart;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.field);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.field, this.datePart, Extract::new, replacer);
    }
}

