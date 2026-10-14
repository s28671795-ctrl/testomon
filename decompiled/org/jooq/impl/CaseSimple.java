/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Map;
import org.jooq.CaseConditionStep;
import org.jooq.CaseWhenStep;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractCaseSimple;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class CaseSimple<V, T>
extends AbstractCaseSimple<V, T, CaseSimple<V, T>>
implements CaseWhenStep<V, T>,
QOM.CaseSimple<V, T> {
    CaseSimple(Field<V> value, Field<V> compareValue, Field<T> result) {
        super(Names.NQ_CASE, value, compareValue, result);
    }

    CaseSimple(Field<V> value, Map<? extends Field<V>, ? extends Field<T>> map) {
        super(Names.NQ_CASE, value, map);
    }

    CaseSimple(Field<V> value, DataType<T> type) {
        super(Names.NQ_CASE, value, type);
    }

    @Override
    public final Field<T> otherwise(T result) {
        return this.else_(result);
    }

    @Override
    public final Field<T> otherwise(Field<T> result) {
        return this.else_(result);
    }

    @Override
    final void accept0(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ACCESS: {
                this.acceptSwitch(ctx);
                break;
            }
            case DERBY: {
                this.acceptSearched(ctx);
                break;
            }
            default: {
                this.acceptNative(ctx);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("case_(").visit(this.value).sql(')').formatIndentStart();
        for (QOM.Tuple2 e : this.when) {
            ctx.formatSeparator().sql(".when(").visit((Field)e.$1()).sql(", ").visit((Field)e.$2()).sql(')');
        }
        if (this.else_ != null) {
            ctx.formatSeparator().sql(".else_(").visit(this.else_).sql(')');
        }
        ctx.formatIndentEnd();
    }

    @Pro
    private final void acceptSwitch(Context<?> ctx) {
        ctx.visit(Keywords.K_SWITCH).sql('(');
        String separator = "";
        for (QOM.Tuple2 e : this.when) {
            ctx.sql(separator).visit(this.value).sql(" = ").visit((Field)e.$1()).sql(", ").visit((Field)e.$2());
            separator = ", ";
        }
        if (this.else_ != null) {
            ctx.sql(", ").visit(Keywords.K_TRUE).sql(", ").visit(this.else_);
        }
        ctx.sql(')');
    }

    private final void acceptSearched(Context<?> ctx) {
        CaseConditionStep w = null;
        for (QOM.Tuple2 e : this.when) {
            if (w == null) {
                w = DSL.when(this.value.eq((Field)e.$1()), (Field)e.$2());
                continue;
            }
            w = w.when(this.value.eq((Field)e.$1()), (Field)e.$2());
        }
        if (w != null) {
            if (this.else_ != null) {
                ctx.visit(w.else_(this.else_));
            } else {
                ctx.visit(w);
            }
        }
    }

    private final void acceptNative(Context<?> ctx) {
        ctx.visit(Keywords.K_CASE);
        ctx.sql(' ').visit(this.value).formatIndentStart();
        for (QOM.Tuple2 e : this.when) {
            ctx.formatSeparator().visit(Keywords.K_WHEN).sql(' ').visit((Field)e.$1()).sql(' ').visit(Keywords.K_THEN).sql(' ').visit((Field)e.$2());
        }
        if (this.else_ != null) {
            ctx.formatSeparator().visit(Keywords.K_ELSE).sql(' ').visit(this.else_);
        } else if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_FORCE_CASE_ELSE_NULL))) {
            ctx.formatSeparator().visit(Keywords.K_ELSE).sql(' ').visit(Keywords.K_NULL);
        }
        ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END);
    }

    @Override
    final CaseSimple<V, T> construct(Field<V> v, DataType<T> t2) {
        return new CaseSimple<V, T>(v, t2);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.CaseSimple.super.$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.CaseSimple.super.$replace(replacer);
    }
}

