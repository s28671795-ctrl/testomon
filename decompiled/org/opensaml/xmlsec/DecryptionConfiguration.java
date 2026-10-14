/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public interface DecryptionConfiguration
extends WhitelistBlacklistConfiguration {
    @Nullable
    public KeyInfoCredentialResolver getDataKeyInfoCredentialResolver();

    @Nullable
    public KeyInfoCredentialResolver getKEKKeyInfoCredentialResolver();

    @Nullable
    public EncryptedKeyResolver getEncryptedKeyResolver();
}

