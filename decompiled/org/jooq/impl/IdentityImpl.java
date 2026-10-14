/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;

final class IdentityImpl<R extends Record, T>
implements Identity<R, T> {
    static final IdentityImpl<?, ?> NULL = new IdentityImpl(null, null);
    private final Table<R> table;
    private final TableField<R, T> field;

    IdentityImpl(Table<R> table, TableField<R, T> field2) {
        this.table = table;
        this.field = field2;
    }

    @Override
    public final Table<R> getTable() {
        return this.table;
    }

    @Override
    public final TableField<R, T> getField() {
        return this.field;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Identity) {
            return this.toString().equals(obj.toString());
        }
        return false;
    }

    public String toString() {
        return this.field.toString();
    }
}

