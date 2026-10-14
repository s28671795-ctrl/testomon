/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;

public final class CredentialSupport {
    private CredentialSupport() {
    }

    @Nullable
    public static Key extractEncryptionKey(@Nullable Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential.getPublicKey() != null) {
            return credential.getPublicKey();
        }
        return credential.getSecretKey();
    }

    @Nullable
    public static Key extractDecryptionKey(@Nullable Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential.getPrivateKey() != null) {
            return credential.getPrivateKey();
        }
        return credential.getSecretKey();
    }

    @Nullable
    public static Key extractSigningKey(@Nullable Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential.getPrivateKey() != null) {
            return credential.getPrivateKey();
        }
        return credential.getSecretKey();
    }

    @Nullable
    public static Key extractVerificationKey(@Nullable Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential.getPublicKey() != null) {
            return credential.getPublicKey();
        }
        return credential.getSecretKey();
    }

    @Nonnull
    public static BasicCredential getSimpleCredential(@Nonnull SecretKey secretKey) {
        return new BasicCredential(secretKey);
    }

    @Nonnull
    public static BasicCredential getSimpleCredential(@Nonnull PublicKey publicKey, @Nullable PrivateKey privateKey) {
        if (privateKey != null) {
            return new BasicCredential(publicKey, privateKey);
        }
        return new BasicCredential(publicKey);
    }

    @Nonnull
    public static BasicX509Credential getSimpleCredential(@Nonnull X509Certificate cert, @Nullable PrivateKey privateKey) {
        if (privateKey != null) {
            return new BasicX509Credential(cert, privateKey);
        }
        return new BasicX509Credential(cert);
    }
}

