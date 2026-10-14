/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.JSONTableColumnPathStep;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.TableLike;
import org.jooq.TableOptions;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class JSONTable
extends AbstractTable<Record>
implements JSONTableColumnPathStep,
QOM.UNotYetImplemented {
    private static final Set<SQLDialect> REQUIRES_COLUMN_PATH = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL);
    @Pro
    private static final Set<SQLDialect> REQUIRES_INLINE_PATH = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.ORACLE);
    private final Field<String> path;
    private final Field<?> json;
    private final QueryPartList<JSONTableColumn> columns;
    private final boolean hasOrdinality;
    private transient FieldsImpl<Record> fields;

    JSONTable(Field<?> json, Field<String> path) {
        this(json, path, null, false);
    }

    private JSONTable(Field<?> json, Field<String> path, QueryPartList<JSONTableColumn> columns, boolean hasOrdinality) {
        super(TableOptions.expression(), Names.N_JSON_TABLE);
        this.json = json;
        this.path = path;
        this.columns = columns == null ? new QueryPartList() : columns;
        this.hasOrdinality = hasOrdinality;
    }

    @Override
    public final JSONTable column(String name) {
        return this.column(DSL.name(name));
    }

    @Override
    public final JSONTable column(Name name) {
        return this.column((Field)DSL.field(name));
    }

    @Override
    public final JSONTable column(Field<?> name) {
        return this.column((Field)name, name.getDataType());
    }

    @Override
    public final JSONTable column(String name, DataType<?> type) {
        return this.column(DSL.name(name), (DataType)type);
    }

    @Override
    public final JSONTable column(Name name, DataType<?> type) {
        return this.column((Field)DSL.field(name), (DataType)type);
    }

    @Override
    public final JSONTable column(Field<?> name, DataType<?> type) {
        QueryPartList<JSONTableColumn> c = new QueryPartList<JSONTableColumn>((Iterable<JSONTableColumn>)this.columns);
        c.add(new JSONTableColumn(name, type, false, null));
        return new JSONTable(this.json, this.path, c, this.hasOrdinality);
    }

    @Override
    public final JSONTable forOrdinality() {
        return this.path0(true, null);
    }

    @Override
    public final JSONTable path(String p) {
        return this.path0(false, p);
    }

    private final JSONTable path0(boolean forOrdinality, String p) {
        QueryPartList<JSONTableColumn> c = new QueryPartList<JSONTableColumn>((Iterable<JSONTableColumn>)this.columns);
        int i = c.size() - 1;
        JSONTableColumn last = (JSONTableColumn)c.get(i);
        c.set(i, new JSONTableColumn(last.field, last.type, forOrdinality, p));
        return new JSONTable(this.json, this.path, c, this.hasOrdinality || forOrdinality);
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    final FieldsImpl<Record> fields0() {
        if (this.fields == null) {
            this.fields = new FieldsImpl(Tools.map(this.columns, c -> c.field.getDataType() == c.type ? c.field : DSL.field(c.field.getQualifiedName(), c.type)));
        }
        return this.fields;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case POSTGRES: 
            case YUGABYTEDB: {
                this.acceptPostgres(ctx);
                break;
            }
            case SQLSERVER: {
                this.acceptSQLServer(ctx);
                break;
            }
            default: {
                this.acceptStandard(ctx);
            }
        }
    }

    private final void acceptPostgres(Context<?> ctx) {
        Tools.visitSubquery(ctx, DSL.select(Tools.map(this.columns, col -> col.forOrdinality ? DSL.field("o").as((Field)col.field) : DSL.field("cast((jsonb_path_query_first(j, cast({0} as jsonpath))->>0) as {1})", col.path != null ? DSL.val(col.path) : DSL.inline("$." + col.field.getName()), DSL.keyword(col.type.getCastTypeName(ctx.configuration()))).as((Field)col.field))).from(this.hasOrdinality ? "jsonb_path_query({0}, cast({1} as jsonpath)) {with} {ordinality} {as} t(j, o)" : "jsonb_path_query({0}, cast({1} as jsonpath)) {as} t(j)", this.json.getType() == JSONB.class ? this.json : this.json.cast(SQLDataType.JSONB), this.path), 1);
    }

    @Pro
    private final void acceptSQLServer(Context<?> ctx) {
        if (this.hasOrdinality) {
            ArrayList<SelectField> select = new ArrayList<SelectField>();
            QueryPartList<JSONTableColumn> cols = new QueryPartList<JSONTableColumn>();
            for (JSONTableColumn col : this.columns) {
                if (col.forOrdinality) {
                    select.add(DSL.rowNumber().over().as((Field)col.field));
                    continue;
                }
                select.add(DSL.field(col.field.getUnqualifiedName()));
                cols.add(col);
            }
            Tools.visitSubquery(ctx, DSL.select(select).from((TableLike<?>)((Object)new JSONTable(this.json, this.path, cols, false).as("t"))), 1);
        } else {
            ctx.visit(Names.N_OPENJSON).sql('(').visit(this.json).sql(", ").visit(this.path).sql(") ").visit(Keywords.K_WITH).sql(" (").visit(this.columns).sql(')');
        }
    }

    private final void acceptStandard(Context<?> ctx) {
        ctx.visit(Keywords.K_JSON_TABLE).sqlIndentStart('(');
        ctx.visit(this.json).sql(',').formatSeparator();
        this.acceptJSONPath(ctx);
        ctx.formatSeparator().visit(Keywords.K_COLUMNS).sql(" (").visit(this.columns).sql(')');
        if (ctx.family() == SQLDialect.DB2) {
            ctx.formatSeparator().visit(Keywords.K_ERROR).sql(' ').visit(Keywords.K_ON).sql(' ').visit(Keywords.K_ERROR);
        }
        ctx.sqlIndentEnd(')');
    }

    private final void acceptJSONPath(Context<?> ctx) {
        if (REQUIRES_INLINE_PATH.contains((Object)ctx.dialect())) {
            ctx.visit(this.path, ParamType.INLINED);
        } else {
            ctx.visit(this.path);
        }
    }

    @Override
    public boolean declaresTables() {
        return true;
    }

    private static class JSONTableColumn
    extends AbstractQueryPart
    implements QOM.UNotYetImplemented {
        final Field<?> field;
        final DataType<?> type;
        final boolean forOrdinality;
        final String path;

        JSONTableColumn(Field<?> field2, DataType<?> type, boolean forOrdinality, String path) {
            this.field = field2;
            this.type = type;
            this.forOrdinality = forOrdinality;
            this.path = path;
        }

        @Override
        public final void accept(Context<?> ctx) {
            String p;
            ctx.qualify(false, c -> c.visit(this.field)).sql(' ');
            if (this.forOrdinality) {
                ctx.visit(Keywords.K_FOR).sql(' ').visit(Keywords.K_ORDINALITY);
            } else {
                Tools.toSQLDDLTypeDeclaration(ctx, this.type);
            }
            String string = this.path != null ? this.path : (p = !this.forOrdinality && REQUIRES_COLUMN_PATH.contains((Object)ctx.dialect()) ? "$." + this.field.getName() : null);
            if (p != null) {
                if (ctx.family() == SQLDialect.SQLSERVER) {
                    ctx.sql(' ').visit(DSL.inline(p));
                } else {
                    ctx.sql(' ').visit(Keywords.K_PATH).sql(' ').visit(DSL.inline(p));
                }
            }
        }
    }
}

