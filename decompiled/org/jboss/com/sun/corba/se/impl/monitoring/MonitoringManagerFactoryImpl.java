/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.monitoring;

import java.util.HashMap;
import org.jboss.com.sun.corba.se.impl.monitoring.MonitoringManagerImpl;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoringManager;
import org.jboss.com.sun.corba.se.spi.monitoring.MonitoringManagerFactory;

public class MonitoringManagerFactoryImpl
implements MonitoringManagerFactory {
    private HashMap<String, MonitoringManager> monitoringManagerTable = new HashMap();

    @Override
    public synchronized MonitoringManager createMonitoringManager(String nameOfTheRoot, String description) {
        MonitoringManagerImpl m4 = null;
        m4 = (MonitoringManagerImpl)this.monitoringManagerTable.get(nameOfTheRoot);
        if (m4 == null) {
            m4 = new MonitoringManagerImpl(nameOfTheRoot, description);
            this.monitoringManagerTable.put(nameOfTheRoot, m4);
        }
        return m4;
    }
}

