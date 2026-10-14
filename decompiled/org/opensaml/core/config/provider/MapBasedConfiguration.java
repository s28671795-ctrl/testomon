/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.opensaml.core.config.Configuration;

public class MapBasedConfiguration
implements Configuration {
    private Map<String, Map<String, Object>> storage = new ConcurrentHashMap<String, Map<String, Object>>();

    @Override
    public <T> T get(Class<T> configClass, String partitionName) {
        Map<String, Object> partition = this.getPartition(partitionName);
        return (T)partition.get(configClass.getName());
    }

    @Override
    public <T, I extends T> void register(Class<T> configClass, I configuration, String partitionName) {
        Map<String, Object> partition = this.getPartition(partitionName);
        partition.put(configClass.getName(), configuration);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T deregister(Class<T> configClass, String partitionName) {
        Map<String, Object> partition;
        Map<String, Object> map = partition = this.getPartition(partitionName);
        synchronized (map) {
            Object old = partition.get(configClass.getName());
            partition.remove(configClass.getName());
            return (T)old;
        }
    }

    private synchronized Map<String, Object> getPartition(String partitionName) {
        Map<String, Object> partition = this.storage.get(partitionName);
        if (partition == null) {
            partition = new ConcurrentHashMap<String, Object>();
            this.storage.put(partitionName, partition);
        }
        return partition;
    }
}

