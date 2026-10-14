/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.resolver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.resolver.ResolverException;

public interface Resolver<ProductType, CriteriaType> {
    @Nonnull
    @NonnullElements
    public Iterable<ProductType> resolve(@Nullable CriteriaType var1) throws ResolverException;

    @Nullable
    public ProductType resolveSingle(@Nullable CriteriaType var1) throws ResolverException;
}

