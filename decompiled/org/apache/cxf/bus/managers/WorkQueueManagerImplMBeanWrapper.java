/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.managers;

import javax.management.JMException;
import javax.management.ObjectName;
import org.apache.cxf.bus.managers.WorkQueueManagerImpl;
import org.apache.cxf.management.ManagedComponent;
import org.apache.cxf.management.annotation.ManagedOperation;
import org.apache.cxf.management.annotation.ManagedResource;

@ManagedResource(componentName="WorkQueueManager", description="The CXF manangement of work queues ", currencyTimeLimit=15, persistPolicy="OnUpdate", persistPeriod=200)
public class WorkQueueManagerImplMBeanWrapper
implements ManagedComponent {
    static final String NAME_VALUE = "Bus.WorkQueueManager";
    static final String TYPE_VALUE = "WorkQueueManager";
    private final WorkQueueManagerImpl wqManager;
    private final String objectName;

    public WorkQueueManagerImplMBeanWrapper(WorkQueueManagerImpl wq) {
        this.wqManager = wq;
        this.objectName = "org.apache.cxf" + ':' + "bus.id" + '=' + this.wqManager.getBus().getId() + ',' + "WorkQueueManager=" + NAME_VALUE + ',' + "type" + '=' + TYPE_VALUE + ',' + "instance.id" + '=' + this.wqManager.hashCode();
    }

    @ManagedOperation(currencyTimeLimit=30)
    public void shutdown(boolean processRemainingWorkItems) {
        this.wqManager.shutdown(processRemainingWorkItems);
    }

    @Override
    public ObjectName getObjectName() throws JMException {
        return new ObjectName(this.objectName);
    }
}

