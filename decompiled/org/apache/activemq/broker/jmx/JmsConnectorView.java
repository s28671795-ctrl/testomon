/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.JmsConnectorViewMBean;
import org.apache.activemq.network.jms.JmsConnector;

public class JmsConnectorView
implements JmsConnectorViewMBean {
    private final JmsConnector connector;

    public JmsConnectorView(JmsConnector connector) {
        this.connector = connector;
    }

    @Override
    public void start() throws Exception {
        this.connector.start();
    }

    @Override
    public void stop() throws Exception {
        this.connector.stop();
    }
}

