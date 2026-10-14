/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.NetworkBridgeView;
import org.apache.activemq.broker.jmx.NetworkDestinationViewMBean;
import org.apache.activemq.management.TimeStatisticImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkDestinationView
implements NetworkDestinationViewMBean {
    private static final Logger LOG = LoggerFactory.getLogger(NetworkDestinationView.class);
    private TimeStatisticImpl timeStatistic = new TimeStatisticImpl("networkEnqueue", "network messages enqueued");
    private final String name;
    private final NetworkBridgeView networkBridgeView;
    private long lastTime = -1L;

    public NetworkDestinationView(NetworkBridgeView networkBridgeView, String name) {
        this.networkBridgeView = networkBridgeView;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void resetStats() {
        this.timeStatistic.reset();
        this.lastTime = -1L;
    }

    @Override
    public long getCount() {
        return this.timeStatistic.getCount();
    }

    @Override
    public double getRate() {
        return this.timeStatistic.getAveragePerSecond();
    }

    public void messageSent() {
        long currentTime = System.currentTimeMillis();
        long time = 0L;
        if (this.lastTime < 0L) {
            time = 0L;
            this.lastTime = currentTime;
        } else {
            time = currentTime - this.lastTime;
        }
        this.timeStatistic.addTime(time);
        this.lastTime = currentTime;
    }

    public long getLastAccessTime() {
        return this.timeStatistic.getLastSampleTime();
    }

    public void close() {
        this.networkBridgeView.removeNetworkDestinationView(this);
    }
}

