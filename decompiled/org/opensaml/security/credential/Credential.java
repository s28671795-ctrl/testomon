/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import org.opensaml.security.credential.CredentialContextSet;
import org.opensaml.security.credential.UsageType;

public interface Credential {
    @Nullable
    public String getEntityId();

    @Nullable
    public UsageType getUsageType();

    @Nonnull
    public Collection<String> getKeyNames();

    @Nullable
    public PublicKey getPublicKey();

    @Nullable
    public PrivateKey getPrivateKey();

    @Nullable
    public SecretKey getSecretKey();

    @Nullable
    public CredentialContextSet getCredentialContextSet();

    @Nonnull
    public Class<? extends Credential> getCredentialType();
}

