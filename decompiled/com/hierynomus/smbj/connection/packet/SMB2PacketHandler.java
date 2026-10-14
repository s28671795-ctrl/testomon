/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBPacketData;
import com.hierynomus.smbj.connection.packet.AbstractIncomingPacketHandler;

public abstract class SMB2PacketHandler
extends AbstractIncomingPacketHandler {
    @Override
    public boolean canHandle(SMBPacketData<?> packetData) {
        return packetData instanceof SMB2PacketData;
    }

    @Override
    protected void doHandle(SMBPacketData<?> packetData) throws TransportException {
        this.doSMB2Handle((SMB2PacketData)packetData);
    }

    protected abstract void doSMB2Handle(SMB2PacketData var1) throws TransportException;
}

