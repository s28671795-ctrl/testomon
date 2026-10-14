/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Commit;
import org.jooq.Commits;
import org.jooq.Migration;
import org.jooq.Version;
import org.jooq.Versions;

@ApiStatus.Experimental
public interface Migrations {
    @ApiStatus.Experimental
    @NotNull
    public Version version(String var1);

    @ApiStatus.Experimental
    @NotNull
    public Versions versions();

    @ApiStatus.Experimental
    @NotNull
    public Commit commit(String var1);

    @ApiStatus.Experimental
    @NotNull
    public Commits commits();

    @ApiStatus.Experimental
    @NotNull
    public Migration migrateTo(Commit var1);
}

