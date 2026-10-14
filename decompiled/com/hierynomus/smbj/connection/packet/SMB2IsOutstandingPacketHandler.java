/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb2.DeadLetterPacketData;
import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBHeader;
import com.hierynomus.smbj.connection.OutstandingRequests;
import com.hierynomus.smbj.connection.packet.SMB2PacketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMB2IsOutstandingPacketHandler
extends SMB2PacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SMB2IsOutstandingPacketHandler.class);
    private OutstandingRequests outstandingRequests;

    public SMB2IsOutstandingPacketHandler(OutstandingRequests outstandingRequests) {
        this.outstandingRequests = outstandingRequests;
    }

    @Override
    protected void doSMB2Handle(SMB2PacketData packetData) throws TransportException {
        long messageId = packetData.getSequenceNumber();
        if (!this.outstandingRequests.isOutstanding(messageId) && !packetData.isOplockBreakNotification()) {
            logger.error("Received response with unknown sequence number << {} >>", (Object)messageId);
            this.next.handle(new DeadLetterPacketData((SMBHeader)packetData.getHeader()));
        } else {
            this.next.handle(packetData);
        }
    }
}

