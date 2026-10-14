/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.trust;

import javax.annotation.Nonnull;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.trust.TrustEngine;

public interface TrustedCredentialTrustEngine<TokenType>
extends TrustEngine<TokenType> {
    @Nonnull
    public CredentialResolver getCredentialResolver();
}

