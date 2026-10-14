/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyRegistry;

public class PolicyRegistryImpl
implements PolicyRegistry {
    private PolicyRegistry parent;
    private Map<String, Policy> reg = new ConcurrentHashMap<String, Policy>();

    public PolicyRegistryImpl() {
    }

    public PolicyRegistryImpl(PolicyRegistry parent) {
        this.parent = parent;
    }

    @Override
    public Policy lookup(String key) {
        Policy policy = this.reg.get(key);
        if (policy == null && this.parent != null) {
            return this.parent.lookup(key);
        }
        return policy;
    }

    @Override
    public void register(String key, Policy policy) {
        this.reg.put(key, policy);
    }

    @Override
    public void remove(String key) {
        this.reg.remove(key);
    }

    public void setParent(PolicyRegistry parent) {
        this.parent = parent;
    }

    public PolicyRegistry getParent() {
        return this.parent;
    }
}

