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
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class JSONGetElement
extends AbstractField<JSON>
implements QOM.JSONGetElement {
    final Field<JSON> field;
    final Field<Integer> index;

    JSONGetElement(Field<JSON> field2, Field<Integer> index) {
        super(Names.N_JSON_GET_ELEMENT, Tools.allNotNull(SQLDataType.JSON, field2, index));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSON);
        this.index = Tools.nullSafeNotNull(index, SQLDataType.INTEGER);
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
                ctx.visit(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSON, this.field, DSL.inline("$.").concat(this.index.cast(SQLDataType.VARCHAR(3998)))));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.coalesce(DSL.function(Names.N_JSON_QUERY, SQLDataType.JSON, this.field, DSL.inline("$[").concat(this.index).concat(DSL.inline("]"))), new Field[]{DSL.jsonValue(this.field, DSL.inline("$[").concat(this.index).concat(DSL.inline("]")))}));
                break;
            }
            case ORACLE: {
                ctx.visit(Names.N_JSON_QUERY).sql('(').visit(this.field).sql(", ");
                Tools.prependInline(ctx, "$[", this.index, "]");
                ctx.sql(')');
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(DSL.function(Names.N_GET, this.getDataType(), this.field, this.index));
                break;
            }
            case AURORA_MYSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSON, this.field, DSL.inline("$[").concat(this.index).concat(DSL.inline("]"))));
                break;
            }
            default: {
                ctx.sql('(').visit(this.field).sql("->").visit(this.index).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonGetElement(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.field));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.index));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<JSON> $arg1() {
        return this.field;
    }

    @Override
    public final Field<Integer> $arg2() {
        return this.index;
    }

    @Override
    public final QOM.JSONGetElement $arg1(Field<JSON> newValue) {
        return this.$constructor().apply((Field<JSON>)newValue, (Field<Integer>)this.$arg2());
    }

    @Override
    public final QOM.JSONGetElement $arg2(Field<Integer> newValue) {
        return this.$constructor().apply((Field<JSON>)this.$arg1(), (Field<Integer>)newValue);
    }

    @Override
    public final Function2<? super Field<JSON>, ? super Field<Integer>, ? extends QOM.JSONGetElement> $constructor() {
        return (a1, a2) -> new JSONGetElement((Field<JSON>)a1, (Field<Integer>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONGetElement) {
            QOM.JSONGetElement o = (QOM.JSONGetElement)that;
            return StringUtils.equals(this.$field(), o.$field()) && StringUtils.equals(this.$index(), o.$index());
        }
        return super.equals(that);
    }
}

