/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.impl;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.security.credential.AbstractCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyStoreX509CredentialAdapter
extends AbstractCredential
implements X509Credential {
    private Logger log = LoggerFactory.getLogger(KeyStoreX509CredentialAdapter.class);
    private final KeyStore keyStore;
    private final String credentialAlias;
    private final char[] keyPassword;

    public KeyStoreX509CredentialAdapter(@Nonnull KeyStore store, @Nonnull String alias, @Nullable char[] password) {
        this.keyStore = Constraint.isNotNull(store, "Keystore cannot be null");
        this.credentialAlias = Constraint.isNotNull(StringSupport.trimOrNull(alias), "Keystore alias cannot be null or empty");
        this.keyPassword = password;
    }

    @Override
    @Nullable
    public Collection<X509CRL> getCRLs() {
        return Collections.EMPTY_LIST;
    }

    @Override
    @Nonnull
    public X509Certificate getEntityCertificate() {
        try {
            return (X509Certificate)this.keyStore.getCertificate(this.credentialAlias);
        }
        catch (KeyStoreException e) {
            this.log.error("Error accessing {} certificates in keystore", (Object)this.credentialAlias, (Object)e);
            return null;
        }
    }

    @Override
    @Nonnull
    public Collection<X509Certificate> getEntityCertificateChain() {
        ArrayList<X509Certificate> certsCollection = Collections.EMPTY_LIST;
        try {
            Certificate[] certs = this.keyStore.getCertificateChain(this.credentialAlias);
            if (certs != null) {
                certsCollection = new ArrayList<X509Certificate>(certs.length);
                for (Certificate cert : certs) {
                    certsCollection.add((X509Certificate)cert);
                }
            }
        }
        catch (KeyStoreException e) {
            this.log.error("Error accessing {} certificates in keystore", (Object)this.credentialAlias, (Object)e);
        }
        return certsCollection;
    }

    @Override
    @Nullable
    public PrivateKey getPrivateKey() {
        try {
            return (PrivateKey)this.keyStore.getKey(this.credentialAlias, this.keyPassword);
        }
        catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            this.log.error("Error accessing {} private key in keystore", (Object)this.credentialAlias, (Object)e);
            return null;
        }
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

