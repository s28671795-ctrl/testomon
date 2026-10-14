/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.jooq.AggregateFilterStep;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.JSON;
import org.jooq.JSONArrayAggNullStep;
import org.jooq.JSONArrayAggOrderByStep;
import org.jooq.JSONArrayAggReturningStep;
import org.jooq.JSONArrayNullStep;
import org.jooq.JSONArrayReturningStep;
import org.jooq.JSONB;
import org.jooq.JSONEntry;
import org.jooq.JSONObjectNullStep;
import org.jooq.JSONObjectReturningStep;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Replacer;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.XML;
import org.jooq.XMLAggOrderByStep;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.DSL;
import org.jooq.impl.DerivedTable;
import org.jooq.impl.JSONArrayAgg;
import org.jooq.impl.JSONEntryImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.MultisetDataType;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Multiset<R extends Record>
extends AbstractField<Result<R>>
implements QOM.Multiset<R> {
    static final Set<SQLDialect> NO_SUPPORT_JSON_COMPARE = SQLDialect.supportedBy(SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> NO_SUPPORT_JSONB_COMPARE = SQLDialect.supportedBy(SQLDialect.ORACLE);
    static final Set<SQLDialect> NO_SUPPORT_XML_COMPARE = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.POSTGRES);
    static final Set<SQLDialect> FORCE_LIMIT_IN_DERIVED_TABLE = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE);
    final TableLike<R> table;
    final Select<R> select;

    Multiset(TableLike<R> table) {
        Select<R> select;
        if (table instanceof Select) {
            Select s2 = (Select)table;
            select = s2;
        } else {
            select = DSL.selectFrom(table);
        }
        this(table, select);
    }

    private Multiset(TableLike<R> table, Select<R> select) {
        super(Names.N_MULTISET, new MultisetDataType((AbstractRow)((Object)DSL.row(select.getSelect())), select.getRecordType()));
        this.table = table;
        this.select = select;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("multiset(").sqlIndentStart().visit(this.table).sqlIndentEnd().sql(')');
        } else if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONDITION))) {
            ctx.data().remove(Tools.BooleanDataKey.DATA_MULTISET_CONDITION);
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT, true, c -> this.accept0((Context<?>)c, true));
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONDITION, true);
        } else {
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT, true, c -> this.accept0((Context<?>)c, false));
        }
    }

    private final void accept0(Context<?> ctx, boolean multisetCondition) {
        block0 : switch (Tools.emulateMultiset(ctx.configuration())) {
            case JSON: {
                JSONArrayAggOrderByStep<JSON> order;
                List<Field<?>> fields = this.select.getSelect();
                Table<R> t2 = new AliasedSelect<R>(this.select, true, false, FORCE_LIMIT_IN_DERIVED_TABLE.contains((Object)ctx.dialect()), Tools.fieldNames(fields.size())).as(DSL.name("t"), (Name[])null);
                switch (ctx.family()) {
                    case SQLSERVER: 
                    case SQLDATAWAREHOUSE: {
                        Tools.visitSubquery(ctx, DSL.function(Names.N_JSON_QUERY, SQLDataType.JSON, DSL.coalesce(DSL.field(DSL.select(Tools.map(fields, (f, i) -> Multiset.castForJSON(ctx, DSL.field(Tools.fieldName(i), f.getDataType())).as(Tools.fieldName(i)))).from((TableLike<?>)t2).forJSON().auto().includeNullValues()), new Field[]{DSL.inline("[]")})));
                        break block0;
                    }
                }
                if (DerivedTable.NO_SUPPORT_CORRELATED_DERIVED_TABLE.contains((Object)ctx.dialect()) && Multiset.isSimple(this.select)) {
                    JSONArrayAggNullStep<JSON> returning = Multiset.jsonArrayaggEmulation(ctx, DSL.row(Tools.map(this.select.getSelect(), f -> Tools.unalias(f))), true, this.select.$distinct()).orderBy(this.select.$orderBy());
                    Select<?> s2 = this.select.$select(Arrays.asList(DSL.coalesce(Multiset.returningClob(ctx, returning), Multiset.returningClob(ctx, DSL.jsonArray(new Field[0]))))).$distinct(false).$orderBy(Arrays.asList(new SortField[0]));
                    Tools.visitSubquery(ctx, s2);
                    break;
                }
                JSONArrayAggNullStep<JSON> returning = order = Multiset.jsonArrayaggEmulation(ctx, t2, true, false);
                if (multisetCondition) {
                    returning = order.orderBy(t2.fields());
                }
                Select s3 = JSONArrayAgg.patchOracleArrayAggBug(ctx, DSL.select(DSL.coalesce(Multiset.returningClob(ctx, returning), Multiset.returningClob(ctx, DSL.jsonArray(new Field[0])))).from((TableLike<?>)t2));
                if (multisetCondition && NO_SUPPORT_JSON_COMPARE.contains((Object)ctx.dialect())) {
                    ctx.visit(DSL.field(s3).cast(SQLDataType.VARCHAR));
                    break;
                }
                Tools.visitSubquery(ctx, s3);
                break;
            }
            case JSONB: {
                JSONArrayAggOrderByStep<JSONB> order;
                List<Field<?>> fields = this.select.getSelect();
                Table<R> t3 = new AliasedSelect<R>(this.select, true, false, FORCE_LIMIT_IN_DERIVED_TABLE.contains((Object)ctx.dialect()), Tools.fieldNames(fields.size())).as(DSL.name("t"), (Name[])null);
                switch (ctx.family()) {
                    case SQLSERVER: 
                    case SQLDATAWAREHOUSE: {
                        Tools.visitSubquery(ctx, DSL.function(Names.N_JSON_QUERY, SQLDataType.JSONB, DSL.coalesce(DSL.field(DSL.select(Tools.map(fields, (f, i) -> Multiset.castForJSON(ctx, DSL.field(Tools.fieldName(i), f.getDataType())).as(Tools.fieldName(i)))).from((TableLike<?>)t3).forJSONB().auto().includeNullValues()), new Field[]{DSL.inline("[]")})));
                        break block0;
                    }
                }
                if (DerivedTable.NO_SUPPORT_CORRELATED_DERIVED_TABLE.contains((Object)ctx.dialect()) && Multiset.isSimple(this.select)) {
                    JSONArrayAggNullStep<JSONB> returning = Multiset.jsonbArrayaggEmulation(ctx, DSL.row(Tools.map(this.select.getSelect(), f -> Tools.unalias(f))), true, this.select.$distinct()).orderBy(this.select.$orderBy());
                    Select<?> s4 = this.select.$select(Arrays.asList(DSL.coalesce(Multiset.returningClob(ctx, returning), Multiset.returningClob(ctx, DSL.jsonbArray(new Field[0]))))).$distinct(false).$orderBy(Arrays.asList(new SortField[0]));
                    Tools.visitSubquery(ctx, s4);
                    break;
                }
                JSONArrayAggNullStep<JSONB> returning = order = Multiset.jsonbArrayaggEmulation(ctx, t3, false, false);
                if (multisetCondition) {
                    returning = order.orderBy(t3.fields());
                }
                Select s5 = JSONArrayAgg.patchOracleArrayAggBug(ctx, DSL.select(DSL.coalesce(Multiset.returningClob(ctx, returning), Multiset.returningClob(ctx, DSL.jsonbArray(new Field[0])))).from((TableLike<?>)t3));
                if (multisetCondition && NO_SUPPORT_JSONB_COMPARE.contains((Object)ctx.dialect())) {
                    ctx.visit(DSL.field(s5).cast(SQLDataType.VARCHAR));
                    break;
                }
                Tools.visitSubquery(ctx, s5);
                break;
            }
            case XML: {
                XMLAggOrderByStep<XML> order;
                List<Field<?>> fields = this.select.getSelect();
                Table<R> t4 = new AliasedSelect<R>(this.select, true, false, FORCE_LIMIT_IN_DERIVED_TABLE.contains((Object)ctx.dialect()), Tools.fieldNames(fields.size())).as(DSL.name("t"), (Name[])null);
                switch (ctx.family()) {
                    case SQLSERVER: 
                    case SQLDATAWAREHOUSE: {
                        Field<XML> xml = DSL.coalesce(DSL.field(DSL.select(Tools.map(fields, (f, i) -> Multiset.castForXML(ctx, DSL.field(Tools.fieldName(i), f.getDataType())).as((Field)f))).from((TableLike<?>)t4).forXML().raw("record").elementsXsinil().type().root("result").binaryBase64()), new Field[]{DSL.inline("<result/>")});
                        ctx.visit(multisetCondition ? xml.cast(SQLDataType.VARCHAR) : xml);
                        break block0;
                    }
                }
                if (DerivedTable.NO_SUPPORT_CORRELATED_DERIVED_TABLE.contains((Object)ctx.dialect()) && Multiset.isSimple(this.select) && !this.select.$distinct()) {
                    Multiset.acceptMultisetSubqueryForXMLEmulation(ctx, multisetCondition, this.select.$select(Arrays.asList(DSL.xmlelement(Multiset.nResult(ctx), Multiset.xmlaggEmulation(ctx, DSL.row(Tools.map(this.select.getSelect(), f -> Tools.unalias(f))), true).orderBy(this.select.$orderBy())))).$orderBy(Arrays.asList(new SortField[0])));
                    break;
                }
                AggregateFilterStep<XML> filter2 = order = Multiset.xmlaggEmulation(ctx, t4, false);
                if (multisetCondition) {
                    filter2 = order.orderBy(t4.fields());
                }
                Multiset.acceptMultisetSubqueryForXMLEmulation(ctx, multisetCondition, DSL.select(DSL.xmlelement(Multiset.nResult(ctx), filter2)).from((TableLike<?>)t4));
                break;
            }
            case NATIVE: {
                Tools.visitSubquery(ctx.visit(Keywords.K_MULTISET), this.select);
            }
        }
    }

    private static final void acceptMultisetSubqueryForXMLEmulation(Context<?> ctx, boolean multisetCondition, Select<Record1<XML>> s2) {
        if (multisetCondition && NO_SUPPORT_XML_COMPARE.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.xmlserializeContent(DSL.field(s2), SQLDataType.VARCHAR));
        } else {
            Tools.visitSubquery(ctx, s2);
        }
    }

    private static final boolean isSimple(Select<?> s2) {
        return s2.$groupBy().isEmpty() && s2.$having() == null && s2.$window().isEmpty() && s2.$qualify() == null && !Tools.selectQueryImpl(s2).hasUnions() && s2.$offset() == null && s2.$limit() == null;
    }

    static final Name nResult(Scope ctx) {
        switch (ctx.family()) {
            case TERADATA: {
                return Names.NQ_RESULT;
            }
        }
        return Names.N_RESULT;
    }

    static final Name xsiNil(Context<?> ctx) {
        switch (ctx.family()) {
            case TERADATA: {
                return DSL.name("nil");
            }
        }
        return DSL.name("xsi:nil");
    }

    static final <J> Field<J> returningClob(Scope ctx, JSONObjectReturningStep<J> j) {
        switch (ctx.family()) {
            case DB2: {
                return j.getType() == JSONB.class ? j.returning(SQLDataType.BLOB) : j;
            }
            case ORACLE: {
                return j.returning(j.getType() == JSONB.class ? SQLDataType.BLOB : SQLDataType.CLOB);
            }
        }
        return j;
    }

    static final <J> Field<J> returningClob(Scope ctx, JSONArrayReturningStep<J> j) {
        switch (ctx.family()) {
            case DB2: {
                return j.getType() == JSONB.class ? j.returning(SQLDataType.BLOB) : j;
            }
            case ORACLE: {
                return j.returning(j.getType() == JSONB.class ? SQLDataType.BLOB : SQLDataType.CLOB);
            }
        }
        return j;
    }

    static final <J> Field<J> returningClob(Scope ctx, JSONArrayAggReturningStep<J> j) {
        switch (ctx.family()) {
            case DB2: {
                return j.getType() == JSONB.class ? j.returning(SQLDataType.BLOB) : j;
            }
            case ORACLE: {
                return j.returning(j.getType() == JSONB.class ? SQLDataType.BLOB : SQLDataType.CLOB);
            }
        }
        return j;
    }

    static final JSONArrayAggOrderByStep<JSON> jsonArrayaggEmulation(Context<?> ctx, Fields fields, boolean agg, boolean distinct2) {
        return Multiset.jsonxArrayaggEmulation(ctx, fields, agg, distinct2 ? DSL::jsonArrayAggDistinct : DSL::jsonArrayAgg, DSL::jsonObject, DSL::jsonArray);
    }

    static final JSONArrayAggOrderByStep<JSONB> jsonbArrayaggEmulation(Context<?> ctx, Fields fields, boolean agg, boolean distinct2) {
        return Multiset.jsonxArrayaggEmulation(ctx, fields, agg, distinct2 ? DSL::jsonbArrayAggDistinct : DSL::jsonbArrayAgg, DSL::jsonbObject, DSL::jsonbArray);
    }

    static final <J> JSONArrayAggOrderByStep<J> jsonxArrayaggEmulation(Context<?> ctx, Fields fields, boolean agg, Function<? super Field<?>, ? extends JSONArrayAggOrderByStep<J>> jsonxArrayAgg, Function<? super Collection<? extends JSONEntry<?>>, ? extends JSONObjectNullStep<J>> jsonxObject, Function<? super Collection<? extends Field<?>>, ? extends JSONArrayNullStep<J>> jsonxArray) {
        switch (ctx.family()) {
            case DB2: {
                return jsonxArrayAgg.apply(Multiset.returningClob(ctx, (JSONObjectReturningStep)jsonxObject.apply(Tools.map(fields.fields(), (f, i) -> DSL.jsonEntry(Tools.fieldNameString(i), agg ? f : DSL.field(Tools.fieldName(i), f.getDataType()))))));
            }
        }
        return jsonxArrayAgg.apply(Multiset.returningClob(ctx, jsonxArray.apply(Tools.map(fields.fields(), (f, i) -> JSONEntryImpl.unescapeNestedJSON(ctx, Multiset.castForJSON(ctx, agg ? f : DSL.field(Tools.fieldName(i), f.getDataType()))))).nullOnNull()));
    }

    static final Field<?> castForJSON(Context<?> ctx, Field<?> field2) {
        if (field2.getDataType().isSpatial()) {
            switch (ctx.family()) {
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: {
                    return DSL.function(Names.N_HEX, SQLDataType.CLOB, DSL.stAsBinary(field2));
                }
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: {
                    return field2.cast(SQLDataType.CLOB);
                }
                case SQLSERVER: {
                    return DSL.stAsText(field2.cast(field2.getDataType()));
                }
            }
        }
        return field2;
    }

    static final Field<?> castForXML(Context<?> ctx, Field<?> field2) {
        if (field2.getDataType().isSpatial()) {
            switch (ctx.family()) {
                case ORACLE: {
                    return DSL.stAsText(field2);
                }
                case SQLSERVER: {
                    return DSL.stAsText(field2.cast(field2.getDataType()));
                }
            }
        }
        return field2;
    }

    static final XMLAggOrderByStep<XML> xmlaggEmulation(Context<?> ctx, Fields fields, boolean agg) {
        return DSL.xmlagg(DSL.xmlelement(Names.N_RECORD, Tools.map(fields.fields(), (f, i) -> {
            Field<?> v = Multiset.castForXML(ctx, agg ? f : DSL.field(Tools.fieldName(i), f.getDataType()));
            String n = Tools.fieldNameString(i);
            DataType t2 = v.getDataType();
            if (t2.isString() || t2.isArray()) {
                return DSL.xmlelement(n, DSL.xmlattributes(new Field[]{DSL.when(v.isNull(), DSL.inline("true")).as(Multiset.xsiNil(ctx))}), v);
            }
            return DSL.xmlelement(n, v);
        })));
    }

    @Override
    public final TableLike<R> $table() {
        return this.table;
    }

    public final <Q> Q $traverse(Traverser<?, Q> traverser) {
        return QOM.traverse(traverser, this.select, new Object[0]);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.select, Multiset::new, replacer);
    }
}

