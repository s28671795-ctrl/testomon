/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery.simple;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryAgentFactory;
import org.apache.activemq.transport.discovery.simple.SimpleDiscoveryAgent;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class SimpleDiscoveryAgentFactory
extends DiscoveryAgentFactory {
    @Override
    protected DiscoveryAgent doCreateDiscoveryAgent(URI uri) throws IOException {
        try {
            URISupport.CompositeData data = URISupport.parseComposite(uri);
            Map<String, String> options = data.getParameters();
            SimpleDiscoveryAgent rc = new SimpleDiscoveryAgent();
            IntrospectionSupport.setProperties(rc, options);
            rc.setServices(data.getComponents());
            return rc;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not create discovery agent: " + uri, e);
        }
    }
}

