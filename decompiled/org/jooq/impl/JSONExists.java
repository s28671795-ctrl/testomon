/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.JSONExistsOnStep;
import org.jooq.Keyword;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Cast;
import org.jooq.impl.DSL;
import org.jooq.impl.JSONValue;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class JSONExists
extends AbstractCondition
implements JSONExistsOnStep,
QOM.UNotYetImplemented {
    private final Field<?> json;
    private final Field<String> path;
    private final Behaviour onError;

    JSONExists(Field<?> json, Field<String> path) {
        this(json, path, null);
    }

    private JSONExists(Field<?> json, Field<String> path, Behaviour onError2) {
        this.json = json;
        this.path = path;
        this.onError = onError2;
    }

    @Override
    public final JSONExists errorOnError() {
        return new JSONExists(this.json, this.path, Behaviour.ERROR);
    }

    @Override
    public final JSONExists trueOnError() {
        return new JSONExists(this.json, this.path, Behaviour.TRUE);
    }

    @Override
    public final JSONExists falseOnError() {
        return new JSONExists(this.json, this.path, Behaviour.FALSE);
    }

    @Override
    public final JSONExists unknownOnError() {
        return new JSONExists(this.json, this.path, Behaviour.UNKNOWN);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MYSQL: {
                ctx.visit(Names.N_JSON_CONTAINS_PATH).sql('(').visit(this.json).sql(", 'one', ").visit(this.path).sql(')');
                break;
            }
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_JSON_TYPE, SQLDataType.JSON, this.json, this.path).isNotNull());
                break;
            }
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(Names.N_JSONB_PATH_EXISTS).sql('(').visit(Tools.castIfNeeded(this.json, SQLDataType.JSONB)).sql(", ");
                Cast.renderCast(ctx, c -> c.visit(this.path), c -> c.visit(Names.N_JSONPATH));
                ctx.sql(')');
                break;
            }
            default: {
                ctx.visit(Keywords.K_JSON_EXISTS).sql('(').visit(this.json).sql(", ");
                if (JSONValue.NO_SUPPORT_PATH_BINDS.contains((Object)ctx.dialect())) {
                    ctx.visit(this.path, ParamType.INLINED);
                } else {
                    ctx.visit(this.path);
                }
                if (this.onError != null) {
                    ctx.sql(' ').visit(this.onError.keyword).sql(' ').visit(Keywords.K_ON).sql(' ').visit(Keywords.K_ERROR);
                }
                ctx.sql(')');
            }
        }
    }

    static enum Behaviour {
        ERROR,
        TRUE,
        FALSE,
        UNKNOWN;

        final Keyword keyword = DSL.keyword(this.name().toLowerCase());
    }
}

