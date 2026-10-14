/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.impl.NoField;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class SortFieldList
extends QueryPartList<SortField<?>> {
    SortFieldList() {
        this((List<SortField<?>>)SortFieldList.emptyList());
    }

    SortFieldList(List<SortField<?>> wrappedList) {
        super(wrappedList);
    }

    final void addAll(Field<?> ... fields) {
        this.addAll(Tools.map(fields, f -> f.asc()));
    }

    @Override
    final boolean canAdd(SortField<?> e) {
        return super.canAdd(e) && !(e instanceof NoField);
    }

    final boolean uniform() {
        return Tools.allMatch(this, f -> f.getOrder() == SortOrder.DESC == (((SortField)this.get(0)).getOrder() == SortOrder.DESC));
    }

    final boolean nulls() {
        return Tools.anyMatch(this, f -> f.$nullOrdering() != null);
    }

    final List<Field<?>> fields() {
        return Tools.map(this, f -> f.$field());
    }
}

