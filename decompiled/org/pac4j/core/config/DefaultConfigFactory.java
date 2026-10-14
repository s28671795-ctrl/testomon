/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.config;

import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;

public class DefaultConfigFactory
implements ConfigFactory {
    protected Config config;

    public DefaultConfigFactory(Config config) {
        this.config = config;
    }

    @Override
    public Config build(Object ... parameters) {
        return this.config;
    }
}

