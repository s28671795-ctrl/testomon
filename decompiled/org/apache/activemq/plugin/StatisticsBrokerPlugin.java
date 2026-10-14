/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.plugin;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.plugin.StatisticsBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsBrokerPlugin
implements BrokerPlugin {
    private static Logger LOG = LoggerFactory.getLogger(StatisticsBrokerPlugin.class);

    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        StatisticsBroker answer = new StatisticsBroker(broker);
        LOG.info("Installing StaticsBroker");
        return answer;
    }
}

