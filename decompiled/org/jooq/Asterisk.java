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
import org.jooq.impl.QOM;

public interface Asterisk
extends SelectFieldOrAsterisk {
    @Support
    @NotNull
    public Asterisk except(String ... var1);

    @Support
    @NotNull
    public Asterisk except(Name ... var1);

    @Support
    @NotNull
    public Asterisk except(Field<?> ... var1);

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends Field<?>> $except();
}

