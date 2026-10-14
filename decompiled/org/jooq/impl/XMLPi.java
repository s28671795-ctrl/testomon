/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.XML;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class XMLPi
extends AbstractField<XML>
implements QOM.XMLPi {
    final Name target;
    final Field<?> content;

    XMLPi(Name target) {
        super(Names.N_XMLPI, Tools.allNotNull(SQLDataType.XML));
        this.target = target;
        this.content = null;
    }

    XMLPi(Name target, Field<?> content) {
        super(Names.N_XMLPI, Tools.allNotNull(SQLDataType.XML, content));
        this.target = target;
        this.content = Tools.nullSafeNotNull(content, SQLDataType.OTHER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        ctx.visit(Names.N_XMLPI).sql('(').visit(Keywords.K_NAME).sql(' ').visit(this.target);
        if (this.content != null) {
            ctx.sql(", ").visit(this.content);
        }
        ctx.sql(')');
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("xmlpi(");
        String s2 = "";
        ctx.sql(s2).visit(this.target);
        s2 = ", ";
        if (this.content != null) {
            ctx.sql(s2).visit(this.content);
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Name $arg1() {
        return this.target;
    }

    @Override
    public final Field<?> $arg2() {
        return this.content;
    }

    @Override
    public final QOM.XMLPi $arg1(Name newValue) {
        return this.$constructor().apply((Name)((Name)newValue), (Field<?>)this.$arg2());
    }

    @Override
    public final QOM.XMLPi $arg2(Field<?> newValue) {
        return this.$constructor().apply(this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Name, ? super Field<?>, ? extends QOM.XMLPi> $constructor() {
        return (a1, a2) -> new XMLPi((Name)a1, (Field<?>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.XMLPi) {
            QOM.XMLPi o = (QOM.XMLPi)that;
            return StringUtils.equals(this.$target(), o.$target()) && StringUtils.equals(this.$content(), o.$content());
        }
        return super.equals(that);
    }
}

