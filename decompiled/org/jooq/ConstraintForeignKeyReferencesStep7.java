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

public interface ConstraintForeignKeyReferencesStep7<T1, T2, T3, T4, T5, T6, T7> {
    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(String var1);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(Name var1);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(Table<?> var1);

    @Support
    @NotNull
    public ConstraintForeignKeyOnStep references(Table<?> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8);
}

