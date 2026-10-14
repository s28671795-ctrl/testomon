/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.JSONB;
import org.jooq.Pro;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.JSONEntryImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class JSONBSet
extends AbstractField<JSONB>
implements QOM.JSONBSet {
    final Field<JSONB> field;
    final Field<String> path;
    final Field<?> value;

    JSONBSet(Field<JSONB> field2, Field<String> path, Field<?> value) {
        super(Names.N_JSONB_SET, Tools.allNotNull(SQLDataType.JSONB, field2, path, value));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSONB);
        this.path = Tools.nullSafeNotNull(path, SQLDataType.VARCHAR);
        this.value = Tools.nullSafeNoConvertValNotNull(value, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ORACLE: {
                ctx.visit(Names.N_JSON_TRANSFORM).sql('(').visit(this.field).sql(", ").visit(Keywords.K_SET).sql(' ').visit(this.path, ParamType.INLINED).sql(" = ").visit(JSONEntryImpl.jsonCast(ctx, this.value, true)).sql(')');
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.function(Names.N_JSON_MODIFY, SQLDataType.JSONB, DSL.function(Names.N_JSON_MODIFY, SQLDataType.JSON, this.field, this.path, DSL.inline("")), DSL.inline("strict ").concat(this.path), this.value.getDataType().isJSON() ? DSL.function(Names.N_JSON_QUERY, SQLDataType.JSONB, this.value) : this.value));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_JSON_SET, SQLDataType.JSONB, this.field, this.path, JSONEntryImpl.jsonCast(ctx, this.value, true)));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonbSet(");
        String s2 = "";
        ctx.sql(s2).visit(this.field);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.path));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
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
    public final Field<?> $arg3() {
        return this.value;
    }

    @Override
    public final QOM.JSONBSet $arg1(Field<JSONB> newValue) {
        return this.$constructor().apply(newValue, (Field<String>)this.$arg2(), (Field<?>)this.$arg3());
    }

    @Override
    public final QOM.JSONBSet $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<JSONB>)this.$arg1(), newValue, (Field<?>)this.$arg3());
    }

    @Override
    public final QOM.JSONBSet $arg3(Field<?> newValue) {
        return this.$constructor().apply((Field<JSONB>)this.$arg1(), (Field<String>)this.$arg2(), newValue);
    }

    @Override
    public final Function3<? super Field<JSONB>, ? super Field<String>, ? super Field<?>, ? extends QOM.JSONBSet> $constructor() {
        return (a1, a2, a3) -> new JSONBSet((Field<JSONB>)a1, (Field<String>)a2, (Field<?>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONBSet) {
            QOM.JSONBSet o = (QOM.JSONBSet)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$path(), o.$path()) && StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

