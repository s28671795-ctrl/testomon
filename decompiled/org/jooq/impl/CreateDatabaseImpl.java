/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateDatabaseFinalStep;
import org.jooq.Function2;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class CreateDatabaseImpl
extends AbstractDDLQuery
implements QOM.CreateDatabase,
CreateDatabaseFinalStep {
    final Catalog database;
    final boolean ifNotExists;
    private static final Set<SQLDialect> NO_SUPPORT_IF_NOT_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);

    CreateDatabaseImpl(Configuration configuration, Catalog database, boolean ifNotExists) {
        super(configuration);
        this.database = database;
        this.ifNotExists = ifNotExists;
    }

    private final boolean supportsIfNotExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_NOT_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.CREATE_DATABASE, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        ctx.visit(Keywords.K_CREATE).sql(' ').visit(Keywords.K_DATABASE);
        if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_NOT_EXISTS);
        }
        ctx.sql(' ').visit(this.database);
    }

    @Override
    public final Catalog $database() {
        return this.database;
    }

    @Override
    public final boolean $ifNotExists() {
        return this.ifNotExists;
    }

    @Override
    public final QOM.CreateDatabase $database(Catalog newValue) {
        return this.$constructor().apply(newValue, (Boolean)this.$ifNotExists());
    }

    @Override
    public final QOM.CreateDatabase $ifNotExists(boolean newValue) {
        return this.$constructor().apply(this.$database(), (Boolean)newValue);
    }

    public final Function2<? super Catalog, ? super Boolean, ? extends QOM.CreateDatabase> $constructor() {
        return (a1, a2) -> new CreateDatabaseImpl(this.configuration(), (Catalog)a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$database(), this.$ifNotExists(), (a1, a2) -> this.$constructor().apply((Catalog)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$database());
    }
}

