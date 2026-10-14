/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface Node<N extends Node<N>> {
    @NotNull
    public String id();

    @NotNull
    public String message();

    @NotNull
    public N root();

    @NotNull
    public List<N> parents();
}

