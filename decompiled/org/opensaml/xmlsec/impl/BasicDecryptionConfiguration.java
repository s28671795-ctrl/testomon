/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.impl.BasicWhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public class BasicDecryptionConfiguration
extends BasicWhitelistBlacklistConfiguration
implements DecryptionConfiguration {
    @Nullable
    private KeyInfoCredentialResolver dataKeyInfoCredentialResolver;
    @Nullable
    private KeyInfoCredentialResolver kekKeyInfoCredentialResolver;
    @Nullable
    private EncryptedKeyResolver encryptedKeyResolver;

    @Override
    @Nullable
    public KeyInfoCredentialResolver getDataKeyInfoCredentialResolver() {
        return this.dataKeyInfoCredentialResolver;
    }

    public void setDataKeyInfoCredentialResolver(@Nullable KeyInfoCredentialResolver resolver2) {
        this.dataKeyInfoCredentialResolver = resolver2;
    }

    @Override
    @Nullable
    public KeyInfoCredentialResolver getKEKKeyInfoCredentialResolver() {
        return this.kekKeyInfoCredentialResolver;
    }

    public void setKEKKeyInfoCredentialResolver(@Nullable KeyInfoCredentialResolver resolver2) {
        this.kekKeyInfoCredentialResolver = resolver2;
    }

    @Override
    @Nullable
    public EncryptedKeyResolver getEncryptedKeyResolver() {
        return this.encryptedKeyResolver;
    }

    public void setEncryptedKeyResolver(@Nullable EncryptedKeyResolver resolver2) {
        this.encryptedKeyResolver = resolver2;
    }
}

