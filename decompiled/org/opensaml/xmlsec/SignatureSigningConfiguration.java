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
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;

public interface SignatureSigningConfiguration
extends WhitelistBlacklistConfiguration {
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<Credential> getSigningCredentials();

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getSignatureAlgorithms();

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getSignatureReferenceDigestMethods();

    @Nullable
    public String getSignatureCanonicalizationAlgorithm();

    @Nullable
    public Integer getSignatureHMACOutputLength();

    @Nullable
    public NamedKeyInfoGeneratorManager getKeyInfoGeneratorManager();
}

