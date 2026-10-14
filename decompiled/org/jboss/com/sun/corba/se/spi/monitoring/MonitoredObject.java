/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.monitoring;

import java.util.Collection;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoredAttribute;

public interface MonitoredObject {
    public String getName();

    public String getDescription();

    public void addChild(MonitoredObject var1);

    public void removeChild(String var1);

    public MonitoredObject getChild(String var1);

    public Collection<MonitoredObject> getChildren();

    public void setParent(MonitoredObject var1);

    public MonitoredObject getParent();

    public void addAttribute(MonitoredAttribute var1);

    public void removeAttribute(String var1);

    public MonitoredAttribute getAttribute(String var1);

    public Collection<MonitoredAttribute> getAttributes();

    public void clearState();
}

