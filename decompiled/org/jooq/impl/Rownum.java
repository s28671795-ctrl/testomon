/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

@Pro
final class Rownum
extends AbstractField<Integer>
implements QOM.Rownum {
    static final Set<SQLDialect> NATIVE_SUPPORT = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.ORACLE);

    Rownum() {
        super(Names.N_ROWNUM, Tools.allNotNull(SQLDataType.INTEGER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
        } else if (ctx.family() == SQLDialect.SNOWFLAKE) {
            ctx.visit(Internal.iadd(DSL.function(Names.N_SEQ4, this.getDataType(), new Field[0]), DSL.one()));
        } else {
            ctx.visit(Names.N_ROWNUM);
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("rownum()");
    }

    @Override
    public final Function0<? extends QOM.Rownum> $constructor() {
        return () -> new Rownum();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Rownum) {
            QOM.Rownum o = (QOM.Rownum)that;
            return true;
        }
        return super.equals(that);
    }
}

