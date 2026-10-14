/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;

public interface ConduitSelector {
    public void prepare(Message var1);

    public Conduit selectConduit(Message var1);

    public void complete(Exchange var1);

    public Endpoint getEndpoint();

    public void setEndpoint(Endpoint var1);
}

