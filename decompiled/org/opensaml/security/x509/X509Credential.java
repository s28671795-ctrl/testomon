/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.security.credential.Credential;

public interface X509Credential
extends Credential {
    @Nonnull
    public X509Certificate getEntityCertificate();

    @Nonnull
    public Collection<X509Certificate> getEntityCertificateChain();

    @Nullable
    public Collection<X509CRL> getCRLs();
}

