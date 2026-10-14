/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Meta;
import org.jooq.Node;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Source;

@ApiStatus.Experimental
public interface Version
extends Node<Version> {
    @NotNull
    public Meta meta();

    @NotNull
    public Queries migrateTo(Version var1);

    @NotNull
    public Version commit(String var1, Meta var2);

    @NotNull
    public Version commit(String var1, String ... var2);

    @NotNull
    public Version commit(String var1, Source ... var2);

    @NotNull
    public Version merge(String var1, Version var2);

    @NotNull
    public Version apply(String var1, Queries var2);

    @NotNull
    public Version apply(String var1, Query ... var2);

    @NotNull
    public Version apply(String var1, Collection<? extends Query> var2);

    @NotNull
    public Version apply(String var1, String var2);
}

