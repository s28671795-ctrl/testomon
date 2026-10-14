/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.JSONEntry;
import org.jooq.JSONEntryValueStep;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.Traverser;
import org.jooq.conf.NestedCollectionEmulation;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractRowAsField;
import org.jooq.impl.ConvertedDataType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;

final class JSONEntryImpl<T>
extends AbstractQueryPart
implements JSONEntry<T>,
JSONEntryValueStep {
    static final Set<SQLDialect> SUPPORT_JSON_MERGE_PRESERVE = SQLDialect.supportedBy(SQLDialect.MARIADB_10_4, SQLDialect.MYSQL_8_0_19);
    private final Field<String> key;
    private final Field<T> value;

    JSONEntryImpl(Field<String> key) {
        this(key, null);
    }

    JSONEntryImpl(Field<String> key, Field<T> value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public final Field<String> key() {
        return this.key;
    }

    @Override
    public final Field<T> value() {
        return this.value;
    }

    public final <X> JSONEntry<X> value(X newValue) {
        return this.value(Tools.field(newValue));
    }

    public final <X> JSONEntry<X> value(Field<X> newValue) {
        return new JSONEntryImpl<X>(this.key, newValue);
    }

    public final <X> JSONEntry<X> value(Select<? extends Record1<X>> newValue) {
        return this.value(DSL.field(newValue));
    }

    @Override
    public void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("key(").visit(this.key).sql(").value(").visit(this.value).sql(')');
                break;
            }
            case DB2: {
                ctx.visit(Keywords.K_KEY).sql(' ').visit(this.key).sql(' ').visit(Keywords.K_VALUE).sql(' ');
                ctx.visit(JSONEntryImpl.jsonCast(ctx, this.value.getDataType().isMultiset() ? DSL.coalesce(DSL.inline(null, SQLDataType.JSON), new Field[]{this.value}) : (Tools.isScalarSubquery(this.value) ? DSL.coalesce(DSL.inline(null, this.value.getDataType()), new Field[]{this.value}) : this.value)));
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case SNOWFLAKE: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                ctx.visit(this.key).sql(", ").visit(JSONEntryImpl.jsonCast(ctx, this.value));
                break;
            }
            default: {
                ctx.visit(Keywords.K_KEY).sql(' ').visit(this.key).sql(' ').visit(Keywords.K_VALUE).sql(' ').visit(JSONEntryImpl.jsonCast(ctx, this.value));
            }
        }
    }

    static final Function<? super Field<?>, ? extends Field<?>> jsonCastMapper(Context<?> ctx) {
        return field2 -> JSONEntryImpl.jsonCast(ctx, field2);
    }

    static final Field<?> jsonCast(Context<?> ctx, Field<?> field2) {
        return JSONEntryImpl.jsonCast(ctx, field2, false);
    }

    static final Field<?> jsonCast(Context<?> ctx, Field<?> field2, boolean castJSONTypes) {
        DataType type = field2.getDataType();
        switch (ctx.family()) {
            case H2: {
                if (JSONEntryImpl.isType(type, UUID.class)) {
                    return field2.cast(SQLDataType.VARCHAR(36));
                }
                if (type.isEnum()) {
                    return field2.cast(SQLDataType.VARCHAR);
                }
                if (type.isTemporal()) {
                    return field2.cast(SQLDataType.VARCHAR);
                }
                if (!type.isBinary()) break;
                return DSL.function(Names.N_RAWTOHEX, SQLDataType.VARCHAR, field2);
            }
            case MARIADB: 
            case MYSQL: {
                if (type.getSQLDataType() == SQLDataType.BIT) {
                    return field2.cast(SQLDataType.BOOLEAN);
                }
                if (JSONEntryImpl.isType(type, Boolean.class)) {
                    return DSL.inlined(field2);
                }
                if (!castJSONTypes || !type.isJSON()) break;
                if (ctx.family() == SQLDialect.MYSQL) {
                    return field2.cast(field2.getDataType());
                }
                return DSL.function(Names.N_JSON_EXTRACT, field2.getDataType(), field2, DSL.inline("$"));
            }
            case SQLITE: {
                if (JSONEntryImpl.isType(type, Boolean.class)) {
                    return DSL.function(Names.N_JSON, SQLDataType.JSON, JSONEntryImpl.booleanCase(field2));
                }
                if (type.isBinary()) {
                    return DSL.when(field2.isNotNull(), DSL.function(Names.N_HEX, SQLDataType.VARCHAR, field2));
                }
                if (!castJSONTypes || !type.isJSON()) break;
                return DSL.function(Names.N_JSON, SQLDataType.JSON, field2);
            }
            case SQLSERVER: {
                if (JSONEntryImpl.isType(type, Boolean.class)) {
                    return field2.cast(SQLDataType.BIT);
                }
                if (!JSONEntryImpl.isType(type, UUID.class)) break;
                return field2.cast(SQLDataType.VARCHAR);
            }
            case DB2: 
            case ORACLE: {
                if (JSONEntryImpl.isType(type, Boolean.class)) {
                    return DSL.field("{0} {1} {2}", JSONEntryImpl.booleanValAsVarchar(field2), Keywords.K_FORMAT, Keywords.K_JSON);
                }
                if (JSONEntryImpl.isJSON(ctx, type)) {
                    return DSL.field("{0} {1} {2}", field2, Keywords.K_FORMAT, Keywords.K_JSON);
                }
                if (type.isTimestamp() && ctx.family() == SQLDialect.DB2) {
                    return DSL.replace(DSL.toChar(field2, DSL.inline("YYYY-MM-DD HH24:MI:SS.FF")), DSL.inline(" "), DSL.inline("T"));
                }
                if (type.isBinary() && ctx.family() == SQLDialect.DB2) {
                    return DSL.field("xmlcast(xmlquery('$e' passing {0} as \"e\") as clob)", SQLDataType.VARCHAR, new QueryPart[]{field2});
                }
                if (field2.getType() == Object.class) {
                    return field2.cast(SQLDataType.VARCHAR);
                }
                if (!(field2 instanceof Param) || ctx.family() != SQLDialect.ORACLE) break;
                return DSL.nvl(DSL.inline(null, field2), field2);
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (field2 instanceof Param) {
                    if (field2.getType() != Object.class) {
                        return field2.cast(field2.getDataType());
                    }
                    return field2.cast(SQLDataType.VARCHAR);
                }
                return field2;
            }
        }
        return field2;
    }

    @Pro
    static Field<JSON> coalesceToJSONNull(Field<?> field2) {
        if (field2.getDataType().isJSON() && field2 instanceof Param) {
            return field2;
        }
        if (!((AbstractField)field2).isNullable()) {
            return field2;
        }
        return DSL.coalesce(DSL.function(Names.N_TO_VARIANT, SQLDataType.JSON, field2), DSL.function(Names.N_PARSE_JSON, SQLDataType.JSON, DSL.inline("null")));
    }

    private static Field<String> booleanCase(Field<?> field2) {
        return DSL.case_(field2).when((Field<?>)DSL.inline(true), DSL.inline("true")).when(DSL.inline(false), DSL.inline("false"));
    }

    static final <T> Field<T> unescapeNestedJSON(Context<?> ctx, Field<T> value) {
        if (JSONEntryImpl.isJSON(ctx, value.getDataType())) {
            switch (ctx.family()) {
                case SQLSERVER: 
                case SQLDATAWAREHOUSE: {
                    return DSL.function(Names.N_JSON_QUERY, value.getDataType(), value);
                }
                case MARIADB: 
                case MYSQL: 
                case SQLITE: 
                case AURORA_MYSQL: {
                    return DSL.function(Names.N_JSON_EXTRACT, value.getDataType(), value, DSL.inline("$"));
                }
            }
        }
        return value;
    }

    static final boolean isType(DataType<?> t2, Class<?> type) {
        return ConvertedDataType.delegate(t2).getType() == type;
    }

    static final boolean isJSON(Context<?> ctx, DataType<?> type) {
        DataType<?> t2 = ConvertedDataType.delegate(type);
        return t2.isJSON() || t2.isEmbeddable() && AbstractRowAsField.forceMultisetContent(ctx, () -> t2.getRow().size() > 1) && JSONEntryImpl.emulateMultisetWithJSON(ctx) || t2.isRecord() && AbstractRowAsField.forceMultisetContent(ctx, () -> t2.getRow().size() > 1) && JSONEntryImpl.emulateMultisetWithJSON(ctx) || t2.isMultiset() && JSONEntryImpl.emulateMultisetWithJSON(ctx);
    }

    private static final boolean emulateMultisetWithJSON(Scope scope) {
        return Tools.emulateMultiset(scope.configuration()) == NestedCollectionEmulation.JSON || Tools.emulateMultiset(scope.configuration()) == NestedCollectionEmulation.JSONB;
    }

    static final Field<?> booleanValAsVarchar(Field<?> field2) {
        Field<String> field3;
        if (field2 instanceof Val) {
            Val v = (Val)field2;
            field3 = v.convertTo0(SQLDataType.VARCHAR);
        } else {
            field3 = JSONEntryImpl.booleanCase(field2);
        }
        return field3;
    }

    static final Field<?> jsonMerge(Scope scope, String empty, Field<?> ... fields) {
        return DSL.function(SUPPORT_JSON_MERGE_PRESERVE.contains((Object)scope.dialect()) ? Names.N_JSON_MERGE_PRESERVE : Names.N_JSON_MERGE, fields[0].getDataType(), Tools.combine(DSL.inline(empty), fields));
    }

    @Override
    public final Field<String> $key() {
        return this.key;
    }

    @Override
    public final Field<?> $value() {
        return this.value;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.key, this.value);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.key, this.value, JSONEntryImpl::new, replacer);
    }
}

