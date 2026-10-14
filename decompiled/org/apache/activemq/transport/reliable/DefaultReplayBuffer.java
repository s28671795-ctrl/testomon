/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.reliable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.transport.reliable.ReplayBuffer;
import org.apache.activemq.transport.reliable.ReplayBufferListener;
import org.apache.activemq.transport.reliable.Replayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultReplayBuffer
implements ReplayBuffer {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultReplayBuffer.class);
    private final int size;
    private ReplayBufferListener listener;
    private Map<Integer, Object> map;
    private int lowestCommandId = 1;
    private Object lock = new Object();

    public DefaultReplayBuffer(int size) {
        this.size = size;
        this.map = this.createMap(size);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addBuffer(int commandId, Object buffer) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Adding command ID: " + commandId + " to replay buffer: " + this + " object: " + buffer);
        }
        Object object = this.lock;
        synchronized (object) {
            int max = this.size - 1;
            while (this.map.size() >= max) {
                Object evictedBuffer = this.map.remove(++this.lowestCommandId);
                this.onEvictedBuffer(this.lowestCommandId, evictedBuffer);
            }
            this.map.put(commandId, buffer);
        }
    }

    @Override
    public void setReplayBufferListener(ReplayBufferListener bufferPoolAdapter) {
        this.listener = bufferPoolAdapter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void replayMessages(int fromCommandId, int toCommandId, Replayer replayer) throws IOException {
        if (replayer == null) {
            throw new IllegalArgumentException("No Replayer parameter specified");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Buffer: " + this + " replaying messages from: " + fromCommandId + " to: " + toCommandId);
        }
        for (int i = fromCommandId; i <= toCommandId; ++i) {
            Object buffer = null;
            Object object = this.lock;
            synchronized (object) {
                buffer = this.map.get(i);
            }
            replayer.sendBuffer(i, buffer);
        }
    }

    protected Map<Integer, Object> createMap(int maximumSize) {
        return new HashMap<Integer, Object>(maximumSize);
    }

    protected void onEvictedBuffer(int commandId, Object buffer) {
        if (this.listener != null) {
            this.listener.onBufferDiscarded(commandId, buffer);
        }
    }
}

