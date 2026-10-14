/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.impl;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.AbstractCriteriaFilteringCredentialResolver;

public class CollectionCredentialResolver
extends AbstractCriteriaFilteringCredentialResolver {
    private final Collection<Credential> collection;

    public CollectionCredentialResolver() {
        this.collection = new ArrayList<Credential>();
    }

    public CollectionCredentialResolver(@Nonnull Collection<Credential> credentials) {
        this.collection = credentials;
    }

    @Nonnull
    public Collection<Credential> getCollection() {
        return this.collection;
    }

    @Override
    @Nonnull
    protected Iterable<Credential> resolveFromSource(@Nullable CriteriaSet criteriaSet) throws ResolverException {
        return this.collection;
    }
}

