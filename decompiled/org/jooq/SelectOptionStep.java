/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record;
import org.jooq.SelectUnionStep;
import org.jooq.Support;

public interface SelectOptionStep<R extends Record>
extends SelectUnionStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public SelectUnionStep<R> option(String var1);
}

