/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Consumer;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;

public abstract class CustomQueryPart
extends AbstractQueryPart
implements QOM.UOpaque {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CUSTOM};

    protected CustomQueryPart() {
    }

    public static final CustomQueryPart of(final Consumer<? super Context<?>> consumer) {
        return new CustomQueryPart(){

            @Override
            public void accept(Context<?> ctx) {
                consumer.accept(ctx);
            }
        };
    }

    @Override
    public abstract void accept(Context<?> var1);

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final boolean declaresFields() {
        return super.declaresFields();
    }

    @Override
    public final boolean declaresTables() {
        return super.declaresTables();
    }
}

