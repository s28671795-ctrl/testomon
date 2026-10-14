/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.JSON;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.OracleJSONKeys;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.TopLevelCte;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class JSONKeys
extends AbstractField<JSON>
implements QOM.JSONKeys {
    final Field<JSON> field;

    JSONKeys(Field<JSON> field2) {
        super(Names.N_JSON_KEYS, Tools.allNotNull(SQLDataType.JSON, field2));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSON);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                return false;
            }
            case ORACLE: {
                return false;
            }
            case SNOWFLAKE: {
                return true;
            }
            case SQLITE: {
                return false;
            }
        }
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(DSL.field(DSL.select(DSL.coalesce(DSL.jsonArrayAgg(DSL.field(DSL.unquotedName("j"))), new Field[]{DSL.jsonArray(new Field[0])})).from("json_object_keys({0}) as j(j)", this.field)));
                break;
            }
            case ORACLE: {
                TopLevelCte.addIfAbsent(ctx, p -> p instanceof OracleJSONKeys, () -> new OracleJSONKeys());
                ctx.visit(DSL.function(Names.N_JSON_KEYS, this.getDataType(), this.field));
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(DSL.function(Names.N_OBJECT_KEYS, this.getDataType(), this.field));
                break;
            }
            case SQLITE: {
                ctx.visit(DSL.field(DSL.select(DSL.jsonArrayAgg(DSL.field(DSL.name("key")))).from("json_each({0})", this.field)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_JSON_KEYS, this.getDataType(), this.field));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonKeys(");
        ctx.visit(Val.getJavaValue(this.field));
        ctx.sql(')');
    }

    @Override
    public final Field<JSON> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.JSONKeys $arg1(Field<JSON> newValue) {
        return this.$constructor().apply((Field<JSON>)newValue);
    }

    @Override
    public final Function1<? super Field<JSON>, ? extends QOM.JSONKeys> $constructor() {
        return a1 -> new JSONKeys((Field<JSON>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONKeys) {
            QOM.JSONKeys o = (QOM.JSONKeys)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

