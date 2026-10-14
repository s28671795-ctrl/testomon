/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.transport;

import java.util.Collection;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.jboss.com.sun.corba.se.pept.transport.TransportManager;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplate;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;

public interface CorbaTransportManager
extends TransportManager {
    public static final String SOCKET_OR_CHANNEL_CONNECTION_CACHE = "SocketOrChannelConnectionCache";

    public Collection<Object> getAcceptors(String var1, ObjectAdapterId var2);

    public void addToIORTemplate(IORTemplate var1, Policies var2, String var3, String var4, ObjectAdapterId var5);
}

