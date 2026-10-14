/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.ExecuteType;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.Scope;

public interface RecordContext
extends Scope {
    @NotNull
    public ExecuteType type();

    @NotNull
    public Record record();

    @NotNull
    public RecordType<?> recordType();

    @NotNull
    public @NotNull Record @NotNull [] batchRecords();

    @Nullable
    public Exception exception();
}

