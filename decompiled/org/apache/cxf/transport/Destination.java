/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.io.IOException;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.transport.Observable;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public interface Destination
extends Observable {
    public EndpointReferenceType getAddress();

    public Conduit getBackChannel(Message var1) throws IOException;

    public void shutdown();

    @Override
    public MessageObserver getMessageObserver();
}

