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
final class ConnectByRoot<T>
extends AbstractField<T>
implements QOM.ConnectByRoot<T> {
    final Field<T> field;

    ConnectByRoot(Field<T> field2) {
        super(Names.N_CONNECT_BY_ROOT, Tools.allNotNull(Tools.dataType(field2), field2));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.OTHER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.field instanceof AbstractField && ((AbstractField)this.field).parenthesised(ctx)) {
            ctx.visit(Names.N_CONNECT_BY_ROOT).sql(' ').visit(this.field);
        } else {
            ctx.visit(Names.N_CONNECT_BY_ROOT).sql(" (").visit(this.field).sql(')');
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("connectByRoot(");
        ctx.visit(this.field);
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.ConnectByRoot<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.ConnectByRoot<T>> $constructor() {
        return a1 -> new ConnectByRoot(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ConnectByRoot) {
            QOM.ConnectByRoot o = (QOM.ConnectByRoot)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

