/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery.zeroconf;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryAgentFactory;
import org.apache.activemq.transport.discovery.zeroconf.ZeroconfDiscoveryAgent;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class ZeroconfDiscoveryAgentFactory
extends DiscoveryAgentFactory {
    @Override
    protected DiscoveryAgent doCreateDiscoveryAgent(URI uri) throws IOException {
        try {
            Map<String, String> options = URISupport.parseParameters(uri);
            ZeroconfDiscoveryAgent rc = new ZeroconfDiscoveryAgent();
            rc.setGroup(uri.getHost());
            IntrospectionSupport.setProperties(rc, options);
            return rc;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not create discovery agent: " + uri, e);
        }
    }
}

