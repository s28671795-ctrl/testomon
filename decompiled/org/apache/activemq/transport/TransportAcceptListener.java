/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import org.apache.activemq.transport.Transport;

public interface TransportAcceptListener {
    public void onAccept(Transport var1);

    public void onAcceptError(Exception var1);
}

