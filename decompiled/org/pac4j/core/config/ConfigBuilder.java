/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.config;

import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ConfigBuilder.class);

    public static synchronized Config build(String factoryName, Object ... parameters) {
        try {
            logger.info("Build the configuration from factory: {}", (Object)factoryName);
            ConfigFactory factory2 = (ConfigFactory)CommonHelper.getConstructor(factoryName).newInstance(new Object[0]);
            return factory2.build(parameters);
        }
        catch (Exception e) {
            throw new TechnicalException("Cannot build configuration", e);
        }
    }
}

