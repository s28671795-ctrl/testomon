/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CommonTableExpression;
import org.jooq.QueryPart;
import org.jooq.Record19;
import org.jooq.ResultQuery;
import org.jooq.Support;

public interface DerivedColumnList19
extends QueryPart {
    @Support
    @NotNull
    public <R extends Record19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> CommonTableExpression<R> as(ResultQuery<R> var1);

    @Support
    @NotNull
    public <R extends Record19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> CommonTableExpression<R> asMaterialized(ResultQuery<R> var1);

    @Support
    @NotNull
    public <R extends Record19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> CommonTableExpression<R> asNotMaterialized(ResultQuery<R> var1);
}

