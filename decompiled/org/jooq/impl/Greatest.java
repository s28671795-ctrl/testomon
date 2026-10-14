/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.GreatestLeast;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

final class Greatest<T>
extends AbstractField<T>
implements QOM.Greatest<T> {
    private final QueryPartListView<Field<T>> args;

    Greatest(Field<?> ... args2) {
        this(args2, Tools.nullSafeDataType(args2));
    }

    Greatest(Field<?>[] args2, DataType<T> type) {
        super(Names.N_GREATEST, type);
        this.args = QueryPartListView.wrap((QueryPart[])args2);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.args.isEmpty()) {
            ctx.visit(DSL.inline(null, this.getDataType()));
            return;
        }
        if (this.args.size() == 1) {
            ctx.visit((Field)this.args.get(0));
            return;
        }
        switch (ctx.family()) {
            case SQLSERVER: {
                if (ctx.dialect().precedesStrictly(SQLDialect.SQLSERVER2022)) {
                    GreatestLeast.acceptValuesEmulation(ctx, this.getDataType(), this.args, DSL::max);
                } else {
                    ctx.visit(DSL.function(Names.N_GREATEST, this.getDataType(), this.args.toArray((E[])Tools.EMPTY_FIELD)));
                }
                return;
            }
            case ACCESS: 
            case ASE: 
            case INFORMIX: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case DERBY: {
                GreatestLeast.acceptCaseEmulation(ctx, this.args, DSL::greatest, Field::gt);
                return;
            }
            case FIREBIRD: {
                ctx.visit(DSL.function(Names.N_MAXVALUE, this.getDataType(), this.args.toArray((E[])Tools.EMPTY_FIELD)));
                return;
            }
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_MAX, this.getDataType(), this.args.toArray((E[])Tools.EMPTY_FIELD)));
                return;
            }
        }
        ctx.visit(DSL.function(Names.N_GREATEST, this.getDataType(), this.args.toArray((E[])Tools.EMPTY_FIELD)));
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<T>> $arg1() {
        return QOM.unmodifiable(this.args);
    }

    @Override
    public final Function1<? super QOM.UnmodifiableList<? extends Field<T>>, ? extends QOM.Greatest<T>> $constructor() {
        return a -> a.isEmpty() ? new Greatest(Tools.EMPTY_FIELD, this.getDataType()) : new Greatest<T>(a.toArray(Tools.EMPTY_FIELD));
    }
}

