/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.JSONB;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class JSONBGetElementAsText
extends AbstractField<String>
implements QOM.JSONBGetElementAsText {
    final Field<JSONB> field;
    final Field<Integer> index;

    JSONBGetElementAsText(Field<JSONB> field2, Field<Integer> index) {
        super(Names.N_JSONB_GET_ELEMENT_AS_TEXT, Tools.allNotNull(SQLDataType.VARCHAR, field2, index));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSONB);
        this.index = Tools.nullSafeNotNull(index, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case DB2: {
                ctx.visit(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSONB, this.field, DSL.inline("$.").concat(this.index.cast(SQLDataType.VARCHAR(3998)))));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.coalesce(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSONB, this.field, DSL.inline("$[").concat(this.index).concat(DSL.inline("]"))), new Field[]{DSL.jsonbValue(this.field, DSL.inline("$[").concat(this.index).concat(DSL.inline("]")))}));
                break;
            }
            case ORACLE: {
                ctx.visit(Tools.derivedTable(ctx, this.field, f -> DSL.coalesce(DSL.jsonbValue((Field<JSONB>)f, CustomField.of(Names.N_JSON, SQLDataType.VARCHAR, c -> Tools.prependInline(c, "$[", this.index, "]"))), new Field[]{DSL.nullif(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSONB, f, CustomField.of(Names.N_JSON, SQLDataType.VARCHAR, c -> Tools.prependInline(c, "$[", this.index, "]"))), DSL.inline("null"))})));
                break;
            }
            case AURORA_MYSQL: 
            case MYSQL: {
                ctx.visit(DSL.function(Names.N_JSON_UNQUOTE, SQLDataType.JSONB, DSL.nullif(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSONB, this.field, DSL.inline("$[").concat(this.index).concat(DSL.inline("]"))), DSL.inline("null").cast(SQLDataType.JSONB))));
                break;
            }
            case MARIADB: {
                ctx.visit(DSL.function(Names.N_JSON_UNQUOTE, SQLDataType.JSONB, DSL.nullif(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSONB, this.field, DSL.inline("$[").concat(this.index).concat(DSL.inline("]"))).cast(SQLDataType.VARCHAR), DSL.inline("null"))));
                break;
            }
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSONB, this.field, DSL.inline("$[").concat(this.index).concat(DSL.inline("]"))));
                break;
            }
            default: {
                ctx.sql('(').visit(this.field).sql("->>").visit(this.index).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonbGetElementAsText(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.field));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.index));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<JSONB> $arg1() {
        return this.field;
    }

    @Override
    public final Field<Integer> $arg2() {
        return this.index;
    }

    @Override
    public final QOM.JSONBGetElementAsText $arg1(Field<JSONB> newValue) {
        return this.$constructor().apply((Field<JSONB>)newValue, (Field<Integer>)this.$arg2());
    }

    @Override
    public final QOM.JSONBGetElementAsText $arg2(Field<Integer> newValue) {
        return this.$constructor().apply((Field<JSONB>)this.$arg1(), (Field<Integer>)newValue);
    }

    @Override
    public final Function2<? super Field<JSONB>, ? super Field<Integer>, ? extends QOM.JSONBGetElementAsText> $constructor() {
        return (a1, a2) -> new JSONBGetElementAsText((Field<JSONB>)a1, (Field<Integer>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONBGetElementAsText) {
            QOM.JSONBGetElementAsText o = (QOM.JSONBGetElementAsText)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$index(), o.$index());
        }
        return super.equals(that);
    }
}

