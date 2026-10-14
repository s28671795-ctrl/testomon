/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectWithTiesAfterOffsetStep;
import org.jooq.Support;

public interface SelectLimitPercentAfterOffsetStep<R extends Record>
extends SelectWithTiesAfterOffsetStep<R> {
    @Support(value={SQLDialect.H2, SQLDialect.ORACLE12C, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public SelectWithTiesAfterOffsetStep<R> percent();
}

