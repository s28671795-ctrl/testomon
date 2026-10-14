/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectSelectStep;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class ArrayTableEmulation
extends AbstractQueryPart
implements QOM.UTransient {
    private final Object[] array;
    private final DataType<?> type;
    private final Name fieldAlias;
    private transient Select<?> table;

    ArrayTableEmulation(Object[] array, Name[] fieldAliases) {
        if (Tools.isEmpty(fieldAliases)) {
            this.fieldAlias = Names.N_COLUMN_VALUE;
        } else if (fieldAliases.length == 1) {
            this.fieldAlias = fieldAliases[0];
        } else {
            throw new IllegalArgumentException("Array table simulations can only have a single field alias");
        }
        this.array = array;
        this.type = Tools.componentDataType(array);
    }

    @Override
    public final void accept(Context<?> ctx) {
        Tools.visitSubquery(ctx, this.table(), 1, true);
    }

    private final Select<?> table() {
        if (this.table == null) {
            Select<Record> select = null;
            for (Object element : this.array) {
                Param<?> val = DSL.val(element, this.type);
                SelectSelectStep<Record> subselect = DSL.select(val.as(this.fieldAlias)).select(new SelectFieldOrAsterisk[0]);
                select = select == null ? subselect : select.unionAll(subselect);
            }
            if (select == null) {
                select = DSL.select(DSL.one().as(this.fieldAlias)).select(new SelectFieldOrAsterisk[0]).where((Condition)DSL.falseCondition());
            }
            this.table = select;
        }
        return this.table;
    }
}

