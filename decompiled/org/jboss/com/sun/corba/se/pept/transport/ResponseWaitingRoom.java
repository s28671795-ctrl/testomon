/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.pept.transport;

import org.jboss.com.sun.corba.se.pept.encoding.InputObject;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;

public interface ResponseWaitingRoom {
    public void registerWaiter(MessageMediator var1);

    public InputObject waitForResponse(MessageMediator var1);

    public void responseReceived(InputObject var1);

    public void unregisterWaiter(MessageMediator var1);

    public int numberRegistered();
}

