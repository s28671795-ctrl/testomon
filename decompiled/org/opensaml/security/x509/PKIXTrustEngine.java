/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.PKIXValidationInformationResolver;

public interface PKIXTrustEngine<TokenType>
extends TrustEngine<TokenType> {
    @Nonnull
    public PKIXValidationInformationResolver getPKIXResolver();
}

