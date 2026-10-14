/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.AbstractCredentialResolver;

public class StaticCredentialResolver
extends AbstractCredentialResolver {
    private List<Credential> creds;

    public StaticCredentialResolver(@Nonnull List<Credential> credentials) {
        Constraint.isNotNull(credentials, "Input credentials list cannot be null");
        this.creds = new ArrayList<Credential>(credentials);
    }

    public StaticCredentialResolver(@Nonnull Credential credential) {
        Constraint.isNotNull(credential, "Input credential cannot be null");
        this.creds = new ArrayList<Credential>();
        this.creds.add(credential);
    }

    @Override
    @Nonnull
    public Iterable<Credential> resolve(@Nullable CriteriaSet criteria) throws ResolverException {
        return this.creds;
    }
}

