/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.BitAnd;
import org.jooq.impl.DSL;
import org.jooq.impl.Expression;
import org.jooq.impl.ExpressionOperator;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Concat
extends AbstractField<String>
implements QOM.Concat {
    private final Field<?>[] arguments;

    Concat(Field<?> ... arguments) {
        super(Names.N_CONCAT, SQLDataType.VARCHAR);
        this.arguments = arguments;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.arguments.length == 0) {
            ctx.visit(DSL.inline(null, this.getDataType()));
            return;
        }
        if (this.arguments.length == 1) {
            ctx.visit(this.arguments[0]);
            return;
        }
        Field<String>[] cast = Tools.castAllIfNeeded(this.arguments, String.class);
        if (Boolean.TRUE.equals(ctx.settings().isRenderCoalesceToEmptyStringInConcat()) && ctx.configuration().commercial(() -> "Auto-coalescing of CONCAT arguments is available in the jOOQ 3.15 Professional Edition and jOOQ Enterprise Edition, see https://github.com/jOOQ/jOOQ/issues/11757")) {
            for (int i = 0; i < cast.length; ++i) {
                if (!Tools.isPossiblyNullable(cast[i])) continue;
                cast[i] = DSL.coalesce(cast[i], new Field[]{DSL.inline("")});
            }
        }
        ExpressionOperator op = ExpressionOperator.CONCAT;
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DSL.function(DSL.systemName("concat"), SQLDataType.VARCHAR, cast));
                return;
            }
            case ACCESS: {
                ctx.visit(new BitAnd<String>(cast[0], cast[1]));
                return;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                op = ExpressionOperator.ADD;
            }
        }
        Expression<String> expression = new Expression<String>(op, false, cast[0], cast[1]);
        for (int i = 2; i < cast.length; ++i) {
            expression = new Expression<String>(op, false, expression, cast[i]);
        }
        ctx.visit(expression);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $arg1() {
        return QOM.unmodifiable((QueryPart[])this.arguments);
    }

    @Override
    public final Function1<? super QOM.UnmodifiableList<? extends Field<?>>, ? extends QOM.Concat> $constructor() {
        return l -> new Concat(l.toArray(Tools.EMPTY_FIELD));
    }
}

