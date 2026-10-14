/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb2.SMB2MessageConverter;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smbj.connection.OutstandingRequests;
import com.hierynomus.smbj.connection.Request;
import com.hierynomus.smbj.connection.packet.SMB2PacketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMB2ProcessResponsePacketHandler
extends SMB2PacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SMB2ProcessResponsePacketHandler.class);
    private final SMB2MessageConverter smb2Converter;
    private final OutstandingRequests outstandingRequests;

    public SMB2ProcessResponsePacketHandler(SMB2MessageConverter smb2Converter, OutstandingRequests outstandingRequests) {
        this.smb2Converter = smb2Converter;
        this.outstandingRequests = outstandingRequests;
    }

    @Override
    protected void doSMB2Handle(SMB2PacketData packetData) throws TransportException {
        Request request = this.outstandingRequests.getRequestByMessageId(((SMB2PacketHeader)packetData.getHeader()).getMessageId());
        SMB2Packet packet = null;
        try {
            packet = this.smb2Converter.readPacket(request.getPacket(), packetData);
        }
        catch (Buffer.BufferException e) {
            logger.error("Failed to deserialize SMB2 Packet Data of {}", (Object)packetData);
            throw new TransportException("Unable to deserialize SMB2 Packet Data.", e);
        }
        this.outstandingRequests.receivedResponseFor(((SMB2PacketHeader)packet.getHeader()).getMessageId()).getPromise().deliver(packet);
    }
}

