/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

@Pro
final class Level
extends AbstractField<Integer>
implements QOM.Level {
    Level() {
        super(Names.N_LEVEL, Tools.allNotNull(SQLDataType.INTEGER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
        } else {
            ctx.visit(Names.N_LEVEL);
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("level()");
    }

    @Override
    public final Function0<? extends QOM.Level> $constructor() {
        return () -> new Level();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Level) {
            QOM.Level o = (QOM.Level)that;
            return true;
        }
        return super.equals(that);
    }
}

