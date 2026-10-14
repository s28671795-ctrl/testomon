/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.tls;

import javax.annotation.Nullable;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.tls.CertificateNameOptions;

public class ClientTLSValidationParameters {
    @Nullable
    private TrustEngine<? super X509Credential> x509TrustEngine;
    @Nullable
    private CertificateNameOptions certificateNameOptions;

    @Nullable
    public TrustEngine<? super X509Credential> getX509TrustEngine() {
        return this.x509TrustEngine;
    }

    public void setX509TrustEngine(@Nullable TrustEngine<? super X509Credential> engine) {
        this.x509TrustEngine = engine;
    }

    @Nullable
    public CertificateNameOptions getCertificateNameOptions() {
        return this.certificateNameOptions;
    }

    public void setCertificateNameOptions(@Nullable CertificateNameOptions options) {
        this.certificateNameOptions = options;
    }
}

