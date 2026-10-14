/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

@Pro
final class ConnectByIsCycle
extends AbstractField<Boolean>
implements QOM.ConnectByIsCycle {
    ConnectByIsCycle() {
        super(Names.N_CONNECT_BY_ISCYCLE, Tools.allNotNull(SQLDataType.BOOLEAN));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(Names.N_CONNECT_BY_ISCYCLE);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("connectByIsCycle()");
    }

    @Override
    public final Function0<? extends QOM.ConnectByIsCycle> $constructor() {
        return () -> new ConnectByIsCycle();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ConnectByIsCycle) {
            QOM.ConnectByIsCycle o = (QOM.ConnectByIsCycle)that;
            return true;
        }
        return super.equals(that);
    }
}

