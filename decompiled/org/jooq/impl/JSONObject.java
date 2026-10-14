/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function4;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.JSONEntry;
import org.jooq.JSONObjectNullStep;
import org.jooq.JSONObjectReturningStep;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.SelectForJSONCommonDirectivesStep;
import org.jooq.TableLike;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.JSONArray;
import org.jooq.impl.JSONArrayAgg;
import org.jooq.impl.JSONEntryImpl;
import org.jooq.impl.JSONNull;
import org.jooq.impl.JSONReturning;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class JSONObject<T>
extends AbstractField<T>
implements QOM.JSONObject<T>,
JSONObjectNullStep<T>,
JSONObjectReturningStep<T> {
    final DataType<T> type;
    final QueryPartListView<? extends JSONEntry<?>> entries;
    QOM.JSONOnNull onNull;
    DataType<?> returning;

    JSONObject(DataType<T> type, Collection<? extends JSONEntry<?>> entries) {
        this(type, entries, null, null);
    }

    JSONObject(DataType<T> type, Collection<? extends JSONEntry<?>> entries, QOM.JSONOnNull onNull, DataType<?> returning) {
        super(Names.N_JSON_OBJECT, type);
        this.type = type;
        this.entries = new QueryPartList((Iterable<JSONEntry<?>>)entries);
        this.onNull = onNull;
        this.returning = returning;
    }

    @Override
    public final JSONObject<T> nullOnNull() {
        this.onNull = QOM.JSONOnNull.NULL_ON_NULL;
        return this;
    }

    @Override
    public final JSONObject<T> absentOnNull() {
        this.onNull = QOM.JSONOnNull.ABSENT_ON_NULL;
        return this;
    }

    @Override
    public final JSONObject<T> returning(DataType<?> returning) {
        this.returning = returning;
        return this;
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
                    ctx.visit(DSL.unquotedName(this.getDataType().getType() == JSONB.class ? "jsonb_strip_nulls" : "json_strip_nulls")).sql('(');
                }
                ctx.visit(DSL.unquotedName(this.getDataType().getType() == JSONB.class ? "jsonb_build_object" : "json_build_object")).sql('(').visit(QueryPartCollectionView.wrap(this.entries)).sql(')');
                if (this.onNull != QOM.JSONOnNull.ABSENT_ON_NULL) break;
                ctx.sql(')');
                break;
            }
            case JAVA: {
                ctx.sql("jsonObject(").visit(QueryPartListView.wrap(this.entries)).sql(')').visit(new JSONNull(this.onNull)).visit(new JSONReturning(this.returning));
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                if (this.entries.isEmpty()) {
                    ctx.visit(DSL.function(Names.N_JSON_QUERY, this.getDataType(), DSL.inline("{}")));
                    break;
                }
                SelectForJSONCommonDirectivesStep<Record1<JSON>> directives = DSL.select(DSL.asterisk()).from((TableLike<?>)DSL.values(DSL.row(Tools.map(this.entries, e -> JSONEntryImpl.unescapeNestedJSON(ctx, JSONEntryImpl.jsonCast(ctx, e.value())), Field[]::new))).as(Names.N_T, Tools.map(this.entries, e -> DSL.name(e.key().getName()), Name[]::new))).forJSON().path().withoutArrayWrapper();
                ctx.sqlIndentStart('(').visit(this.onNull != QOM.JSONOnNull.ABSENT_ON_NULL ? directives.includeNullValues() : directives).sqlIndentEnd(')');
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL ? Names.N_OBJECT_CONSTRUCT : Names.N_OBJECT_CONSTRUCT_KEEP_NULL).sql('(').visit(QueryPartCollectionView.wrap(this.entries)).sql(')');
                break;
            }
            case MARIADB: {
                JSONEntry first;
                if (this.entries.size() > 1) {
                    ctx.visit(JSONEntryImpl.jsonMerge(ctx, "{}", Tools.map(this.entries, e -> DSL.jsonObject(e), Field[]::new)));
                    break;
                }
                if (!this.entries.isEmpty() && JSONObject.isJSONArray((first = (JSONEntry)this.entries.iterator().next()).value())) {
                    ctx.visit(DSL.jsonObject(DSL.key(first.key()).value(JSONEntryImpl.jsonMerge(ctx, "[]", first.value()))));
                    break;
                }
                this.acceptStandard(ctx);
                break;
            }
            case MYSQL: {
                if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
                    Field<String> k = DSL.field(DSL.name("jt", "k"), SQLDataType.VARCHAR);
                    Field<JSON> o = DSL.field(DSL.name("j", "o"), SQLDataType.JSON);
                    ctx.visit(DSL.field(DSL.select(DSL.coalesce(DSL.jsonObjectAgg(k, DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSON, o, DSL.concat(DSL.inline("$.\""), k, DSL.inline("\"")))), new Field[]{DSL.jsonObject()})).from(DSL.select(CustomField.of("o", SQLDataType.JSON, c -> this.acceptStandard((Context<?>)c)).as((Field)o)).asTable("j"), DSL.jsonTable(DSL.function(Names.N_JSON_KEYS, SQLDataType.JSON, o), DSL.inline("$[*]")).column("k", SQLDataType.VARCHAR).path("$").asTable("jt")).where(DSL.function(Names.N_JSON_EXTRACT, SQLDataType.JSON, o, DSL.concat(DSL.inline("$.\""), k, DSL.inline("\""))).ne(DSL.inline("null").cast(SQLDataType.JSON)))));
                    break;
                }
                this.acceptStandard(ctx);
                break;
            }
            default: {
                this.acceptStandard(ctx);
            }
        }
    }

    private static final boolean isJSONArray(Field<?> field2) {
        return field2 instanceof JSONArray || field2 instanceof JSONArrayAgg || field2 instanceof ScalarSubquery && JSONObject.isJSONArray(((ScalarSubquery)field2).query.getSelect().get(0));
    }

    private final void acceptStandard(Context<?> ctx) {
        JSONNull jsonNull = this.entries.isEmpty() && ctx.family() == SQLDialect.H2 ? new JSONNull(QOM.JSONOnNull.NULL_ON_NULL) : (this.entries.isEmpty() && ctx.family() == SQLDialect.ORACLE ? new JSONNull(null) : new JSONNull(this.onNull));
        JSONReturning jsonReturning = this.returning == null && ctx.family() == SQLDialect.DB2 && this.getDataType() == SQLDataType.JSONB ? new JSONReturning(SQLDataType.JSONB) : new JSONReturning(this.returning);
        ctx.visit(Names.N_JSON_OBJECT).sql('(').visit(QueryPartListView.wrap((QueryPart[])new AbstractQueryPart[]{QueryPartCollectionView.wrap(this.entries), jsonNull, jsonReturning}).separator("")).sql(')');
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonObject(");
        String s2 = "";
        ctx.sql(s2).visit(this.type);
        s2 = ", ";
        ctx.sql(s2).visit(QueryPartCollectionView.wrap(this.entries));
        s2 = ", ";
        ctx.sql(s2).sql("JSONOnNull.").sql(this.onNull.name());
        s2 = ", ";
        ctx.sql(s2).visit(this.returning);
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final DataType<T> $arg1() {
        return this.type;
    }

    @Override
    public final QOM.UnmodifiableList<? extends JSONEntry<?>> $arg2() {
        return QOM.unmodifiable(this.entries);
    }

    @Override
    public final QOM.JSONOnNull $arg3() {
        return this.onNull;
    }

    @Override
    public final DataType<?> $arg4() {
        return this.returning;
    }

    @Override
    public final QOM.JSONObject<T> $arg1(DataType<T> newValue) {
        return this.$constructor().apply(newValue, (Collection<JSONEntry<?>>)this.$arg2(), this.$arg3(), (DataType<?>)this.$arg4());
    }

    @Override
    public final QOM.JSONObject<T> $arg2(QOM.UnmodifiableList<? extends JSONEntry<?>> newValue) {
        return this.$constructor().apply((DataType<T>)this.$arg1(), (Collection<JSONEntry<?>>)newValue, this.$arg3(), (DataType<?>)this.$arg4());
    }

    @Override
    public final QOM.JSONObject<T> $arg3(QOM.JSONOnNull newValue) {
        return this.$constructor().apply((DataType<T>)this.$arg1(), (Collection<JSONEntry<?>>)this.$arg2(), newValue, (DataType<?>)this.$arg4());
    }

    @Override
    public final QOM.JSONObject<T> $arg4(DataType<?> newValue) {
        return this.$constructor().apply((DataType<T>)this.$arg1(), (Collection<JSONEntry<?>>)this.$arg2(), this.$arg3(), newValue);
    }

    @Override
    public final Function4<? super DataType<T>, ? super Collection<? extends JSONEntry<?>>, ? super QOM.JSONOnNull, ? super DataType<?>, ? extends QOM.JSONObject<T>> $constructor() {
        return (a1, a2, a3, a4) -> new JSONObject(a1, (Collection<? extends JSONEntry<?>>)((Collection<JSONEntry<?>>)a2), (QOM.JSONOnNull)((Object)a3), (DataType<?>)a4);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONObject) {
            QOM.JSONObject o = (QOM.JSONObject)that;
            return StringUtils.equals(this.$type(), o.$type()) && StringUtils.equals(this.$entries(), o.$entries()) && StringUtils.equals((Object)this.$onNull(), (Object)o.$onNull()) && StringUtils.equals(this.$returning(), o.$returning());
        }
        return super.equals(that);
    }
}

