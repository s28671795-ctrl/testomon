/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb.SMB1NotSupportedException;
import com.hierynomus.mssmb.SMB1PacketData;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBPacketData;
import com.hierynomus.smbj.connection.packet.AbstractIncomingPacketHandler;

public class SMB1PacketHandler
extends AbstractIncomingPacketHandler {
    @Override
    public boolean canHandle(SMBPacketData<?> packetData) {
        return packetData instanceof SMB1PacketData;
    }

    @Override
    protected void doHandle(SMBPacketData<?> packetData) throws TransportException {
        throw new SMB1NotSupportedException();
    }
}

