/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
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

final class Pi
extends AbstractField<BigDecimal>
implements QOM.Pi {
    Pi() {
        super(Names.N_PI, Tools.allNotNull(SQLDataType.NUMERIC));
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case BIGQUERY: 
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case ORACLE: 
            case TERADATA: {
                return false;
            }
            case ACCESS: {
                return false;
            }
            case SQLITE: {
                return false;
            }
        }
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case BIGQUERY: 
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case ORACLE: 
            case TERADATA: {
                ctx.visit(DSL.acos(Internal.ineg(DSL.one())));
                break;
            }
            case ACCESS: {
                ctx.visit(DSL.inline((Object)Math.PI, BigDecimal.class));
                break;
            }
            case SQLITE: {
                if (ctx.dialect().precedes(SQLDialect.SQLITE_3_30)) {
                    ctx.visit(DSL.inline((Object)Math.PI, BigDecimal.class));
                    break;
                }
                ctx.visit(DSL.function(Names.N_PI, this.getDataType(), new Field[0]));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_PI, this.getDataType(), new Field[0]));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("pi()");
    }

    @Override
    public final Function0<? extends QOM.Pi> $constructor() {
        return () -> new Pi();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Pi) {
            QOM.Pi o = (QOM.Pi)that;
            return true;
        }
        return super.equals(that);
    }
}

