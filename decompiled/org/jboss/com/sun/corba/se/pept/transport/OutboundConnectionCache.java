/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import org.jboss.com.sun.corba.se.pept.transport.Connection;
import org.jboss.com.sun.corba.se.pept.transport.ConnectionCache;
import org.jboss.com.sun.corba.se.pept.transport.ContactInfo;

public interface OutboundConnectionCache
extends ConnectionCache {
    public Connection get(ContactInfo var1);

    public void put(ContactInfo var1, Connection var2);

    public void remove(ContactInfo var1);
}

