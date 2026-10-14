/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

@Pro
final class SysConnectByPath
extends AbstractField<String>
implements QOM.SysConnectByPath {
    final Field<?> field;
    final String separator;

    SysConnectByPath(Field<?> field2, String separator) {
        super(Names.N_SYS_CONNECT_BY_PATH, Tools.allNotNull(SQLDataType.VARCHAR, field2));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.OTHER);
        this.separator = separator;
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_SYS_CONNECT_BY_PATH, this.getDataType(), this.field, DSL.inline(this.separator)));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("sysConnectByPath(");
        String s2 = "";
        ctx.sql(s2).visit(this.field);
        s2 = ", ";
        ctx.sql(s2).visit(DSL.inline(this.separator));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<?> $arg1() {
        return this.field;
    }

    @Override
    public final String $arg2() {
        return this.separator;
    }

    @Override
    public final QOM.SysConnectByPath $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue, this.$arg2());
    }

    @Override
    public final QOM.SysConnectByPath $arg2(String newValue) {
        return this.$constructor().apply((Field<?>)this.$arg1(), (String)((String)newValue));
    }

    @Override
    public final Function2<? super Field<?>, ? super String, ? extends QOM.SysConnectByPath> $constructor() {
        return (a1, a2) -> new SysConnectByPath((Field<?>)a1, (String)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.SysConnectByPath) {
            QOM.SysConnectByPath o = (QOM.SysConnectByPath)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$separator(), o.$separator());
        }
        return super.equals(that);
    }
}

