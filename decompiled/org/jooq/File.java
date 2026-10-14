/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.ContentType;

@ApiStatus.Experimental
public interface File {
    @NotNull
    public String path();

    @NotNull
    public String name();

    @Nullable
    public String content();

    @NotNull
    public ContentType type();
}

