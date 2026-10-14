/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.XML;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class XMLConcat
extends AbstractField<XML>
implements QOM.XMLConcat {
    final QueryPartListView<? extends Field<?>> args;

    XMLConcat(Collection<? extends Field<?>> args2) {
        super(Names.N_XMLCONCAT, Tools.allNotNull(SQLDataType.XML));
        this.args = new QueryPartList((Iterable<Field<?>>)args2);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field(DSL.select(Tools.castAllIfNeeded(this.args.toArray((E[])Tools.EMPTY_FIELD), SQLDataType.XML)).forXML().path("").type()));
                break;
            }
            default: {
                ctx.visit(Names.N_XMLCONCAT).sql('(').visit(QueryPartCollectionView.wrap(this.args)).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("xmlconcat(");
        ctx.visit(QueryPartCollectionView.wrap(this.args));
        ctx.sql(')');
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $arg1() {
        return QOM.unmodifiable(this.args);
    }

    @Override
    public final QOM.XMLConcat $arg1(QOM.UnmodifiableList<? extends Field<?>> newValue) {
        return this.$constructor().apply((Collection<Field<?>>)newValue);
    }

    @Override
    public final Function1<? super Collection<? extends Field<?>>, ? extends QOM.XMLConcat> $constructor() {
        return a1 -> new XMLConcat((Collection<? extends Field<?>>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.XMLConcat) {
            QOM.XMLConcat o = (QOM.XMLConcat)that;
            return StringUtils.equals(this.$args(), o.$args());
        }
        return super.equals(that);
    }
}

