/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function4;
import org.jooq.JSONArrayNullStep;
import org.jooq.JSONArrayReturningStep;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Row1;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;
import org.jooq.impl.JSONEntryImpl;
import org.jooq.impl.JSONNull;
import org.jooq.impl.JSONReturning;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class JSONArray<T>
extends AbstractField<T>
implements QOM.JSONArray<T>,
JSONArrayNullStep<T>,
JSONArrayReturningStep<T> {
    final DataType<T> type;
    final QueryPartListView<? extends Field<?>> fields;
    QOM.JSONOnNull onNull;
    DataType<?> returning;

    JSONArray(DataType<T> type, Collection<? extends Field<?>> fields) {
        this(type, fields, null, null);
    }

    JSONArray(DataType<T> type, Collection<? extends Field<?>> fields, QOM.JSONOnNull onNull, DataType<?> returning) {
        super(Names.N_JSON_ARRAY, type);
        this.type = type;
        this.fields = new QueryPartList((Iterable<Field<?>>)fields);
        this.onNull = onNull;
        this.returning = returning;
    }

    @Override
    public final JSONArray<T> nullOnNull() {
        this.onNull = QOM.JSONOnNull.NULL_ON_NULL;
        return this;
    }

    @Override
    public final JSONArray<T> absentOnNull() {
        this.onNull = QOM.JSONOnNull.ABSENT_ON_NULL;
        return this;
    }

    @Override
    public final JSONArray<T> returning(DataType<?> returning) {
        this.returning = returning;
        return this;
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public void accept(Context<?> ctx) {
        QueryPartCollectionView<Field<?>> mapped = QueryPartCollectionView.wrap(this.fields).map(JSONEntryImpl.jsonCastMapper(ctx));
        block0 : switch (ctx.family()) {
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL && !mapped.isEmpty()) {
                    Row1[] rows = Tools.map(this.fields, f -> DSL.row(f), Row1[]::new);
                    Table t2 = DSL.values(rows).as("t", "a");
                    Field<?> a = t2.field("a");
                    ctx.visit(DSL.field(DSL.select(this.getDataType() == SQLDataType.JSON ? DSL.jsonArrayAgg(a) : DSL.jsonbArrayAgg(a)).from((TableLike<?>)t2).where(a.isNotNull())));
                    break;
                }
                ctx.visit(this.getDataType() == SQLDataType.JSON ? Names.N_JSON_BUILD_ARRAY : Names.N_JSONB_BUILD_ARRAY).sql('(').visit(mapped).sql(')');
                break;
            }
            case JAVA: {
                ctx.sql("jsonArray(").visit(QueryPartListView.wrap(this.fields)).sql(')').visit(new JSONNull(this.onNull)).visit(new JSONReturning(this.returning));
                break;
            }
            case SQLSERVER: {
                if (mapped.isEmpty()) {
                    ctx.visit(DSL.function(Names.N_JSON_QUERY, this.getDataType(), DSL.inline("[]")));
                    break;
                }
                Field<String> f2 = DSL.inline("[]");
                for (Field<?> e : mapped) {
                    f2 = DSL.function(Names.N_JSON_MODIFY, this.getDataType(), f2, DSL.inline("append $"), JSONEntryImpl.unescapeNestedJSON(ctx, JSONEntryImpl.jsonCast(ctx, e)));
                }
                ctx.visit(f2);
                break;
            }
            case SNOWFLAKE: {
                if (this.onNull == QOM.JSONOnNull.ABSENT_ON_NULL) {
                    ctx.visit(Names.N_ARRAY_CONSTRUCT_COMPACT).sql('(').visit(mapped).sql(')');
                    break;
                }
                ctx.visit(Names.N_ARRAY_CONSTRUCT).sql('(').visit(mapped.map(JSONEntryImpl::coalesceToJSONNull)).sql(')');
                break;
            }
            default: {
                JSONNull jsonNull = ctx.family() == SQLDialect.H2 && this.fields.isEmpty() ? new JSONNull(QOM.JSONOnNull.NULL_ON_NULL) : new JSONNull(this.onNull);
                JSONReturning jsonReturning = this.returning == null && ctx.family() == SQLDialect.DB2 && this.getDataType() == SQLDataType.JSONB ? new JSONReturning(SQLDataType.JSONB) : new JSONReturning(this.returning);
                CustomField jsonArray = CustomField.of(Names.N_JSON_ARRAY, this.getDataType(), c -> c.visit(Names.N_JSON_ARRAY).sql('(').visit(QueryPartListView.wrap((QueryPart[])new AbstractQueryPart[]{mapped, jsonNull, jsonReturning}).separator("")).sql(')'));
                switch (ctx.family()) {
                    case DB2: {
                        if (Tools.anyMatch(this.fields, f -> !(f instanceof Param))) {
                            ctx.visit(DSL.field(DSL.select(jsonArray)));
                            break block0;
                        }
                        ctx.visit(jsonArray);
                        break block0;
                    }
                }
                ctx.visit(jsonArray);
                break;
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("jsonArray(");
        String s2 = "";
        ctx.sql(s2).visit(this.type);
        s2 = ", ";
        ctx.sql(s2).visit(QueryPartCollectionView.wrap(this.fields));
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
    public final QOM.UnmodifiableList<? extends Field<?>> $arg2() {
        return QOM.unmodifiable(this.fields);
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
    public final QOM.JSONArray<T> $arg1(DataType<T> newValue) {
        return this.$constructor().apply(newValue, (Collection<Field<?>>)this.$arg2(), this.$arg3(), (DataType<?>)this.$arg4());
    }

    @Override
    public final QOM.JSONArray<T> $arg2(QOM.UnmodifiableList<? extends Field<?>> newValue) {
        return this.$constructor().apply((DataType<T>)this.$arg1(), (Collection<Field<?>>)newValue, this.$arg3(), (DataType<?>)this.$arg4());
    }

    @Override
    public final QOM.JSONArray<T> $arg3(QOM.JSONOnNull newValue) {
        return this.$constructor().apply((DataType<T>)this.$arg1(), (Collection<Field<?>>)this.$arg2(), newValue, (DataType<?>)this.$arg4());
    }

    @Override
    public final QOM.JSONArray<T> $arg4(DataType<?> newValue) {
        return this.$constructor().apply((DataType<T>)this.$arg1(), (Collection<Field<?>>)this.$arg2(), this.$arg3(), newValue);
    }

    @Override
    public final Function4<? super DataType<T>, ? super Collection<? extends Field<?>>, ? super QOM.JSONOnNull, ? super DataType<?>, ? extends QOM.JSONArray<T>> $constructor() {
        return (a1, a2, a3, a4) -> new JSONArray(a1, (Collection<? extends Field<?>>)((Collection<Field<?>>)a2), (QOM.JSONOnNull)((Object)a3), (DataType<?>)a4);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.JSONArray) {
            QOM.JSONArray o = (QOM.JSONArray)that;
            return StringUtils.equals(this.$type(), o.$type()) && StringUtils.equals(this.$fields(), o.$fields()) && StringUtils.equals((Object)this.$onNull(), (Object)o.$onNull()) && StringUtils.equals(this.$returning(), o.$returning());
        }
        return super.equals(that);
    }
}

