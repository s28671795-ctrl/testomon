/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.monitoring;

import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredObject;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredObjectFactory;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoringFactories;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoringManager;

public class MonitoringManagerImpl
implements MonitoringManager {
    private final MonitoredObject rootMonitoredObject;

    MonitoringManagerImpl(String nameOfTheRoot, String description) {
        MonitoredObjectFactory f = MonitoringFactories.getMonitoredObjectFactory();
        this.rootMonitoredObject = f.createMonitoredObject(nameOfTheRoot, description);
    }

    @Override
    public void clearState() {
        this.rootMonitoredObject.clearState();
    }

    @Override
    public MonitoredObject getRootMonitoredObject() {
        return this.rootMonitoredObject;
    }
}

