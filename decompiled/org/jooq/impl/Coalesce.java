/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.ParenthesisedField;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Coalesce<T>
extends AbstractField<T>
implements QOM.Coalesce<T> {
    private final Field<T>[] fields;

    Coalesce(Collection<? extends Field<?>> fields) {
        this(fields.toArray(Tools.EMPTY_FIELD));
    }

    Coalesce(Field<?>[] fields) {
        this(fields, Tools.anyNotNull(SQLDataType.OTHER, fields));
    }

    Coalesce(Field<?>[] fields, DataType<T> type) {
        super(Names.N_COALESCE, type);
        this.fields = fields;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.fields.length == 0) {
            ctx.visit(DSL.inline(null, this.getDataType()));
        } else if (this.fields.length == 1) {
            ctx.visit(this.fields[0]);
        } else {
            switch (ctx.family()) {
                case INFORMIX: {
                    Field<T> coalesce = this.fields[0];
                    for (int i = 1; i < this.fields.length; ++i) {
                        coalesce = DSL.nvl(coalesce, this.fields[i]);
                    }
                    ctx.visit(coalesce);
                    break;
                }
                case DERBY: {
                    ctx.visit(DSL.function(Names.N_COALESCE, this.getDataType(), Tools.map(this.fields, f -> f.getDataType().isBoolean() ? new ParenthesisedField(f) : f, Field[]::new)));
                    break;
                }
                default: {
                    ctx.visit(DSL.function(Names.N_COALESCE, this.getDataType(), this.fields));
                }
            }
        }
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<T>> $arg1() {
        return QOM.unmodifiable((QueryPart[])this.fields);
    }

    @Override
    public final Function1<? super QOM.UnmodifiableList<? extends Field<T>>, ? extends QOM.Coalesce<T>> $constructor() {
        return l -> l.isEmpty() ? new Coalesce(Tools.EMPTY_FIELD, this.getDataType()) : new Coalesce<T>(l.toArray(Tools.EMPTY_FIELD));
    }
}

