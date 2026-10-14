/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import java.util.UUID;
import org.jooq.Context;
import org.jooq.DMLQuery;
import org.jooq.Delete;
import org.jooq.Insert;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.Traverser;
import org.jooq.Update;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.CustomQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.InsertQueryImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.MergeImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.TableAlias;
import org.jooq.impl.Tools;
import org.jooq.impl.TopLevelCte;

final class DataChangeDeltaTable<R extends Record>
extends AbstractTable<R>
implements AutoAlias<Table<R>>,
QOM.DataChangeDeltaTable<R> {
    private final Set<SQLDialect> EMULATE_USING_CTE = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private final QOM.ResultOption resultOption;
    private final DMLQuery<R> query;
    private final Table<R> table;
    private final Name alias;

    DataChangeDeltaTable(QOM.ResultOption resultOption, DMLQuery<R> query) {
        this(resultOption, query, DataChangeDeltaTable.table(query));
    }

    private DataChangeDeltaTable(QOM.ResultOption resultOption, DMLQuery<R> query, Table<R> table) {
        this(resultOption, query, table, table.getUnqualifiedName());
    }

    private DataChangeDeltaTable(QOM.ResultOption resultOption, DMLQuery<R> query, Table<R> table, Name alias) {
        super(TableOptions.expression(), alias);
        this.resultOption = resultOption;
        this.query = query;
        this.table = table;
        this.alias = alias;
    }

    private static final <R extends Record> Table<R> table(DMLQuery<R> query) {
        if (query instanceof Insert || query instanceof Update || query instanceof Delete) {
            return Tools.abstractDMLQuery(query).table();
        }
        if (query instanceof MergeImpl) {
            MergeImpl m4 = (MergeImpl)query;
            return m4.table();
        }
        throw new IllegalStateException("Unsupported query type: " + String.valueOf(query));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.EMULATE_USING_CTE.contains((Object)ctx.dialect())) {
            this.acceptCte(ctx);
        } else {
            this.acceptNative(ctx);
        }
    }

    private final void acceptCte(Context<?> ctx) {
        TopLevelCte cte = (TopLevelCte)ctx.data(Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE);
        if (cte != null) {
            cte.add(DSL.name(this.alias).as(DSL.resultQuery("{0}", CustomQueryPart.of(c2 -> c2.visit(this.query).formatSeparator().visit(Keywords.K_RETURNING).sql(' ').visit(new SelectFieldList((SelectFieldOrAsterisk[])this.table.fields()))))));
        }
        ctx.visit(DSL.table(this.alias));
    }

    private final void acceptNative(Context<?> ctx) {
        switch (this.resultOption) {
            case FINAL: {
                ctx.visit(Keywords.K_FINAL);
                break;
            }
            case OLD: {
                ctx.visit(Keywords.K_OLD);
                break;
            }
            case NEW: {
                ctx.visit(Keywords.K_NEW);
                break;
            }
            default: {
                throw new IllegalStateException("Unsupported result option: " + String.valueOf((Object)this.resultOption));
            }
        }
        ctx.sql(' ').visit(Keywords.K_TABLE).sqlIndentStart(" (");
        if (this.requiresWorkaroundFor12925(ctx)) {
            ctx.sql("/* [#12925] ").sql(UUID.randomUUID().toString()).sql(" */").formatSeparator();
        }
        Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_RENDERING_DATA_CHANGE_DELTA_TABLE, () -> ctx.visit(this.query).sqlIndentEnd(')'));
    }

    private final boolean requiresWorkaroundFor12925(Context<?> ctx) {
        if (ctx.family() == SQLDialect.H2) {
            if (this.query instanceof MergeImpl) {
                return true;
            }
            InsertQueryImpl<?> i = Tools.insertQueryImpl(this.query);
            return i != null && (i.onDuplicateKeyIgnore || i.onDuplicateKeyUpdate);
        }
        return false;
    }

    @Override
    public Table<R> as(Name as) {
        return new TableAlias<R>(new DataChangeDeltaTable<R>(this.resultOption, this.query, this.table, as), as);
    }

    @Override
    public Table<R> as(Name as, Name ... fieldAliases) {
        return new TableAlias<R>(new DataChangeDeltaTable<R>(this.resultOption, this.query, this.table, as), as, fieldAliases);
    }

    @Override
    public final Table<R> autoAlias(Context<?> ctx, Table<R> t2) {
        return t2.as(this.alias);
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return this.table.getRecordType();
    }

    @Override
    final FieldsImpl<R> fields0() {
        return ((AbstractRow)this.table.as((Name)this.alias).fieldsRow()).fields;
    }

    @Override
    public final QOM.ResultOption $resultOption() {
        return this.resultOption;
    }

    @Override
    public final DMLQuery<R> $query() {
        return this.query;
    }

    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.query);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.resultOption, this.query, DataChangeDeltaTable::new, replacer);
    }
}

