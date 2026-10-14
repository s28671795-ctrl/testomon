/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ContextConverter;
import org.jooq.impl.AbstractConverter;

public abstract class AbstractContextConverter<T, U>
extends AbstractConverter<T, U>
implements ContextConverter<T, U> {
    public AbstractContextConverter(Class<T> fromType, Class<U> toType) {
        super(fromType, toType);
    }

    @Override
    public String toString() {
        return "ContextConverter [ " + this.fromType().getName() + " -> " + this.toType().getName() + " ]";
    }
}

