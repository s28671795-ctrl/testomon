/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapUtils;
import org.ldaptive.PooledConnectionFactory;
import org.ldaptive.SingleConnectionFactory;
import org.ldaptive.transport.Transport;
import org.ldaptive.transport.netty.ConnectionFactoryTransport;
import org.ldaptive.transport.netty.ConnectionTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TransportFactory {
    private static final String POOLED_FACTORY_TRANSPORT_PROPERTY = "org.ldaptive.transport.pooledConnectionFactory";
    private static final String SINGLE_FACTORY_TRANSPORT_PROPERTY = "org.ldaptive.transport.singleConnectionFactory";
    private static final Logger LOGGER;
    private static final Map<Class<? extends ConnectionFactory>, Constructor<?>> TRANSPORT_OVERRIDE;

    private TransportFactory() {
    }

    public static Transport getTransport(Class<? extends ConnectionFactory> clazz) {
        if (TRANSPORT_OVERRIDE.containsKey(clazz)) {
            try {
                return (Transport)TRANSPORT_OVERRIDE.get(clazz).newInstance(new Object[0]);
            }
            catch (Exception e) {
                LOGGER.error("Error creating new transport instance with {}", (Object)TRANSPORT_OVERRIDE.get(clazz), (Object)e);
                throw new IllegalStateException(e);
            }
        }
        Transport transport = PooledConnectionFactory.class.isAssignableFrom(clazz) ? new ConnectionFactoryTransport() : (SingleConnectionFactory.class.isAssignableFrom(clazz) ? new ConnectionTransport.SingleThread() : (DefaultConnectionFactory.class.isAssignableFrom(clazz) ? new ConnectionTransport.SingleThread() : new ConnectionTransport.SingleThread()));
        return transport;
    }

    static {
        Constructor<?> singleTransport;
        LOGGER = LoggerFactory.getLogger(TransportFactory.class);
        HashMap constructors = new HashMap(2);
        Constructor<?> pooledTransport = LdapUtils.createConstructorFromProperty(POOLED_FACTORY_TRANSPORT_PROPERTY);
        if (pooledTransport != null) {
            constructors.put(PooledConnectionFactory.class, pooledTransport);
        }
        if ((singleTransport = LdapUtils.createConstructorFromProperty(SINGLE_FACTORY_TRANSPORT_PROPERTY)) != null) {
            constructors.put(SingleConnectionFactory.class, singleTransport);
        }
        if (!(TRANSPORT_OVERRIDE = Collections.unmodifiableMap(constructors)).isEmpty()) {
            LOGGER.info("Transport override set to {}", (Object)TRANSPORT_OVERRIDE);
        }
    }
}

