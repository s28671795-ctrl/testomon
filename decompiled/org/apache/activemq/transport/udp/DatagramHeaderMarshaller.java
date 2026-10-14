/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.Endpoint;
import org.apache.activemq.transport.udp.DatagramEndpoint;

public class DatagramHeaderMarshaller {
    private Map<SocketAddress, Endpoint> endpoints = new HashMap<SocketAddress, Endpoint>();

    public Endpoint createEndpoint(ByteBuffer readBuffer, SocketAddress address) {
        return this.getEndpoint(address);
    }

    public Endpoint createEndpoint(DatagramPacket datagram, DataInputStream dataIn) {
        return this.getEndpoint(datagram.getSocketAddress());
    }

    public void writeHeader(Command command, ByteBuffer writeBuffer) {
    }

    public void writeHeader(Command command, DataOutputStream dataOut) {
    }

    protected Endpoint getEndpoint(SocketAddress address) {
        Endpoint endpoint = this.endpoints.get(address);
        if (endpoint == null) {
            endpoint = this.createEndpoint(address);
            this.endpoints.put(address, endpoint);
        }
        return endpoint;
    }

    protected Endpoint createEndpoint(SocketAddress address) {
        return new DatagramEndpoint(address.toString(), address);
    }
}

