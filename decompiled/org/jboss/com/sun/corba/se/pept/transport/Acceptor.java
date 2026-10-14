/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import org.jboss.com.sun.corba.se.pept.broker.Broker;
import org.jboss.com.sun.corba.se.pept.encoding.InputObject;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.jboss.com.sun.corba.se.pept.transport.EventHandler;
import org.jboss.com.sun.corba.se.pept.transport.InboundConnectionCache;

public interface Acceptor {
    public boolean initialize();

    public boolean initialized();

    public String getConnectionCacheType();

    public void setConnectionCache(InboundConnectionCache var1);

    public InboundConnectionCache getConnectionCache();

    public boolean shouldRegisterAcceptEvent();

    public void accept();

    public void close();

    public EventHandler getEventHandler();

    public MessageMediator createMessageMediator(Broker var1, Connection var2);

    public MessageMediator finishCreatingMessageMediator(Broker var1, Connection var2, MessageMediator var3);

    public InputObject createInputObject(Broker var1, MessageMediator var2);

    public OutputObject createOutputObject(Broker var1, MessageMediator var2);
}

