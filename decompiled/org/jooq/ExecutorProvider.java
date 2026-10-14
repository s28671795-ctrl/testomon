/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExecutorProvider {
    @NotNull
    public Executor provide();
}

