/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.jndi;

import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.activemq.ActiveMQXASslConnectionFactory;
import org.apache.activemq.jndi.ActiveMQInitialContextFactory;

public class ActiveMQSslInitialContextFactory
extends ActiveMQInitialContextFactory {
    @Override
    protected ActiveMQConnectionFactory createConnectionFactory(Hashtable environment) throws URISyntaxException {
        ActiveMQSslConnectionFactory answer = this.needsXA(environment) ? new ActiveMQXASslConnectionFactory() : new ActiveMQSslConnectionFactory();
        Properties properties = new Properties();
        properties.putAll((Map<?, ?>)environment);
        answer.setProperties(properties);
        return answer;
    }
}

