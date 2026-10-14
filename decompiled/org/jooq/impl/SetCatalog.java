/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class SetCatalog
extends AbstractDDLQuery
implements QOM.SetCatalog {
    final Catalog catalog;

    SetCatalog(Configuration configuration, Catalog catalog) {
        super(configuration);
        this.catalog = catalog;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case SNOWFLAKE: {
                ctx.visit(Keywords.K_USE).sql(' ').visit(Keywords.K_DATABASE).sql(' ').visit(this.catalog);
                break;
            }
            case SQLSERVER: {
                ctx.visit(Keywords.K_USE).sql(' ').visit(this.catalog);
                break;
            }
            case AURORA_MYSQL: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case TERADATA: 
            case VERTICA: 
            case DERBY: 
            case H2: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(DSL.setSchema(this.catalog.getName()));
                break;
            }
            default: {
                ctx.visit(Keywords.K_SET).sql(' ').visit(Keywords.K_CATALOG).sql(' ').visit(this.catalog);
            }
        }
    }

    @Override
    public final Catalog $catalog() {
        return this.catalog;
    }

    @Override
    public final QOM.SetCatalog $catalog(Catalog newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Catalog, ? extends QOM.SetCatalog> $constructor() {
        return a1 -> new SetCatalog(this.configuration(), (Catalog)a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$catalog(), a1 -> this.$constructor().apply((Catalog)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$catalog());
    }
}

