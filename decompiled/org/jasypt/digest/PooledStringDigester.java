/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.digest;

import java.security.Provider;
import org.jasypt.commons.CommonUtils;
import org.jasypt.digest.StandardStringDigester;
import org.jasypt.digest.StringDigester;
import org.jasypt.digest.config.DigesterConfig;
import org.jasypt.exceptions.AlreadyInitializedException;
import org.jasypt.salt.SaltGenerator;

public class PooledStringDigester
implements StringDigester {
    private final StandardStringDigester firstDigester = new StandardStringDigester();
    private DigesterConfig config = null;
    private int poolSize = 0;
    private boolean poolSizeSet = false;
    private StandardStringDigester[] pool;
    private int roundRobin = 0;
    private boolean initialized = false;

    public synchronized void setConfig(DigesterConfig config) {
        this.firstDigester.setConfig(config);
        this.config = config;
    }

    public synchronized void setAlgorithm(String algorithm) {
        this.firstDigester.setAlgorithm(algorithm);
    }

    public synchronized void setSaltSizeBytes(int saltSizeBytes) {
        this.firstDigester.setSaltSizeBytes(saltSizeBytes);
    }

    public synchronized void setIterations(int iterations) {
        this.firstDigester.setIterations(iterations);
    }

    public synchronized void setSaltGenerator(SaltGenerator saltGenerator) {
        this.firstDigester.setSaltGenerator(saltGenerator);
    }

    public synchronized void setProviderName(String providerName) {
        this.firstDigester.setProviderName(providerName);
    }

    public synchronized void setProvider(Provider provider) {
        this.firstDigester.setProvider(provider);
    }

    public synchronized void setInvertPositionOfSaltInMessageBeforeDigesting(boolean invertPositionOfSaltInMessageBeforeDigesting) {
        this.firstDigester.setInvertPositionOfSaltInMessageBeforeDigesting(invertPositionOfSaltInMessageBeforeDigesting);
    }

    public synchronized void setInvertPositionOfPlainSaltInEncryptionResults(boolean invertPositionOfPlainSaltInEncryptionResults) {
        this.firstDigester.setInvertPositionOfPlainSaltInEncryptionResults(invertPositionOfPlainSaltInEncryptionResults);
    }

    public synchronized void setUseLenientSaltSizeCheck(boolean useLenientSaltSizeCheck) {
        this.firstDigester.setUseLenientSaltSizeCheck(useLenientSaltSizeCheck);
    }

    public synchronized void setUnicodeNormalizationIgnored(boolean unicodeNormalizationIgnored) {
        this.firstDigester.setUnicodeNormalizationIgnored(unicodeNormalizationIgnored);
    }

    public synchronized void setStringOutputType(String stringOutputType) {
        this.firstDigester.setStringOutputType(stringOutputType);
    }

    public synchronized void setPrefix(String prefix) {
        this.firstDigester.setPrefix(prefix);
    }

    public synchronized void setSuffix(String suffix) {
        this.firstDigester.setSuffix(suffix);
    }

    public synchronized void setPoolSize(int poolSize) {
        CommonUtils.validateIsTrue(poolSize > 0, "Pool size be > 0");
        if (this.isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.poolSize = poolSize;
        this.poolSizeSet = true;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public synchronized void initialize() {
        if (!this.initialized) {
            if (this.config != null) {
                Integer configPoolSize = this.config.getPoolSize();
                int n = this.poolSize = this.poolSizeSet || configPoolSize == null ? this.poolSize : configPoolSize;
            }
            if (this.poolSize <= 0) {
                throw new IllegalArgumentException("Pool size must be set and > 0");
            }
            this.pool = new StandardStringDigester[this.poolSize];
            this.pool[0] = this.firstDigester;
            for (int i = 1; i < this.poolSize; ++i) {
                this.pool[i] = this.pool[i - 1].cloneDigester();
            }
            this.initialized = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String digest(String message) {
        int poolPosition;
        if (!this.isInitialized()) {
            this.initialize();
        }
        PooledStringDigester pooledStringDigester = this;
        synchronized (pooledStringDigester) {
            poolPosition = this.roundRobin;
            this.roundRobin = (this.roundRobin + 1) % this.poolSize;
        }
        return this.pool[poolPosition].digest(message);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean matches(String message, String digest) {
        int poolPosition;
        if (!this.isInitialized()) {
            this.initialize();
        }
        PooledStringDigester pooledStringDigester = this;
        synchronized (pooledStringDigester) {
            poolPosition = this.roundRobin;
            this.roundRobin = (this.roundRobin + 1) % this.poolSize;
        }
        return this.pool[poolPosition].matches(message, digest);
    }
}

