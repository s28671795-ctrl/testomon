/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import org.jboss.com.sun.corba.se.pept.encoding.InputObject;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.pept.transport.Acceptor;
import org.jboss.com.sun.corba.se.pept.transport.ConnectionCache;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfo;
import org.jboss.com.sun.corba.se.pept.transport.EventHandler;

public interface Connection {
    public boolean shouldRegisterReadEvent();

    public boolean shouldRegisterServerReadEvent();

    public boolean read();

    public void close();

    public Acceptor getAcceptor();

    public ContactInfo getContactInfo();

    public EventHandler getEventHandler();

    public boolean isServer();

    public boolean isBusy();

    public long getTimeStamp();

    public void setTimeStamp(long var1);

    public void setState(String var1);

    public void writeLock();

    public void writeUnlock();

    public void sendWithoutLock(OutputObject var1);

    public void registerWaiter(MessageMediator var1);

    public InputObject waitForResponse(MessageMediator var1);

    public void unregisterWaiter(MessageMediator var1);

    public void setConnectionCache(ConnectionCache var1);

    public ConnectionCache getConnectionCache();
}

