/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import javax.management.ObjectName;
import org.apache.activemq.TransportLoggerSupport;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.transport.LogWriter;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportLogger;
import org.apache.activemq.transport.TransportLoggerControl;
import org.apache.activemq.transport.TransportLoggerView;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.LogWriterFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportLoggerFactory {
    private static final Logger LOG = LoggerFactory.getLogger(TransportLoggerFactory.class);
    private static TransportLoggerFactory instance;
    private static int lastId;
    private static final LogWriterFinder logWriterFinder;
    public static String defaultLogWriterName;
    private static boolean defaultDynamicManagement;
    private static boolean defaultInitialBehavior;
    private boolean transportLoggerControlCreated = false;
    private ManagementContext managementContext;
    private ObjectName objectName;

    private TransportLoggerFactory() {
    }

    public static synchronized TransportLoggerFactory getInstance() {
        if (instance == null) {
            instance = new TransportLoggerFactory();
        }
        return instance;
    }

    public void stop() {
        try {
            if (this.transportLoggerControlCreated) {
                this.managementContext.unregisterMBean(this.objectName);
                this.managementContext.stop();
                this.managementContext = null;
            }
        }
        catch (Exception e) {
            LOG.error("TransportLoggerFactory could not be stopped, reason: " + e, e);
        }
    }

    public TransportLogger createTransportLogger(Transport next) throws IOException {
        int id = TransportLoggerFactory.getNextId();
        return this.createTransportLogger(next, id, TransportLoggerFactory.createLog(id), defaultLogWriterName, defaultDynamicManagement, defaultInitialBehavior, TransportLoggerSupport.defaultJmxPort);
    }

    public TransportLogger createTransportLogger(Transport next, Logger log) throws IOException {
        return this.createTransportLogger(next, TransportLoggerFactory.getNextId(), log, defaultLogWriterName, defaultDynamicManagement, defaultInitialBehavior, TransportLoggerSupport.defaultJmxPort);
    }

    public TransportLogger createTransportLogger(Transport next, String logWriterName, boolean useJmx, boolean startLogging, int jmxport) throws IOException {
        int id = -1;
        if (!useJmx && jmxport != TransportLoggerSupport.defaultJmxPort) {
            id = TransportLoggerFactory.getNextId();
        }
        return this.createTransportLogger(next, id, TransportLoggerFactory.createLog(id), logWriterName, useJmx, startLogging, jmxport);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TransportLogger createTransportLogger(Transport next, int id, Logger log, String logWriterName, boolean dynamicManagement, boolean startLogging, int jmxport) throws IOException {
        try {
            LogWriter logWriter = logWriterFinder.newInstance(logWriterName);
            if (id == -1) {
                logWriter.setPrefix(String.format("%08X: ", TransportLoggerFactory.getNextId()));
            }
            TransportLogger tl = new TransportLogger(next, log, startLogging, logWriter);
            if (dynamicManagement) {
                TransportLoggerFactory transportLoggerFactory = this;
                synchronized (transportLoggerFactory) {
                    if (!this.transportLoggerControlCreated) {
                        this.createTransportLoggerControl(jmxport);
                    }
                }
                TransportLoggerView tlv = new TransportLoggerView(tl, next.toString(), id, this.managementContext);
                tl.setView(tlv);
            }
            return tl;
        }
        catch (Throwable e) {
            throw IOExceptionSupport.create("Could not create log writer object for: " + logWriterName + ", reason: " + e, e);
        }
    }

    private static synchronized int getNextId() {
        return ++lastId;
    }

    private static Logger createLog(int id) {
        return LoggerFactory.getLogger(TransportLogger.class.getName() + ".Connection" + (id > 0 ? ":" + id : ""));
    }

    private void createTransportLoggerControl(int port) {
        try {
            this.managementContext = new ManagementContext();
            this.managementContext.setConnectorPort(port);
            this.managementContext.start();
        }
        catch (Exception e) {
            LOG.error("Management context could not be started, reason: " + e, e);
        }
        try {
            this.objectName = new ObjectName(this.managementContext.getJmxDomainName() + ":Type=TransportLoggerControl");
            AnnotatedMBean.registerMBean(this.managementContext, new TransportLoggerControl(this.managementContext), this.objectName);
            this.transportLoggerControlCreated = true;
        }
        catch (Exception e) {
            LOG.error("TransportLoggerControlMBean could not be registered, reason: " + e, e);
        }
    }

    static {
        lastId = 0;
        logWriterFinder = new LogWriterFinder("META-INF/services/org/apache/activemq/transport/logwriters/");
        defaultLogWriterName = "default";
        defaultDynamicManagement = false;
        defaultInitialBehavior = true;
    }
}

