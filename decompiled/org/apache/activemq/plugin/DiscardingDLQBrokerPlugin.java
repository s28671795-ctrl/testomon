/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.plugin;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.plugin.DiscardingDLQBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardingDLQBrokerPlugin
implements BrokerPlugin {
    public static Logger log = LoggerFactory.getLogger(DiscardingDLQBrokerPlugin.class);
    private boolean dropTemporaryTopics = true;
    private boolean dropTemporaryQueues = true;
    private boolean dropAll = true;
    private String dropOnly;
    private int reportInterval = 1000;

    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        log.info("Installing Discarding Dead Letter Queue broker plugin[dropAll={}; dropTemporaryTopics={}; dropTemporaryQueues={}; dropOnly={}; reportInterval={}]", this.isDropAll(), this.isDropTemporaryTopics(), this.isDropTemporaryQueues(), this.getDropOnly(), this.reportInterval);
        DiscardingDLQBroker cb = new DiscardingDLQBroker(broker);
        cb.setDropAll(this.isDropAll());
        cb.setDropTemporaryQueues(this.isDropTemporaryQueues());
        cb.setDropTemporaryTopics(this.isDropTemporaryTopics());
        cb.setDestFilter(this.getDestFilter());
        cb.setReportInterval(this.getReportInterval());
        return cb;
    }

    public boolean isDropAll() {
        return this.dropAll;
    }

    public boolean isDropTemporaryQueues() {
        return this.dropTemporaryQueues;
    }

    public boolean isDropTemporaryTopics() {
        return this.dropTemporaryTopics;
    }

    public String getDropOnly() {
        return this.dropOnly;
    }

    public int getReportInterval() {
        return this.reportInterval;
    }

    public void setDropTemporaryTopics(boolean dropTemporaryTopics) {
        this.dropTemporaryTopics = dropTemporaryTopics;
    }

    public void setDropTemporaryQueues(boolean dropTemporaryQueues) {
        this.dropTemporaryQueues = dropTemporaryQueues;
    }

    public void setDropAll(boolean dropAll) {
        this.dropAll = dropAll;
    }

    public void setDropOnly(String dropOnly) {
        this.dropOnly = dropOnly;
    }

    public void setReportInterval(int reportInterval) {
        this.reportInterval = reportInterval;
    }

    public Pattern[] getDestFilter() {
        if (this.getDropOnly() == null) {
            return null;
        }
        ArrayList<Pattern> list = new ArrayList<Pattern>();
        StringTokenizer t2 = new StringTokenizer(this.getDropOnly(), " ");
        while (t2.hasMoreTokens()) {
            String s2 = t2.nextToken();
            if (s2 == null || s2.trim().length() <= 0) continue;
            list.add(Pattern.compile(s2));
        }
        if (list.size() == 0) {
            return null;
        }
        return list.toArray(new Pattern[0]);
    }
}

