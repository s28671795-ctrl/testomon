/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Commit;
import org.jooq.migrations.xml.jaxb.MigrationsType;

public interface Commits
extends Iterable<Commit> {
    @NotNull
    public Commit root();

    @Nullable
    public Commit get(String var1);

    public void add(Commit var1);

    public void addAll(Commit ... var1);

    public void addAll(Collection<? extends Commit> var1);

    @NotNull
    public Commits load(MigrationsType var1);

    @NotNull
    public MigrationsType export();
}

