/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;

public abstract class AbstractCredentialResolver
implements CredentialResolver {
    @Override
    @Nullable
    public Credential resolveSingle(@Nullable CriteriaSet criteriaSet) throws ResolverException {
        Iterable<Credential> creds = this.resolve(criteriaSet);
        if (creds.iterator().hasNext()) {
            return creds.iterator().next();
        }
        return null;
    }

    @Override
    @Nonnull
    public abstract Iterable<Credential> resolve(@Nullable CriteriaSet var1) throws ResolverException;
}

