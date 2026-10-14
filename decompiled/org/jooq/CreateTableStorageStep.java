/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateTableFinalStep;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Support;

public interface CreateTableStorageStep
extends CreateTableFinalStep {
    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateTableFinalStep storage(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateTableFinalStep storage(String var1, QueryPart ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateTableFinalStep storage(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public CreateTableFinalStep storage(String var1);
}

