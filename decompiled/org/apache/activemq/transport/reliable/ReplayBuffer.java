/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.reliable;

import java.io.IOException;
import org.apache.activemq.transport.reliable.ReplayBufferListener;
import org.apache.activemq.transport.reliable.Replayer;

public interface ReplayBuffer {
    public void addBuffer(int var1, Object var2);

    public void setReplayBufferListener(ReplayBufferListener var1);

    public void replayMessages(int var1, int var2, Replayer var3) throws IOException;
}

