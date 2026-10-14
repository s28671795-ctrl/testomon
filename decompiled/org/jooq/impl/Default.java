/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class Default<T>
extends AbstractField<T>
implements QOM.Default<T> {
    Default(DataType<T> type) {
        super(Names.N_DEFAULT, type);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_DEFAULT);
    }
}

