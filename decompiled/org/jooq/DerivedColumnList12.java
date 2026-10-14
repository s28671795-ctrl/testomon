/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CommonTableExpression;
import org.jooq.QueryPart;
import org.jooq.Record12;
import org.jooq.ResultQuery;
import org.jooq.Support;

public interface DerivedColumnList12
extends QueryPart {
    @Support
    @NotNull
    public <R extends Record12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> CommonTableExpression<R> as(ResultQuery<R> var1);

    @Support
    @NotNull
    public <R extends Record12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> CommonTableExpression<R> asMaterialized(ResultQuery<R> var1);

    @Support
    @NotNull
    public <R extends Record12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>> CommonTableExpression<R> asNotMaterialized(ResultQuery<R> var1);
}

