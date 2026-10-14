/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationFilter;

public class SimpleDestinationFilter
extends DestinationFilter {
    private ActiveMQDestination destination;

    public SimpleDestinationFilter(ActiveMQDestination destination) {
        this.destination = destination;
    }

    @Override
    public boolean matches(ActiveMQDestination destination) {
        return this.destination.equals(destination);
    }

    @Override
    public boolean isWildcard() {
        return false;
    }
}

