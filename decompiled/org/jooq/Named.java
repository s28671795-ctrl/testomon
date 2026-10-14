/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Comment;
import org.jooq.Name;
import org.jooq.QueryPart;

public interface Named
extends QueryPart {
    @NotNull
    public String getName();

    @NotNull
    public Name getQualifiedName();

    @NotNull
    public Name getUnqualifiedName();

    @NotNull
    public String getComment();

    @NotNull
    public Comment getCommentPart();

    @ApiStatus.Experimental
    @NotNull
    public Name $name();
}

