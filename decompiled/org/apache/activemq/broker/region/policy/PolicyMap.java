/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.List;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationMap;
import org.apache.activemq.filter.DestinationMapEntry;

public class PolicyMap
extends DestinationMap {
    private PolicyEntry defaultEntry;

    public PolicyEntry getEntryFor(ActiveMQDestination destination) {
        PolicyEntry answer = (PolicyEntry)this.chooseValue(destination);
        if (answer == null) {
            answer = this.getDefaultEntry();
        }
        return answer;
    }

    public void setPolicyEntries(List entries) {
        super.setEntries(entries);
    }

    public PolicyEntry getDefaultEntry() {
        return this.defaultEntry;
    }

    public void setDefaultEntry(PolicyEntry defaultEntry) {
        this.defaultEntry = defaultEntry;
    }

    @Override
    protected Class<? extends DestinationMapEntry> getEntryClass() {
        return PolicyEntry.class;
    }
}

