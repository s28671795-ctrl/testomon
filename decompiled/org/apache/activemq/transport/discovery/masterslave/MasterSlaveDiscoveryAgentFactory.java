/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery.masterslave;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryAgentFactory;
import org.apache.activemq.transport.discovery.masterslave.MasterSlaveDiscoveryAgent;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class MasterSlaveDiscoveryAgentFactory
extends DiscoveryAgentFactory {
    @Override
    protected DiscoveryAgent doCreateDiscoveryAgent(URI uri) throws IOException {
        try {
            URISupport.CompositeData data = URISupport.parseComposite(uri);
            Map<String, String> options = data.getParameters();
            MasterSlaveDiscoveryAgent rc = new MasterSlaveDiscoveryAgent();
            IntrospectionSupport.setProperties(rc, options);
            rc.setServices(data.getComponents());
            return rc;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not create discovery agent: " + uri, e);
        }
    }
}

