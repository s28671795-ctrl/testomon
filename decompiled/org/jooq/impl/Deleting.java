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
final class Deleting
extends AbstractCondition
implements QOM.Deleting {
    Deleting() {
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
                ctx.visit(Names.N_DELETING);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("deleting()");
    }

    @Override
    public final Function0<? extends QOM.Deleting> $constructor() {
        return () -> new Deleting();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Deleting) {
            QOM.Deleting o = (QOM.Deleting)that;
            return true;
        }
        return super.equals(that);
    }
}

