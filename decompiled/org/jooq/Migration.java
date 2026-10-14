/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jooq.Commit;
import org.jooq.Queries;
import org.jooq.Scope;
import org.jooq.exception.DataMigrationException;
import org.jooq.exception.DataMigrationValidationException;

@ApiStatus.Experimental
public interface Migration
extends Scope {
    public Commit from();

    public Commit to();

    @NotNull
    public Queries queries();

    public void validate() throws DataMigrationValidationException;

    @Blocking
    public void execute() throws DataMigrationException;
}

