/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.ColumnElement;

public interface Comment
extends ColumnElement {
    public String getComment();

    @ApiStatus.Experimental
    @NotNull
    public String $comment();
}

