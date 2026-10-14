/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import org.jboss.com.sun.corba.se.pept.broker.Broker;
import org.jboss.com.sun.corba.se.pept.encoding.InputObject;
import org.jboss.com.sun.corba.se.pept.encoding.OutputObject;
import org.jboss.com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfoList;
import org.jboss.com.sun.corba.se.pept.transport.OutboundConnectionCache;

public interface ContactInfo {
    public Broker getBroker();

    public ContactInfoList getContactInfoList();

    public ClientRequestDispatcher getClientRequestDispatcher();

    public boolean isConnectionBased();

    public boolean shouldCacheConnection();

    public String getConnectionCacheType();

    public void setConnectionCache(OutboundConnectionCache var1);

    public OutboundConnectionCache getConnectionCache();

    public Connection createConnection();

    public MessageMediator createMessageMediator(Broker var1, ContactInfo var2, Connection var3, String var4, boolean var5);

    public MessageMediator createMessageMediator(Broker var1, Connection var2);

    public MessageMediator finishCreatingMessageMediator(Broker var1, Connection var2, MessageMediator var3);

    public InputObject createInputObject(Broker var1, MessageMediator var2);

    public OutputObject createOutputObject(MessageMediator var1);

    public int hashCode();
}

