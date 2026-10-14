/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.mssmb2.DeadLetterPacketData;
import com.hierynomus.mssmb2.SMB2CompressionTransformHeader;
import com.hierynomus.mssmb2.SMB2DecryptedPacketData;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.mssmb2.SMB2TransformHeader;
import com.hierynomus.mssmb2.SMB3CompressedPacketData;
import com.hierynomus.mssmb2.SMB3EncryptedPacketData;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBHeader;
import com.hierynomus.smb.SMBPacketData;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.connection.PacketEncryptor;
import com.hierynomus.smbj.connection.SessionTable;
import com.hierynomus.smbj.connection.packet.AbstractIncomingPacketHandler;
import com.hierynomus.smbj.session.Session;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMB3DecryptingPacketHandler
extends AbstractIncomingPacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SMB3DecryptingPacketHandler.class);
    private SessionTable sessionTable;
    private PacketEncryptor encryptor;

    public SMB3DecryptingPacketHandler(SessionTable sessionTable, PacketEncryptor encryptor) {
        this.sessionTable = sessionTable;
        this.encryptor = encryptor;
    }

    @Override
    protected boolean canHandle(SMBPacketData<?> packetData) {
        return packetData instanceof SMB3EncryptedPacketData;
    }

    @Override
    protected void doHandle(SMBPacketData<?> packetData) throws TransportException {
        SMB3EncryptedPacketData data = (SMB3EncryptedPacketData)packetData;
        logger.debug("Decrypting packet {}", (Object)data);
        if (!this.encryptor.canDecrypt(data)) {
            this.next.handle(new DeadLetterPacketData((SMBHeader)packetData.getHeader()));
            return;
        }
        long sessionId = ((SMB2TransformHeader)data.getHeader()).getSessionId();
        Session session = this.sessionTable.find(sessionId);
        if (session == null) {
            this.next.handle(new DeadLetterPacketData((SMBHeader)packetData.getHeader()));
            return;
        }
        byte[] decrypted = this.encryptor.decrypt(data, session.getSessionContext().getDecryptionKey());
        byte[] decryptedProtocolId = Arrays.copyOf(decrypted, 4);
        if (Arrays.equals(decryptedProtocolId, SMB2TransformHeader.ENCRYPTED_PROTOCOL_ID)) {
            logger.error("Encountered a nested encrypted packet in packet {}, disconnecting the transport", (Object)packetData);
            throw new TransportException("Cannot nest an encrypted packet in encrypted packet " + packetData);
        }
        if (Arrays.equals(decryptedProtocolId, SMB2CompressionTransformHeader.COMPRESSED_PROTOCOL_ID)) {
            this.handleCompressedPacket(packetData, decrypted);
            return;
        }
        if (Arrays.equals(decryptedProtocolId, SMB2PacketHeader.PROTOCOL_ID)) {
            this.handleSMB2Packet(decrypted, data);
            return;
        }
        logger.error("Could not determine the encrypted packet contents of packet {}", (Object)packetData);
        throw new TransportException("Could not determine the encrypted packet data, disconnecting");
    }

    private void handleCompressedPacket(SMBPacketData<?> packetData, byte[] decrypted) throws TransportException {
        logger.debug("Packet {} is compressed.", (Object)packetData);
        try {
            this.next.handle(new SMB3CompressedPacketData(decrypted, true));
            return;
        }
        catch (Buffer.BufferException e) {
            throw new SMBRuntimeException("Could not load compression header", e);
        }
    }

    private void handleSMB2Packet(byte[] decrypted, SMB3EncryptedPacketData packetData) throws TransportException {
        try {
            SMB2DecryptedPacketData nextPacket = new SMB2DecryptedPacketData(decrypted);
            logger.debug("Decrypted packet {} is packet {}.", (Object)packetData, (Object)nextPacket);
            if (((SMB2PacketHeader)nextPacket.getHeader()).getSessionId() != ((SMB2TransformHeader)packetData.getHeader()).getSessionId()) {
                logger.error("Mismatched sessionId between encrypted packet {} and decrypted contents {}", (Object)packetData, (Object)nextPacket);
                this.next.handle(new DeadLetterPacketData((SMBHeader)nextPacket.getHeader()));
            } else {
                this.next.handle(nextPacket);
            }
        }
        catch (Buffer.BufferException e) {
            throw new SMBRuntimeException("Could not load SMB2 Packet", e);
        }
    }
}

