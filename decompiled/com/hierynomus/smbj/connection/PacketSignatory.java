/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection;

import com.hierynomus.mssmb2.SMB2MessageFlag;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.security.Mac;
import com.hierynomus.security.SecurityException;
import com.hierynomus.security.SecurityProvider;
import com.hierynomus.smb.SMBBuffer;
import java.util.Arrays;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketSignatory {
    private static final Logger logger = LoggerFactory.getLogger(PacketSignatory.class);
    private SecurityProvider securityProvider;

    PacketSignatory(SecurityProvider securityProvider) {
        this.securityProvider = securityProvider;
    }

    void init() {
    }

    public SMB2Packet sign(SMB2Packet packet, SecretKey secretKey) {
        if (secretKey != null) {
            return new SignedPacketWrapper(packet, secretKey);
        }
        logger.debug("Not wrapping {} as signed, as no key is set.", (Object)((SMB2PacketHeader)packet.getHeader()).getMessage());
        return packet;
    }

    public boolean verify(SMB2PacketData packet, SecretKey secretKey) {
        try {
            SMBBuffer buffer = packet.getDataBuffer();
            Mac mac = PacketSignatory.getMac(secretKey, this.securityProvider);
            mac.update(buffer.array(), ((SMB2PacketHeader)packet.getHeader()).getHeaderStartPosition(), 48);
            mac.update(SMB2PacketHeader.EMPTY_SIGNATURE);
            mac.update(buffer.array(), 64, ((SMB2PacketHeader)packet.getHeader()).getMessageEndPosition() - 64);
            byte[] signature = mac.doFinal();
            byte[] receivedSignature = ((SMB2PacketHeader)packet.getHeader()).getSignature();
            for (int i = 0; i < 16; ++i) {
                if (signature[i] == receivedSignature[i]) continue;
                logger.error("Signatures for packet {} do not match (received: {}, calculated: {})", packet, Arrays.toString(receivedSignature), Arrays.toString(signature));
                logger.error("Packet {} has header: {}", (Object)packet, packet.getHeader());
                return false;
            }
            return true;
        }
        catch (SecurityException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Mac getMac(SecretKey secretKey, SecurityProvider securityProvider) throws SecurityException {
        Mac mac = securityProvider.getMac(secretKey.getAlgorithm());
        mac.init(secretKey.getEncoded());
        return mac;
    }

    public class SignedPacketWrapper
    extends SMB2Packet {
        private final SMB2Packet wrappedPacket;
        private SecretKey secretKey;

        SignedPacketWrapper(SMB2Packet packet, SecretKey secretKey) {
            this.wrappedPacket = packet;
            this.secretKey = secretKey;
        }

        @Override
        public int getMaxPayloadSize() {
            return this.wrappedPacket.getMaxPayloadSize();
        }

        @Override
        public void write(SMBBuffer buffer) {
            try {
                ((SMB2PacketHeader)this.wrappedPacket.getHeader()).setFlag(SMB2MessageFlag.SMB2_FLAGS_SIGNED);
                int packetStartPos = buffer.wpos();
                SigningBuffer signingBuffer = new SigningBuffer(buffer);
                this.wrappedPacket.write(signingBuffer);
                byte[] signature = signingBuffer.mac.doFinal();
                System.arraycopy(signature, 0, buffer.array(), packetStartPos + 48, 16);
            }
            catch (SecurityException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public SMB2PacketHeader getHeader() {
            return (SMB2PacketHeader)this.wrappedPacket.getHeader();
        }

        @Override
        public long getSequenceNumber() {
            return this.wrappedPacket.getSequenceNumber();
        }

        @Override
        public int getStructureSize() {
            return this.wrappedPacket.getStructureSize();
        }

        @Override
        public String toString() {
            return "Signed(" + this.wrappedPacket.toString() + ")";
        }

        @Override
        public SMB2Packet getPacket() {
            return this.wrappedPacket.getPacket();
        }

        private class SigningBuffer
        extends SMBBuffer {
            private SMBBuffer wrappedBuffer;
            private final Mac mac;

            SigningBuffer(SMBBuffer wrappedBuffer) throws SecurityException {
                this.wrappedBuffer = wrappedBuffer;
                this.mac = PacketSignatory.getMac(SignedPacketWrapper.this.secretKey, PacketSignatory.this.securityProvider);
            }

            @Override
            public Buffer<SMBBuffer> putByte(byte b) {
                this.mac.update(b);
                this.wrappedBuffer.putByte(b);
                return this;
            }

            @Override
            public Buffer<SMBBuffer> putBuffer(Buffer<? extends Buffer<?>> buffer) {
                this.mac.update(buffer.array(), buffer.rpos(), buffer.available());
                this.wrappedBuffer.putBuffer(buffer);
                return this;
            }

            @Override
            public Buffer<SMBBuffer> putRawBytes(byte[] buf, int offset, int length) {
                this.mac.update(buf, offset, length);
                this.wrappedBuffer.putRawBytes(buf, offset, length);
                return this;
            }
        }
    }
}

