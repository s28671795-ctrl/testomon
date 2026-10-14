/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import org.apache.activemq.broker.jmx.BrokerView;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.transport.TransportLoggerControlMBean;
import org.apache.activemq.transport.TransportLoggerView;

public class TransportLoggerControl
implements TransportLoggerControlMBean {
    public TransportLoggerControl(ManagementContext managementContext) {
    }

    @Override
    public void disableAllTransportLoggers() {
        TransportLoggerView.disableAllTransportLoggers();
    }

    @Override
    public void enableAllTransportLoggers() {
        TransportLoggerView.enableAllTransportLoggers();
    }

    @Override
    public void reloadLog4jProperties() throws Throwable {
        new BrokerView(null, null).reloadLog4jProperties();
    }
}

