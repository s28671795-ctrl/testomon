/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.DestinationView;
import org.apache.activemq.broker.jmx.HealthStatus;
import org.apache.activemq.broker.jmx.HealthViewMBean;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.jmx.OpenTypeSupport;
import org.apache.activemq.broker.scheduler.JobSchedulerStore;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.usage.SystemUsage;

public class HealthView
implements HealthViewMBean {
    private ManagedRegionBroker broker;
    private volatile String currentState = "Good";

    public HealthView(ManagedRegionBroker broker) {
        this.broker = broker;
    }

    @Override
    public TabularData health() throws Exception {
        OpenTypeSupport.OpenTypeFactory factory2 = OpenTypeSupport.getFactory(HealthStatus.class);
        CompositeType ct = factory2.getCompositeType();
        TabularType tt = new TabularType("HealthStatus", "HealthStatus", ct, new String[]{"healthId", "level", "message", "resource"});
        TabularDataSupport rc = new TabularDataSupport(tt);
        List<HealthStatus> list = this.healthList();
        for (HealthStatus healthStatus : list) {
            rc.put(new CompositeDataSupport(ct, factory2.getFields(healthStatus)));
        }
        return rc;
    }

    @Override
    public List<HealthStatus> healthList() throws Exception {
        int val;
        long dirFreeSpace;
        long storeLimit;
        long storeSize;
        String dirPath;
        SystemUsage usage;
        File dir;
        ArrayList<HealthStatus> answer = new ArrayList<HealthStatus>();
        Map<ObjectName, DestinationView> queueViews = this.broker.getQueueViews();
        for (Map.Entry<ObjectName, DestinationView> entry : queueViews.entrySet()) {
            DestinationView queue = entry.getValue();
            if (queue.getConsumerCount() != 0L || queue.getProducerCount() <= 0L) continue;
            ObjectName key = entry.getKey();
            String message = "Queue " + queue.getName() + " has no consumers";
            answer.add(new HealthStatus("org.apache.activemq.noConsumer", "WARNING", message, key.toString()));
        }
        BrokerService brokerService = this.broker.getBrokerService();
        if (brokerService != null && brokerService.getPersistenceAdapter() != null) {
            PersistenceAdapter adapter = brokerService.getPersistenceAdapter();
            dir = adapter.getDirectory();
            if (brokerService.isPersistent()) {
                File tmpDir;
                usage = brokerService.getSystemUsage();
                if (dir != null && usage != null) {
                    dirPath = dir.getAbsolutePath();
                    if (!dir.isAbsolute()) {
                        dir = new File(dirPath);
                    }
                    while (dir != null && !dir.isDirectory()) {
                        dir = dir.getParentFile();
                    }
                    storeSize = adapter.size();
                    storeLimit = usage.getStoreUsage().getLimit();
                    dirFreeSpace = dir.getUsableSpace();
                    if (storeSize != 0L && storeLimit != 0L && (val = (int)(storeSize * 100L / storeLimit)) > 90) {
                        answer.add(new HealthStatus("org.apache.activemq.StoreLimit", "WARNING", "Message Store size is within " + val + "% of its limit", adapter.toString()));
                    }
                    if (storeLimit - storeSize > dirFreeSpace) {
                        String message = "Store limit is " + storeLimit / 0x100000L + " mb, whilst the data directory: " + dir.getAbsolutePath() + " only has " + dirFreeSpace / 0x100000L + " mb of usable space";
                        answer.add(new HealthStatus("org.apache.activemq.FreeDiskSpaceLeft", "WARNING", message, adapter.toString()));
                    }
                }
                if ((tmpDir = brokerService.getTmpDataDirectory()) != null) {
                    int val2;
                    String tmpDirPath = tmpDir.getAbsolutePath();
                    if (!tmpDir.isAbsolute()) {
                        tmpDir = new File(tmpDirPath);
                    }
                    long storeSize2 = usage.getTempUsage().getUsage();
                    long storeLimit2 = usage.getTempUsage().getLimit();
                    while (tmpDir != null && !tmpDir.isDirectory()) {
                        tmpDir = tmpDir.getParentFile();
                    }
                    if (storeLimit2 != 0L && (val2 = (int)(storeSize2 * 100L / storeLimit2)) > 90) {
                        answer.add(new HealthStatus("org.apache.activemq.TempStoreLimit", "WARNING", "TempMessage Store size is within " + val2 + "% of its limit", adapter.toString()));
                    }
                }
            }
        }
        if (brokerService != null && brokerService.getJobSchedulerStore() != null) {
            JobSchedulerStore scheduler = brokerService.getJobSchedulerStore();
            dir = scheduler.getDirectory();
            if (brokerService.isPersistent()) {
                usage = brokerService.getSystemUsage();
                if (dir != null && usage != null) {
                    dirPath = dir.getAbsolutePath();
                    if (!dir.isAbsolute()) {
                        dir = new File(dirPath);
                    }
                    while (dir != null && !dir.isDirectory()) {
                        dir = dir.getParentFile();
                    }
                    storeSize = scheduler.size();
                    storeLimit = usage.getJobSchedulerUsage().getLimit();
                    dirFreeSpace = dir.getUsableSpace();
                    if (storeSize != 0L && storeLimit != 0L && (val = (int)(storeSize * 100L / storeLimit)) > 90) {
                        answer.add(new HealthStatus("org.apache.activemq.JobSchedulerLimit", "WARNING", "JobSchedulerMessage Store size is within " + val + "% of its limit", scheduler.toString()));
                    }
                    if (storeLimit - storeSize > dirFreeSpace) {
                        String message = "JobSchedulerStore limit is " + storeLimit / 0x100000L + " mb, whilst the data directory: " + dir.getAbsolutePath() + " only has " + dirFreeSpace / 0x100000L + " mb of usable space";
                        answer.add(new HealthStatus("org.apache.activemq.FreeDiskSpaceLeft", "WARNING", message, scheduler.toString()));
                    }
                }
            }
        }
        StringBuilder currentState = new StringBuilder();
        if (answer != null && !answer.isEmpty()) {
            currentState.append("Getting Worried {");
            for (HealthStatus hs : answer) {
                currentState.append(hs).append(" , ");
            }
            currentState.append(" }");
        } else {
            currentState.append("Good");
        }
        this.currentState = currentState.toString();
        return answer;
    }

    @Override
    public String healthStatus() throws Exception {
        this.healthList();
        return this.getCurrentStatus();
    }

    @Override
    public String getCurrentStatus() {
        return this.currentState;
    }
}

