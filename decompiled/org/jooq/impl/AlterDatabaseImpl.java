/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.AlterDatabaseFinalStep;
import org.jooq.AlterDatabaseStep;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class AlterDatabaseImpl
extends AbstractDDLQuery
implements QOM.AlterDatabase,
AlterDatabaseStep,
AlterDatabaseFinalStep {
    final Catalog database;
    final boolean ifExists;
    Catalog renameTo;
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.HANA, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> SUPPORT_RENAME_DATABASE = SQLDialect.supportedBy(SQLDialect.HANA);

    AlterDatabaseImpl(Configuration configuration, Catalog database, boolean ifExists) {
        this(configuration, database, ifExists, null);
    }

    AlterDatabaseImpl(Configuration configuration, Catalog database, boolean ifExists, Catalog renameTo) {
        super(configuration);
        this.database = database;
        this.ifExists = ifExists;
        this.renameTo = renameTo;
    }

    @Override
    public final AlterDatabaseImpl renameTo(String renameTo) {
        return this.renameTo(DSL.catalog(DSL.name(renameTo)));
    }

    @Override
    public final AlterDatabaseImpl renameTo(Name renameTo) {
        return this.renameTo(DSL.catalog(renameTo));
    }

    @Override
    public final AlterDatabaseImpl renameTo(Catalog renameTo) {
        this.renameTo = renameTo;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.ALTER_DATABASE, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        boolean supportRename = SUPPORT_RENAME_DATABASE.contains((Object)ctx.dialect());
        if (supportRename) {
            ctx.visit(Keywords.K_RENAME);
        } else {
            ctx.visit(Keywords.K_ALTER);
        }
        ctx.sql(' ').visit(Keywords.K_DATABASE);
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        ctx.sql(' ').visit(this.database);
        if (this.renameTo != null) {
            ctx.sql(' ').visit(supportRename ? Keywords.K_TO : Keywords.K_RENAME_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo));
        }
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
    public final Catalog $renameTo() {
        return this.renameTo;
    }

    @Override
    public final QOM.AlterDatabase $database(Catalog newValue) {
        return this.$constructor().apply(newValue, (Boolean)this.$ifExists(), this.$renameTo());
    }

    @Override
    public final QOM.AlterDatabase $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$database(), (Boolean)newValue, this.$renameTo());
    }

    @Override
    public final QOM.AlterDatabase $renameTo(Catalog newValue) {
        return this.$constructor().apply(this.$database(), (Boolean)this.$ifExists(), newValue);
    }

    public final Function3<? super Catalog, ? super Boolean, ? super Catalog, ? extends QOM.AlterDatabase> $constructor() {
        return (a1, a2, a3) -> new AlterDatabaseImpl(this.configuration(), (Catalog)a1, (boolean)a2, (Catalog)a3);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$database(), this.$ifExists(), this.$renameTo(), (a1, a2, a3) -> this.$constructor().apply((Catalog)a1, (Boolean)a2, (Catalog)a3), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$database(), this.$renameTo());
    }
}

