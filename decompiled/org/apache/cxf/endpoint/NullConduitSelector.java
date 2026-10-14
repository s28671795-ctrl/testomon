/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;

@NoJSR250Annotations
public class NullConduitSelector
implements ConduitSelector {
    private Endpoint endpoint;

    @Override
    public void prepare(Message message) {
    }

    @Override
    public Conduit selectConduit(Message message) {
        return null;
    }

    @Override
    public void complete(Exchange exchange) {
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @Override
    public void setEndpoint(Endpoint ep) {
        this.endpoint = ep;
    }
}

