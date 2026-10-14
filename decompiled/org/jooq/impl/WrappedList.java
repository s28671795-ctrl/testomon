/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;

final class WrappedList
extends AbstractQueryPart
implements QOM.UTransient {
    private final QueryPartList<?> wrapped;

    WrappedList(QueryPartList<?> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.sql('(').visit(this.wrapped).sql(')');
    }
}

