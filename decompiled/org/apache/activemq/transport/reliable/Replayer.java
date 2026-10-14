/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.reliable;

import java.io.IOException;

public interface Replayer {
    public void sendBuffer(int var1, Object var2) throws IOException;
}

