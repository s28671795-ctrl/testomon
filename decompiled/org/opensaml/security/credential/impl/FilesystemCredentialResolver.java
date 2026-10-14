/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.impl;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.AbstractCriteriaFilteringCredentialResolver;

public class FilesystemCredentialResolver
extends AbstractCriteriaFilteringCredentialResolver {
    public FilesystemCredentialResolver(@Nonnull String credentialDirectory, @Nonnull Map<String, String> passwords) {
    }

    @Override
    @Nonnull
    protected Iterable<Credential> resolveFromSource(@Nullable CriteriaSet criteriaSet) {
        throw new UnsupportedOperationException("Functionality not yet implemented");
    }
}

