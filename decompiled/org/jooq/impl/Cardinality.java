/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Cardinality
extends AbstractField<Integer>
implements QOM.Cardinality {
    final Field<? extends Object[]> array;

    Cardinality(Field<? extends Object[]> array) {
        super(Names.N_CARDINALITY, Tools.allNotNull(SQLDataType.INTEGER, array));
        this.array = Tools.nullSafeNotNull(array, SQLDataType.OTHER.array());
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
                ctx.visit(DSL.function(Names.N_CARDINALITY, this.getDataType(), this.array));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("cardinality(");
        ctx.visit(this.array);
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Object[]> $arg1() {
        return this.array;
    }

    @Override
    public final QOM.Cardinality $arg1(Field<? extends Object[]> newValue) {
        return this.$constructor().apply((Field<? extends Object[]>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Object[]>, ? extends QOM.Cardinality> $constructor() {
        return a1 -> new Cardinality((Field<? extends Object[]>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Cardinality) {
            QOM.Cardinality o = (QOM.Cardinality)that;
            return StringUtils.equals(this.$array(), o.$array());
        }
        return super.equals(that);
    }
}

