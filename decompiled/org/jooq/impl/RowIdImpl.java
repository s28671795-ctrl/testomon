/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.RowId;

record RowIdImpl(Object value) implements RowId
{
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}

