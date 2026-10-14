/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.JSONB;
import org.jooq.Pro;
import org.jooq.SQLDialect;
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

final class JSONBKeys
extends AbstractField<JSONB>
implements QOM.JSONBKeys {
    static final Set<SQLDialect> NO_SUPPORT_PATH_QUERY = SQLDialect.supportedUntil(SQLDialect.COCKROACHDB, SQLDialect.POSTGRES_11);
    final Field<JSONB> field;

    JSONBKeys(Field<JSONB> field2) {
        super(Names.N_JSONB_KEYS, Tools.allNotNull(SQLDataType.JSONB, field2));
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.JSONB);
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
        return false;
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
                if (NO_SUPPORT_PATH_QUERY.contains((Object)ctx.dialect())) {
                    ctx.visit(DSL.field(DSL.select(DSL.coalesce(DSL.jsonArrayAgg(DSL.field(DSL.unquotedName("j"))), new Field[]{DSL.jsonArray(new Field[0])})).from("json_object_keys({0}) as j(j)", this.field)));
                    break;
                }
                ctx.visit(DSL.function(Names.N_JSONB_PATH_QUERY_ARRAY, this.getDataType(), this.field, DSL.inline("$.keyvalue().key")));
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
                ctx.visit(DSL.field(DSL.select(DSL.jsonbArrayAgg(DSL.field(DSL.name("key")))).from("json_each({0})", this.field)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_JSON_KEYS, SQLDataType.JSONB, this.field));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonbKeys(");
        ctx.visit(Val.getJavaValue(this.field));
        ctx.sql(')');
    }

    @Override
    public final Field<JSONB> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.JSONBKeys $arg1(Field<JSONB> newValue) {
        return this.$constructor().apply((Field<JSONB>)newValue);
    }

    @Override
    public final Function1<? super Field<JSONB>, ? extends QOM.JSONBKeys> $constructor() {
        return a1 -> new JSONBKeys((Field<JSONB>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONBKeys) {
            QOM.JSONBKeys o = (QOM.JSONBKeys)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

