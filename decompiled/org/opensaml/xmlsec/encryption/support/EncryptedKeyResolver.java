/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.support;

import java.util.Set;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;

public interface EncryptedKeyResolver {
    @Nonnull
    public Iterable<EncryptedKey> resolve(@Nonnull EncryptedData var1);

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<String> getRecipients();
}

