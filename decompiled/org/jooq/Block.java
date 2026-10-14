/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.RowCountQuery;
import org.jooq.Statement;
import org.jooq.impl.QOM;

public interface Block
extends RowCountQuery {
    @ApiStatus.Experimental
    @NotNull
    public QOM.UnmodifiableList<? extends Statement> $statements();
}

