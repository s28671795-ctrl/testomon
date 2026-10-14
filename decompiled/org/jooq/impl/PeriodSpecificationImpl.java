/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Period;
import org.jooq.PeriodSpecification;
import org.jooq.PeriodSpecificationBetweenAndStep;
import org.jooq.PeriodSpecificationFromToStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.PeriodRange;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

@Pro
final class PeriodSpecificationImpl<T>
extends AbstractQueryPart
implements PeriodSpecification,
PeriodSpecificationBetweenAndStep<T>,
PeriodSpecificationFromToStep<T>,
QOM.UNotYetImplemented {
    private static final Set<SQLDialect> EMULATE_ALL = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.HSQLDB);
    private final Period<T> period;
    private final Field<T> asOf;
    private final PeriodRange range;
    private final Field<T> from;
    private final Field<T> to;

    PeriodSpecificationImpl(Period<T> period) {
        this(period, null);
    }

    PeriodSpecificationImpl(Period<T> period, Field<T> asOfField) {
        this.period = period;
        this.asOf = asOfField;
        this.range = null;
        this.from = null;
        this.to = null;
    }

    PeriodSpecificationImpl(Period<T> period, PeriodRange range, Field<T> from, Field<T> to) {
        this.period = period;
        this.asOf = null;
        this.range = range;
        this.from = from;
        this.to = to;
    }

    @Override
    public final PeriodSpecification to(T value) {
        return this.to(Tools.field(value, this.period.getDataType()));
    }

    @Override
    public final PeriodSpecification to(Field<T> field2) {
        return new PeriodSpecificationImpl<T>(this.period, this.range, this.from, field2);
    }

    @Override
    public final PeriodSpecification and(T value) {
        return this.and(Tools.field(value, this.period.getDataType()));
    }

    @Override
    public final PeriodSpecification and(Field<T> field2) {
        return new PeriodSpecificationImpl<T>(this.period, this.range, this.from, field2);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case COCKROACHDB: {
                ctx.paramType(ParamType.INLINED, c -> c.visit(Keywords.K_AS_OF).sql(' ').visit(this.period).sql(' ').visit(this.asOf));
                break;
            }
            case ORACLE: {
                if (this.asOf != null) {
                    ctx.visit(Keywords.K_AS_OF);
                } else {
                    ctx.visit(Keywords.K_VERSIONS);
                }
                ctx.sql(' ').visit(Keywords.K_PERIOD).sql(' ').visit(Keywords.K_FOR).sql(' ').visit(this.period).sql(' ');
                if (this.asOf != null) {
                    ctx.visit(this.asOf);
                    break;
                }
                if (this.range == PeriodRange.BETWEEN) {
                    ctx.visit(Keywords.K_BETWEEN).sql(' ').visit(this.from).sql(' ').visit(Keywords.K_AND).sql(' ').visit(this.to);
                    break;
                }
                ctx.visit(Keywords.K_BETWEEN).sql(' ').visit(Keywords.K_MINVALUE).sql(' ').visit(Keywords.K_AND).sql(' ').visit(Keywords.K_MAXVALUE);
                break;
            }
            case JAVA: {
                ctx.visit(this.period);
                if (this.asOf != null) {
                    ctx.sql(".asOf(").visit(this.asOf).sql(')');
                    break;
                }
                if (this.range == PeriodRange.BETWEEN) {
                    ctx.sql(".between(").visit(this.from).sql(").and(").visit(this.to).sql(')');
                    break;
                }
                if (this.range == PeriodRange.FROM) {
                    ctx.sql(".from(").visit(this.from).sql(").to(").visit(this.to).sql(')');
                    break;
                }
                if (this.range == PeriodRange.CONTAINED) {
                    ctx.sql(".containedIn(").visit(this.from).sql(", ").visit(this.to).sql(')');
                    break;
                }
                ctx.sql(".all()");
                break;
            }
            default: {
                ctx.visit(this.period).sql(' ');
                if (this.asOf != null) {
                    ctx.visit(Keywords.K_AS_OF).sql(' ').visit(this.asOf);
                    break;
                }
                if (this.range == PeriodRange.BETWEEN && ctx.family() == SQLDialect.HSQLDB) {
                    ctx.visit(Keywords.K_BETWEEN).sql(' ').visit(this.from).sql(" + INTERVAL 0 DAY ").visit(Keywords.K_AND).sql(' ').visit(this.to);
                    break;
                }
                if (this.range == PeriodRange.BETWEEN) {
                    ctx.visit(Keywords.K_BETWEEN).sql(' ').visit(this.from).sql(' ').visit(Keywords.K_AND).sql(' ').visit(this.to);
                    break;
                }
                if (this.range == PeriodRange.FROM) {
                    ctx.visit(Keywords.K_FROM).sql(' ').visit(this.from).sql(' ').visit(Keywords.K_TO).sql(' ').visit(this.to);
                    break;
                }
                if (this.range == PeriodRange.CONTAINED) {
                    ctx.visit(Keywords.K_CONTAINED).sql(' ').visit(Keywords.K_IN).sql(" (").visit(this.from).sql(", ").visit(this.to).sql(')');
                    break;
                }
                if (EMULATE_ALL.contains((Object)ctx.dialect())) {
                    ctx.visit(Keywords.K_FROM).sql(' ').visit(DSL.inline(Date.valueOf("0001-01-01"))).sql(' ').visit(Keywords.K_TO).sql(' ').visit(DSL.inline(Date.valueOf("9999-12-31")));
                    break;
                }
                ctx.visit(Keywords.K_ALL);
            }
        }
    }
}

