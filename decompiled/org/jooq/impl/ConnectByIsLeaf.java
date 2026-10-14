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
final class ConnectByIsLeaf
extends AbstractField<Boolean>
implements QOM.ConnectByIsLeaf {
    ConnectByIsLeaf() {
        super(Names.N_CONNECT_BY_ISLEAF, Tools.allNotNull(SQLDataType.BOOLEAN));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(Names.N_CONNECT_BY_ISLEAF);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("connectByIsLeaf()");
    }

    @Override
    public final Function0<? extends QOM.ConnectByIsLeaf> $constructor() {
        return () -> new ConnectByIsLeaf();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ConnectByIsLeaf) {
            QOM.ConnectByIsLeaf o = (QOM.ConnectByIsLeaf)that;
            return true;
        }
        return super.equals(that);
    }
}

