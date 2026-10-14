/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Gauge;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public class JmxAttributeGauge
implements Gauge<Object> {
    private final MBeanServerConnection mBeanServerConn;
    private final ObjectName objectName;
    private final String attributeName;

    public JmxAttributeGauge(ObjectName objectName, String attributeName) {
        this(ManagementFactory.getPlatformMBeanServer(), objectName, attributeName);
    }

    public JmxAttributeGauge(MBeanServerConnection mBeanServerConn, ObjectName objectName, String attributeName) {
        this.mBeanServerConn = mBeanServerConn;
        this.objectName = objectName;
        this.attributeName = attributeName;
    }

    @Override
    public Object getValue() {
        try {
            return this.mBeanServerConn.getAttribute(this.objectName, this.attributeName);
        }
        catch (IOException e) {
            return null;
        }
        catch (JMException e) {
            return null;
        }
    }
}

