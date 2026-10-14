/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.advisory;

import java.util.EventObject;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.DestinationInfo;

public class DestinationEvent
extends EventObject {
    private static final long serialVersionUID = 2442156576867593780L;
    private DestinationInfo destinationInfo;

    public DestinationEvent(DestinationSource source2, DestinationInfo destinationInfo) {
        super(source2);
        this.destinationInfo = destinationInfo;
    }

    public ActiveMQDestination getDestination() {
        return this.getDestinationInfo().getDestination();
    }

    public boolean isAddOperation() {
        return this.getDestinationInfo().isAddOperation();
    }

    public long getTimeout() {
        return this.getDestinationInfo().getTimeout();
    }

    public boolean isRemoveOperation() {
        return this.getDestinationInfo().isRemoveOperation();
    }

    public DestinationInfo getDestinationInfo() {
        return this.destinationInfo;
    }
}

