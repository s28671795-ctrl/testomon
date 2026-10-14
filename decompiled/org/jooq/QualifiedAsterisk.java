/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.impl.QOM;

public interface QualifiedAsterisk
extends SelectFieldOrAsterisk {
    @NotNull
    public Table<?> qualifier();

    @Support
    @NotNull
    public QualifiedAsterisk except(String ... var1);

    @Support
    @NotNull
    public QualifiedAsterisk except(Name ... var1);

    @Support
    @NotNull
    public QualifiedAsterisk except(Field<?> ... var1);

    @ApiStatus.Experimental
    @NotNull
    public Table<?> $table();

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends Field<?>> $except();
}

