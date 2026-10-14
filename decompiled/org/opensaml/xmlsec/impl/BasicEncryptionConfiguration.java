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
import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.KeyTransportAlgorithmPredicate;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.impl.BasicWhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicEncryptionConfiguration
extends BasicWhitelistBlacklistConfiguration
implements EncryptionConfiguration {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(BasicEncryptionConfiguration.class);
    @Nonnull
    @NonnullElements
    private List<Credential> dataEncryptionCredentials = Collections.emptyList();
    @Nonnull
    @NonnullElements
    private List<String> dataEncryptionAlgorithms = Collections.emptyList();
    @Nonnull
    @NonnullElements
    private List<Credential> keyTransportEncryptionCredentials = Collections.emptyList();
    @Nonnull
    @NonnullElements
    private List<String> keyTransportEncryptionAlgorithms = Collections.emptyList();
    @Nullable
    private NamedKeyInfoGeneratorManager dataKeyInfoGeneratorManager;
    @Nullable
    private NamedKeyInfoGeneratorManager keyTransportKeyInfoGeneratorManager;
    @Nullable
    private RSAOAEPParameters rsaOAEPParameters;
    private boolean rsaOAEPParametersMerge = true;
    @Nullable
    private KeyTransportAlgorithmPredicate keyTransportPredicate;

    @Override
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<Credential> getDataEncryptionCredentials() {
        return ImmutableList.copyOf(this.dataEncryptionCredentials);
    }

    public void setDataEncryptionCredentials(@Nullable List<Credential> credentials) {
        if (credentials == null) {
            this.dataEncryptionCredentials = Collections.emptyList();
            return;
        }
        this.dataEncryptionCredentials = new ArrayList<Credential>(Collections2.filter(credentials, Predicates.notNull()));
    }

    @Override
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getDataEncryptionAlgorithms() {
        return ImmutableList.copyOf(this.dataEncryptionAlgorithms);
    }

    public void setDataEncryptionAlgorithms(@Nullable List<String> algorithms) {
        if (algorithms == null) {
            this.dataEncryptionAlgorithms = Collections.emptyList();
            return;
        }
        this.dataEncryptionAlgorithms = new ArrayList<String>(StringSupport.normalizeStringCollection(algorithms));
    }

    @Override
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<Credential> getKeyTransportEncryptionCredentials() {
        return ImmutableList.copyOf(this.keyTransportEncryptionCredentials);
    }

    public void setKeyTransportEncryptionCredentials(@Nullable List<Credential> credentials) {
        if (credentials == null) {
            this.keyTransportEncryptionCredentials = Collections.emptyList();
            return;
        }
        this.keyTransportEncryptionCredentials = new ArrayList<Credential>(Collections2.filter(credentials, Predicates.notNull()));
    }

    @Override
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<String> getKeyTransportEncryptionAlgorithms() {
        return ImmutableList.copyOf(this.keyTransportEncryptionAlgorithms);
    }

    public void setKeyTransportEncryptionAlgorithms(@Nullable List<String> algorithms) {
        if (algorithms == null) {
            this.keyTransportEncryptionAlgorithms = Collections.emptyList();
            return;
        }
        this.keyTransportEncryptionAlgorithms = new ArrayList<String>(StringSupport.normalizeStringCollection(algorithms));
    }

    @Override
    @Nullable
    public NamedKeyInfoGeneratorManager getDataKeyInfoGeneratorManager() {
        return this.dataKeyInfoGeneratorManager;
    }

    public void setDataKeyInfoGeneratorManager(@Nullable NamedKeyInfoGeneratorManager keyInfoManager) {
        this.dataKeyInfoGeneratorManager = keyInfoManager;
    }

    @Override
    @Nullable
    public NamedKeyInfoGeneratorManager getKeyTransportKeyInfoGeneratorManager() {
        return this.keyTransportKeyInfoGeneratorManager;
    }

    public void setKeyTransportKeyInfoGeneratorManager(@Nullable NamedKeyInfoGeneratorManager keyInfoManager) {
        this.keyTransportKeyInfoGeneratorManager = keyInfoManager;
    }

    @Override
    @Nullable
    public RSAOAEPParameters getRSAOAEPParameters() {
        return this.rsaOAEPParameters;
    }

    public void setRSAOAEPParameters(@Nullable RSAOAEPParameters params) {
        this.rsaOAEPParameters = params;
    }

    @Override
    public boolean isRSAOAEPParametersMerge() {
        return this.rsaOAEPParametersMerge;
    }

    public void setRSAOAEPParametersMerge(boolean flag) {
        this.rsaOAEPParametersMerge = flag;
    }

    @Override
    @Nullable
    public KeyTransportAlgorithmPredicate getKeyTransportAlgorithmPredicate() {
        return this.keyTransportPredicate;
    }

    public void setKeyTransportAlgorithmPredicate(KeyTransportAlgorithmPredicate predicate) {
        this.keyTransportPredicate = predicate;
    }
}

