/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.JSONB;
import org.jooq.Pro;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class JSONBRemove
extends AbstractField<JSONB>
implements QOM.JSONBRemove {
    final Field<JSONB> field;
    final Field<String> path;

    JSONBRemove(Field<JSONB> field2, Field<String> path) {
        super(Names.N_JSONB_REMOVE, Tools.allNotNull(SQLDataType.JSONB, field2, path));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSONB);
        this.path = Tools.nullSafeNotNull(path, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ORACLE: {
                ctx.visit(Names.N_JSON_TRANSFORM).sql('(').visit(this.field).sql(", ").visit(Keywords.K_REMOVE).sql(' ').visit(this.path, ParamType.INLINED).sql(')');
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.function(Names.N_JSON_MODIFY, SQLDataType.JSONB, this.field, this.path, DSL.inline(null, SQLDataType.JSONB)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_JSON_REMOVE, SQLDataType.JSONB, this.field, this.path));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonbRemove(");
        String s2 = "";
        ctx.sql(s2).visit(this.field);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.path));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<JSONB> $arg1() {
        return this.field;
    }

    @Override
    public final Field<String> $arg2() {
        return this.path;
    }

    @Override
    public final QOM.JSONBRemove $arg1(Field<JSONB> newValue) {
        return this.$constructor().apply((Field<JSONB>)newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.JSONBRemove $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<JSONB>)this.$arg1(), (Field<String>)newValue);
    }

    @Override
    public final Function2<? super Field<JSONB>, ? super Field<String>, ? extends QOM.JSONBRemove> $constructor() {
        return (a1, a2) -> new JSONBRemove((Field<JSONB>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONBRemove) {
            QOM.JSONBRemove o = (QOM.JSONBRemove)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$path(), o.$path());
        }
        return super.equals(that);
    }
}

