/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.XML;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class XMLSerialize<T>
extends AbstractField<T>
implements QOM.XMLSerialize<T> {
    final boolean content;
    final Field<XML> value;
    final DataType<T> type;

    XMLSerialize(boolean content, Field<XML> value, DataType<T> type) {
        super(Names.N_XMLSERIALIZE, type);
        this.content = content;
        this.value = Tools.nullSafeNotNull(value, SQLDataType.XML);
        this.type = type;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql(this.content ? "xmlserializeContent(" : "xmlserializeDocument(").visit(this.value).sql(", ").visit(this.type).sql(')');
            return;
        }
        ctx.visit(Names.N_XMLSERIALIZE).sql('(');
        if (this.content) {
            ctx.visit(Keywords.K_CONTENT).sql(' ');
        } else if (ctx.family() != SQLDialect.DB2) {
            ctx.visit(Keywords.K_DOCUMENT).sql(' ');
        }
        ctx.visit(this.value).sql(' ').visit(Keywords.K_AS).sql(' ').sql(this.type.getCastTypeName(ctx.configuration())).sql(')');
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("xmlserializeDocument(");
        String s2 = "";
        ctx.sql(s2).visit(DSL.inline(this.content));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(this.type);
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Boolean $arg1() {
        return this.content;
    }

    @Override
    public final Field<XML> $arg2() {
        return this.value;
    }

    @Override
    public final DataType<T> $arg3() {
        return this.type;
    }

    @Override
    public final QOM.XMLSerialize<T> $arg1(Boolean newValue) {
        return this.$constructor().apply(newValue, (Field<XML>)this.$arg2(), (DataType<T>)this.$arg3());
    }

    @Override
    public final QOM.XMLSerialize<T> $arg2(Field<XML> newValue) {
        return this.$constructor().apply(this.$arg1(), newValue, (DataType<T>)this.$arg3());
    }

    @Override
    public final QOM.XMLSerialize<T> $arg3(DataType<T> newValue) {
        return this.$constructor().apply(this.$arg1(), (Field<XML>)this.$arg2(), newValue);
    }

    @Override
    public final Function3<? super Boolean, ? super Field<XML>, ? super DataType<T>, ? extends QOM.XMLSerialize<T>> $constructor() {
        return (a1, a2, a3) -> new XMLSerialize((boolean)a1, (Field<XML>)a2, a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.XMLSerialize) {
            QOM.XMLSerialize o = (QOM.XMLSerialize)that;
            return this.$content() == o.$content() && StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$type(), o.$type());
        }
        return super.equals(that);
    }
}

