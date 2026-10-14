/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.TableAlias;
import org.jooq.impl.Tools;

class DerivedTable<R extends Record>
extends AbstractTable<R>
implements QOM.DerivedTable<R> {
    static final Set<SQLDialect> NO_SUPPORT_CORRELATED_DERIVED_TABLE = SQLDialect.supportedUntil(SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE12C);
    private final Select<R> query;

    DerivedTable(Select<R> query) {
        this(query, Names.N_T);
    }

    DerivedTable(Select<R> query, Name name) {
        super(TableOptions.expression(), name);
        this.query = query;
    }

    final Select<R> query() {
        return this.query;
    }

    @Override
    public final Table<R> as(Name alias) {
        return new TableAlias(this, alias, c -> true);
    }

    @Override
    public final Table<R> as(Name alias, Name ... fieldAliases) {
        return new TableAlias(this, alias, fieldAliases, c -> true);
    }

    @Override
    FieldsImpl<R> fields0() {
        return new FieldsImpl(this.query.getSelect());
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return this.query.getRecordType();
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("/* derived tables are not yet supported. See known limitations: https://github.com/jOOQ/jOOQ/issues/11911 */");
        }
        Tools.visitSubquery(ctx, this.query, 1, false);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }

    @Override
    public final Function1<? super Select<R>, ? extends QOM.DerivedTable<R>> $constructor() {
        return t2 -> new DerivedTable(t2);
    }

    @Override
    public final Select<R> $arg1() {
        return this.query;
    }
}

