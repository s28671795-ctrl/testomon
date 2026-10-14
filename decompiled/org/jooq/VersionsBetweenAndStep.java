/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;

@Pro
public interface VersionsBetweenAndStep<R extends Record, T> {
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public Table<R> and(T var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public Table<R> and(Field<? extends T> var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public Table<R> andMaxvalue();
}

