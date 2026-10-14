/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBPacketData;
import com.hierynomus.smbj.connection.packet.SMB2PacketHandler;

public class SMB2CompoundedPacketHandler
extends SMB2PacketHandler {
    @Override
    public boolean canHandle(SMBPacketData<?> packetData) {
        return super.canHandle(packetData) && ((SMB2PacketData)packetData).isCompounded();
    }

    @Override
    protected void doSMB2Handle(SMB2PacketData packetData) throws TransportException {
        do {
            this.next.handle(packetData);
            try {
                packetData = packetData.next();
            }
            catch (Buffer.BufferException e) {
                throw new TransportException("Missing compounded message data", e);
            }
        } while (packetData != null);
    }
}

