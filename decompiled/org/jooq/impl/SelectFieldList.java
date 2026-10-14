/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.AsteriskImpl;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class SelectFieldList<F extends SelectFieldOrAsterisk>
extends QueryPartList<F> {
    SelectFieldList() {
    }

    SelectFieldList(Iterable<? extends F> wrappedList) {
        super(wrappedList);
    }

    SelectFieldList(F[] wrappedList) {
        super(wrappedList);
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return true;
    }

    @Override
    protected final void toSQLEmptyList(Context<?> ctx) {
        ctx.visit(AsteriskImpl.INSTANCE.get());
    }

    @Override
    public final boolean declaresFields() {
        return true;
    }

    @Override
    protected void acceptElement(Context<?> ctx, F part) {
        if (part instanceof AbstractTable) {
            AbstractTable t2 = (AbstractTable)part;
            this.acceptElement0(ctx, t2.tf());
        } else if (part instanceof AbstractRow) {
            AbstractRow r = (AbstractRow)part;
            this.acceptElement0(ctx, r.rf());
        } else {
            this.acceptElement0(ctx, part);
        }
    }

    private void acceptElement0(Context<?> ctx, F part) {
        Tools.visitAutoAliased(ctx, part, Context::declareFields, (c, t2) -> super.acceptElement((Context<?>)c, t2));
    }
}

