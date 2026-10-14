/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.io.Closeable;
import java.io.IOException;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.MessageObserver;

public class PreexistingConduitSelector
implements ConduitSelector,
Closeable {
    private Conduit selectedConduit;
    private Endpoint endpoint;

    public PreexistingConduitSelector(Conduit c) {
        this(c, null);
    }

    public PreexistingConduitSelector(Conduit c, Endpoint e) {
        this.selectedConduit = c;
        this.endpoint = e;
    }

    @Override
    public void prepare(Message message) {
        MessageObserver observer = message.getExchange().get(MessageObserver.class);
        if (observer != null) {
            this.selectedConduit.setMessageObserver(observer);
        }
    }

    @Override
    public Conduit selectConduit(Message message) {
        return this.selectedConduit;
    }

    @Override
    public void complete(Exchange exchange) {
        try {
            if (exchange.getInMessage() != null) {
                this.selectedConduit.close(exchange.getInMessage());
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @Override
    public void setEndpoint(Endpoint ep) {
        this.endpoint = ep;
    }

    @Override
    public void close() throws IOException {
        this.selectedConduit.close();
    }
}

