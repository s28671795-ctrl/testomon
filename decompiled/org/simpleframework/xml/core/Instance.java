/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

interface Instance {
    public Object getInstance() throws Exception;

    public Object setInstance(Object var1) throws Exception;

    public boolean isReference();

    public Class getType();
}

