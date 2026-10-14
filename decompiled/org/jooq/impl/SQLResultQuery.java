/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPartInternal;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.impl.AbstractResultQuery;
import org.jooq.impl.MetaDataFieldProvider;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.Tools;

final class SQLResultQuery
extends AbstractResultQuery<Record>
implements QOM.UEmptyQuery {
    private final SQL delegate;

    SQLResultQuery(Configuration configuration, SQL delegate) {
        super(configuration);
        this.delegate = delegate;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("resultQuery(").visit(this.delegate).sql(')');
                break;
            }
            default: {
                ctx.visit(this.delegate);
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        SQL sQL = this.delegate;
        if (sQL instanceof QueryPartInternal) {
            QueryPartInternal q = (QueryPartInternal)((Object)sQL);
            return q.clauses(ctx);
        }
        return null;
    }

    @Override
    public final Class<? extends Record> getRecordType0() {
        return RecordImplN.class;
    }

    @Override
    public final Field<?>[] getFields(ThrowingSupplier<? extends ResultSetMetaData, SQLException> rs) throws SQLException {
        Object[] result = this.getFields();
        if (!Tools.isEmpty(result)) {
            return result;
        }
        return new MetaDataFieldProvider(this.configuration(), rs.get()).getFields();
    }

    @Override
    public final Field<?>[] getFields() {
        Collection<Field<?>> coerce = this.coerce();
        if (!Tools.isEmpty(coerce)) {
            return coerce.toArray(Tools.EMPTY_FIELD);
        }
        return Tools.EMPTY_FIELD;
    }
}

