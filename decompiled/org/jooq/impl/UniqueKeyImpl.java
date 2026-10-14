/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKey;
import org.jooq.impl.DSL;

final class UniqueKeyImpl<R extends Record>
extends AbstractKey<R>
implements UniqueKey<R> {
    final List<ForeignKey<?, R>> references = new ArrayList();

    UniqueKeyImpl(Table<R> table, TableField<R, ?>[] fields, boolean enforced) {
        this(table, null, fields, enforced);
    }

    UniqueKeyImpl(Table<R> table, Name name, TableField<R, ?>[] fields, boolean enforced) {
        super(table, name, fields, enforced);
    }

    @Override
    public final boolean isPrimary() {
        return this.equals(this.getTable().getPrimaryKey());
    }

    @Override
    public final List<ForeignKey<?, R>> getReferences() {
        return Collections.unmodifiableList(this.references);
    }

    @Override
    final ConstraintEnforcementStep constraint0() {
        if (this.isPrimary()) {
            return DSL.constraint(this.getName()).primaryKey(this.getFieldsArray());
        }
        return DSL.constraint(this.getName()).unique(this.getFieldsArray());
    }
}

