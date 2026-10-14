/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.monitoring;

import org.jboss.com.sun.corba.se.impl.monitoring.MonitoredAttributeInfoFactoryImpl;
import org.jboss.com.sun.corba.se.impl.monitoring.MonitoredObjectFactoryImpl;
import org.jboss.com.sun.corba.se.impl.monitoring.MonitoringManagerFactoryImpl;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredAttributeInfoFactory;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredObjectFactory;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoringManagerFactory;

public class MonitoringFactories {
    private static final MonitoredObjectFactoryImpl monitoredObjectFactory = new MonitoredObjectFactoryImpl();
    private static final MonitoredAttributeInfoFactoryImpl monitoredAttributeInfoFactory = new MonitoredAttributeInfoFactoryImpl();
    private static final MonitoringManagerFactoryImpl monitoringManagerFactory = new MonitoringManagerFactoryImpl();

    public static MonitoredObjectFactory getMonitoredObjectFactory() {
        return monitoredObjectFactory;
    }

    public static MonitoredAttributeInfoFactory getMonitoredAttributeInfoFactory() {
        return monitoredAttributeInfoFactory;
    }

    public static MonitoringManagerFactory getMonitoringManagerFactory() {
        return monitoringManagerFactory;
    }
}

