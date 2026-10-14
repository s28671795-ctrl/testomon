/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.Function1;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.WrappedList;

final class GroupingSets
extends AbstractField<Object>
implements QOM.GroupingSets {
    private final QueryPartList<QueryPartList<Field<?>>> fieldSets = new QueryPartList();

    @SafeVarargs
    GroupingSets(Collection<? extends Field<?>> ... fieldSets) {
        super(Names.N_GROUPING_SETS, SQLDataType.OTHER);
        for (Collection<? extends Field<?>> fieldSet : fieldSets) {
            this.fieldSets.add(new QueryPartList((Iterable<? extends Field<?>>)fieldSet));
        }
    }

    @Override
    public final void accept(Context<?> ctx) {
        QueryPartList<WrappedList> arg = new QueryPartList<WrappedList>();
        for (Collection collection : this.fieldSets) {
            arg.add(new WrappedList(new QueryPartList(collection)));
        }
        ctx.visit(Keywords.K_GROUPING_SETS).sql(" (").visit(arg).sql(')');
    }

    @Override
    public final QOM.UnmodifiableList<? extends QOM.UnmodifiableList<? extends FieldOrRow>> $arg1() {
        return QOM.unmodifiable(this.fieldSets);
    }

    @Override
    public final Function1<? super QOM.UnmodifiableList<? extends QOM.UnmodifiableList<? extends FieldOrRow>>, ? extends QOM.GroupingSets> $constructor() {
        return l -> new GroupingSets(l.toArray(Tools.EMPTY_COLLECTION));
    }
}

