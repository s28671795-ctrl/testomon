/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.JSONValueDefaultStep;
import org.jooq.JSONValueOnStep;
import org.jooq.Keyword;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.JSONReturning;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class JSONValue<J>
extends AbstractField<J>
implements JSONValueOnStep<J>,
JSONValueDefaultStep<J>,
QOM.UNotYetImplemented {
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_PATH_BINDS = SQLDialect.supportedBy(SQLDialect.ORACLE);
    private final Field<?> json;
    private final Field<String> path;
    private final DataType<?> returning;
    private final Behaviour onError;
    private final Field<?> onErrorDefault;
    private final Behaviour onEmpty;
    private final Field<?> onEmptyDefault;
    private final Field<?> default_;

    JSONValue(DataType<J> type, Field<?> json, Field<String> path, DataType<?> returning) {
        this(type, json, path, returning, null, null, null, null, null);
    }

    private JSONValue(DataType<J> type, Field<?> json, Field<String> path, DataType<?> returning, Behaviour onError2, Field<?> onErrorDefault, Behaviour onEmpty, Field<?> onEmptyDefault, Field<?> default_) {
        super(Names.N_JSON_VALUE, type);
        this.json = json;
        this.path = path;
        this.returning = returning;
        this.onError = onError2;
        this.onErrorDefault = onErrorDefault;
        this.onEmpty = onEmpty;
        this.onEmptyDefault = onEmptyDefault;
        this.default_ = default_;
    }

    @Override
    public final JSONValue<J> errorOnEmpty() {
        return new JSONValue(this.getDataType(), this.json, this.path, this.returning, this.onError, this.onErrorDefault, Behaviour.ERROR, null, null);
    }

    @Override
    public final JSONValue<J> errorOnError() {
        return new JSONValue(this.getDataType(), this.json, this.path, this.returning, Behaviour.ERROR, null, this.onEmpty, this.onEmptyDefault, null);
    }

    @Override
    public final JSONValue<J> nullOnEmpty() {
        return new JSONValue(this.getDataType(), this.json, this.path, this.returning, this.onError, this.onErrorDefault, Behaviour.NULL, null, null);
    }

    @Override
    public final JSONValue<J> nullOnError() {
        return new JSONValue(this.getDataType(), this.json, this.path, this.returning, Behaviour.NULL, null, this.onEmpty, this.onEmptyDefault, null);
    }

    @Override
    public final JSONValue<J> default_(Field<?> d) {
        return new JSONValue(this.getDataType(), this.json, this.path, this.returning, this.onError, this.onErrorDefault, this.onEmpty, this.onEmptyDefault, d);
    }

    @Override
    public final JSONValue<J> onEmpty() {
        return new JSONValue(this.getDataType(), this.json, this.path, this.returning, this.onError, this.onErrorDefault, Behaviour.DEFAULT, this.default_, null);
    }

    @Override
    public final JSONValue<J> onError() {
        return new JSONValue(this.getDataType(), this.json, this.path, this.returning, Behaviour.DEFAULT, this.default_, this.onEmpty, this.onEmptyDefault, null);
    }

    @Override
    public final JSONValue<J> returning(DataType<?> r) {
        return new JSONValue(this.getDataType(), this.json, this.path, r, this.onError, this.onErrorDefault, this.onEmpty, this.onEmptyDefault, null);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MYSQL: 
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_JSON_EXTRACT, this.json.getDataType(), this.json, this.path));
                break;
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(DSL.function(Names.N_JSONB_PATH_QUERY_FIRST, this.json.getDataType(), Tools.castIfNeeded(this.json, SQLDataType.JSONB), DSL.field("cast({0} as jsonpath)", this.path)));
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                if (this.json instanceof TableField && this.json.getDataType().length() <= 4000) {
                    this.acceptDefault(ctx);
                    break;
                }
                Field<String> field2 = this.path;
                if (field2 instanceof Param) {
                    Param p = (Param)field2;
                    Field<String> c = DSL.field(DSL.name("c"), SQLDataType.VARCHAR);
                    ctx.visit(DSL.field(DSL.select(c).from((TableLike<?>)DSL.jsonTable(this.json, DSL.inline("$")).column(c).path((String)p.getValue()))));
                    break;
                }
                this.acceptDefault(ctx);
                break;
            }
            default: {
                this.acceptDefault(ctx);
            }
        }
    }

    private final void acceptDefault(Context<?> ctx) {
        JSONReturning r;
        boolean format = !Tools.isSimple(ctx, this.json, this.path);
        ctx.visit(Names.N_JSON_VALUE).sql('(');
        if (format) {
            ctx.sqlIndentStart();
        }
        ctx.visit(this.json).sql(",");
        if (format) {
            ctx.formatSeparator();
        } else {
            ctx.sql(' ');
        }
        if (NO_SUPPORT_PATH_BINDS.contains((Object)ctx.dialect())) {
            ctx.visit(this.path, ParamType.INLINED);
        } else {
            ctx.visit(this.path);
        }
        if (this.onEmpty != null) {
            this.acceptOnClause(ctx, format, this.onEmpty, this.onEmptyDefault, Keywords.K_EMPTY);
        }
        if (this.onError != null) {
            this.acceptOnClause(ctx, format, this.onError, this.onErrorDefault, Keywords.K_ERROR);
        }
        if (this.returning != null && (r = new JSONReturning(this.returning)).rendersContent(ctx)) {
            if (format) {
                ctx.formatNewLine();
            }
            ctx.separatorRequired(true).visit(r);
        }
        if (format) {
            ctx.sqlIndentEnd();
        }
        ctx.sql(')');
    }

    private final void acceptOnClause(Context<?> ctx, boolean format, Behaviour behaviour, Field<?> defaultValue, Keyword keyword) {
        if (format) {
            ctx.formatSeparator();
        } else {
            ctx.sql(' ');
        }
        ctx.visit(behaviour.keyword);
        if (behaviour == Behaviour.DEFAULT) {
            if (ctx.family() == SQLDialect.ORACLE) {
                ctx.sql(' ').visit(DSL.inlined(defaultValue));
            } else {
                ctx.sql(' ').visit(defaultValue);
            }
        }
        ctx.sql(' ').visit(Keywords.K_ON).sql(' ').visit(keyword);
    }

    static enum Behaviour {
        ERROR,
        NULL,
        DEFAULT;

        final Keyword keyword = DSL.keyword(this.name().toLowerCase());
    }
}

