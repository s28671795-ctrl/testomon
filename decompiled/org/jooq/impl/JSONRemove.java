/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.JSON;
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

final class JSONRemove
extends AbstractField<JSON>
implements QOM.JSONRemove {
    final Field<JSON> field;
    final Field<String> path;

    JSONRemove(Field<JSON> field2, Field<String> path) {
        super(Names.N_JSON_REMOVE, Tools.allNotNull(SQLDataType.JSON, field2, path));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSON);
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
                ctx.visit(DSL.function(Names.N_JSON_MODIFY, SQLDataType.JSON, this.field, this.path, DSL.inline(null, SQLDataType.JSON)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_JSON_REMOVE, SQLDataType.JSON, this.field, this.path));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonRemove(");
        String s2 = "";
        ctx.sql(s2).visit(this.field);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.path));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<JSON> $arg1() {
        return this.field;
    }

    @Override
    public final Field<String> $arg2() {
        return this.path;
    }

    @Override
    public final QOM.JSONRemove $arg1(Field<JSON> newValue) {
        return this.$constructor().apply((Field<JSON>)newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.JSONRemove $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<JSON>)this.$arg1(), (Field<String>)newValue);
    }

    @Override
    public final Function2<? super Field<JSON>, ? super Field<String>, ? extends QOM.JSONRemove> $constructor() {
        return (a1, a2) -> new JSONRemove((Field<JSON>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONRemove) {
            QOM.JSONRemove o = (QOM.JSONRemove)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$path(), o.$path());
        }
        return super.equals(that);
    }
}

