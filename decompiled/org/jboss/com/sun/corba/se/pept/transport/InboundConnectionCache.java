/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import org.jboss.com.sun.corba.se.pept.transport.Acceptor;
import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.jboss.com.sun.corba.se.pept.transport.ConnectionCache;

public interface InboundConnectionCache
extends ConnectionCache {
    public Connection get(Acceptor var1);

    public void put(Acceptor var1, Connection var2);

    public void remove(Connection var1);
}

