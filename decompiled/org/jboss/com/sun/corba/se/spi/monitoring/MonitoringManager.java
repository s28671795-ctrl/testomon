/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.monitoring;

import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredObject;

public interface MonitoringManager {
    public MonitoredObject getRootMonitoredObject();

    public void clearState();
}

