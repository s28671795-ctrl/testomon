/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

public interface Transform<T> {
    public T read(String var1) throws Exception;

    public String write(T var1) throws Exception;
}

