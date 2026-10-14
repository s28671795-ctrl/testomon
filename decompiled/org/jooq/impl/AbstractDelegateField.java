/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.impl.AbstractField;
import org.jooq.impl.QOM;

abstract class AbstractDelegateField<T>
extends AbstractField<T>
implements QOM.UTransient {
    private Field<T> delegate;

    AbstractDelegateField(Field<T> delegate) {
        super(delegate.getQualifiedName(), delegate.getDataType(), delegate.getCommentPart());
        this.delegate = delegate;
    }

    @Override
    public void accept(Context<?> ctx) {
        ctx.visit(this.delegate);
    }
}

