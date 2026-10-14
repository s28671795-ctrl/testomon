/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import org.opensaml.security.SecurityException;
import org.opensaml.security.x509.PKIXValidationInformation;
import org.opensaml.security.x509.PKIXValidationOptions;
import org.opensaml.security.x509.X509Credential;

public interface PKIXTrustEvaluator {
    public boolean validate(@Nonnull PKIXValidationInformation var1, @Nonnull X509Credential var2) throws SecurityException;

    @Nonnull
    public PKIXValidationOptions getPKIXValidationOptions();
}

