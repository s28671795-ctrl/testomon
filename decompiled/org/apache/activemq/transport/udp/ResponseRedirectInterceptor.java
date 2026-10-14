/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import org.apache.activemq.command.Command;
import org.apache.activemq.command.Endpoint;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.transport.udp.UdpTransport;

public class ResponseRedirectInterceptor
extends TransportFilter {
    private final UdpTransport transport;

    public ResponseRedirectInterceptor(Transport next, UdpTransport transport) {
        super(next);
        this.transport = transport;
    }

    @Override
    public void onCommand(Object o) {
        Command command = (Command)o;
        Endpoint from = command.getFrom();
        this.transport.setTargetEndpoint(from);
        super.onCommand(command);
    }
}

