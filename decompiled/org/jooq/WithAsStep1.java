/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record1;
import org.jooq.ResultQuery;
import org.jooq.Support;
import org.jooq.WithStep;

public interface WithAsStep1 {
    @Support
    @NotNull
    @CheckReturnValue
    public WithStep as(ResultQuery<? extends Record1<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithStep asMaterialized(ResultQuery<? extends Record1<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithStep asNotMaterialized(ResultQuery<? extends Record1<?>> var1);
}

