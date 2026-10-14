/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Period;
import org.jooq.PeriodSpecification;
import org.jooq.PeriodSpecificationBetweenAndStep;
import org.jooq.PeriodSpecificationFromToStep;
import org.jooq.Pro;
import org.jooq.impl.AbstractTypedNamed;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.PeriodRange;
import org.jooq.impl.PeriodSpecificationImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

@Pro
final class PeriodImpl<T>
extends AbstractTypedNamed<T>
implements Period<T>,
QOM.UNotYetImplemented {
    PeriodImpl(Name name, DataType<T> type) {
        super(name, CommentImpl.NO_COMMENT, type);
    }

    @Override
    public final Field<T> getStart() {
        return null;
    }

    @Override
    public final Field<T> getEnd() {
        return null;
    }

    @Override
    public final PeriodSpecification asOf(T value) {
        return this.asOf(Tools.field(value, this.getDataType()));
    }

    @Override
    public final PeriodSpecification asOf(Field<T> field2) {
        return new PeriodSpecificationImpl<T>(this, field2);
    }

    @Override
    public final PeriodSpecification all() {
        return new PeriodSpecificationImpl(this);
    }

    @Override
    public final PeriodSpecificationBetweenAndStep<T> between(T start2) {
        return this.between(Tools.field(start2, this.getDataType()));
    }

    @Override
    public final PeriodSpecificationBetweenAndStep<T> between(Field<T> start2) {
        return new PeriodSpecificationImpl<T>(this, PeriodRange.BETWEEN, start2, null);
    }

    @Override
    public final PeriodSpecification between(T start2, T end) {
        return this.between(Tools.field(start2, this.getDataType()), Tools.field(end, this.getDataType()));
    }

    @Override
    public final PeriodSpecification between(Field<T> start2, Field<T> end) {
        return new PeriodSpecificationImpl<T>(this, PeriodRange.BETWEEN, start2, end);
    }

    @Override
    public final PeriodSpecificationFromToStep<T> from(T start2) {
        return this.from(Tools.field(start2, this.getDataType()));
    }

    @Override
    public final PeriodSpecificationFromToStep<T> from(Field<T> start2) {
        return new PeriodSpecificationImpl<T>(this, PeriodRange.FROM, start2, null);
    }

    @Override
    public final PeriodSpecification containedIn(T start2, T end) {
        return this.containedIn(Tools.field(start2, this.getDataType()), Tools.field(end, this.getDataType()));
    }

    @Override
    public final PeriodSpecification containedIn(Field<T> start2, Field<T> end) {
        return new PeriodSpecificationImpl<T>(this, PeriodRange.CONTAINED, start2, end);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.equals(DSL.systemTime())) {
                    ctx.sql("systemTime()");
                    break;
                }
                ctx.sql("period(").visit(this.getUnqualifiedName()).sql(")");
                break;
            }
            case COCKROACHDB: {
                if (this.equals(DSL.systemTime())) {
                    ctx.visit(Keywords.K_SYSTEM).sql(' ').visit(Keywords.K_TIME);
                    break;
                }
                ctx.visit(this.getUnqualifiedName());
                break;
            }
            default: {
                ctx.visit(this.getUnqualifiedName());
            }
        }
    }
}

