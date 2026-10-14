/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Function1;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.TableLike;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.Tools;

final class ScalarSubquery<T>
extends AbstractField<T>
implements QOM.ScalarSubquery<T> {
    static final Set<SQLDialect> NO_SUPPORT_WITH_IN_SCALAR_SUBQUERY = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.HSQLDB);
    final Select<?> query;
    final boolean predicandSubquery;

    ScalarSubquery(Select<?> query, DataType<T> type, boolean predicandSubquery) {
        super(Names.NQ_SELECT, type);
        this.query = query;
        this.predicandSubquery = predicandSubquery;
    }

    @Override
    public final void accept(Context<?> ctx) {
        SelectQueryImpl q = Tools.selectQueryImpl(this.query);
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("field(").visitSubquery(q).sql(')');
        } else if (q != null && q.with != null && NO_SUPPORT_WITH_IN_SCALAR_SUBQUERY.contains((Object)ctx.dialect())) {
            Tools.visitSubquery(ctx, DSL.select(DSL.asterisk()).from((TableLike<?>)this.query.asTable("t")), this.predicandSubquery ? 256 : 0);
        } else {
            Tools.visitSubquery(ctx, this.query, this.predicandSubquery ? 256 : 0);
        }
    }

    @Override
    public final Function1<? super Select<? extends Record1<T>>, ? extends QOM.ScalarSubquery<T>> $constructor() {
        return s2 -> new ScalarSubquery((Select<?>)s2, Tools.scalarType(s2), this.predicandSubquery);
    }

    @Override
    public final Select<? extends Record1<T>> $arg1() {
        return this.query;
    }
}

