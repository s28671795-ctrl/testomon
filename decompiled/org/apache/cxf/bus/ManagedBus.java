/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus;

import javax.management.JMException;
import javax.management.ObjectName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.management.ManagedComponent;
import org.apache.cxf.management.annotation.ManagedOperation;
import org.apache.cxf.management.annotation.ManagedResource;

@ManagedResource(componentName="Bus", description="Responsible for managing services.")
public class ManagedBus
implements ManagedComponent {
    private static final String TYPE_VALUE = "Bus";
    private static final String INSTANCE_ID = "managed.bus.instance.id";
    private final Bus bus;

    public ManagedBus(Bus b) {
        this.bus = b;
    }

    @ManagedOperation
    public void shutdown(boolean wait) {
        this.bus.shutdown(wait);
    }

    @Override
    public ObjectName getObjectName() throws JMException {
        String busId = this.bus.getId();
        StringBuilder buffer = new StringBuilder("org.apache.cxf").append(':');
        buffer.append("bus.id").append('=').append(busId).append(',');
        buffer.append("type").append('=').append(TYPE_VALUE).append(',');
        String instanceId = (String)this.bus.getProperties().get(INSTANCE_ID);
        if (StringUtils.isEmpty(instanceId)) {
            instanceId = "" + this.bus.hashCode();
        }
        buffer.append("instance.id").append('=').append(instanceId);
        return new ObjectName(buffer.toString());
    }
}

