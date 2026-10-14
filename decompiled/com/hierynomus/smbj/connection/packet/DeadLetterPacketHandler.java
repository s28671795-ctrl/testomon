/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBPacketData;
import com.hierynomus.smbj.connection.packet.AbstractIncomingPacketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadLetterPacketHandler
extends AbstractIncomingPacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeadLetterPacketHandler.class);

    @Override
    protected boolean canHandle(SMBPacketData<?> packetData) {
        return true;
    }

    @Override
    protected void doHandle(SMBPacketData<?> packetData) throws TransportException {
        logger.warn("Packet << {} >> ended up in dead letters", (Object)packetData);
    }
}

