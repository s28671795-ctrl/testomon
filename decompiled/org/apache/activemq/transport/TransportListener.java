/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;

public interface TransportListener {
    public void onCommand(Object var1);

    public void onException(IOException var1);

    public void transportInterupted();

    public void transportResumed();
}

