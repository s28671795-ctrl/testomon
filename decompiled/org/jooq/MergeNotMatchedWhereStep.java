/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.MergeFinalStep;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface MergeNotMatchedWhereStep<R extends Record>
extends MergeFinalStep<R> {
    @Support(value={SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public MergeFinalStep<R> where(Condition var1);

    @Support(value={SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public MergeFinalStep<R> where(Field<Boolean> var1);
}

