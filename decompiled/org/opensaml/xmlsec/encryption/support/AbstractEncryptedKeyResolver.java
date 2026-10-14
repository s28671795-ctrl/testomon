/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.support;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;

public abstract class AbstractEncryptedKeyResolver
implements EncryptedKeyResolver {
    private final Collection<String> recipients;

    public AbstractEncryptedKeyResolver() {
        this.recipients = Collections.emptySet();
    }

    public AbstractEncryptedKeyResolver(@Nullable Set<String> newRecipents) {
        this.recipients = new HashSet<String>(StringSupport.normalizeStringCollection(newRecipents));
    }

    public AbstractEncryptedKeyResolver(@Nullable String recipient) {
        String trimmed = StringSupport.trimOrNull(recipient);
        this.recipients = trimmed != null ? Collections.singleton(trimmed) : Collections.emptySet();
    }

    @Override
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<String> getRecipients() {
        return ImmutableSet.copyOf(this.recipients);
    }

    protected boolean matchRecipient(@Nullable String recipient) {
        String trimmedRecipient = StringSupport.trimOrNull(recipient);
        if (trimmedRecipient == null || this.recipients.isEmpty()) {
            return true;
        }
        return this.recipients.contains(trimmedRecipient);
    }

    protected boolean matchCarriedKeyName(@Nonnull EncryptedData encryptedData, @Nonnull EncryptedKey encryptedKey) {
        Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
        Constraint.isNotNull(encryptedKey, "EncryptedKey cannot be null");
        if (encryptedKey.getCarriedKeyName() == null || Strings.isNullOrEmpty(encryptedKey.getCarriedKeyName().getValue())) {
            return true;
        }
        if (encryptedData.getKeyInfo() == null || encryptedData.getKeyInfo().getKeyNames().isEmpty()) {
            return false;
        }
        String keyCarriedKeyName = encryptedKey.getCarriedKeyName().getValue();
        List<String> dataKeyNames = KeyInfoSupport.getKeyNames(encryptedData.getKeyInfo());
        return dataKeyNames.contains(keyCarriedKeyName);
    }

    protected boolean matchDataReference(@Nonnull EncryptedData encryptedData, @Nonnull EncryptedKey encryptedKey) {
        Constraint.isNotNull(encryptedData, "EncryptedData cannot be null");
        Constraint.isNotNull(encryptedKey, "EncryptedKey cannot be null");
        if (encryptedKey.getReferenceList() == null || encryptedKey.getReferenceList().getDataReferences().isEmpty()) {
            return true;
        }
        if (Strings.isNullOrEmpty(encryptedData.getID())) {
            return false;
        }
        List<DataReference> drlist = encryptedKey.getReferenceList().getDataReferences();
        for (DataReference dr : drlist) {
            if (Strings.isNullOrEmpty(dr.getURI()) || !dr.getURI().startsWith("#") || dr.resolveIDFromRoot(dr.getURI().substring(1)) != encryptedData) continue;
            return true;
        }
        return false;
    }
}

