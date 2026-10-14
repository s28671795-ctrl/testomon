/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory;

public interface Cache {
    public Object get(Object var1);

    public Object put(Object var1, Object var2);

    public Object remove(Object var1);

    public void close();

    public int size();
}

