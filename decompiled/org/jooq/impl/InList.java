/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.RowN;
import org.jooq.impl.AbstractInList;
import org.jooq.impl.QOM;

final class InList<T>
extends AbstractInList<T>
implements QOM.InList<T> {
    static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_IN};

    InList(Field<T> field2, List<? extends Field<?>> values2) {
        super(field2, values2);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    final Function2<? super RowN, ? super RowN[], ? extends Condition> rowCondition() {
        return RowN::in;
    }

    @Override
    public final Function2<? super Field<T>, ? super QOM.UnmodifiableList<? extends Field<T>>, ? extends QOM.InList<T>> $constructor() {
        return (a1, a2) -> new InList(a1, (List<? extends Field<?>>)((List<Field<?>>)a2));
    }
}

