/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

public interface LocalTransactionEventListener {
    public void beginEvent();

    public void commitEvent();

    public void rollbackEvent();
}

