/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.monitoring;

import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredAttributeInfo;

public interface MonitoredAttributeInfoFactory {
    public MonitoredAttributeInfo createMonitoredAttributeInfo(String var1, Class<?> var2, boolean var3, boolean var4);
}

