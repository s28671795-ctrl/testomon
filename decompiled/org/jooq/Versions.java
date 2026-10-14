/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Version;

@ApiStatus.Experimental
public interface Versions
extends Iterable<Version> {
    @NotNull
    public Version root();

    @Nullable
    public Version get(String var1);
}

