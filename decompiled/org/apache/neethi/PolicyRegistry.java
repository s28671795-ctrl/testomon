/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import org.apache.neethi.Policy;

public interface PolicyRegistry {
    public void register(String var1, Policy var2);

    public Policy lookup(String var1);

    public void remove(String var1);
}

