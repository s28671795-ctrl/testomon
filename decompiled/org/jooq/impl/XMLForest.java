/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.XML;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class XMLForest
extends AbstractField<XML>
implements QOM.XMLForest {
    final QueryPartListView<? extends Field<?>> fields;

    XMLForest(Collection<? extends Field<?>> fields) {
        super(Names.N_XMLFOREST, Tools.allNotNull(SQLDataType.XML));
        this.fields = new QueryPartList((Iterable<Field<?>>)fields);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
        } else {
            ctx.data(Tools.BooleanDataKey.DATA_AS_REQUIRED, true, c -> c.visit(Names.N_XMLFOREST).sql('(').declareFields(true, x -> x.visit(new SelectFieldList((Iterable<? extends Field<?>>)this.fields))).sql(')'));
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("xmlforest(");
        ctx.visit(QueryPartCollectionView.wrap(this.fields));
        ctx.sql(')');
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $arg1() {
        return QOM.unmodifiable(this.fields);
    }

    @Override
    public final QOM.XMLForest $arg1(QOM.UnmodifiableList<? extends Field<?>> newValue) {
        return this.$constructor().apply((Collection<Field<?>>)newValue);
    }

    @Override
    public final Function1<? super Collection<? extends Field<?>>, ? extends QOM.XMLForest> $constructor() {
        return a1 -> new XMLForest((Collection<? extends Field<?>>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.XMLForest) {
            QOM.XMLForest o = (QOM.XMLForest)that;
            return StringUtils.equals(this.$fields(), o.$fields());
        }
        return super.equals(that);
    }
}

