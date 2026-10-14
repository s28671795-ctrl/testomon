/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import org.apache.activemq.command.Command;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.wireformat.WireFormat;

public class MarshallingTransportFilter
extends TransportFilter {
    private final WireFormat localWireFormat;
    private final WireFormat remoteWireFormat;

    public MarshallingTransportFilter(Transport next, WireFormat localWireFormat, WireFormat remoteWireFormat) {
        super(next);
        this.localWireFormat = localWireFormat;
        this.remoteWireFormat = remoteWireFormat;
    }

    @Override
    public void oneway(Object command) throws IOException {
        this.next.oneway((Command)this.remoteWireFormat.unmarshal(this.localWireFormat.marshal(command)));
    }

    @Override
    public void onCommand(Object command) {
        try {
            this.getTransportListener().onCommand((Command)this.localWireFormat.unmarshal(this.remoteWireFormat.marshal(command)));
        }
        catch (IOException e) {
            this.getTransportListener().onException(e);
        }
    }
}

