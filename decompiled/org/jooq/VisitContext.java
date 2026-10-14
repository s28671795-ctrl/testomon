/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.BindContext;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.Scope;

public interface VisitContext
extends Scope {
    @NotNull
    public Clause clause();

    @NotNull
    public @NotNull Clause @NotNull [] clauses();

    public int clausesLength();

    @NotNull
    public QueryPart queryPart();

    public void queryPart(QueryPart var1);

    @NotNull
    public @NotNull QueryPart @NotNull [] queryParts();

    public int queryPartsLength();

    @NotNull
    public Context<?> context();

    @Nullable
    public RenderContext renderContext();

    @Nullable
    public BindContext bindContext() throws UnsupportedOperationException;
}

