/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.management;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.apache.cxf.management.ManagedComponent;

public interface InstrumentationManager {
    public ObjectName register(ManagedComponent var1) throws JMException;

    public ObjectName register(ManagedComponent var1, boolean var2) throws JMException;

    public void register(Object var1, ObjectName var2) throws JMException;

    public void register(Object var1, ObjectName var2, boolean var3) throws JMException;

    public void unregister(ManagedComponent var1) throws JMException;

    public void unregister(ObjectName var1) throws JMException;

    public void shutdown();

    public MBeanServer getMBeanServer();

    public String getPersistentBusId();

    public void setPersistentBusId(String var1);
}

