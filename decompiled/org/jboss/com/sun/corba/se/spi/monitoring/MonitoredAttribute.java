/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.monitoring;

import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredAttributeInfo;

public interface MonitoredAttribute {
    public MonitoredAttributeInfo getAttributeInfo();

    public void setValue(Object var1);

    public Object getValue();

    public String getName();

    public void clearState();
}

