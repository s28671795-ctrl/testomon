/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.digest.config;

import java.security.Provider;
import org.jasypt.salt.SaltGenerator;

public interface DigesterConfig {
    public String getAlgorithm();

    public Integer getSaltSizeBytes();

    public Integer getIterations();

    public SaltGenerator getSaltGenerator();

    public String getProviderName();

    public Provider getProvider();

    public Boolean getInvertPositionOfSaltInMessageBeforeDigesting();

    public Boolean getInvertPositionOfPlainSaltInEncryptionResults();

    public Boolean getUseLenientSaltSizeCheck();

    public Integer getPoolSize();
}

