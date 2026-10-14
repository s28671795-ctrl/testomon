/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

public interface TransmitCallback {
    public void onSuccess();

    public void onFailure();
}

