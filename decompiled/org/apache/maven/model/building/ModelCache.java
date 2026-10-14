/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

public interface ModelCache {
    public void put(String var1, String var2, String var3, String var4, Object var5);

    public Object get(String var1, String var2, String var3, String var4);
}

