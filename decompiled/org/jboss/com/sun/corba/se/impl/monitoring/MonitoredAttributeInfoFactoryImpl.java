/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.monitoring;

import org.jboss.com.sun.corba.se.impl.monitoring.MonitoredAttributeInfoImpl;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredAttributeInfo;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredAttributeInfoFactory;

public class MonitoredAttributeInfoFactoryImpl
implements MonitoredAttributeInfoFactory {
    @Override
    public MonitoredAttributeInfo createMonitoredAttributeInfo(String description, Class<?> type, boolean isWritable, boolean isStatistic) {
        return new MonitoredAttributeInfoImpl(description, type, isWritable, isStatistic);
    }
}

