/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.impl.AbstractDelegateField;

final class ParenthesisedField<T>
extends AbstractDelegateField<T> {
    ParenthesisedField(Field<T> delegate) {
        super(delegate);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.sql('(');
        super.accept(ctx);
        ctx.sql(')');
    }
}

