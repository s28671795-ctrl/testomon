/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jooq.Batch;
import org.jooq.CheckReturnValue;
import org.jooq.Support;

public interface BatchBindStep
extends Batch {
    @Support
    @NotNull
    @CheckReturnValue
    public BatchBindStep bind(Object ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public BatchBindStep bind(Object[] ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public BatchBindStep bind(Map<String, Object> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public BatchBindStep bind(Map<String, Object> ... var1);
}

