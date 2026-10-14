/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.KeyTransportAlgorithmPredicate;
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;

public interface EncryptionConfiguration
extends WhitelistBlacklistConfiguration {
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<Credential> getDataEncryptionCredentials();

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getDataEncryptionAlgorithms();

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<Credential> getKeyTransportEncryptionCredentials();

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getKeyTransportEncryptionAlgorithms();

    @Nullable
    public NamedKeyInfoGeneratorManager getDataKeyInfoGeneratorManager();

    @Nullable
    public NamedKeyInfoGeneratorManager getKeyTransportKeyInfoGeneratorManager();

    @Nullable
    public RSAOAEPParameters getRSAOAEPParameters();

    public boolean isRSAOAEPParametersMerge();

    @Nullable
    public KeyTransportAlgorithmPredicate getKeyTransportAlgorithmPredicate();
}

