/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

public interface TransportLoggerControlMBean {
    public void enableAllTransportLoggers();

    public void disableAllTransportLoggers();

    public void reloadLog4jProperties() throws Throwable;
}

