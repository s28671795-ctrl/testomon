/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.List;
import javax.management.openmbean.TabularData;
import org.apache.activemq.broker.jmx.HealthStatus;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface HealthViewMBean {
    public TabularData health() throws Exception;

    @MBeanInfo(value="List of warnings and errors about the current health of the Broker - empty list is Good!")
    public List<HealthStatus> healthList() throws Exception;

    @MBeanInfo(value="String representation of current Broker state")
    public String healthStatus() throws Exception;

    @MBeanInfo(value="String representation of current Broker state")
    public String getCurrentStatus();
}

