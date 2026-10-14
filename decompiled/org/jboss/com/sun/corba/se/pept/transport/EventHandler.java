/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import org.jboss.com.sun.corba.se.pept.transport.Acceptor;
import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.jboss.com.sun.corba.se.spi.orbutil.threadpool.Work;

public interface EventHandler {
    public void setUseSelectThreadToWait(boolean var1);

    public boolean shouldUseSelectThreadToWait();

    public SelectableChannel getChannel();

    public int getInterestOps();

    public void setSelectionKey(SelectionKey var1);

    public SelectionKey getSelectionKey();

    public void handleEvent();

    public void setUseWorkerThreadForEvent(boolean var1);

    public boolean shouldUseWorkerThreadForEvent();

    public void setWork(Work var1);

    public Work getWork();

    public Acceptor getAcceptor();

    public Connection getConnection();
}

