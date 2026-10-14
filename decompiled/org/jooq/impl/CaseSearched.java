/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import org.jooq.CaseConditionStep;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Exists;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.Not;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class CaseSearched<T>
extends AbstractField<T>
implements CaseConditionStep<T>,
QOM.CaseSearched<T> {
    private final List<QOM.Tuple2<Condition, Field<T>>> when = new QueryPartList<QOM.Tuple2<Condition, Field<T>>>();
    private Field<T> else_;

    CaseSearched(DataType<T> type) {
        super(Names.NQ_CASE, type);
    }

    CaseSearched(Condition condition, Field<T> result) {
        this(result.getDataType());
        this.when(condition, result);
    }

    @Override
    public final CaseConditionStep<T> when(Condition condition, T result) {
        return this.when(condition, Tools.field(result));
    }

    @Override
    public final CaseConditionStep<T> when(Condition condition, Field<T> result) {
        this.when.add(QOM.tuple(condition, result));
        return this;
    }

    @Override
    public final CaseConditionStep<T> when(Condition condition, Select<? extends Record1<T>> result) {
        return this.when(condition, DSL.field(result));
    }

    @Override
    public final CaseConditionStep<T> when(Field<Boolean> condition, T result) {
        return this.when(DSL.condition(condition), result);
    }

    @Override
    public final CaseConditionStep<T> when(Field<Boolean> condition, Field<T> result) {
        return this.when(DSL.condition(condition), result);
    }

    @Override
    public final CaseConditionStep<T> when(Field<Boolean> condition, Select<? extends Record1<T>> result) {
        return this.when(DSL.condition(condition), result);
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
    public final Field<T> otherwise(Select<? extends Record1<T>> result) {
        return this.else_(result);
    }

    @Override
    public final Field<T> else_(T result) {
        return this.else_(Tools.field(result));
    }

    @Override
    public final Field<T> else_(Field<T> result) {
        this.else_ = result;
        return this;
    }

    @Override
    public final Field<T> else_(Select<? extends Record1<T>> result) {
        return this.else_(DSL.field(result));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.when.isEmpty()) {
            if (this.else_ != null) {
                ctx.visit(this.else_);
            } else {
                ctx.visit(DSL.inline(null, this.getDataType()));
            }
        } else {
            switch (ctx.family()) {
                case JAVA: {
                    this.acceptJava(ctx);
                    break;
                }
                case ACCESS: {
                    this.acceptSwitch(ctx);
                    break;
                }
                default: {
                    this.acceptNative(ctx);
                }
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("case_()").formatIndentStart();
        for (QOM.Tuple2<Condition, Field<T>> e : this.when) {
            ctx.formatSeparator().sql(".when(").visit(e.$1()).sql(", ").visit(e.$2()).sql(')');
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
        for (QOM.Tuple2<Condition, Field<T>> e : this.when) {
            ctx.sql(separator).visit(e.$1()).sql(", ").visit(e.$2());
            separator = ", ";
        }
        if (this.else_ != null) {
            ctx.sql(", ").visit(Keywords.K_TRUE).sql(", ").visit(this.else_);
        }
        ctx.sql(')');
    }

    private final void acceptNative(Context<?> ctx) {
        ctx.visit(Keywords.K_CASE).formatIndentStart();
        for (QOM.Tuple2<Condition, Field<T>> e : this.when) {
            Condition c = e.$1();
            if (ctx.family() == SQLDialect.TERADATA) {
                c = (Condition)c.$replace(Replacer.of(p -> !(p instanceof Select), p -> {
                    QueryPart queryPart;
                    if (p instanceof Not && ((Not)p).condition instanceof Not) {
                        queryPart = ((Not)((Not)p).condition).condition;
                    } else if (p instanceof Not && ((Not)p).condition instanceof Exists) {
                        queryPart = DSL.select(DSL.one()).where((Condition)((Not)p)).isNotNull();
                    } else if (p instanceof Exists) {
                        Exists x = (Exists)p;
                        queryPart = DSL.select(DSL.one()).where((Condition)x).isNotNull();
                    } else {
                        queryPart = p;
                    }
                    return queryPart;
                }));
            }
            ctx.formatSeparator().visit(Keywords.K_WHEN).sql(' ').visit(c).sql(' ').visit(Keywords.K_THEN).sql(' ').visit(e.$2());
        }
        if (this.else_ != null) {
            ctx.formatSeparator().visit(Keywords.K_ELSE).sql(' ').visit(this.else_);
        } else if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_FORCE_CASE_ELSE_NULL))) {
            ctx.formatSeparator().visit(Keywords.K_ELSE).sql(' ').visit(Keywords.K_NULL);
        }
        ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END);
    }

    @Override
    public final Function2<? super QOM.UnmodifiableList<? extends QOM.Tuple2<Condition, Field<T>>>, ? super Field<T>, ? extends CaseSearched<T>> $constructor() {
        return (w, e) -> {
            CaseSearched r = new CaseSearched(this.getDataType());
            w.forEach(t2 -> r.when((Condition)t2.$1(), (Field)t2.$2()));
            r.else_((Field<T>)e);
            return r;
        };
    }

    @Override
    public final QOM.UnmodifiableList<? extends QOM.Tuple2<Condition, Field<T>>> $arg1() {
        return QOM.unmodifiable(this.when);
    }

    @Override
    public final CaseSearched<T> $arg1(QOM.UnmodifiableList<? extends QOM.Tuple2<Condition, Field<T>>> w) {
        return this.$constructor().apply(w, this.$else());
    }

    @Override
    public final Field<T> $arg2() {
        return this.else_;
    }

    @Override
    public final CaseSearched<T> $arg2(Field<T> e) {
        return this.$constructor().apply(this.$when(), e);
    }
}

