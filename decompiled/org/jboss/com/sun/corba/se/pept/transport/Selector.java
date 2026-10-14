/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import org.jboss.com.sun.corba.se.pept.transport.EventHandler;

public interface Selector {
    public void setTimeout(long var1);

    public long getTimeout();

    public void registerInterestOps(EventHandler var1);

    public void registerForEvent(EventHandler var1);

    public void unregisterForEvent(EventHandler var1);

    public void close();
}

