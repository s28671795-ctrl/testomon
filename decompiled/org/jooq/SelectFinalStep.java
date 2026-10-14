/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.Support;

public interface SelectFinalStep<R extends Record>
extends Select<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectQuery<R> getQuery();
}

