/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.time.Instant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.GeneratorStatementType;
import org.jooq.Record;
import org.jooq.Scope;
import org.jooq.Table;

public interface GeneratorContext<R extends Record, X extends Table<R>, T>
extends Scope {
    @NotNull
    public Instant renderTime();

    @Nullable
    public X table();

    @Nullable
    public Field<T> field();

    @Nullable
    public GeneratorStatementType statementType();
}

