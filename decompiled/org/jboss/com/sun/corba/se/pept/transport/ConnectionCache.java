/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import org.jboss.com.sun.corba.se.pept.transport.Connection;

public interface ConnectionCache {
    public String getCacheType();

    public void stampTime(Connection var1);

    public long numberOfConnections();

    public long numberOfIdleConnections();

    public long numberOfBusyConnections();

    public boolean reclaim();
}

