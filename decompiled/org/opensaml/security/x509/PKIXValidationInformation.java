/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import javax.annotation.Nullable;

public interface PKIXValidationInformation {
    @Nullable
    public Integer getVerificationDepth();

    @Nullable
    public Collection<X509Certificate> getCertificates();

    @Nullable
    public Collection<X509CRL> getCRLs();
}

