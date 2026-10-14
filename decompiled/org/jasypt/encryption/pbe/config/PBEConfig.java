/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.encryption.pbe.config;

import java.security.Provider;
import org.jasypt.iv.IvGenerator;
import org.jasypt.salt.SaltGenerator;

public interface PBEConfig {
    public String getAlgorithm();

    public String getPassword();

    public Integer getKeyObtentionIterations();

    public SaltGenerator getSaltGenerator();

    public IvGenerator getIvGenerator();

    public String getProviderName();

    public Provider getProvider();

    public Integer getPoolSize();
}

