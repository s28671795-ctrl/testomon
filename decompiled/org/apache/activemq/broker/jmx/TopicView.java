/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.DestinationView;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.jmx.TopicViewMBean;
import org.apache.activemq.broker.region.Topic;

public class TopicView
extends DestinationView
implements TopicViewMBean {
    public TopicView(ManagedRegionBroker broker, Topic destination) {
        super(broker, destination);
    }
}

