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

final class Least<T>
extends AbstractField<T>
implements QOM.Least<T> {
    private final QueryPartListView<Field<T>> args;

    Least(Field<?> ... args2) {
        this(args2, Tools.nullSafeDataType(args2));
    }

    Least(Field<?>[] args2, DataType<T> type) {
        super(Names.N_LEAST, type);
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
                    GreatestLeast.acceptValuesEmulation(ctx, this.getDataType(), this.args, DSL::min);
                } else {
                    ctx.visit(DSL.function(Names.N_LEAST, this.getDataType(), this.args.toArray((E[])Tools.EMPTY_FIELD)));
                }
                return;
            }
            case ACCESS: 
            case ASE: 
            case INFORMIX: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case DERBY: {
                GreatestLeast.acceptCaseEmulation(ctx, this.args, DSL::least, Field::lt);
                return;
            }
            case FIREBIRD: {
                ctx.visit(DSL.function(Names.N_MINVALUE, this.getDataType(), this.args.toArray((E[])Tools.EMPTY_FIELD)));
                return;
            }
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_MIN, this.getDataType(), this.args.toArray((E[])Tools.EMPTY_FIELD)));
                return;
            }
        }
        ctx.visit(DSL.function(Names.N_LEAST, this.getDataType(), this.args.toArray((E[])Tools.EMPTY_FIELD)));
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<T>> $arg1() {
        return QOM.unmodifiable(this.args);
    }

    @Override
    public final Function1<? super QOM.UnmodifiableList<? extends Field<T>>, ? extends QOM.Least<T>> $constructor() {
        return a -> a.isEmpty() ? new Least(Tools.EMPTY_FIELD, this.getDataType()) : new Least<T>(a.toArray(Tools.EMPTY_FIELD));
    }
}

