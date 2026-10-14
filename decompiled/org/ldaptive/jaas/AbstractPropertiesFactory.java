/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.util.Map;
import java.util.Properties;
import org.ldaptive.props.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPropertiesFactory {
    public static final String CACHE_ID = "cacheId";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static Properties createProperties(Map<String, ?> options) {
        Properties p = new Properties();
        for (Map.Entry<String, ?> entry : options.entrySet()) {
            if (entry.getKey().contains(".")) {
                p.setProperty(entry.getKey(), entry.getValue().toString());
                continue;
            }
            p.setProperty(PropertySource.PropertyDomain.AUTH.value() + entry.getKey(), entry.getValue().toString());
        }
        return p;
    }
}

