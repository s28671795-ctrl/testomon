/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.X509Credential;

public class BasicX509Credential
extends BasicCredential
implements X509Credential {
    private X509Certificate entityCert;
    private Collection<X509Certificate> entityCertChain;
    private Collection<X509CRL> crls;

    public BasicX509Credential(@Nonnull X509Certificate entityCertificate) {
        this.setEntityCertificate(entityCertificate);
    }

    public BasicX509Credential(@Nonnull X509Certificate entityCertificate, @Nonnull PrivateKey privateKey) {
        this.setEntityCertificate(entityCertificate);
        this.setPrivateKey(privateKey);
    }

    @Override
    @Nonnull
    public Class<? extends Credential> getCredentialType() {
        return X509Credential.class;
    }

    @Override
    @Nullable
    public Collection<X509CRL> getCRLs() {
        return this.crls;
    }

    public void setCRLs(@Nullable Collection<X509CRL> newCRLs) {
        this.crls = newCRLs;
    }

    @Override
    @Nonnull
    public X509Certificate getEntityCertificate() {
        return this.entityCert;
    }

    public void setEntityCertificate(@Nonnull X509Certificate newEntityCertificate) {
        Constraint.isNotNull(newEntityCertificate, "Credential certificate cannot be null");
        this.entityCert = newEntityCertificate;
    }

    @Override
    @Nonnull
    public PublicKey getPublicKey() {
        return this.getEntityCertificate().getPublicKey();
    }

    @Override
    public void setPublicKey(PublicKey newPublicKey) {
        throw new UnsupportedOperationException("Public key may not be set explicitly on an X509 credential");
    }

    @Override
    @Nonnull
    public Collection<X509Certificate> getEntityCertificateChain() {
        if (this.entityCertChain == null) {
            LazySet<X509Certificate> constructedChain = new LazySet<X509Certificate>();
            constructedChain.add(this.entityCert);
            return constructedChain;
        }
        return this.entityCertChain;
    }

    public void setEntityCertificateChain(@Nonnull Collection<X509Certificate> newCertificateChain) {
        Constraint.isNotNull(newCertificateChain, "Certificate chain collection cannot be null");
        Constraint.isNotEmpty(newCertificateChain, "Certificate chain collection cannot be empty");
        this.entityCertChain = new ArrayList<X509Certificate>(newCertificateChain);
    }

    @Override
    @Nullable
    public SecretKey getSecretKey() {
        return null;
    }

    @Override
    public void setSecretKey(SecretKey newSecretKey) {
        throw new UnsupportedOperationException("An X509Credential may not contain a secret key");
    }
}

