/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.JSON;
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

final class JSONGetAttribute
extends AbstractField<JSON>
implements QOM.JSONGetAttribute {
    final Field<JSON> field;
    final Field<String> attribute;

    JSONGetAttribute(Field<JSON> field2, Field<String> attribute) {
        super(Names.N_JSON_GET_ATTRIBUTE, Tools.allNotNull(SQLDataType.JSON, field2, attribute));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSON);
        this.attribute = Tools.nullSafeNotNull(attribute, SQLDataType.VARCHAR);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: {
                return false;
            }
            case SQLSERVER: {
                return false;
            }
            case ORACLE: {
                return false;
            }
            case SNOWFLAKE: {
                return true;
            }
            case AURORA_MYSQL: 
            case MARIADB: 
            case MYSQL: {
                return false;
            }
        }
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case DB2: {
                ctx.visit(DSL.coalesce(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSON, this.field, DSL.inline("$.").concat(this.attribute.cast(SQLDataType.VARCHAR(3998)))), DSL.nvl2(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSON, this.field, DSL.field("{0} {empty array on empty}", DSL.inline("$.").concat(this.attribute.cast(SQLDataType.VARCHAR(3998))))), DSL.inline(null, SQLDataType.VARCHAR), DSL.inline("null"))));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.coalesce(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSON, this.field, DSL.inline("$.").concat(this.attribute)), new Field[]{DSL.jsonValue(this.field, DSL.inline("$.").concat(this.attribute))}));
                break;
            }
            case ORACLE: {
                ctx.visit(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSON, this.field, CustomField.of(Names.N_JSON, SQLDataType.VARCHAR, c -> Tools.prependInline(c, "$.", this.attribute, ""))));
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(DSL.function(Names.N_GET, this.getDataType(), this.field, this.attribute));
                break;
            }
            case AURORA_MYSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSON, this.field, DSL.inline("$.").concat(this.attribute)));
                break;
            }
            default: {
                ctx.sql('(').visit(this.field).sql("->").visit(this.attribute).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonGetAttribute(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.field));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.attribute));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<JSON> $arg1() {
        return this.field;
    }

    @Override
    public final Field<String> $arg2() {
        return this.attribute;
    }

    @Override
    public final QOM.JSONGetAttribute $arg1(Field<JSON> newValue) {
        return this.$constructor().apply((Field<JSON>)newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.JSONGetAttribute $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<JSON>)this.$arg1(), (Field<String>)newValue);
    }

    @Override
    public final Function2<? super Field<JSON>, ? super Field<String>, ? extends QOM.JSONGetAttribute> $constructor() {
        return (a1, a2) -> new JSONGetAttribute((Field<JSON>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONGetAttribute) {
            QOM.JSONGetAttribute o = (QOM.JSONGetAttribute)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$attribute(), o.$attribute());
        }
        return super.equals(that);
    }
}

