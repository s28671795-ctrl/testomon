/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.broker;

import org.jboss.com.sun.corba.se.pept.protocol.ClientInvocationInfo;
import org.jboss.com.sun.corba.se.pept.transport.TransportManager;

public interface Broker {
    public ClientInvocationInfo createOrIncrementInvocationInfo();

    public ClientInvocationInfo getInvocationInfo();

    public void releaseOrDecrementInvocationInfo();

    public TransportManager getTransportManager();
}

