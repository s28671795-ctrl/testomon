/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

public interface Value {
    public Object getValue();

    public void setValue(Object var1);

    public Class getType();

    public int getLength();

    public boolean isReference();
}

