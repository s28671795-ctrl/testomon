/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collections;
import java.util.Set;
import org.jooq.Commit;
import org.jooq.Configuration;
import org.jooq.MigrationContext;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Schema;
import org.jooq.impl.AbstractScope;

final class DefaultMigrationContext
extends AbstractScope
implements MigrationContext {
    final Set<Schema> migratedSchemas;
    final Commit migrationFrom;
    final Commit migrationTo;
    final Queries migrationQueries;
    final Queries revertUntrackedQueries;
    Query query;

    DefaultMigrationContext(Configuration configuration, Set<Schema> migratedSchemas, Commit migrationFrom, Commit migrationTo, Queries migrationQueries, Queries revertUntrackedQueries) {
        super(configuration);
        this.migratedSchemas = migratedSchemas;
        this.migrationFrom = migrationFrom;
        this.migrationTo = migrationTo;
        this.migrationQueries = migrationQueries;
        this.revertUntrackedQueries = revertUntrackedQueries;
    }

    @Override
    public final Set<Schema> migratedSchemas() {
        return Collections.unmodifiableSet(this.migratedSchemas);
    }

    @Override
    public final Commit migrationFrom() {
        return this.migrationFrom;
    }

    @Override
    public final Commit migrationTo() {
        return this.migrationTo;
    }

    @Override
    public final Queries migrationQueries() {
        return this.migrationQueries;
    }

    @Override
    public final Commit queriesFrom() {
        return this.migrationFrom;
    }

    @Override
    public final Commit queriesTo() {
        return this.migrationTo;
    }

    @Override
    public final Queries queries() {
        return this.migrationQueries;
    }

    @Override
    public final Query query() {
        return this.query;
    }

    final void query(Query q) {
        this.query = q;
    }
}

