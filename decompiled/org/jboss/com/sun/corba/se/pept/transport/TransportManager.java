/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import java.util.Collection;
import org.jboss.com.sun.corba.se.pept.transport.Acceptor;
import org.jboss.com.sun.corba.se.pept.transport.ByteBufferPool;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfo;
import org.jboss.com.sun.corba.se.pept.transport.InboundConnectionCache;
import org.jboss.com.sun.corba.se.pept.transport.OutboundConnectionCache;
import org.jboss.com.sun.corba.se.pept.transport.Selector;

public interface TransportManager {
    public ByteBufferPool getByteBufferPool(int var1);

    public OutboundConnectionCache getOutboundConnectionCache(ContactInfo var1);

    public Collection<OutboundConnectionCache> getOutboundConnectionCaches();

    public InboundConnectionCache getInboundConnectionCache(Acceptor var1);

    public Collection<InboundConnectionCache> getInboundConnectionCaches();

    public Selector getSelector(int var1);

    public void registerAcceptor(Acceptor var1);

    public Collection<Acceptor> getAcceptors();

    public void unregisterAcceptor(Acceptor var1);

    public void close();
}

