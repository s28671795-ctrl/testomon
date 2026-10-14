/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.io.IOException;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Observable;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public interface Conduit
extends Observable {
    public void prepare(Message var1) throws IOException;

    public void close(Message var1) throws IOException;

    public EndpointReferenceType getTarget();

    public void close();
}

