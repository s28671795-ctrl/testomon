/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.management.CountStatisticImpl;
import org.apache.activemq.management.StatsImpl;

public class RegionStatistics
extends StatsImpl {
    private CountStatisticImpl advisoryDestinations = new CountStatisticImpl("advisoryTopics", "The number of advisory destinations in the region");
    private CountStatisticImpl destinations = new CountStatisticImpl("destinations", "The number of regular (non-adivsory) destinations in the region");
    private CountStatisticImpl allDestinations = new CountStatisticImpl("allDestinations", "The total number of destinations, including advisory destinations, in the region");

    public RegionStatistics() {
        this(true);
    }

    public RegionStatistics(boolean enabled) {
        this.addStatistic("advisoryDestinations", this.advisoryDestinations);
        this.addStatistic("destinations", this.destinations);
        this.addStatistic("allDestinations", this.allDestinations);
        this.setEnabled(enabled);
    }

    public CountStatisticImpl getAdvisoryDestinations() {
        return this.advisoryDestinations;
    }

    public CountStatisticImpl getDestinations() {
        return this.destinations;
    }

    public CountStatisticImpl getAllDestinations() {
        return this.allDestinations;
    }

    @Override
    public void reset() {
        super.reset();
        this.advisoryDestinations.reset();
        this.destinations.reset();
        this.allDestinations.reset();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.advisoryDestinations.setEnabled(enabled);
        this.destinations.setEnabled(enabled);
        this.allDestinations.setEnabled(enabled);
    }

    public void setParent(RegionStatistics parent) {
        if (parent != null) {
            this.advisoryDestinations.setParent(parent.getAdvisoryDestinations());
            this.destinations.setParent(parent.getDestinations());
            this.allDestinations.setParent(parent.getAllDestinations());
        } else {
            this.advisoryDestinations.setParent(null);
            this.destinations.setParent(null);
            this.allDestinations.setParent(null);
        }
    }
}

