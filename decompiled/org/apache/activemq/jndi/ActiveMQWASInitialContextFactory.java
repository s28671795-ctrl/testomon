/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.jndi;

import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;
import org.apache.activemq.jndi.ActiveMQInitialContextFactory;

public class ActiveMQWASInitialContextFactory
extends ActiveMQInitialContextFactory {
    @Override
    public Context getInitialContext(Hashtable environment) throws NamingException {
        return super.getInitialContext(this.transformEnvironment(environment));
    }

    protected Hashtable transformEnvironment(Hashtable environment) {
        Hashtable<String, String> environment1 = new Hashtable<String, String>();
        for (Map.Entry entry : environment.entrySet()) {
            String key1;
            if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) continue;
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            if (key.startsWith("java.naming.queue.")) {
                key1 = key.substring("java.naming.queue.".length());
                key1 = key1.replace('.', '/');
                environment1.put("queue." + key1, value);
                continue;
            }
            if (key.startsWith("java.naming.topic.")) {
                key1 = key.substring("java.naming.topic.".length());
                key1 = key1.replace('.', '/');
                environment1.put("topic." + key1, value);
                continue;
            }
            if (key.startsWith("java.naming.connectionFactoryNames")) {
                key1 = key.substring("java.naming.".length());
                environment1.put(key1, value);
                continue;
            }
            if (key.startsWith("java.naming.connection")) {
                key1 = key.substring("java.naming.".length());
                environment1.put(key1, value);
                continue;
            }
            if (key.startsWith("java.naming.provider.url")) {
                value = value.replace(';', ',');
                environment1.put("java.naming.provider.url", value);
                continue;
            }
            environment1.put(key, value);
        }
        return environment1;
    }
}

