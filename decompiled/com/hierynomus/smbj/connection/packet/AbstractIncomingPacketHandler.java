/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBPacketData;
import com.hierynomus.smbj.connection.packet.IncomingPacketHandler;

public abstract class AbstractIncomingPacketHandler
implements IncomingPacketHandler {
    protected IncomingPacketHandler next;

    @Override
    public void handle(SMBPacketData<?> packetData) throws TransportException {
        if (this.canHandle(packetData)) {
            this.doHandle(packetData);
        } else {
            this.next.handle(packetData);
        }
    }

    @Override
    public IncomingPacketHandler setNext(IncomingPacketHandler handler) {
        this.next = handler;
        return this;
    }

    protected abstract boolean canHandle(SMBPacketData<?> var1);

    protected abstract void doHandle(SMBPacketData<?> var1) throws TransportException;
}

