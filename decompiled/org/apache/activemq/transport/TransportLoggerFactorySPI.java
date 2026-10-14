/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import org.apache.activemq.TransportLoggerSupport;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportLoggerFactory;

public class TransportLoggerFactorySPI
implements TransportLoggerSupport.SPI {
    @Override
    public Transport createTransportLogger(Transport transport) throws IOException {
        return TransportLoggerFactory.getInstance().createTransportLogger(transport);
    }

    @Override
    public Transport createTransportLogger(Transport transport, String logWriterName, boolean dynamicManagement, boolean startLogging, int jmxPort) throws IOException {
        return TransportLoggerFactory.getInstance().createTransportLogger(transport, logWriterName, dynamicManagement, startLogging, jmxPort);
    }
}

