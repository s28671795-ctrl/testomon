/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.support;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.support.AbstractEncryptedKeyResolver;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRetrievalMethodEncryptedKeyResolver
extends AbstractEncryptedKeyResolver {
    private final Logger log = LoggerFactory.getLogger(SimpleRetrievalMethodEncryptedKeyResolver.class);

    public SimpleRetrievalMethodEncryptedKeyResolver() {
    }

    public SimpleRetrievalMethodEncryptedKeyResolver(@Nullable Set<String> recipients) {
        super(recipients);
    }

    public SimpleRetrievalMethodEncryptedKeyResolver(@Nullable String recipient) {
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
        for (RetrievalMethod rm : encryptedData.getKeyInfo().getRetrievalMethods()) {
            if (!Objects.equals(rm.getType(), "http://www.w3.org/2001/04/xmlenc#EncryptedKey")) continue;
            if (rm.getTransforms() != null) {
                this.log.warn("EncryptedKey RetrievalMethod has transforms, cannot process");
                continue;
            }
            EncryptedKey encKey = this.dereferenceURI(rm);
            if (encKey == null || !this.matchRecipient(encKey.getRecipient())) continue;
            resolvedEncKeys.add(encKey);
        }
        return resolvedEncKeys;
    }

    @Nullable
    protected EncryptedKey dereferenceURI(@Nonnull RetrievalMethod rm) {
        String uri = rm.getURI();
        if (Strings.isNullOrEmpty(uri) || !uri.startsWith("#")) {
            this.log.warn("EncryptedKey RetrievalMethod did not contain a same-document URI reference, cannot process");
            return null;
        }
        XMLObject target = rm.resolveIDFromRoot(uri.substring(1));
        if (target == null) {
            this.log.warn("EncryptedKey RetrievalMethod URI could not be dereferenced");
            return null;
        }
        if (!(target instanceof EncryptedKey)) {
            this.log.warn("The product of dereferencing the EncryptedKey RetrievalMethod was not an EncryptedKey");
            return null;
        }
        return (EncryptedKey)target;
    }
}

