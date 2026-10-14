/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.AggregateFunction;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.OrderField;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.ListAgg;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SortFieldList;
import org.jooq.impl.Tools;

final class GroupConcat
extends AbstractAggregateFunction<String>
implements GroupConcatOrderByStep,
QOM.UNotYetImplemented {
    private final Field<?> field;
    private final SortFieldList orderBy;
    private String separator;

    GroupConcat(Field<?> field2) {
        this(field2, false);
    }

    GroupConcat(Field<?> field2, boolean distinct2) {
        super(distinct2, Names.N_GROUP_CONCAT, SQLDataType.VARCHAR, field2);
        this.field = field2;
        this.orderBy = new SortFieldList();
    }

    @Override
    public final void accept(Context<?> ctx) {
        ListAgg result = this.separator == null ? new ListAgg(this.distinct, this.field, DSL.inline(",")) : new ListAgg(this.distinct, this.field, DSL.inline(this.separator));
        if (!this.orderBy.isEmpty()) {
            result.withinGroupOrderBy((Collection)this.orderBy);
        } else if (ctx.family() == SQLDialect.ORACLE) {
            result.withinGroupOrderBy((Collection)this.orderBy);
        }
        ctx.visit(this.fo(result));
    }

    @Override
    public final AggregateFunction<String> separator(String s2) {
        this.separator = s2;
        return this;
    }

    @Override
    public final GroupConcat orderBy(OrderField<?> ... fields) {
        return this.orderBy(Arrays.asList(fields));
    }

    @Override
    public final GroupConcat orderBy(Collection<? extends OrderField<?>> fields) {
        this.orderBy.addAll(Tools.sortFields(fields));
        return this;
    }
}

