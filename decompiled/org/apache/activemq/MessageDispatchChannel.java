/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.List;
import org.apache.activemq.command.MessageDispatch;

public interface MessageDispatchChannel {
    public void enqueue(MessageDispatch var1);

    public void enqueueFirst(MessageDispatch var1);

    public boolean isEmpty();

    public MessageDispatch dequeue(long var1) throws InterruptedException;

    public MessageDispatch dequeueNoWait();

    public MessageDispatch peek();

    public void start();

    public void stop();

    public void close();

    public void clear();

    public boolean isClosed();

    public int size();

    public Object getMutex();

    public boolean isRunning();

    public List<MessageDispatch> removeAll();
}

