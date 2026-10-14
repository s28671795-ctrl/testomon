/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import org.apache.activemq.transport.FutureResponse;

public interface ResponseCallback {
    public void onCompletion(FutureResponse var1);
}

