/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import java.io.IOException;
import java.util.Map;
import org.apache.activemq.broker.region.group.MessageGroupMapFactory;
import org.apache.activemq.util.FactoryFinder;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class GroupFactoryFinder {
    private static final FactoryFinder GROUP_FACTORY_FINDER = new FactoryFinder("META-INF/services/org/apache/activemq/groups/");

    private GroupFactoryFinder() {
    }

    public static MessageGroupMapFactory createMessageGroupMapFactory(String type) throws IOException {
        try {
            Map<String, String> properties = null;
            String factoryType = type.trim();
            int p = factoryType.indexOf(63);
            if (p >= 0) {
                String propertiesString = factoryType.substring(p + 1);
                factoryType = factoryType.substring(0, p);
                properties = URISupport.parseQuery(propertiesString);
            }
            MessageGroupMapFactory result = (MessageGroupMapFactory)GROUP_FACTORY_FINDER.newInstance(factoryType);
            if (properties != null && result != null) {
                IntrospectionSupport.setProperties(result, properties);
            }
            return result;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not load " + type + " factory:" + e, e);
        }
    }
}

