/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection;

import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.mssmb2.SMB2TransformHeader;
import com.hierynomus.mssmb2.SMB3EncryptedPacketData;
import com.hierynomus.mssmb2.SMB3EncryptionCipher;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.security.AEADBlockCipher;
import com.hierynomus.security.Cipher;
import com.hierynomus.security.SecurityException;
import com.hierynomus.security.SecurityProvider;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.connection.ConnectionContext;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketEncryptor {
    private static final Logger logger = LoggerFactory.getLogger(PacketEncryptor.class);
    private SecurityProvider securityProvider;
    private SMB3EncryptionCipher cipher;
    private SMB2Dialect dialect;
    private AtomicInteger nonceCounter = new AtomicInteger(0);

    public PacketEncryptor(SecurityProvider securityProvider) {
        this.securityProvider = securityProvider;
    }

    void init(ConnectionContext connectionContext) {
        this.dialect = connectionContext.getNegotiatedProtocol().getDialect();
        this.cipher = connectionContext.getNegotiatedProtocol().getDialect().equals((Object)SMB2Dialect.SMB_3_1_1) ? connectionContext.getCipherId() : SMB3EncryptionCipher.AES_128_CCM;
        logger.info("Initialized PacketEncryptor with Cipher << {} >>", (Object)this.cipher);
    }

    public boolean canDecrypt(SMB3EncryptedPacketData packetData) {
        return this.dialect.isSmb3x() && packetData.getDataBuffer().available() != 0 && ((SMB2TransformHeader)packetData.getHeader()).getFlagsEncryptionAlgorithm() == 1;
    }

    public byte[] decrypt(SMB3EncryptedPacketData packetData, SecretKey decryptionKey) {
        byte[] realNonce = Arrays.copyOf(((SMB2TransformHeader)packetData.getHeader()).getNonce(), this.cipher.getNonceLength());
        try {
            byte[] aad = this.createAAD((SMB2TransformHeader)packetData.getHeader());
            byte[] cipherText = packetData.getCipherText();
            byte[] signature = ((SMB2TransformHeader)packetData.getHeader()).getSignature();
            AEADBlockCipher aeadBlockCipher = this.securityProvider.getAEADBlockCipher(this.cipher.getAlgorithmName());
            aeadBlockCipher.init(Cipher.CryptMode.DECRYPT, decryptionKey.getEncoded(), new GCMParameterSpec(128, realNonce));
            aeadBlockCipher.updateAAD(aad, 0, aad.length);
            byte[] bytes = aeadBlockCipher.update(cipherText, 0, cipherText.length);
            byte[] bytes2 = aeadBlockCipher.doFinal(signature, 0, signature.length);
            if (bytes != null && bytes.length != 0) {
                byte[] decrypted = new byte[bytes.length + bytes2.length];
                System.arraycopy(bytes, 0, decrypted, 0, bytes.length);
                System.arraycopy(bytes2, 0, decrypted, bytes.length, bytes2.length);
                return decrypted;
            }
            return bytes2;
        }
        catch (SecurityException e) {
            logger.error("Security exception while decrypting packet << {} >>", (Object)packetData);
            throw new SMBRuntimeException(e);
        }
        catch (Buffer.BufferException be) {
            logger.error("Could not read cipherText from packet << {} >>", (Object)packetData);
            throw new SMBRuntimeException("Could not read cipherText from packet", be);
        }
    }

    public SMB2Packet encrypt(SMB2Packet packet, SecretKey encryptionKey) {
        if (encryptionKey != null) {
            return new EncryptedPacketWrapper(packet, encryptionKey);
        }
        logger.debug("Not wrapping {} as encrypted, as no key is set.", (Object)((SMB2PacketHeader)packet.getHeader()).getMessage());
        return packet;
    }

    byte[] createAAD(SMB2TransformHeader header) {
        SMBBuffer b = new SMBBuffer();
        header.writeTo(b);
        b.rpos(20);
        return b.getCompactData();
    }

    byte[] getNewNonce() {
        long nonce = System.nanoTime();
        SMBBuffer b = new SMBBuffer();
        b.putUInt64(nonce);
        int padding = this.cipher.getNonceLength() - 8;
        b.putReserved(padding);
        return b.getCompactData();
    }

    public class EncryptedPacketWrapper
    extends SMB2Packet {
        private final SMB2Packet packet;
        private final SecretKey encryptionKey;

        public EncryptedPacketWrapper(SMB2Packet packet, SecretKey encryptionKey) {
            this.packet = packet;
            this.encryptionKey = encryptionKey;
        }

        @Override
        public void write(SMBBuffer buffer) {
            byte[] cipherTextWithMac;
            SMBBuffer wrappedPacketPlain = new SMBBuffer();
            this.packet.write(wrappedPacketPlain);
            byte[] plainText = wrappedPacketPlain.getCompactData();
            byte[] nonceField = PacketEncryptor.this.getNewNonce();
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, nonceField);
            SMB2TransformHeader header = new SMB2TransformHeader(nonceField, plainText.length, ((SMB2PacketHeader)this.packet.getHeader()).getSessionId());
            byte[] aad = PacketEncryptor.this.createAAD(header);
            try {
                AEADBlockCipher aeadBlockCipher = PacketEncryptor.this.securityProvider.getAEADBlockCipher(PacketEncryptor.this.cipher.getAlgorithmName());
                aeadBlockCipher.init(Cipher.CryptMode.ENCRYPT, this.encryptionKey.getEncoded(), parameterSpec);
                aeadBlockCipher.updateAAD(aad, 0, aad.length);
                cipherTextWithMac = aeadBlockCipher.doFinal(plainText, 0, plainText.length);
            }
            catch (SecurityException e) {
                logger.error("Security exception while encrypting packet << {} >>", this.packet.getHeader());
                throw new SMBRuntimeException(e);
            }
            if (cipherTextWithMac.length != plainText.length + 16) {
                throw new IllegalStateException("Invalid length for cipherText after encryption.");
            }
            byte[] signature = new byte[16];
            System.arraycopy(cipherTextWithMac, plainText.length, signature, 0, signature.length);
            header.setSignature(signature);
            header.writeTo(buffer);
            buffer.putRawBytes(cipherTextWithMac, 0, plainText.length);
        }

        @Override
        public SMB2PacketHeader getHeader() {
            return (SMB2PacketHeader)this.packet.getHeader();
        }

        @Override
        public int getMaxPayloadSize() {
            return this.packet.getMaxPayloadSize();
        }

        @Override
        public long getSequenceNumber() {
            return this.packet.getSequenceNumber();
        }

        @Override
        public int getStructureSize() {
            return this.packet.getStructureSize();
        }

        @Override
        public String toString() {
            return "Encrypted[" + this.packet.toString() + "]";
        }

        @Override
        public SMB2Packet getPacket() {
            return this.packet.getPacket();
        }
    }
}

