/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.net.URI;
import java.util.HashMap;
import org.apache.activemq.broker.BrokerFactoryHandler;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.URISupport;

public class DefaultBrokerFactory
implements BrokerFactoryHandler {
    @Override
    public BrokerService createBroker(URI brokerURI) throws Exception {
        URISupport.CompositeData compositeData = URISupport.parseComposite(brokerURI);
        HashMap<String, String> params = new HashMap<String, String>(compositeData.getParameters());
        BrokerService brokerService = new BrokerService();
        IntrospectionSupport.setProperties(brokerService, params);
        if (!params.isEmpty()) {
            String msg = "There are " + params.size() + " Broker options that couldn't be set on the BrokerService. Check the options are spelled correctly. Unknown parameters=[" + params + "]. This BrokerService cannot be started.";
            throw new IllegalArgumentException(msg);
        }
        if (compositeData.getPath() != null) {
            brokerService.setBrokerName(compositeData.getPath());
        }
        URI[] components = compositeData.getComponents();
        for (int i = 0; i < components.length; ++i) {
            if ("network".equals(components[i].getScheme())) {
                brokerService.addNetworkConnector(components[i].getSchemeSpecificPart());
                continue;
            }
            if ("proxy".equals(components[i].getScheme())) {
                brokerService.addProxyConnector(components[i].getSchemeSpecificPart());
                continue;
            }
            brokerService.addConnector(components[i]);
        }
        return brokerService;
    }
}

