/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

public interface TransportLoggerViewMBean {
    public boolean isLogging();

    public void setLogging(boolean var1);

    public void enableLogging();

    public void disableLogging();
}

