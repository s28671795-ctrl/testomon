/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Scope;
import org.jooq.Transaction;

public interface TransactionContext
extends Scope {
    @Nullable
    public Transaction transaction();

    @NotNull
    public TransactionContext transaction(Transaction var1);

    @Nullable
    public Exception cause();

    @Nullable
    public Throwable causeThrowable();

    @NotNull
    public TransactionContext cause(Exception var1);

    @NotNull
    public TransactionContext causeThrowable(Throwable var1);
}

