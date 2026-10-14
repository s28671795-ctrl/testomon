/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.service.cm.Configuration
 *  org.osgi.service.cm.ConfigurationAdmin
 *  org.osgi.service.cm.ConfigurationException
 *  org.osgi.service.cm.ManagedServiceFactory
 *  org.osgi.util.tracker.ServiceTracker
 */
package org.apache.cxf.bus.osgi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.workqueue.AutomaticWorkQueueImpl;
import org.apache.cxf.workqueue.WorkQueueManager;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.util.tracker.ServiceTracker;

public class ManagedWorkQueueList
implements ManagedServiceFactory,
PropertyChangeListener {
    public static final String FACTORY_PID = "org.apache.cxf.workqueues";
    private static final Logger LOG = LogUtils.getL7dLogger(ManagedWorkQueueList.class);
    private Map<String, AutomaticWorkQueueImpl> queues = new ConcurrentHashMap<String, AutomaticWorkQueueImpl>(4, 0.75f, 2);
    private ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configAdminTracker;

    public String getName() {
        return FACTORY_PID;
    }

    public void updated(String pid, Dictionary<String, ?> props) throws ConfigurationException {
        if (pid == null) {
            return;
        }
        Dictionary<String, String> properties = CastUtils.cast(props);
        String queueName = (String)properties.get("name");
        if (this.queues.containsKey(queueName)) {
            this.queues.get(queueName).update(properties);
        } else {
            AutomaticWorkQueueImpl wq = new AutomaticWorkQueueImpl(queueName);
            wq.setShared(true);
            wq.update(properties);
            wq.addChangeListener(this);
            this.queues.put(pid, wq);
        }
    }

    public void deleted(String pid) {
        this.queues.remove(pid);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            Configuration selectedConfig;
            AutomaticWorkQueueImpl queue = (AutomaticWorkQueueImpl)evt.getSource();
            ConfigurationAdmin configurationAdmin = (ConfigurationAdmin)this.configAdminTracker.getService();
            if (configurationAdmin != null && (selectedConfig = this.findConfigForQueueName(queue, configurationAdmin)) != null) {
                Dictionary<String, String> properties = queue.getProperties();
                selectedConfig.update(properties);
            }
        }
        catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private Configuration findConfigForQueueName(AutomaticWorkQueueImpl queue, ConfigurationAdmin configurationAdmin) throws Exception {
        Configuration[] configs;
        Configuration selectedConfig = null;
        String filter2 = "(service.factoryPid=org.apache.cxf.workqueues)";
        for (Configuration configuration : configs = configurationAdmin.listConfigurations(filter2)) {
            Dictionary props = configuration.getProperties();
            String name = (String)props.get("name");
            if (!queue.getName().equals(name)) continue;
            selectedConfig = configuration;
        }
        return selectedConfig;
    }

    public void addAllToWorkQueueManager(WorkQueueManager manager) {
        if (manager != null) {
            for (AutomaticWorkQueueImpl wq : this.queues.values()) {
                if (manager.getNamedWorkQueue(wq.getName()) != null) continue;
                manager.addNamedWorkQueue(wq.getName(), wq);
            }
        }
    }

    public void setConfigAdminTracker(ServiceTracker<ConfigurationAdmin, ConfigurationAdmin> configAdminTracker) {
        this.configAdminTracker = configAdminTracker;
    }

    public void shutDown() {
        for (AutomaticWorkQueueImpl wq : this.queues.values()) {
            wq.setShared(false);
            wq.shutdown(true);
        }
        this.queues.clear();
    }
}

