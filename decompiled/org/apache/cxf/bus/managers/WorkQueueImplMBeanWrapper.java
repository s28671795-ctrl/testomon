/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import javax.management.JMException;
import javax.management.ObjectName;
import org.apache.cxf.bus.managers.WorkQueueManagerImpl;
import org.apache.cxf.management.ManagedComponent;
import org.apache.cxf.management.annotation.ManagedAttribute;
import org.apache.cxf.management.annotation.ManagedResource;
import org.apache.cxf.workqueue.AutomaticWorkQueueImpl;
import org.apache.cxf.workqueue.WorkQueueManager;

@ManagedResource(componentName="WorkQueue", description="The CXF work queue", currencyTimeLimit=15, persistPolicy="OnUpdate", persistPeriod=200)
public class WorkQueueImplMBeanWrapper
implements ManagedComponent {
    private static final String TYPE_VALUE = "WorkQueues";
    private final AutomaticWorkQueueImpl aWorkQueue;
    private final String objectName;

    public WorkQueueImplMBeanWrapper(AutomaticWorkQueueImpl wq, WorkQueueManager mgr) {
        this.aWorkQueue = wq;
        StringBuilder sb = new StringBuilder("org.apache.cxf").append(':');
        if (!this.aWorkQueue.isShared()) {
            String busId = "cxf";
            if (mgr instanceof WorkQueueManagerImpl) {
                busId = ((WorkQueueManagerImpl)mgr).getBus().getId();
            }
            sb.append("bus.id").append('=').append(busId).append(',').append("WorkQueueManager").append('=').append("Bus.WorkQueueManager").append(',');
        } else {
            sb.append("bus.id").append("=Shared,");
        }
        sb.append("type").append('=').append(TYPE_VALUE).append(',').append("name").append('=').append(this.aWorkQueue.getName()).append(',').append("instance.id").append('=').append(this.aWorkQueue.hashCode());
        this.objectName = sb.toString();
    }

    @ManagedAttribute(description="The WorkQueueMaxSize", persistPolicy="OnUpdate")
    public long getWorkQueueMaxSize() {
        return this.aWorkQueue.getMaxSize();
    }

    @ManagedAttribute(description="The WorkQueue Current size", persistPolicy="OnUpdate")
    public long getWorkQueueSize() {
        return this.aWorkQueue.getSize();
    }

    @ManagedAttribute(description="The largest number of threads")
    public int getLargestPoolSize() {
        return this.aWorkQueue.getLargestPoolSize();
    }

    @ManagedAttribute(description="The current number of threads")
    public int getPoolSize() {
        return this.aWorkQueue.getPoolSize();
    }

    @ManagedAttribute(description="The number of threads currently busy")
    public int getActiveCount() {
        return this.aWorkQueue.getActiveCount();
    }

    @ManagedAttribute(description="The WorkQueue has nothing to do", persistPolicy="OnUpdate")
    public boolean isEmpty() {
        return this.aWorkQueue.isEmpty();
    }

    @ManagedAttribute(description="The WorkQueue is very busy")
    public boolean isFull() {
        return this.aWorkQueue.isFull();
    }

    @ManagedAttribute(description="The WorkQueue HighWaterMark", persistPolicy="OnUpdate")
    public int getHighWaterMark() {
        return this.aWorkQueue.getHighWaterMark();
    }

    public void setHighWaterMark(int hwm) {
        this.aWorkQueue.setHighWaterMark(hwm);
    }

    @ManagedAttribute(description="The WorkQueue LowWaterMark", persistPolicy="OnUpdate")
    public int getLowWaterMark() {
        return this.aWorkQueue.getLowWaterMark();
    }

    public void setLowWaterMark(int lwm) {
        this.aWorkQueue.setLowWaterMark(lwm);
    }

    @Override
    public ObjectName getObjectName() throws JMException {
        return new ObjectName(this.objectName);
    }
}

