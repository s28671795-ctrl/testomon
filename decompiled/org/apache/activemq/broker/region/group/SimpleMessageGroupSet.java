/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import java.util.HashSet;
import java.util.Set;
import org.apache.activemq.broker.region.group.MessageGroupSet;

public class SimpleMessageGroupSet
implements MessageGroupSet {
    private Set<String> set = new HashSet<String>();

    @Override
    public boolean contains(String groupID) {
        return this.set.contains(groupID);
    }

    public void add(String group) {
        this.set.add(group);
    }

    protected Set<String> getUnderlyingSet() {
        return this.set;
    }
}

