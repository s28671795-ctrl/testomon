/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.IOException;
import org.apache.activemq.transport.Transport;

public class TransportLoggerSupport {
    public static String defaultLogWriterName;
    public static int defaultJmxPort;
    public static final SPI spi;

    public static Transport createTransportLogger(Transport transport) throws IOException {
        if (spi != null) {
            return spi.createTransportLogger(transport);
        }
        return transport;
    }

    public static Transport createTransportLogger(Transport transport, String logWriterName, boolean dynamicManagement, boolean startLogging, int jmxPort) throws IOException {
        if (spi != null) {
            return spi.createTransportLogger(transport, logWriterName, dynamicManagement, startLogging, jmxPort);
        }
        return transport;
    }

    static {
        SPI temp;
        defaultLogWriterName = "default";
        defaultJmxPort = 1099;
        try {
            temp = (SPI)TransportLoggerSupport.class.getClassLoader().loadClass("org.apache.activemq.transport.TransportLoggerFactorySPI").newInstance();
        }
        catch (Throwable e) {
            temp = null;
        }
        spi = temp;
    }

    public static interface SPI {
        public Transport createTransportLogger(Transport var1) throws IOException;

        public Transport createTransportLogger(Transport var1, String var2, boolean var3, boolean var4, int var5) throws IOException;
    }
}

