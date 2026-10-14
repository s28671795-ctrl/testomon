/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Pro;
import org.jooq.Replacer;
import org.jooq.Traverser;

public interface QueryPart
extends Serializable {
    public String toString();

    public boolean equals(Object var1);

    public int hashCode();

    @ApiStatus.Experimental
    @Pro
    public <R> R $traverse(Traverser<?, R> var1);

    @ApiStatus.Experimental
    @Pro
    default public <R> R $traverse(Supplier<R> supplier, BiFunction<? super R, ? super QueryPart, ? extends R> before) {
        return this.$traverse(Traverser.of(supplier, before));
    }

    @CheckReturnValue
    @ApiStatus.Experimental
    @NotNull
    @Pro
    public QueryPart $replace(Replacer var1);

    @CheckReturnValue
    @ApiStatus.Experimental
    @NotNull
    @Pro
    default public QueryPart $replace(Function<? super QueryPart, ? extends QueryPart> replacer) {
        return this.$replace(Replacer.of(replacer));
    }
}

