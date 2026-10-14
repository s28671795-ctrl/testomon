/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.management.ObjectName;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.transport.TransportLogger;
import org.apache.activemq.transport.TransportLoggerViewMBean;
import org.apache.activemq.util.JMXSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportLoggerView
implements TransportLoggerViewMBean {
    private static final Logger log = LoggerFactory.getLogger(TransportLoggerView.class);
    private static Set<TransportLoggerView> transportLoggerViews = Collections.synchronizedSet(new HashSet());
    private final WeakReference<TransportLogger> transportLogger;
    private final String nextTransportName;
    private final int id;
    private final ManagementContext managementContext;
    private final ObjectName name;

    public TransportLoggerView(TransportLogger transportLogger, String nextTransportName, int id, ManagementContext managementContext) {
        this.transportLogger = new WeakReference<TransportLogger>(transportLogger);
        this.nextTransportName = nextTransportName;
        this.id = id;
        this.managementContext = managementContext;
        this.name = this.createTransportLoggerObjectName();
        transportLoggerViews.add(this);
        this.register();
    }

    public static void enableAllTransportLoggers() {
        for (TransportLoggerView view : transportLoggerViews) {
            view.enableLogging();
        }
    }

    public static void disableAllTransportLoggers() {
        for (TransportLoggerView view : transportLoggerViews) {
            view.disableLogging();
        }
    }

    @Override
    public void enableLogging() {
        this.setLogging(true);
    }

    @Override
    public void disableLogging() {
        this.setLogging(false);
    }

    @Override
    public boolean isLogging() {
        return ((TransportLogger)this.transportLogger.get()).isLogging();
    }

    @Override
    public void setLogging(boolean logging) {
        ((TransportLogger)this.transportLogger.get()).setLogging(logging);
    }

    private void register() {
        try {
            AnnotatedMBean.registerMBean(this.managementContext, this, this.name);
        }
        catch (Exception e) {
            log.error("Could not register MBean for TransportLoggerView " + this.id + "with name " + this.name.toString() + ", reason: " + e, e);
        }
    }

    public void unregister() {
        transportLoggerViews.remove(this);
        try {
            this.managementContext.unregisterMBean(this.name);
        }
        catch (Exception e) {
            log.error("Could not unregister MBean for TransportLoggerView " + this.id + "with name " + this.name.toString() + ", reason: " + e, e);
        }
    }

    private ObjectName createTransportLoggerObjectName() {
        try {
            return new ObjectName(TransportLoggerView.createTransportLoggerObjectNameRoot(this.managementContext) + JMXSupport.encodeObjectNamePart(TransportLogger.class.getSimpleName() + " " + this.id + ";" + this.nextTransportName));
        }
        catch (Exception e) {
            log.error("Could not create ObjectName for TransportLoggerView " + this.id + ", reason: " + e, e);
            return null;
        }
    }

    public static String createTransportLoggerObjectNameRoot(ManagementContext managementContext) {
        return managementContext.getJmxDomainName() + ":Type=TransportLogger,TransportLoggerName=";
    }
}

