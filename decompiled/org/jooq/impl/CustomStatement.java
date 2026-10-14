/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Consumer;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Pro;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.QOM;

@Pro
public abstract class CustomStatement
extends AbstractStatement
implements QOM.UEmptyStatement,
QOM.UOpaque {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CUSTOM};

    protected CustomStatement() {
    }

    public static final CustomStatement of(final Consumer<? super Context<?>> consumer) {
        return new CustomStatement(){

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

