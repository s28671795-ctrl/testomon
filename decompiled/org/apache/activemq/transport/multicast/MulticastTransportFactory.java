/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.multicast;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.multicast.MulticastTransport;
import org.apache.activemq.transport.udp.UdpTransportFactory;
import org.apache.activemq.wireformat.WireFormat;

public class MulticastTransportFactory
extends UdpTransportFactory {
    @Override
    protected Transport createTransport(URI location, WireFormat wf) throws UnknownHostException, IOException {
        OpenWireFormat wireFormat = this.asOpenWireFormat(wf);
        return new MulticastTransport(wireFormat, location);
    }
}

