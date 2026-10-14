/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record2;
import org.jooq.ResultQuery;
import org.jooq.Support;
import org.jooq.WithStep;

public interface WithAsStep2 {
    @Support
    @NotNull
    @CheckReturnValue
    public WithStep as(ResultQuery<? extends Record2<?, ?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithStep asMaterialized(ResultQuery<? extends Record2<?, ?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithStep asNotMaterialized(ResultQuery<? extends Record2<?, ?>> var1);
}

