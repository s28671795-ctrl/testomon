/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.impl;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import javax.annotation.Nullable;
import org.opensaml.security.x509.PKIXValidationInformation;

public class BasicPKIXValidationInformation
implements PKIXValidationInformation {
    private final Collection<X509Certificate> trustAnchors;
    private final Collection<X509CRL> trustedCRLs;
    private final Integer verificationDepth;

    public BasicPKIXValidationInformation(@Nullable Collection<X509Certificate> anchors, @Nullable Collection<X509CRL> crls, @Nullable Integer depth) {
        this.verificationDepth = depth;
        this.trustAnchors = anchors;
        this.trustedCRLs = crls;
    }

    @Override
    @Nullable
    public Collection<X509CRL> getCRLs() {
        return this.trustedCRLs;
    }

    @Override
    @Nullable
    public Collection<X509Certificate> getCertificates() {
        return this.trustAnchors;
    }

    @Override
    @Nullable
    public Integer getVerificationDepth() {
        return this.verificationDepth;
    }
}

