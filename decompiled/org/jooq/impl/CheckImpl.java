/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Check;
import org.jooq.Condition;
import org.jooq.Constraint;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;

final class CheckImpl<R extends Record>
extends AbstractNamed
implements Check<R>,
QOM.UNotYetImplemented {
    final Table<R> table;
    final Condition condition;
    final boolean enforced;

    CheckImpl(Table<R> table, Condition condition, boolean enforced) {
        this(table, null, condition, enforced);
    }

    CheckImpl(Table<R> table, Name name, Condition condition, boolean enforced) {
        super(name, null);
        this.table = table;
        this.condition = condition;
        this.enforced = enforced;
    }

    @Override
    public final Table<R> getTable() {
        return this.table;
    }

    @Override
    public final Condition condition() {
        return this.condition;
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
        return this.enforced(DSL.constraint(this.getName()).check(this.condition));
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.getUnqualifiedName());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + (this.condition == null ? 0 : this.condition.hashCode());
        result = 31 * result + (this.table == null ? 0 : this.table.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CheckImpl other = (CheckImpl)obj;
        if (!this.getQualifiedName().equals(other.getQualifiedName())) {
            return false;
        }
        if (this.condition == null ? other.condition != null : !this.condition.equals(other.condition)) {
            return false;
        }
        return !(this.table == null ? other.table != null : !this.table.equals(other.table));
    }

    @Override
    public String toString() {
        return this.constraint().toString();
    }
}

