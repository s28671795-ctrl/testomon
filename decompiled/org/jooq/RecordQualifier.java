/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DataType;
import org.jooq.Fields;
import org.jooq.Package;
import org.jooq.Qualified;
import org.jooq.Record;

public interface RecordQualifier<R extends Record>
extends Qualified,
Fields {
    @Nullable
    public Package getPackage();

    @NotNull
    public Class<? extends R> getRecordType();

    @NotNull
    public DataType<R> getDataType();

    @NotNull
    public R newRecord();
}

