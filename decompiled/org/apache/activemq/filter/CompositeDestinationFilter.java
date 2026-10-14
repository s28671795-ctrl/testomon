/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationFilter;

public class CompositeDestinationFilter
extends DestinationFilter {
    private DestinationFilter[] filters;

    public CompositeDestinationFilter(ActiveMQDestination destination) {
        ActiveMQDestination[] destinations = destination.getCompositeDestinations();
        this.filters = new DestinationFilter[destinations.length];
        for (int i = 0; i < destinations.length; ++i) {
            ActiveMQDestination childDestination = destinations[i];
            this.filters[i] = DestinationFilter.parseFilter(childDestination);
        }
    }

    @Override
    public boolean matches(ActiveMQDestination destination) {
        for (int i = 0; i < this.filters.length; ++i) {
            if (!this.filters[i].matches(destination)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isWildcard() {
        for (DestinationFilter filter2 : this.filters) {
            if (!filter2.isWildcard()) continue;
            return true;
        }
        return false;
    }
}

