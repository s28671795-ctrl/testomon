/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.XML;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class XMLComment
extends AbstractField<XML>
implements QOM.XMLComment {
    final Field<String> comment;

    XMLComment(Field<String> comment) {
        super(Names.N_XMLCOMMENT, Tools.allNotNull(SQLDataType.XML, comment));
        this.comment = Tools.nullSafeNotNull(comment, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.cast(DSL.concat(DSL.inline("<!--"), this.comment, DSL.inline("-->")), SQLDataType.XML));
                break;
            }
            default: {
                ctx.visit(Names.N_XMLCOMMENT).sql('(').visit(this.comment).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("xmlcomment(");
        ctx.visit(Val.getJavaValue(this.comment));
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.comment;
    }

    @Override
    public final QOM.XMLComment $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue);
    }

    @Override
    public final Function1<? super Field<String>, ? extends QOM.XMLComment> $constructor() {
        return a1 -> new XMLComment((Field<String>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.XMLComment) {
            QOM.XMLComment o = (QOM.XMLComment)that;
            return StringUtils.equals(this.$comment(), o.$comment());
        }
        return super.equals(that);
    }
}

