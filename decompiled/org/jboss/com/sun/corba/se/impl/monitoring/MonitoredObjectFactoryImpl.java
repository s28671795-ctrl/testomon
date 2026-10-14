/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.monitoring;

import org.jboss.com.sun.corba.se.impl.monitoring.MonitoredObjectImpl;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredObject;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredObjectFactory;

public class MonitoredObjectFactoryImpl
implements MonitoredObjectFactory {
    @Override
    public MonitoredObject createMonitoredObject(String name, String description) {
        return new MonitoredObjectImpl(name, description);
    }
}

