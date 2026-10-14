/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.plugin;

import java.io.File;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.plugin.SubQueueSelectorCacheBroker;

public class SubQueueSelectorCacheBrokerPlugin
implements BrokerPlugin {
    private File persistFile;
    private boolean singleSelectorPerDestination = false;
    private boolean ignoreWildcardSelectors = false;
    private long persistInterval = 600000L;

    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        SubQueueSelectorCacheBroker rc = new SubQueueSelectorCacheBroker(broker, this.persistFile);
        rc.setSingleSelectorPerDestination(this.singleSelectorPerDestination);
        rc.setPersistInterval(this.persistInterval);
        rc.setIgnoreWildcardSelectors(this.ignoreWildcardSelectors);
        return rc;
    }

    public void setPersistFile(File persistFile) {
        this.persistFile = persistFile;
    }

    public File getPersistFile() {
        return this.persistFile;
    }

    public boolean isSingleSelectorPerDestination() {
        return this.singleSelectorPerDestination;
    }

    public void setSingleSelectorPerDestination(boolean singleSelectorPerDestination) {
        this.singleSelectorPerDestination = singleSelectorPerDestination;
    }

    public long getPersistInterval() {
        return this.persistInterval;
    }

    public void setPersistInterval(long persistInterval) {
        this.persistInterval = persistInterval;
    }

    public boolean isIgnoreWildcardSelectors() {
        return this.ignoreWildcardSelectors;
    }

    public void setIgnoreWildcardSelectors(boolean ignoreWildcardSelectors) {
        this.ignoreWildcardSelectors = ignoreWildcardSelectors;
    }
}

