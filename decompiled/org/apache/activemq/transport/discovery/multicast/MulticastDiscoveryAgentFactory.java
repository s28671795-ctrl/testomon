/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery.multicast;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryAgentFactory;
import org.apache.activemq.transport.discovery.multicast.MulticastDiscoveryAgent;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulticastDiscoveryAgentFactory
extends DiscoveryAgentFactory {
    private static final Logger LOG = LoggerFactory.getLogger(MulticastDiscoveryAgentFactory.class);

    @Override
    protected DiscoveryAgent doCreateDiscoveryAgent(URI uri) throws IOException {
        try {
            if (LOG.isTraceEnabled()) {
                LOG.trace("doCreateDiscoveryAgent: uri = " + uri.toString());
            }
            MulticastDiscoveryAgent mda = new MulticastDiscoveryAgent();
            mda.setDiscoveryURI(uri);
            Map<String, String> options = URISupport.parseParameters(uri);
            IntrospectionSupport.setProperties(mda, options);
            return mda;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not create discovery agent: " + uri, e);
        }
    }
}

