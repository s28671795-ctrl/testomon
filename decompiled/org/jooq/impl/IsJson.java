/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class IsJson
extends AbstractCondition
implements QOM.IsJson {
    final Field<?> field;

    IsJson(Field<?> field2) {
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.OTHER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case MARIADB: 
            case MYSQL: {
                return true;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                return false;
            }
            case SNOWFLAKE: {
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
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DSL.function(Names.N_JSON_VALID, SQLDataType.BOOLEAN, this.field));
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.sql('(').visit(DSL.function(Names.N_ISJSON, SQLDataType.INTEGER, this.field).eq(DSL.inline(1))).sql(')');
                break;
            }
            case SNOWFLAKE: {
                ctx.sql('(').visit(DSL.function(Names.N_CHECK_JSON, SQLDataType.VARCHAR, this.field).isNull()).sql(')');
                break;
            }
            default: {
                ctx.visit(this.field).sql(' ').visit(Keywords.K_IS_JSON);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.field).sql(".isJson()");
    }

    @Override
    public final Field<?> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.IsJson $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<?>, ? extends QOM.IsJson> $constructor() {
        return a1 -> new IsJson((Field<?>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.IsJson) {
            QOM.IsJson o = (QOM.IsJson)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

