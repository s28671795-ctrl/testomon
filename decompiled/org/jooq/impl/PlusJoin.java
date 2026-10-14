/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

@Pro
final class PlusJoin<T>
extends AbstractField<T>
implements QOM.PlusJoin<T> {
    final Field<T> field;

    PlusJoin(Field<T> field2) {
        super(Names.N_PLUS, Tools.allNotNull(Tools.dataType(field2), field2));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.OTHER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(this.field).sql("(+)");
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.field).sql(".plus()");
    }

    @Override
    public final Field<T> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.PlusJoin<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.PlusJoin<T>> $constructor() {
        return a1 -> new PlusJoin(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.PlusJoin) {
            QOM.PlusJoin o = (QOM.PlusJoin)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

