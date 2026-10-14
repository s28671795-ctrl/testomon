/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Meta;
import org.jooq.MetaProvider;
import org.jooq.impl.MetaImpl;
import org.jooq.impl.NoConnectionProvider;
import org.jooq.tools.JooqLogger;

public class DefaultMetaProvider
implements MetaProvider {
    private static final JooqLogger log = JooqLogger.getLogger(DefaultMetaProvider.class);
    private final Configuration configuration;

    public DefaultMetaProvider(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Meta provide() {
        return new MetaImpl(this.configuration, null);
    }

    static Meta meta(Configuration configuration) {
        Meta meta = configuration.metaProvider().provide();
        if (meta instanceof MetaImpl && configuration.connectionProvider() instanceof NoConnectionProvider) {
            log.debug("No MetaProvider configured. For best results when looking up meta data, configure a MetaProvider, or connect to a database");
            return configuration.dsl().meta("");
        }
        return meta;
    }
}

