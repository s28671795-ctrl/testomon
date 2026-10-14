/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.impl;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.X509KeyManager;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.security.credential.AbstractCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.x509.X509Credential;

public class X509KeyManagerX509CredentialAdapter
extends AbstractCredential
implements X509Credential {
    private final String credentialAlias;
    private final X509KeyManager keyManager;

    public X509KeyManagerX509CredentialAdapter(@Nonnull X509KeyManager manager, @Nonnull String alias) {
        this.keyManager = Constraint.isNotNull(manager, "Key manager cannot be null");
        this.credentialAlias = Constraint.isNotNull(StringSupport.trimOrNull(alias), "Entity alias cannot be null");
    }

    @Override
    @Nullable
    public Collection<X509CRL> getCRLs() {
        return Collections.EMPTY_LIST;
    }

    @Override
    @Nonnull
    public X509Certificate getEntityCertificate() {
        X509Certificate[] certs = this.keyManager.getCertificateChain(this.credentialAlias);
        if (certs != null && certs.length > 0) {
            return certs[0];
        }
        return null;
    }

    @Override
    @Nonnull
    public Collection<X509Certificate> getEntityCertificateChain() {
        X509Certificate[] certs = this.keyManager.getCertificateChain(this.credentialAlias);
        if (certs != null && certs.length > 0) {
            return Arrays.asList(certs);
        }
        return null;
    }

    @Override
    @Nullable
    public PrivateKey getPrivateKey() {
        return this.keyManager.getPrivateKey(this.credentialAlias);
    }

    @Override
    @Nullable
    public PublicKey getPublicKey() {
        return this.getEntityCertificate().getPublicKey();
    }

    @Override
    @Nonnull
    public Class<? extends Credential> getCredentialType() {
        return X509Credential.class;
    }

    @Override
    public void setEntityId(@Nullable String newEntityID) {
        super.setEntityId(newEntityID);
    }

    @Override
    public void setUsageType(@Nonnull UsageType newUsageType) {
        super.setUsageType(newUsageType);
    }
}

