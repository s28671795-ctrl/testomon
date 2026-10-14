/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Constraint;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.Context;
import org.jooq.ForeignKey;
import org.jooq.Key;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

abstract class AbstractKey<R extends Record>
extends AbstractNamed
implements Key<R>,
QOM.UEmpty {
    private final Table<R> table;
    private final TableField<R, ?>[] fields;
    private final boolean enforced;

    AbstractKey(Table<R> table, TableField<R, ?>[] fields, boolean enforced) {
        this(table, null, fields, enforced);
    }

    AbstractKey(Table<R> table, Name name, TableField<R, ?>[] fields, boolean enforced) {
        super(AbstractKey.qualify(table, name), null);
        this.table = table;
        this.fields = fields;
        this.enforced = enforced;
    }

    @Override
    public final Table<R> getTable() {
        return this.table;
    }

    @Override
    public final List<TableField<R, ?>> getFields() {
        return Arrays.asList(this.fields);
    }

    @Override
    public final TableField<R, ?>[] getFieldsArray() {
        return this.fields;
    }

    @Override
    public final boolean nullable() {
        return Tools.anyMatch(this.fields, f -> f.getDataType().nullable());
    }

    @Override
    public final boolean enforced() {
        return this.enforced;
    }

    private final Constraint enforced(ConstraintEnforcementStep key) {
        return this.enforced() ? key : key.notEnforced();
    }

    @Override
    public final Constraint constraint() {
        return this.enforced(this.constraint0());
    }

    abstract ConstraintEnforcementStep constraint0();

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.getUnqualifiedName());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.getQualifiedName().hashCode();
        result = 31 * result + (this.table == null ? 0 : this.table.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractKey)) {
            return false;
        }
        if (this instanceof ForeignKey ? !(obj instanceof ForeignKey) : this instanceof UniqueKey && !(obj instanceof UniqueKey)) {
            return false;
        }
        AbstractKey other = (AbstractKey)obj;
        if (!this.getQualifiedName().equals(other.getQualifiedName())) {
            return false;
        }
        if (this.table == null) {
            if (other.table != null) {
                return false;
            }
        } else {
            if (!this.table.equals(other.table)) {
                return false;
            }
            if (!Arrays.equals(this.fields, other.fields)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return this.constraint().toString();
    }
}

