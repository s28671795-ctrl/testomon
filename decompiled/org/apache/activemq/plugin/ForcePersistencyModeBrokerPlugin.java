/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.plugin;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.plugin.ForcePersistencyModeBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForcePersistencyModeBrokerPlugin
implements BrokerPlugin {
    private static Logger LOG = LoggerFactory.getLogger(ForcePersistencyModeBrokerPlugin.class);
    private boolean persistenceFlag = false;

    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        ForcePersistencyModeBroker pB = new ForcePersistencyModeBroker(broker);
        pB.setPersistenceFlag(this.isPersistenceForced());
        LOG.info("Installing ForcePersistencyModeBroker plugin: persistency enforced={}", (Object)pB.isPersistent());
        return pB;
    }

    public void setPersistenceFlag(boolean persistenceFlag) {
        this.persistenceFlag = persistenceFlag;
    }

    public final boolean isPersistenceForced() {
        return this.persistenceFlag;
    }
}

