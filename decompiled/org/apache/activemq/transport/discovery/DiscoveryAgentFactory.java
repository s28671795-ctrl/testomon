/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.util.FactoryFinder;
import org.apache.activemq.util.IOExceptionSupport;

public abstract class DiscoveryAgentFactory {
    private static final FactoryFinder DISCOVERY_AGENT_FINDER = new FactoryFinder("META-INF/services/org/apache/activemq/transport/discoveryagent/");
    private static final ConcurrentMap<String, DiscoveryAgentFactory> DISCOVERY_AGENT_FACTORYS = new ConcurrentHashMap<String, DiscoveryAgentFactory>();

    private static DiscoveryAgentFactory findDiscoveryAgentFactory(URI uri) throws IOException {
        String scheme2 = uri.getScheme();
        if (scheme2 == null) {
            throw new IOException("DiscoveryAgent scheme not specified: [" + uri + "]");
        }
        DiscoveryAgentFactory daf = (DiscoveryAgentFactory)DISCOVERY_AGENT_FACTORYS.get(scheme2);
        if (daf == null) {
            try {
                daf = (DiscoveryAgentFactory)DISCOVERY_AGENT_FINDER.newInstance(scheme2);
                DISCOVERY_AGENT_FACTORYS.put(scheme2, daf);
            }
            catch (Throwable e) {
                throw IOExceptionSupport.create("DiscoveryAgent scheme NOT recognized: [" + scheme2 + "]", e);
            }
        }
        return daf;
    }

    public static DiscoveryAgent createDiscoveryAgent(URI uri) throws IOException {
        DiscoveryAgentFactory tf = DiscoveryAgentFactory.findDiscoveryAgentFactory(uri);
        return tf.doCreateDiscoveryAgent(uri);
    }

    protected abstract DiscoveryAgent doCreateDiscoveryAgent(URI var1) throws IOException;
}

