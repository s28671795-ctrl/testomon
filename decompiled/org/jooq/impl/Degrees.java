/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Degrees
extends AbstractField<BigDecimal>
implements QOM.Degrees {
    final Field<? extends Number> radians;

    Degrees(Field<? extends Number> radians2) {
        super(Names.N_DEGREES, Tools.allNotNull(SQLDataType.NUMERIC, radians2));
        this.radians = Tools.nullSafeNotNull(radians2, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
                return false;
            }
            case BIGQUERY: 
            case HANA: 
            case INGRES: 
            case ORACLE: 
            case FIREBIRD: {
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
            case ACCESS: {
                ctx.visit(Internal.idiv(Internal.imul(this.radians.coerce(BigDecimal.class), DSL.inline(180)), DSL.pi()));
                break;
            }
            case BIGQUERY: 
            case HANA: 
            case INGRES: 
            case ORACLE: 
            case FIREBIRD: {
                ctx.visit(Internal.idiv(Internal.imul(Tools.castIfNeeded(this.radians, BigDecimal.class), DSL.inline(180)), DSL.pi()));
                break;
            }
            case SQLITE: {
                if (ctx.dialect().precedes(SQLDialect.SQLITE_3_30)) {
                    ctx.visit(Internal.idiv(Internal.imul(Tools.castIfNeeded(this.radians, BigDecimal.class), DSL.inline(180)), DSL.pi()));
                    break;
                }
                ctx.visit(DSL.function(Names.N_DEGREES, this.getDataType(), this.radians));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_DEGREES, this.getDataType(), this.radians));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("deg(");
        ctx.visit(Val.getJavaValue(this.radians));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.radians;
    }

    @Override
    public final QOM.Degrees $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Degrees> $constructor() {
        return a1 -> new Degrees((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Degrees) {
            QOM.Degrees o = (QOM.Degrees)that;
            return StringUtils.equals(this.$radians(), o.$radians());
        }
        return super.equals(that);
    }
}

