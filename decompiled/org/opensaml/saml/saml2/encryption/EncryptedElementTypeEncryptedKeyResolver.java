/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.encryption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nullable;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.support.AbstractEncryptedKeyResolver;

public class EncryptedElementTypeEncryptedKeyResolver
extends AbstractEncryptedKeyResolver {
    public EncryptedElementTypeEncryptedKeyResolver() {
    }

    public EncryptedElementTypeEncryptedKeyResolver(@Nullable Set<String> recipients) {
        super(recipients);
    }

    public EncryptedElementTypeEncryptedKeyResolver(@Nullable String recipient) {
        this(Collections.singleton(recipient));
    }

    @Override
    public Iterable<EncryptedKey> resolve(EncryptedData encryptedData) {
        ArrayList<EncryptedKey> resolvedEncKeys = new ArrayList<EncryptedKey>();
        if (!(encryptedData.getParent() instanceof EncryptedElementType)) {
            return resolvedEncKeys;
        }
        EncryptedElementType encElementType = (EncryptedElementType)encryptedData.getParent();
        for (EncryptedKey encKey : encElementType.getEncryptedKeys()) {
            if (!this.matchRecipient(encKey.getRecipient())) continue;
            resolvedEncKeys.add(encKey);
        }
        return resolvedEncKeys;
    }
}

