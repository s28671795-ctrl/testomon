/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

@Pro
final class Inserting
extends AbstractCondition
implements QOM.Inserting {
    Inserting() {
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(Names.N_INSERTING);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("inserting()");
    }

    @Override
    public final Function0<? extends QOM.Inserting> $constructor() {
        return () -> new Inserting();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Inserting) {
            QOM.Inserting o = (QOM.Inserting)that;
            return true;
        }
        return super.equals(that);
    }
}

