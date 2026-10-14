/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.DSL;
import org.jooq.impl.TableAlias;

abstract class AbstractAutoAliasTable<R extends Record>
extends AbstractTable<R>
implements AutoAlias<Table<R>> {
    final Name alias;
    final Name[] fieldAliases;

    AbstractAutoAliasTable(Name alias) {
        this(alias, (Name[])null);
    }

    AbstractAutoAliasTable(Name alias, Name[] fieldAliases) {
        super(TableOptions.expression(), alias != null ? alias : DSL.name("t"));
        this.alias = alias;
        this.fieldAliases = fieldAliases;
    }

    abstract AbstractAutoAliasTable<R> construct(Name var1, Name[] var2);

    @Override
    public final boolean declaresTables() {
        return true;
    }

    @Override
    public final Table<R> autoAlias(Context<?> ctx, Table<R> t2) {
        return t2.as(this.alias, this.fieldAliases);
    }

    @Override
    public final Table<R> as(Name as) {
        return new TableAlias<R>(this.construct(as, null), as, this.fieldAliases);
    }

    @Override
    public final Table<R> as(Name as, Name ... fields) {
        return new TableAlias<R>(this.construct(as, fields), as, fields);
    }

    @Override
    public final Table<R> $aliased() {
        return this.construct(this.alias, null);
    }

    @Override
    public final Name $alias() {
        return this.alias;
    }
}

