/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.Set;
import org.apache.activemq.broker.jmx.VirtualDestinationSelectorCacheViewMBean;
import org.apache.activemq.plugin.SubQueueSelectorCacheBroker;

public class VirtualDestinationSelectorCacheView
implements VirtualDestinationSelectorCacheViewMBean {
    private final SubQueueSelectorCacheBroker selectorCacheBroker;

    public VirtualDestinationSelectorCacheView(SubQueueSelectorCacheBroker selectorCacheBroker) {
        this.selectorCacheBroker = selectorCacheBroker;
    }

    @Override
    public Set<String> selectorsForDestination(String destinationName) {
        return this.selectorCacheBroker.getSelectorsForDestination(destinationName);
    }

    @Override
    public boolean deleteSelectorForDestination(String destinationName, String selector) {
        return this.selectorCacheBroker.deleteSelectorForDestination(destinationName, selector);
    }

    @Override
    public boolean deleteAllSelectorsForDestination(String destinationName) {
        return this.selectorCacheBroker.deleteAllSelectorsForDestination(destinationName);
    }
}

