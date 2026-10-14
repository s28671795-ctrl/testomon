/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.ConstraintForeignKeyOnStep;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Support;
import org.jooq.Table;

public interface ConstraintForeignKeyReferencesStep1<T1> {
    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(String var1);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(String var1, String var2);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(Name var1);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(Name var1, Name var2);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(Table<?> var1);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(Table<?> var1, Field<T1> var2);
}

