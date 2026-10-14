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

final class JSONBGetAttributeAsText
extends AbstractField<String>
implements QOM.JSONBGetAttributeAsText {
    final Field<JSONB> field;
    final Field<String> attribute;

    JSONBGetAttributeAsText(Field<JSONB> field2, Field<String> attribute) {
        super(Names.N_JSONB_GET_ATTRIBUTE_AS_TEXT, Tools.allNotNull(SQLDataType.VARCHAR, field2, attribute));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSONB);
        this.attribute = Tools.nullSafeNotNull(attribute, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case DB2: {
                ctx.visit(DSL.coalesce(DSL.jsonbValue(this.field, DSL.inline("$.").concat(this.attribute.cast(SQLDataType.VARCHAR(3998)))), new Field[]{DSL.function(Names.N_JSON_QUERY, SQLDataType.JSONB, this.field, DSL.inline("$.").concat(this.attribute.cast(SQLDataType.VARCHAR(3998))))}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(Tools.derivedTable(ctx, this.field, DSL.inline("$.").concat(this.attribute), (f, p) -> DSL.coalesce(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSONB, f, p), new Field[]{DSL.jsonbValue((Field<JSONB>)f, p)})));
                break;
            }
            case ORACLE: {
                ctx.visit(Tools.derivedTable(ctx, this.field, f -> DSL.coalesce(DSL.jsonbValue((Field<JSONB>)f, CustomField.of(Names.N_JSON, SQLDataType.VARCHAR, c -> Tools.prependInline(c, "$.", this.attribute, ""))), new Field[]{DSL.nullif(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSONB, f, CustomField.of(Names.N_JSON, SQLDataType.VARCHAR, c -> Tools.prependInline(c, "$.", this.attribute, ""))), DSL.inline("null"))})));
                break;
            }
            case AURORA_MYSQL: 
            case MYSQL: {
                ctx.visit(DSL.function(Names.N_JSON_UNQUOTE, SQLDataType.JSONB, DSL.nullif(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSONB, this.field, DSL.inline("$.").concat(this.attribute)), DSL.inline("null").cast(SQLDataType.JSONB))));
                break;
            }
            case MARIADB: {
                ctx.visit(DSL.function(Names.N_JSON_UNQUOTE, SQLDataType.JSONB, DSL.nullif(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSONB, this.field, DSL.inline("$.").concat(this.attribute)).cast(SQLDataType.VARCHAR), DSL.inline("null"))));
                break;
            }
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSONB, this.field, DSL.inline("$.").concat(this.attribute)));
                break;
            }
            default: {
                ctx.sql('(').visit(this.field).sql("->>").visit(this.attribute).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonbGetAttributeAsText(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.field));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.attribute));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<JSONB> $arg1() {
        return this.field;
    }

    @Override
    public final Field<String> $arg2() {
        return this.attribute;
    }

    @Override
    public final QOM.JSONBGetAttributeAsText $arg1(Field<JSONB> newValue) {
        return this.$constructor().apply((Field<JSONB>)newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.JSONBGetAttributeAsText $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<JSONB>)this.$arg1(), (Field<String>)newValue);
    }

    @Override
    public final Function2<? super Field<JSONB>, ? super Field<String>, ? extends QOM.JSONBGetAttributeAsText> $constructor() {
        return (a1, a2) -> new JSONBGetAttributeAsText((Field<JSONB>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONBGetAttributeAsText) {
            QOM.JSONBGetAttributeAsText o = (QOM.JSONBGetAttributeAsText)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$attribute(), o.$attribute());
        }
        return super.equals(that);
    }
}

