/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Tau
extends AbstractField<BigDecimal>
implements QOM.Tau {
    Tau() {
        super(Names.N_TAU, Tools.allNotNull(SQLDataType.NUMERIC));
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(Internal.imul(DSL.pi(), DSL.two()));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("tau()");
    }

    @Override
    public final Function0<? extends QOM.Tau> $constructor() {
        return () -> new Tau();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Tau) {
            QOM.Tau o = (QOM.Tau)that;
            return true;
        }
        return super.equals(that);
    }
}

