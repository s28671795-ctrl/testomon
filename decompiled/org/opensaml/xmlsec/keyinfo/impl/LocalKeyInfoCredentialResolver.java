/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.criteria.KeyNameCriterion;
import org.opensaml.security.criteria.PublicKeyCriterion;
import org.opensaml.xmlsec.keyinfo.impl.BasicProviderKeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoProvider;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;

public class LocalKeyInfoCredentialResolver
extends BasicProviderKeyInfoCredentialResolver {
    private final CredentialResolver localCredResolver;

    public LocalKeyInfoCredentialResolver(@Nonnull List<KeyInfoProvider> keyInfoProviders, @Nonnull CredentialResolver localCredentialResolver) {
        super(keyInfoProviders);
        this.localCredResolver = Constraint.isNotNull(localCredentialResolver, "Local credential resolver cannot be null");
    }

    @Nonnull
    public CredentialResolver getLocalCredentialResolver() {
        return this.localCredResolver;
    }

    @Override
    protected void postProcess(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List<Credential> credentials) throws ResolverException {
        ArrayList<Credential> localCreds = new ArrayList<Credential>();
        for (Credential cred : credentials) {
            if (this.isLocalCredential(cred)) {
                localCreds.add(cred);
                continue;
            }
            if (cred.getPublicKey() == null) continue;
            localCreds.addAll(this.resolveByPublicKey(cred.getPublicKey()));
        }
        for (String keyName : kiContext.getKeyNames()) {
            localCreds.addAll(this.resolveByKeyName(keyName));
        }
        credentials.clear();
        credentials.addAll(localCreds);
    }

    protected boolean isLocalCredential(@Nonnull Credential credential) {
        return credential.getPrivateKey() != null || credential.getSecretKey() != null;
    }

    @Nonnull
    protected Collection<? extends Credential> resolveByKeyName(@Nonnull String keyName) throws ResolverException {
        ArrayList<Credential> localCreds = new ArrayList<Credential>();
        CriteriaSet criteriaSet = new CriteriaSet(new KeyNameCriterion(keyName));
        for (Credential cred : this.getLocalCredentialResolver().resolve(criteriaSet)) {
            if (!this.isLocalCredential(cred)) continue;
            localCreds.add(cred);
        }
        return localCreds;
    }

    @Nonnull
    protected Collection<? extends Credential> resolveByPublicKey(@Nonnull PublicKey publicKey) throws ResolverException {
        ArrayList<Credential> localCreds = new ArrayList<Credential>();
        CriteriaSet criteriaSet = new CriteriaSet(new PublicKeyCriterion(publicKey));
        for (Credential cred : this.getLocalCredentialResolver().resolve(criteriaSet)) {
            if (!this.isLocalCredential(cred)) continue;
            localCreds.add(cred);
        }
        return localCreds;
    }
}

