/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Commit;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Schema;
import org.jooq.Scope;

@ApiStatus.Experimental
public interface MigrationContext
extends Scope {
    @NotNull
    public Set<Schema> migratedSchemas();

    @NotNull
    public Commit migrationFrom();

    @NotNull
    public Commit migrationTo();

    @NotNull
    public Queries migrationQueries();

    @NotNull
    public Commit queriesFrom();

    @NotNull
    public Commit queriesTo();

    @NotNull
    public Queries queries();

    @NotNull
    public Query query();
}

