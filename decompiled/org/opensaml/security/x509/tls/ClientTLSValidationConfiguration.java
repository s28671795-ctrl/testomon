/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.tls;

import javax.annotation.Nullable;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.tls.CertificateNameOptions;

public interface ClientTLSValidationConfiguration {
    @Nullable
    public TrustEngine<? super X509Credential> getX509TrustEngine();

    @Nullable
    public CertificateNameOptions getCertificateNameOptions();
}

