/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential;

import java.security.PrivateKey;
import java.security.PublicKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import org.opensaml.security.credential.AbstractCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.MutableCredential;
import org.opensaml.security.credential.UsageType;

public class BasicCredential
extends AbstractCredential
implements MutableCredential {
    public BasicCredential(@Nonnull PublicKey publicKey) {
        this.setPublicKey(publicKey);
    }

    public BasicCredential(@Nonnull PublicKey publicKey, @Nullable PrivateKey privateKey) {
        this.setPublicKey(publicKey);
        if (privateKey != null) {
            this.setPrivateKey(privateKey);
        }
    }

    public BasicCredential(@Nonnull SecretKey secretKey) {
        this.setSecretKey(secretKey);
    }

    protected BasicCredential() {
    }

    @Override
    public Class<? extends Credential> getCredentialType() {
        return Credential.class;
    }

    @Override
    public void setEntityId(@Nullable String newEntityId) {
        super.setEntityId(newEntityId);
    }

    @Override
    public void setUsageType(@Nonnull UsageType newUsageType) {
        super.setUsageType(newUsageType);
    }

    @Override
    public void setPublicKey(@Nonnull PublicKey newPublicKey) {
        super.setPublicKey(newPublicKey);
    }

    @Override
    public void setPrivateKey(@Nonnull PrivateKey newPrivateKey) {
        super.setPrivateKey(newPrivateKey);
    }

    @Override
    public void setSecretKey(@Nonnull SecretKey newSecretKey) {
        super.setSecretKey(newSecretKey);
    }
}

