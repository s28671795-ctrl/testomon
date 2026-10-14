/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.io.IOException;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.AbstractConduit;
import org.apache.cxf.transport.AbstractObservable;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.DestinationWithEndpoint;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public abstract class AbstractDestination
extends AbstractObservable
implements Destination,
DestinationWithEndpoint {
    protected final EndpointReferenceType reference;
    protected final EndpointInfo endpointInfo;
    protected final Bus bus;

    public AbstractDestination(EndpointReferenceType ref, EndpointInfo ei) {
        this(null, ref, ei);
    }

    public AbstractDestination(Bus b, EndpointReferenceType ref, EndpointInfo ei) {
        this.reference = ref;
        this.endpointInfo = ei;
        this.bus = b;
    }

    @Override
    public EndpointReferenceType getAddress() {
        return this.reference;
    }

    @Override
    public Conduit getBackChannel(Message inMessage) throws IOException {
        return this.getInbuiltBackChannel(inMessage);
    }

    @Override
    public void shutdown() {
    }

    protected abstract Conduit getInbuiltBackChannel(Message var1);

    private static EndpointReferenceType getAnonymousEndpointReference() {
        EndpointReferenceType reference = new EndpointReferenceType();
        AttributedURIType a = new AttributedURIType();
        a.setValue("http://www.w3.org/2005/08/addressing/anonymous");
        reference.setAddress(a);
        return reference;
    }

    @Override
    public EndpointInfo getEndpointInfo() {
        return this.endpointInfo;
    }

    protected abstract class AbstractBackChannelConduit
    extends AbstractConduit {
        public AbstractBackChannelConduit() {
            super(AbstractDestination.getAnonymousEndpointReference());
        }

        @Override
        public void setMessageObserver(MessageObserver observer) {
        }

        @Override
        protected Logger getLogger() {
            return AbstractDestination.this.getLogger();
        }
    }
}

