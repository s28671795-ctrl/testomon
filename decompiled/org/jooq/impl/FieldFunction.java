/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class FieldFunction<T>
extends AbstractField<Integer>
implements QOM.FieldFunction<T> {
    private final Field<T> field;
    private final QueryPartListView<Field<T>> arguments;

    FieldFunction(Field<T> field2, Field<T>[] arguments) {
        super(Names.N_FIELD, SQLDataType.INTEGER);
        this.field = field2;
        this.arguments = QueryPartListView.wrap((QueryPart[])arguments);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                if (this.arguments.size() > 1) {
                    ctx.visit(Names.N_FIELD).sql('(').visit(QueryPartListView.wrap((QueryPart[])Tools.combine(this.field, this.arguments.toArray((E[])Tools.EMPTY_FIELD)))).sql(')');
                    break;
                }
                this.acceptDefault(ctx);
                break;
            }
            default: {
                this.acceptDefault(ctx);
            }
        }
    }

    private final void acceptDefault(Context<?> ctx) {
        int size = this.arguments.size();
        if (size == 0) {
            ctx.visit(DSL.zero());
        } else {
            ArrayList<Field> args2 = new ArrayList<Field>();
            args2.add(this.field);
            for (int i = 0; i < size; ++i) {
                args2.add((Field)this.arguments.get(i));
                args2.add(DSL.inline(i + 1));
            }
            args2.add(DSL.inline(0));
            ctx.visit(DSL.decode((Field)args2.get(0), (Field)args2.get(1), (Field)args2.get(2), (Object[])args2.subList(3, args2.size()).toArray(Tools.EMPTY_FIELD)));
        }
    }

    @Override
    public final Field<T> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<T>> $arg2() {
        return QOM.unmodifiable(this.arguments);
    }

    @Override
    public final Function2<? super Field<T>, ? super QOM.UnmodifiableList<? extends Field<T>>, ? extends QOM.FieldFunction<T>> $constructor() {
        return (f, a) -> new FieldFunction((Field<?>)f, a.toArray(Tools.EMPTY_FIELD));
    }
}

