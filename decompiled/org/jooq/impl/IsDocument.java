/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class IsDocument
extends AbstractCondition
implements QOM.IsDocument {
    final Field<?> field;

    IsDocument(Field<?> field2) {
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
                ctx.visit(this.field).sql(' ').visit(Keywords.K_IS_DOCUMENT);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.field).sql(".isDocument()");
    }

    @Override
    public final Field<?> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.IsDocument $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<?>, ? extends QOM.IsDocument> $constructor() {
        return a1 -> new IsDocument((Field<?>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.IsDocument) {
            QOM.IsDocument o = (QOM.IsDocument)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

