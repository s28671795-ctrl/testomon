/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyRegistry;

public class PolicyRegistryImpl
implements PolicyRegistry {
    private Map<String, Policy> reg = new ConcurrentHashMap<String, Policy>(16, 0.75f, 4);

    @Override
    public Policy lookup(String key) {
        return this.reg.get(key);
    }

    @Override
    public void register(String key, Policy policy) {
        this.reg.put(key, policy);
    }

    @Override
    public void remove(String key) {
        this.reg.remove(key);
    }
}

