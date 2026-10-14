/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.FieldOrRow;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Cube
extends AbstractField<Object>
implements QOM.Cube {
    private QueryPartList<FieldOrRow> arguments;

    Cube(FieldOrRow ... arguments) {
        super(Names.N_CUBE, SQLDataType.OTHER);
        this.arguments = new QueryPartList((QueryPart[])arguments);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_CUBE).sql(" (").visit(this.arguments).sql(')');
    }

    @Override
    public final QOM.UnmodifiableList<? extends FieldOrRow> $arg1() {
        return QOM.unmodifiable(this.arguments);
    }

    @Override
    public final Function1<? super QOM.UnmodifiableList<? extends FieldOrRow>, ? extends QOM.Cube> $constructor() {
        return l -> new Cube(l.toArray(Tools.EMPTY_FIELD_OR_ROW));
    }
}

