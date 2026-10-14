/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smbj.connection.OutstandingRequests;
import com.hierynomus.smbj.connection.Request;
import com.hierynomus.smbj.connection.packet.SMB2PacketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMB2AsyncResponsePacketHandler
extends SMB2PacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SMB2AsyncResponsePacketHandler.class);
    private OutstandingRequests outstandingRequests;

    public SMB2AsyncResponsePacketHandler(OutstandingRequests outstandingRequests) {
        this.outstandingRequests = outstandingRequests;
    }

    @Override
    protected void doSMB2Handle(SMB2PacketData packetData) throws TransportException {
        Request request = this.outstandingRequests.getRequestByMessageId(((SMB2PacketHeader)packetData.getHeader()).getMessageId());
        logger.trace("Send/Recv of packet {} took << {} ms >>", (Object)packetData, (Object)(System.currentTimeMillis() - request.getTimestamp().getTime()));
        if (packetData.isIntermediateAsyncResponse()) {
            logger.debug("Received ASYNC packet {} with AsyncId << {} >>", (Object)packetData, (Object)((SMB2PacketHeader)packetData.getHeader()).getAsyncId());
            request.setAsyncId(((SMB2PacketHeader)packetData.getHeader()).getAsyncId());
            return;
        }
        this.next.handle(packetData);
    }
}

