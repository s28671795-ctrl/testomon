/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Replacer;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class ArrayQuery<T>
extends AbstractField<T[]>
implements QOM.ArrayQuery<T> {
    private final Select<? extends Record1<T>> query;

    ArrayQuery(Select<? extends Record1<T>> query) {
        super(Names.N_ARRAY, query.getSelect().get(0).getDataType().getArrayDataType());
        this.query = query;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("array(").visitSubquery(this.query).sql(')');
                break;
            }
            case H2: {
                Table t2 = this.query.asTable("t", "c");
                Field<?> c = t2.field("c");
                Tools.visitSubquery(ctx, DSL.select(DSL.arrayAgg(c)).from((TableLike<?>)t2));
                break;
            }
            default: {
                ctx.visit(Keywords.K_ARRAY).visitSubquery(this.query);
            }
        }
    }

    @Override
    public final Select<? extends Record1<T>> $query() {
        return this.query;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.query);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.query, ArrayQuery::new, replacer);
    }
}

