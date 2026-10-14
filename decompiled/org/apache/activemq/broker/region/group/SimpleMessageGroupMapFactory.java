/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import org.apache.activemq.broker.region.group.MessageGroupMap;
import org.apache.activemq.broker.region.group.MessageGroupMapFactory;
import org.apache.activemq.broker.region.group.SimpleMessageGroupMap;

public class SimpleMessageGroupMapFactory
implements MessageGroupMapFactory {
    @Override
    public MessageGroupMap createMessageGroupMap() {
        return new SimpleMessageGroupMap();
    }
}

