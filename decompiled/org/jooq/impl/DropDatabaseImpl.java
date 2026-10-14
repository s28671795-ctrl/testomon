/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropDatabaseFinalStep;
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

final class DropDatabaseImpl
extends AbstractDDLQuery
implements QOM.DropDatabase,
DropDatabaseFinalStep {
    final Catalog database;
    final boolean ifExists;
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);

    DropDatabaseImpl(Configuration configuration, Catalog database, boolean ifExists) {
        super(configuration);
        this.database = database;
        this.ifExists = ifExists;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.DROP_DATABASE, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private void accept0(Context<?> ctx) {
        ctx.visit(Keywords.K_DROP).sql(' ').visit(Keywords.K_DATABASE);
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        ctx.sql(' ').visit(this.database);
    }

    @Override
    public final Catalog $database() {
        return this.database;
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final QOM.DropDatabase $database(Catalog newValue) {
        return this.$constructor().apply(newValue, (Boolean)this.$ifExists());
    }

    @Override
    public final QOM.DropDatabase $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$database(), (Boolean)newValue);
    }

    public final Function2<? super Catalog, ? super Boolean, ? extends QOM.DropDatabase> $constructor() {
        return (a1, a2) -> new DropDatabaseImpl(this.configuration(), (Catalog)a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$database(), this.$ifExists(), (a1, a2) -> this.$constructor().apply((Catalog)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$database());
    }
}

