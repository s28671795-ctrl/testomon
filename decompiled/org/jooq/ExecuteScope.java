/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.Nullable;
import org.jooq.ConverterContext;
import org.jooq.ExecuteContext;
import org.jooq.Scope;

public interface ExecuteScope
extends Scope {
    public ConverterContext converterContext();

    @Nullable
    public ExecuteContext executeContext();
}

