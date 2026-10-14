/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class QualifiedSelectFieldList
extends QueryPartList<SelectFieldOrAsterisk> {
    private final Table<?> table;

    QualifiedSelectFieldList(Table<?> table, Iterable<SelectFieldOrAsterisk> fields) {
        this.table = table;
        for (SelectFieldOrAsterisk field2 : fields) {
            this.add(Tools.qualify(table, field2));
        }
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return super.rendersContent(ctx);
    }

    @Override
    protected final void toSQLEmptyList(Context<?> context) {
        this.table.asterisk();
    }

    @Override
    public final boolean declaresFields() {
        return true;
    }
}

