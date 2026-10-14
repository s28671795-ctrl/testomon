/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.support.AbstractEncryptedKeyResolver;

public class InlineEncryptedKeyResolver
extends AbstractEncryptedKeyResolver {
    public InlineEncryptedKeyResolver() {
    }

    public InlineEncryptedKeyResolver(@Nullable Set<String> recipients) {
        super(recipients);
    }

    public InlineEncryptedKeyResolver(@Nullable String recipient) {
        this(Collections.singleton(recipient));
    }

    @Override
    @Nonnull
    public Iterable<EncryptedKey> resolve(@Nonnull EncryptedData encryptedData) {
        Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
        ArrayList<EncryptedKey> resolvedEncKeys = new ArrayList<EncryptedKey>();
        if (encryptedData.getKeyInfo() == null) {
            return resolvedEncKeys;
        }
        for (EncryptedKey encKey : encryptedData.getKeyInfo().getEncryptedKeys()) {
            if (!this.matchRecipient(encKey.getRecipient())) continue;
            resolvedEncKeys.add(encKey);
        }
        return resolvedEncKeys;
    }
}

