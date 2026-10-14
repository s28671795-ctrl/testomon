/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.management.JMException;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.managers.WorkQueueImplMBeanWrapper;
import org.apache.cxf.bus.managers.WorkQueueManagerImplMBeanWrapper;
import org.apache.cxf.buslifecycle.BusLifeCycleListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.management.InstrumentationManager;
import org.apache.cxf.workqueue.AutomaticWorkQueue;
import org.apache.cxf.workqueue.AutomaticWorkQueueImpl;
import org.apache.cxf.workqueue.WorkQueueManager;

@NoJSR250Annotations(unlessNull={"bus"})
public class WorkQueueManagerImpl
implements WorkQueueManager {
    public static final String DEFAULT_QUEUE_NAME = "default";
    public static final String DEFAULT_WORKQUEUE_BEAN_NAME = "cxf.default.workqueue";
    private static final Logger LOG = LogUtils.getL7dLogger(WorkQueueManagerImpl.class);
    Map<String, AutomaticWorkQueue> namedQueues = new ConcurrentHashMap<String, AutomaticWorkQueue>(4, 0.75f, 2);
    boolean inShutdown;
    InstrumentationManager imanager;
    Bus bus;

    public WorkQueueManagerImpl() {
    }

    public WorkQueueManagerImpl(Bus b) {
        this.setBus(b);
    }

    public Bus getBus() {
        return this.bus;
    }

    @Resource
    public final void setBus(Bus bus) {
        this.bus = bus;
        if (null != bus) {
            AutomaticWorkQueue defaultQueue;
            ConfiguredBeanLocator locator;
            Collection<AutomaticWorkQueue> q;
            bus.setExtension(this, WorkQueueManager.class);
            this.imanager = bus.getExtension(InstrumentationManager.class);
            if (null != this.imanager) {
                try {
                    this.imanager.register(new WorkQueueManagerImplMBeanWrapper(this));
                }
                catch (JMException jmex) {
                    LOG.log(Level.WARNING, jmex.getMessage(), jmex);
                }
            }
            if ((q = (locator = bus.getExtension(ConfiguredBeanLocator.class)).getBeansOfType(AutomaticWorkQueue.class)) != null) {
                for (AutomaticWorkQueue awq : q) {
                    this.addNamedWorkQueue(awq.getName(), awq);
                }
            }
            if (!this.namedQueues.containsKey(DEFAULT_QUEUE_NAME) && (defaultQueue = locator.getBeanOfType(DEFAULT_WORKQUEUE_BEAN_NAME, AutomaticWorkQueue.class)) != null) {
                this.addNamedWorkQueue(DEFAULT_QUEUE_NAME, defaultQueue);
            }
            bus.getExtension(BusLifeCycleManager.class).registerLifeCycleListener(new WQLifecycleListener());
        }
    }

    @Override
    public synchronized AutomaticWorkQueue getAutomaticWorkQueue() {
        AutomaticWorkQueue defaultQueue = this.getNamedWorkQueue(DEFAULT_QUEUE_NAME);
        if (defaultQueue == null) {
            defaultQueue = this.createAutomaticWorkQueue();
        }
        return defaultQueue;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized void shutdown(boolean processRemainingTasks) {
        this.inShutdown = true;
        for (AutomaticWorkQueue q : this.namedQueues.values()) {
            if (q instanceof AutomaticWorkQueueImpl) {
                AutomaticWorkQueueImpl impl = (AutomaticWorkQueueImpl)q;
                if (impl.isShared()) {
                    AutomaticWorkQueueImpl automaticWorkQueueImpl = impl;
                    synchronized (automaticWorkQueueImpl) {
                        impl.removeSharedUser();
                        if (impl.getShareCount() == 0 && this.imanager != null && this.imanager.getMBeanServer() != null) {
                            try {
                                this.imanager.unregister(new WorkQueueImplMBeanWrapper(impl, this));
                            }
                            catch (JMException jmex) {
                                LOG.log(Level.WARNING, jmex.getMessage(), jmex);
                            }
                        }
                        continue;
                    }
                }
                q.shutdown(processRemainingTasks);
                continue;
            }
            q.shutdown(processRemainingTasks);
        }
        WorkQueueManagerImpl workQueueManagerImpl = this;
        synchronized (workQueueManagerImpl) {
            this.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Handler[] handlerArray = this;
        synchronized (this) {
            while (!this.inShutdown) {
                try {
                    this.wait();
                }
                catch (InterruptedException interruptedException) {}
            }
            for (AutomaticWorkQueue q : this.namedQueues.values()) {
                while (!q.isShutdown()) {
                    try {
                        this.wait(100L);
                    }
                    catch (InterruptedException interruptedException) {}
                }
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            for (Handler h2 : LOG.getHandlers()) {
                h2.flush();
            }
            return;
        }
    }

    @Override
    public AutomaticWorkQueue getNamedWorkQueue(String name) {
        return this.namedQueues.get(name);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void addNamedWorkQueue(String name, AutomaticWorkQueue q) {
        this.namedQueues.put(name, q);
        if (q instanceof AutomaticWorkQueueImpl) {
            AutomaticWorkQueueImpl impl = (AutomaticWorkQueueImpl)q;
            if (impl.isShared()) {
                AutomaticWorkQueueImpl automaticWorkQueueImpl = impl;
                synchronized (automaticWorkQueueImpl) {
                    if (impl.getShareCount() == 0 && this.imanager != null && this.imanager.getMBeanServer() != null) {
                        try {
                            this.imanager.register(new WorkQueueImplMBeanWrapper((AutomaticWorkQueueImpl)q, this));
                        }
                        catch (JMException jmex) {
                            LOG.log(Level.WARNING, jmex.getMessage(), jmex);
                        }
                    }
                    impl.addSharedUser();
                }
            }
            if (this.imanager != null) {
                try {
                    this.imanager.register(new WorkQueueImplMBeanWrapper((AutomaticWorkQueueImpl)q, this));
                }
                catch (JMException jmex) {
                    LOG.log(Level.WARNING, jmex.getMessage(), jmex);
                }
            }
        }
    }

    private AutomaticWorkQueue createAutomaticWorkQueue() {
        AutomaticWorkQueueImpl q = new AutomaticWorkQueueImpl(DEFAULT_QUEUE_NAME);
        this.addNamedWorkQueue(DEFAULT_QUEUE_NAME, q);
        return q;
    }

    class WQLifecycleListener
    implements BusLifeCycleListener {
        WQLifecycleListener() {
        }

        @Override
        public void initComplete() {
        }

        @Override
        public void preShutdown() {
            WorkQueueManagerImpl.this.shutdown(true);
        }

        @Override
        public void postShutdown() {
        }
    }
}

