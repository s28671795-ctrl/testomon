/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb2.DeadLetterPacketData;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;
import com.hierynomus.mssmb2.SMB2MessageFlag;
import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBHeader;
import com.hierynomus.smbj.connection.PacketSignatory;
import com.hierynomus.smbj.connection.SessionTable;
import com.hierynomus.smbj.connection.packet.SMB2PacketHandler;
import com.hierynomus.smbj.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMB2SignatureVerificationPacketHandler
extends SMB2PacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SMB2SignatureVerificationPacketHandler.class);
    private SessionTable sessionTable;
    private PacketSignatory signatory;

    public SMB2SignatureVerificationPacketHandler(SessionTable sessionTable, PacketSignatory signatory) {
        this.sessionTable = sessionTable;
        this.signatory = signatory;
    }

    @Override
    protected void doSMB2Handle(SMB2PacketData packetData) throws TransportException {
        long sessionId;
        Session session;
        if (((SMB2PacketHeader)packetData.getHeader()).getMessageId() == -1L) {
            logger.debug("Message ID is 0xFFFFFFFFFFFFFFFF, no verification necessary");
            this.next.handle(packetData);
            return;
        }
        if (packetData.isDecrypted()) {
            logger.debug("Passthrough Signature Verification as packet is decrypted");
            this.next.handle(packetData);
            return;
        }
        if (((SMB2PacketHeader)packetData.getHeader()).isFlagSet(SMB2MessageFlag.SMB2_FLAGS_SIGNED)) {
            long sessionId2 = ((SMB2PacketHeader)packetData.getHeader()).getSessionId();
            if (sessionId2 == 0L || ((SMB2PacketHeader)packetData.getHeader()).getMessage() == SMB2MessageCommandCode.SMB2_SESSION_SETUP) {
                this.next.handle(packetData);
                return;
            }
            Session session2 = this.sessionTable.find(sessionId2);
            if (session2 == null) {
                logger.error("Could not find session << {} >> for packet {}.", (Object)sessionId2, (Object)packetData);
                this.next.handle(new DeadLetterPacketData((SMBHeader)packetData.getHeader()));
                return;
            }
            if (this.signatory.verify(packetData, session2.getSigningKey((SMB2PacketHeader)packetData.getHeader(), false))) {
                logger.debug("Signature for packet {} verified.", (Object)packetData);
                this.next.handle(packetData);
                return;
            }
            logger.warn("Invalid packet signature for packet {}", (Object)packetData);
            this.next.handle(new DeadLetterPacketData((SMBHeader)packetData.getHeader()));
            return;
        }
        if (!((SMB2PacketHeader)packetData.getHeader()).isFlagSet(SMB2MessageFlag.SMB2_FLAGS_SIGNED) && !packetData.isIntermediateAsyncResponse() && !packetData.isOplockBreakNotification() && (session = this.sessionTable.find(sessionId = ((SMB2PacketHeader)packetData.getHeader()).getSessionId())) != null && session.isSigningRequired()) {
            logger.warn("Illegal request, session requires message signing, but packet {} is not signed.", (Object)packetData);
            this.next.handle(new DeadLetterPacketData((SMBHeader)packetData.getHeader()));
            return;
        }
        this.next.handle(packetData);
    }
}

