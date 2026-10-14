/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.view;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.view.ConnectionDotFileInterceptor;

public class ConnectionDotFilePlugin
implements BrokerPlugin {
    private String file = "ActiveMQConnections.dot";
    private boolean redrawOnRemove;

    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        return new ConnectionDotFileInterceptor(broker, this.file, this.redrawOnRemove);
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}

