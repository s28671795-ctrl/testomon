/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Cast;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

final class Array<T>
extends AbstractField<T[]>
implements QOM.Array<T> {
    private static final Set<SQLDialect> REQUIRES_CAST = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    final FieldsImpl<Record> fields;

    Array(Collection<? extends Field<T>> fields) {
        super(Names.N_ARRAY, Array.type(fields));
        this.fields = new FieldsImpl(fields);
    }

    private static <T> DataType<T[]> type(Collection<? extends Field<T>> fields) {
        if (fields == null || fields.isEmpty()) {
            return SQLDataType.OTHER.getArrayDataType();
        }
        return fields.iterator().next().getDataType().getArrayDataType();
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("array(").visit(this.fields).sql(')');
                break;
            }
            default: {
                Cast.renderCastIf(ctx, c -> {
                    switch (ctx.family()) {
                        case HANA: {
                            ctx.visit(Keywords.K_ARRAY).sql('(').visit(this.fields).sql(')');
                            break;
                        }
                        default: {
                            ctx.visit(Keywords.K_ARRAY).sql('[').visit(this.fields).sql(']');
                        }
                    }
                }, c -> c.visit(Keywords.K_INT).sql("[]"), () -> this.fields.fields.length == 0 && REQUIRES_CAST.contains((Object)ctx.dialect()));
            }
        }
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<T>> $elements() {
        return QOM.unmodifiable((QueryPart[])this.fields.fields);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$elements());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.fields, f -> new Array(Arrays.asList(f.fields)), replacer);
    }
}

