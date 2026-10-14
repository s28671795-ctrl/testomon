/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.view;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.view.DestinationDotFileInterceptor;

public class DestinationDotFilePlugin
implements BrokerPlugin {
    private String file = "ActiveMQDestinations.dot";

    @Override
    public Broker installPlugin(Broker broker) {
        return new DestinationDotFileInterceptor(broker, this.file);
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}

