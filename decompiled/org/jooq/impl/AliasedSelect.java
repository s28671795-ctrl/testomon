/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.TableAlias;
import org.jooq.impl.Tools;

final class AliasedSelect<R extends Record>
extends AbstractTable<R>
implements QOM.UTransient {
    private final Select<R> query;
    private final boolean subquery;
    private final boolean ignoreOrderBy;
    private final boolean forceLimit;
    private final Name[] aliases;

    AliasedSelect(Select<R> query, boolean subquery, boolean ignoreOrderBy, boolean forceLimit) {
        this(query, subquery, ignoreOrderBy, forceLimit, Tools.fieldNames(Tools.degree(query)));
    }

    AliasedSelect(Select<R> query, boolean subquery, boolean ignoreOrderBy, boolean forceLimit, Name ... aliases) {
        super(TableOptions.expression(), Names.NQ_SELECT);
        this.query = query;
        this.subquery = subquery;
        this.ignoreOrderBy = ignoreOrderBy;
        this.forceLimit = forceLimit;
        this.aliases = aliases;
    }

    final Select<R> query() {
        return this.query;
    }

    @Override
    public final Table<R> as(Name alias) {
        SelectQueryImpl q = Tools.selectQueryImpl(this.query);
        if (q != null && (this.ignoreOrderBy && !q.getOrderBy().isEmpty() || Tools.hasEmbeddedFields(q.getSelect()))) {
            return this.query.asTable(alias, this.aliases);
        }
        return new TableAlias(this, alias, c -> true);
    }

    @Override
    public final Table<R> as(Name alias, Name ... fieldAliases) {
        return new TableAlias(this, alias, fieldAliases, c -> true);
    }

    @Override
    final FieldsImpl<R> fields0() {
        return new FieldsImpl(this.query.asTable(DSL.name("t"), this.aliases).fields());
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return this.query.getRecordType();
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.forceLimit) {
            ctx.data(Tools.BooleanDataKey.DATA_FORCE_LIMIT_WITH_ORDER_BY, true, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        SelectQueryImpl q = Tools.selectQueryImpl(this.query);
        if (ctx.family() == SQLDialect.DERBY && q != null && q.hasUnions()) {
            Tools.visitSubquery(ctx, DSL.selectFrom(this.query.asTable(DSL.name("t"), this.aliases)), 1, false);
        } else {
            ctx.data(Tools.SimpleDataKey.DATA_SELECT_ALIASES, this.aliases, this.subquery ? c -> Tools.visitSubquery(c, this.query, 1, false) : c -> c.visit(this.query));
        }
    }

    static final boolean avoidAliasPushdown(Context<?> ctx, Select<?> query) {
        SelectQueryImpl q = Tools.selectQueryImpl(query);
        return q != null && (ctx.family() == SQLDialect.DERBY && q.hasUnions() || !q.getOrderBy().isEmpty() || Tools.hasEmbeddedFields(q.getSelect()));
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }
}

