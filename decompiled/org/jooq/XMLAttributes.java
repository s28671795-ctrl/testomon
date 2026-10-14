/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.impl.QOM;

public interface XMLAttributes
extends QueryPart {
    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends Field<?>> $attributes();
}

