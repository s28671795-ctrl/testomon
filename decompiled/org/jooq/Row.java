/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.Fields;
import org.jooq.Support;
import org.jooq.impl.QOM;

public interface Row
extends Fields,
FieldOrRow,
FieldOrRowOrSelect {
    public int size();

    @Support
    @NotNull
    public Condition isNull();

    @Support
    @NotNull
    public Condition isNotNull();

    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends Field<?>> $fields();
}

