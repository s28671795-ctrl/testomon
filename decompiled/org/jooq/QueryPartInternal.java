/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Pro;
import org.jooq.QueryPart;

@ApiStatus.Internal
public interface QueryPartInternal
extends QueryPart {
    @Deprecated
    public boolean rendersContent(Context<?> var1);

    @Deprecated
    public void accept(Context<?> var1);

    public Clause[] clauses(Context<?> var1);

    public boolean declaresFields();

    public boolean declaresTables();

    public boolean declaresWindows();

    public boolean declaresCTE();

    @Pro
    public boolean declaresParameters();

    public boolean generatesCast();
}

