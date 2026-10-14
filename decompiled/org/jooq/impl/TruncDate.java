/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import org.jooq.Context;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class TruncDate<T>
extends AbstractField<T>
implements QOM.UNotYetImplemented {
    private final Field<T> date;
    private final DatePart part;

    TruncDate(Field<T> date, DatePart part) {
        super(Names.N_TRUNC, date.getDataType());
        this.date = date;
        this.part = part;
    }

    @Override
    public final void accept(Context<?> ctx) {
        String keyword = null;
        String format = null;
        block0 : switch (ctx.family()) {
            case CUBRID: 
            case HSQLDB: {
                switch (this.part) {
                    case YEAR: {
                        keyword = "YY";
                        break;
                    }
                    case MONTH: {
                        keyword = "MM";
                        break;
                    }
                    case DAY: {
                        keyword = "DD";
                        break;
                    }
                    case HOUR: {
                        keyword = "HH";
                        break;
                    }
                    case MINUTE: {
                        keyword = "MI";
                        break;
                    }
                    case SECOND: {
                        keyword = "SS";
                        break;
                    }
                    default: {
                        this.throwUnsupported();
                    }
                }
                ctx.visit(Names.N_TRUNC).sql('(').visit(this.date).sql(", ").visit(DSL.inline(keyword)).sql(')');
                break;
            }
            case H2: {
                switch (this.part) {
                    case YEAR: {
                        format = "yyyy";
                        break;
                    }
                    case MONTH: {
                        format = "yyyy-MM";
                        break;
                    }
                    case DAY: {
                        format = "yyyy-MM-dd";
                        break;
                    }
                    case HOUR: {
                        format = "yyyy-MM-dd HH";
                        break;
                    }
                    case MINUTE: {
                        format = "yyyy-MM-dd HH:mm";
                        break;
                    }
                    case SECOND: {
                        format = "yyyy-MM-dd HH:mm:ss";
                        break;
                    }
                    default: {
                        this.throwUnsupported();
                    }
                }
                ctx.visit(DSL.keyword("parsedatetime")).sql('(').visit(DSL.keyword("formatdatetime")).sql('(').visit(this.date).sql(", ").visit(DSL.inline(format)).sql("), ").visit(DSL.inline(format)).sql(')');
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case REDSHIFT: 
            case VERTICA: 
            case POSTGRES: 
            case YUGABYTEDB: {
                switch (this.part) {
                    case YEAR: {
                        keyword = "year";
                        break;
                    }
                    case MONTH: {
                        keyword = "month";
                        break;
                    }
                    case DAY: {
                        keyword = "day";
                        break;
                    }
                    case HOUR: {
                        keyword = "hour";
                        break;
                    }
                    case MINUTE: {
                        keyword = "minute";
                        break;
                    }
                    case SECOND: {
                        keyword = "second";
                        break;
                    }
                    default: {
                        this.throwUnsupported();
                    }
                }
                ctx.visit(Names.N_DATE_TRUNC).sql('(').visit(DSL.inline(keyword)).sql(", ").visit(this.date).sql(')');
                break;
            }
            case BIGQUERY: {
                if (this.getDataType().isDate()) {
                    ctx.visit(DSL.function(Names.N_DATE_TRUNC, this.getDataType(), this.date, DSL.field("{0}", this.part.toKeyword())));
                    break;
                }
                ctx.visit(DSL.function(Names.N_DATETIME_TRUNC, this.getDataType(), this.date, DSL.field("{0}", this.part.toKeyword())));
                break;
            }
            case JAVA: {
                ctx.sql("trunc(").visit(this.date).sql(", DatePart.").sql(this.part.name()).sql(')');
                break;
            }
            case INFORMIX: {
                switch (this.part) {
                    case YEAR: {
                        keyword = "YEAR";
                        break;
                    }
                    case MONTH: {
                        keyword = "MONTH";
                        break;
                    }
                    case DAY: {
                        keyword = "DD";
                        break;
                    }
                    case HOUR: {
                        keyword = "HH";
                        break;
                    }
                    case MINUTE: {
                        keyword = "MI";
                        break;
                    }
                    case SECOND: {
                        ctx.visit(Keywords.K_CAST).sql('(').visit(this.date).sql(' ').visit(DSL.keyword("as datetime year to second")).sql(')');
                        return;
                    }
                    default: {
                        this.throwUnsupported();
                    }
                }
                ctx.visit(Names.N_TRUNC).sql('(').visit(this.date).sql(", ").visit(DSL.inline(keyword)).sql(')');
                break;
            }
            case DB2: {
                switch (this.part) {
                    case YEAR: {
                        keyword = "YYYY";
                        break;
                    }
                    case MONTH: {
                        keyword = "MM";
                        break;
                    }
                    case DAY: {
                        keyword = "DD";
                        break;
                    }
                    case HOUR: {
                        keyword = "HH";
                        break;
                    }
                    case MINUTE: {
                        keyword = "MI";
                        break;
                    }
                    case SECOND: {
                        keyword = "SS";
                        break;
                    }
                    default: {
                        this.throwUnsupported();
                    }
                }
                ctx.visit(Names.N_TRUNC).sql('(').visit(this.date).sql(", ").visit(DSL.inline(keyword)).sql(')');
                break;
            }
            case HANA: {
                switch (this.part) {
                    case DAY: {
                        ctx.visit(Tools.castIfNeeded(Tools.castIfNeeded(this.date, Date.class), this.getDataType()));
                        break block0;
                    }
                }
                ctx.visit(Names.N_TRUNC).sql('(').visit(this.date).sql(", ").visit(DSL.inline(keyword)).sql(')');
                break;
            }
            case ORACLE: {
                switch (this.part) {
                    case YEAR: {
                        keyword = "YYYY";
                        break;
                    }
                    case MONTH: {
                        keyword = "MM";
                        break;
                    }
                    case DAY: {
                        keyword = "DD";
                        break;
                    }
                    case HOUR: {
                        keyword = "HH";
                        break;
                    }
                    case MINUTE: {
                        keyword = "MI";
                        break;
                    }
                    case SECOND: {
                        ctx.visit(Tools.castIfNeeded(this.date, Date.class));
                        return;
                    }
                    default: {
                        this.throwUnsupported();
                    }
                }
                ctx.visit(Names.N_TRUNC).sql('(').visit(this.date).sql(", ").visit(DSL.inline(keyword)).sql(')');
                break;
            }
            default: {
                ctx.visit(Names.N_TRUNC).sql('(').visit(this.date).sql(", ").visit(DSL.inline(keyword)).sql(')');
            }
        }
    }

    private final void throwUnsupported() {
        throw new UnsupportedOperationException("Unknown date part : " + String.valueOf((Object)this.part));
    }
}

