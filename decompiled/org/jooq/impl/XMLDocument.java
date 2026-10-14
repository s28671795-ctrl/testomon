/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.XML;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

@Pro
final class XMLDocument
extends AbstractField<XML>
implements QOM.XMLDocument {
    final Field<XML> content;

    XMLDocument(Field<XML> content) {
        super(Names.N_XMLDOCUMENT, Tools.allNotNull(SQLDataType.XML, content));
        this.content = Tools.nullSafeNotNull(content, SQLDataType.XML);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        ctx.visit(Names.N_XMLDOCUMENT).sql('(').visit(this.content).sql(')');
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("xmldocument(");
        ctx.visit(this.content);
        ctx.sql(')');
    }

    @Override
    public final Field<XML> $arg1() {
        return this.content;
    }

    @Override
    public final QOM.XMLDocument $arg1(Field<XML> newValue) {
        return this.$constructor().apply((Field<XML>)newValue);
    }

    @Override
    public final Function1<? super Field<XML>, ? extends QOM.XMLDocument> $constructor() {
        return a1 -> new XMLDocument((Field<XML>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.XMLDocument) {
            QOM.XMLDocument o = (QOM.XMLDocument)that;
            return StringUtils.equals(this.$content(), o.$content());
        }
        return super.equals(that);
    }
}

