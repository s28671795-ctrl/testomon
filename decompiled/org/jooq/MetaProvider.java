/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Meta;

@FunctionalInterface
public interface MetaProvider {
    @NotNull
    public Meta provide();
}

