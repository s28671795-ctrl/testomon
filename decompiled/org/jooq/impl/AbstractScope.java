/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.time.Instant;
import java.util.Map;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.conf.Settings;
import org.jooq.impl.DataMap;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.Tools;

abstract class AbstractScope
implements Scope {
    final Configuration configuration;
    final Map<Object, Object> data;
    final Instant creationTime;

    AbstractScope(Configuration configuration) {
        this(configuration, null);
    }

    AbstractScope(Configuration configuration, Map<Object, Object> data) {
        if (configuration == null) {
            configuration = new DefaultConfiguration();
        }
        if (data == null) {
            data = new DataMap();
        }
        this.configuration = configuration;
        this.data = data;
        this.creationTime = configuration.clock().instant();
    }

    @Override
    public final Instant creationTime() {
        return this.creationTime;
    }

    @Override
    public final Configuration configuration() {
        return this.configuration;
    }

    @Override
    public final DSLContext dsl() {
        return this.configuration().dsl();
    }

    @Override
    public final Settings settings() {
        return Tools.settings(this.configuration());
    }

    @Override
    public final SQLDialect dialect() {
        return Tools.configuration(this.configuration()).dialect();
    }

    @Override
    public final SQLDialect family() {
        return this.dialect().family();
    }

    @Override
    public final Map<Object, Object> data() {
        return this.data;
    }

    @Override
    public final Object data(Object key) {
        return this.data.get(key);
    }

    @Override
    public final Object data(Object key, Object value) {
        return this.data.put(key, value);
    }
}

