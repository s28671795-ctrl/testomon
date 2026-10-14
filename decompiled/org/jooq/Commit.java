/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.File;
import org.jooq.Files;
import org.jooq.Meta;
import org.jooq.Node;
import org.jooq.Source;
import org.jooq.Version;

@ApiStatus.Experimental
public interface Commit
extends Node<Commit> {
    @NotNull
    public Collection<File> delta();

    @NotNull
    public Collection<File> files();

    @NotNull
    public Collection<Source> sources();

    @NotNull
    public Version version();

    @NotNull
    public Meta meta();

    @NotNull
    public Files migrateTo(Commit var1);

    @NotNull
    public Commit commit(String var1, File ... var2);

    @NotNull
    public Commit commit(String var1, Collection<? extends File> var2);

    @NotNull
    public Commit commit(String var1, String var2, File ... var3);

    @NotNull
    public Commit commit(String var1, String var2, Collection<? extends File> var3);

    @NotNull
    public Commit merge(String var1, Commit var2, File ... var3);

    @NotNull
    public Commit merge(String var1, Commit var2, Collection<? extends File> var3);

    @NotNull
    public Commit merge(String var1, String var2, Commit var3, File ... var4);

    @NotNull
    public Commit merge(String var1, String var2, Commit var3, Collection<? extends File> var4);
}

