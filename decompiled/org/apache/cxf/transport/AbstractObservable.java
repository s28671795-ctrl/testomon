/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.transport.Observable;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;

public abstract class AbstractObservable
implements Observable {
    protected MessageObserver incomingObserver;

    @Override
    public synchronized void setMessageObserver(MessageObserver observer) {
        if (observer != this.incomingObserver) {
            MessageObserver old = this.incomingObserver;
            if (observer != null) {
                this.incomingObserver = observer;
                Logger logger2 = this.getLogger();
                if (logger2.isLoggable(Level.FINE)) {
                    logger2.fine("registering incoming observer: " + observer);
                }
                if (old == null) {
                    try {
                        this.activate();
                    }
                    catch (RuntimeException ex) {
                        this.incomingObserver = null;
                        throw ex;
                    }
                }
            } else {
                if (old != null) {
                    Logger logger3 = this.getLogger();
                    if (logger3.isLoggable(Level.FINE)) {
                        logger3.fine("unregistering incoming observer: " + old);
                    }
                    this.deactivate();
                }
                this.incomingObserver = observer;
            }
        }
    }

    @Override
    public MessageObserver getMessageObserver() {
        return this.incomingObserver;
    }

    protected static EndpointReferenceType getTargetReference(EndpointInfo ei, Bus bus) {
        return AbstractObservable.getTargetReference(ei, null, bus);
    }

    protected static EndpointReferenceType getTargetReference(EndpointInfo ei, EndpointReferenceType t2, Bus bus) {
        EndpointReferenceType ref;
        if (null == t2) {
            ref = new EndpointReferenceType();
            AttributedURIType address = new AttributedURIType();
            address.setValue(ei.getAddress());
            ref.setAddress(address);
            if (ei.getService() != null) {
                EndpointReferenceUtils.setServiceAndPortName(ref, ei.getService().getName(), ei.getName().getLocalPart());
            }
        } else {
            ref = t2;
        }
        return ref;
    }

    protected void activate() {
    }

    protected void deactivate() {
    }

    protected abstract Logger getLogger();
}

