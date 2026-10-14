/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.Set;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface VirtualDestinationSelectorCacheViewMBean {
    @MBeanInfo(value="Dump raw cache of selectors organized by destination")
    public Set<String> selectorsForDestination(String var1);

    @MBeanInfo(value="Delete a selector for a destination. Selector must match what returns from selectorsForDestination operation")
    public boolean deleteSelectorForDestination(String var1, String var2);

    @MBeanInfo(value="Dump raw cache of selectors organized by destination")
    public boolean deleteAllSelectorsForDestination(String var1);
}

