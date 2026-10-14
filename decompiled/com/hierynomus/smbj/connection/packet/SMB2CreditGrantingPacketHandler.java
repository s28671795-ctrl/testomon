/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smbj.connection.SequenceWindow;
import com.hierynomus.smbj.connection.packet.SMB2PacketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMB2CreditGrantingPacketHandler
extends SMB2PacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SMB2CreditGrantingPacketHandler.class);
    private SequenceWindow sequenceWindow;

    public SMB2CreditGrantingPacketHandler(SequenceWindow sequenceWindow) {
        this.sequenceWindow = sequenceWindow;
    }

    @Override
    protected void doSMB2Handle(SMB2PacketData packetData) throws TransportException {
        this.sequenceWindow.creditsGranted(((SMB2PacketHeader)packetData.getHeader()).getCreditResponse());
        logger.debug("Server granted us {} credits for {}, now available: {} credits", ((SMB2PacketHeader)packetData.getHeader()).getCreditResponse(), packetData, this.sequenceWindow.available());
        this.next.handle(packetData);
    }
}

