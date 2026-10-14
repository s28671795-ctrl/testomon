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

final class IsNotJson
extends AbstractCondition
implements QOM.IsNotJson {
    final Field<?> field;

    IsNotJson(Field<?> field2) {
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.OTHER);
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
                ctx.visit(DSL.condition(DSL.function(Names.N_JSON_VALID, SQLDataType.BOOLEAN, this.field)).not());
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.sql("not(").visit(DSL.function(Names.N_ISJSON, SQLDataType.INTEGER, this.field).eq(DSL.inline(1))).sql(')');
                break;
            }
            case SNOWFLAKE: {
                ctx.sql('(').visit(DSL.function(Names.N_CHECK_JSON, SQLDataType.VARCHAR, this.field).isNotNull()).sql(')');
                break;
            }
            default: {
                ctx.visit(this.field).sql(' ').visit(Keywords.K_IS_NOT_JSON);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.field).sql(".isNotJson()");
    }

    @Override
    public final Field<?> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.IsNotJson $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<?>, ? extends QOM.IsNotJson> $constructor() {
        return a1 -> new IsNotJson((Field<?>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.IsNotJson) {
            QOM.IsNotJson o = (QOM.IsNotJson)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

