/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import org.apache.activemq.broker.region.group.MessageGroupSet;

public class EmptyMessageGroupSet
implements MessageGroupSet {
    public static final MessageGroupSet INSTANCE = new EmptyMessageGroupSet();

    @Override
    public boolean contains(String groupID) {
        return false;
    }
}

