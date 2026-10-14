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
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.support.AbstractEncryptedKeyResolver;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleKeyInfoReferenceEncryptedKeyResolver
extends AbstractEncryptedKeyResolver {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SimpleKeyInfoReferenceEncryptedKeyResolver.class);
    private int depthLimit = 5;

    public SimpleKeyInfoReferenceEncryptedKeyResolver() {
        this((Set<String>)null);
    }

    public SimpleKeyInfoReferenceEncryptedKeyResolver(@Nullable Set<String> recipients) {
        super(recipients);
    }

    public SimpleKeyInfoReferenceEncryptedKeyResolver(@Nullable String recipient) {
        this(Collections.singleton(recipient));
    }

    public int getDepthLimit() {
        return this.depthLimit;
    }

    public void setDepthLimit(int limit) {
        this.depthLimit = Math.max(1, limit);
    }

    @Override
    @Nonnull
    public Iterable<EncryptedKey> resolve(@Nonnull EncryptedData encryptedData) {
        Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
        return this.resolveKeyInfo(encryptedData.getKeyInfo(), this.depthLimit);
    }

    @Nonnull
    protected Iterable<EncryptedKey> resolveKeyInfo(@Nullable KeyInfo keyInfo, int limit) {
        ArrayList<EncryptedKey> resolvedEncKeys = new ArrayList<EncryptedKey>();
        if (keyInfo == null) {
            return resolvedEncKeys;
        }
        if (limit < this.depthLimit) {
            for (EncryptedKey encKey : keyInfo.getEncryptedKeys()) {
                if (!this.matchRecipient(encKey.getRecipient())) continue;
                resolvedEncKeys.add(encKey);
            }
        }
        if (limit == 0) {
            this.log.info("Reached depth limit for KeyInfoReferences");
        } else {
            for (KeyInfoReference ref : keyInfo.getKeyInfoReferences()) {
                for (EncryptedKey encKey : this.resolveKeyInfo(this.dereferenceURI(ref), limit - 1)) {
                    resolvedEncKeys.add(encKey);
                }
            }
        }
        return resolvedEncKeys;
    }

    @Nullable
    protected KeyInfo dereferenceURI(@Nonnull KeyInfoReference ref) {
        String uri = ref.getURI();
        if (uri == null || uri.isEmpty() || !uri.startsWith("#")) {
            this.log.warn("EncryptedKey KeyInfoReference did not contain a same-document URI reference, cannot process");
            return null;
        }
        XMLObject target = ref.resolveIDFromRoot(uri.substring(1));
        if (target == null) {
            this.log.warn("EncryptedKey KeyInfoReference URI could not be dereferenced");
            return null;
        }
        if (!(target instanceof KeyInfo)) {
            this.log.warn("The product of dereferencing the EncryptedKey KeyInfoReference was not a KeyInfo");
            return null;
        }
        return (KeyInfo)target;
    }
}

