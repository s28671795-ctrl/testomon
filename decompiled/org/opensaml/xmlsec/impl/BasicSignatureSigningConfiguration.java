/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.impl.BasicWhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSignatureSigningConfiguration
extends BasicWhitelistBlacklistConfiguration
implements SignatureSigningConfiguration {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(BasicSignatureSigningConfiguration.class);
    @Nonnull
    @NonnullElements
    private List<Credential> signingCredentials = Collections.emptyList();
    @Nonnull
    @NonnullElements
    private List<String> signatureAlgorithms = Collections.emptyList();
    @Nonnull
    @NonnullElements
    private List<String> signatureReferenceDigestMethods = Collections.emptyList();
    @Nullable
    private String signatureCanonicalization;
    @Nullable
    private Integer signatureHMACOutputLength;
    @Nullable
    private NamedKeyInfoGeneratorManager keyInfoGeneratorManager;

    @Override
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<Credential> getSigningCredentials() {
        return ImmutableList.copyOf(this.signingCredentials);
    }

    public void setSigningCredentials(@Nullable List<Credential> credentials) {
        if (credentials == null) {
            this.signingCredentials = Collections.emptyList();
            return;
        }
        this.signingCredentials = new ArrayList<Credential>(Collections2.filter(credentials, Predicates.notNull()));
    }

    @Override
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getSignatureAlgorithms() {
        return ImmutableList.copyOf(this.signatureAlgorithms);
    }

    public void setSignatureAlgorithms(@Nullable List<String> algorithms) {
        if (algorithms == null) {
            this.signatureAlgorithms = Collections.emptyList();
            return;
        }
        this.signatureAlgorithms = new ArrayList<String>(StringSupport.normalizeStringCollection(algorithms));
    }

    @Override
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getSignatureReferenceDigestMethods() {
        return ImmutableList.copyOf(this.signatureReferenceDigestMethods);
    }

    public void setSignatureReferenceDigestMethods(@Nullable List<String> algorithms) {
        if (algorithms == null) {
            this.signatureReferenceDigestMethods = Collections.emptyList();
            return;
        }
        this.signatureReferenceDigestMethods = new ArrayList<String>(StringSupport.normalizeStringCollection(algorithms));
    }

    @Override
    @Nullable
    public String getSignatureCanonicalizationAlgorithm() {
        return this.signatureCanonicalization;
    }

    public void setSignatureCanonicalizationAlgorithm(@Nullable String algorithmURI) {
        this.signatureCanonicalization = StringSupport.trimOrNull(algorithmURI);
    }

    @Override
    @Nullable
    public Integer getSignatureHMACOutputLength() {
        return this.signatureHMACOutputLength;
    }

    public void setSignatureHMACOutputLength(@Nullable Integer length) {
        this.signatureHMACOutputLength = length;
    }

    @Override
    @Nullable
    public NamedKeyInfoGeneratorManager getKeyInfoGeneratorManager() {
        return this.keyInfoGeneratorManager;
    }

    public void setKeyInfoGeneratorManager(@Nullable NamedKeyInfoGeneratorManager keyInfoManager) {
        this.keyInfoGeneratorManager = keyInfoManager;
    }
}

