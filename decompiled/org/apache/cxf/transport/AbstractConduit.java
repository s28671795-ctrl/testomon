/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.AbstractObservable;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public abstract class AbstractConduit
extends AbstractObservable
implements Conduit {
    protected final EndpointReferenceType target;

    public AbstractConduit(EndpointReferenceType t2) {
        this.target = t2;
    }

    @Override
    public EndpointReferenceType getTarget() {
        return this.target;
    }

    @Override
    public void close(Message msg) throws IOException {
        InputStream in;
        OutputStream os = msg.getContent(OutputStream.class);
        if (os != null) {
            os.close();
        }
        if ((in = msg.getContent(InputStream.class)) != null) {
            in.close();
        }
    }

    @Override
    public void close() {
    }

    public String toString() {
        return "conduit: " + this.getClass() + System.identityHashCode(this) + "target: " + (this.getTarget() != null && this.getTarget().getAddress() != null ? this.getTarget().getAddress().getValue() : "null");
    }
}

