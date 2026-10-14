/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection;

import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb.messages.SMB1ComNegotiateRequest;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2GlobalCapability;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.mssmb2.SMB3CompressionAlgorithm;
import com.hierynomus.mssmb2.SMB3EncryptionCipher;
import com.hierynomus.mssmb2.SMB3HashAlgorithm;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.mssmb2.messages.SMB2NegotiateRequest;
import com.hierynomus.mssmb2.messages.SMB2NegotiateResponse;
import com.hierynomus.mssmb2.messages.negotiate.SMB2CompressionCapabilities;
import com.hierynomus.mssmb2.messages.negotiate.SMB2EncryptionCapabilities;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContext;
import com.hierynomus.mssmb2.messages.negotiate.SMB2PreauthIntegrityCapabilities;
import com.hierynomus.protocol.commons.concurrent.AFuture;
import com.hierynomus.protocol.commons.concurrent.Futures;
import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.security.MessageDigest;
import com.hierynomus.security.SecurityException;
import com.hierynomus.smb.Packets;
import com.hierynomus.smb.SMBPacket;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.connection.ConnectionContext;
import com.hierynomus.smbj.connection.Request;
import com.hierynomus.smbj.server.Server;
import com.hierynomus.smbj.utils.DigestUtil;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SMBProtocolNegotiator {
    private static final Logger logger = LoggerFactory.getLogger(SMBProtocolNegotiator.class);
    private final SmbConfig config;
    private final ConnectionContext connectionContext;
    private Connection connection;
    private NegotiationContext negotiationContext = new NegotiationContext();
    private static final int SALT_LENGTH = 32;

    public SMBProtocolNegotiator(Connection connection, SmbConfig config, ConnectionContext connectionContext) {
        this.connection = connection;
        this.config = config;
        this.connectionContext = connectionContext;
    }

    void negotiateDialect() throws TransportException {
        logger.debug("Negotiating dialects {}", (Object)this.config.getSupportedDialects());
        SMB2NegotiateResponse resp = this.config.isUseMultiProtocolNegotiate() ? this.multiProtocolNegotiate() : this.smb2OnlyNegotiate();
        this.negotiationContext.negotiationResponse = resp;
        if (!NtStatus.isSuccess(((SMB2PacketHeader)resp.getHeader()).getStatusCode())) {
            throw new SMBApiException((SMB2PacketHeader)resp.getHeader(), "Failure during dialect negotiation");
        }
        this.initializeNegotiationContext();
        this.initializeOrValidateServerDetails();
        this.connectionContext.negotiated(this.negotiationContext);
        logger.debug("Negotiated the following connection settings: {}", (Object)this.connectionContext);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void initializeNegotiationContext() {
        SMB2Dialect dialect = this.negotiationContext.negotiationResponse.getDialect();
        if (dialect == SMB2Dialect.SMB_3_1_1) {
            List<SMB2NegotiateContext> negotiateContextList = this.negotiationContext.negotiationResponse.getNegotiateContextList();
            if (negotiateContextList == null) throw new IllegalStateException("negotiate context list is null for SMB 3.1.1 dialect");
            boolean seenPreAuth = false;
            boolean seenEncryption = false;
            boolean seenCompression = false;
            block5: for (SMB2NegotiateContext negotiateContext : negotiateContextList) {
                switch (negotiateContext.getNegotiateContextType()) {
                    case SMB2_PREAUTH_INTEGRITY_CAPABILITIES: {
                        if (seenPreAuth) {
                            throw new IllegalStateException("SMB2_PREAUTH_INTEGRITY_CAPABILITIES should only appear once in the NegotiateContextList");
                        }
                        seenPreAuth = true;
                        this.handlePreAuthNegotiateContext((SMB2PreauthIntegrityCapabilities)negotiateContext);
                        continue block5;
                    }
                    case SMB2_ENCRYPTION_CAPABILITIES: {
                        if (seenEncryption) {
                            throw new IllegalStateException("SMB2_ENCRYPTION_CAPABILITIES should only appear once in the NegotiateContextList");
                        }
                        seenEncryption = true;
                        this.handleEncryptionNegotiateContext((SMB2EncryptionCapabilities)negotiateContext);
                        continue block5;
                    }
                    case SMB2_COMPRESSION_CAPABILITIES: {
                        if (seenCompression) {
                            throw new IllegalStateException("SMB2_COMPRESSION_CAPABILITIES should only appear once in the NegotiateContextList");
                        }
                        seenCompression = true;
                        this.handleCompressionNegotiateContext((SMB2CompressionCapabilities)negotiateContext);
                        continue block5;
                    }
                }
                throw new IllegalStateException("unknown negotiate context type");
            }
            return;
        } else {
            if (!dialect.isSmb3x() || !this.negotiationContext.negotiationResponse.getCapabilities().contains(SMB2GlobalCapability.SMB2_GLOBAL_CAP_ENCRYPTION)) return;
            this.negotiationContext.cipher = SMB3EncryptionCipher.AES_128_CCM;
        }
    }

    private void handleCompressionNegotiateContext(SMB2CompressionCapabilities negotiateContext) {
        List<SMB3CompressionAlgorithm> compressionAlgorithms = negotiateContext.getCompressionAlgorithms();
        if (compressionAlgorithms.size() == 0) {
            throw new IllegalStateException("The SMB2CompressionCapabilities NegotiateContext should contain at least 1 algorithm");
        }
        if (compressionAlgorithms.size() == 1 && compressionAlgorithms.get(0) == SMB3CompressionAlgorithm.NONE) {
            logger.info("SMB3CompressionAlgorithm is 'NONE', continuing without compression");
            return;
        }
        this.negotiationContext.compressionIds = EnumSet.copyOf(compressionAlgorithms);
    }

    private void handleEncryptionNegotiateContext(SMB2EncryptionCapabilities negotiateContext) {
        List<SMB3EncryptionCipher> cipherList = negotiateContext.getCipherList();
        if (cipherList.size() != 1) {
            throw new IllegalStateException("The SMB2EncryptionCapabilities NegotiateContext does not contain exactly 1 cipher");
        }
        this.negotiationContext.cipher = cipherList.get(0);
    }

    private void handlePreAuthNegotiateContext(SMB2PreauthIntegrityCapabilities negotiateContext) {
        if (negotiateContext.getHashAlgorithms().size() != 1) {
            throw new IllegalStateException("The SMB2PreauthIntegrityCapabilities NegotiateContext does not contain exactly 1 hash algorithm");
        }
        SMB3HashAlgorithm hashAlgorithm = negotiateContext.getHashAlgorithms().get(0);
        this.negotiationContext.preauthIntegrityHashId = hashAlgorithm;
        NegotiationContext.access$402(this.negotiationContext, this.calculatePreauthHashValue());
    }

    private byte[] calculatePreauthHashValue() {
        MessageDigest messageDigest;
        byte[] requestBytes = Packets.getPacketBytes(this.negotiationContext.negotiationRequest);
        byte[] responseBytes = Packets.getPacketBytes(this.negotiationContext.negotiationResponse);
        String algorithmName = this.negotiationContext.preauthIntegrityHashId.getAlgorithmName();
        try {
            messageDigest = this.config.getSecurityProvider().getDigest(algorithmName);
        }
        catch (SecurityException se) {
            throw new SMBRuntimeException("Cannot get the message digest for " + algorithmName, se);
        }
        byte[] hashValue = new byte[messageDigest.getDigestLength()];
        hashValue = DigestUtil.digest(messageDigest, hashValue, requestBytes);
        hashValue = DigestUtil.digest(messageDigest, hashValue, responseBytes);
        return hashValue;
    }

    private SMB2NegotiateResponse smb2OnlyNegotiate() throws TransportException {
        byte[] salt = new byte[32];
        this.config.getRandomProvider().nextBytes(salt);
        SMB2NegotiateRequest negotiatePacket = new SMB2NegotiateRequest(this.config.getSupportedDialects(), this.connectionContext.getClientGuid(), this.config.isSigningRequired(), this.config.getClientCapabilities(), salt);
        this.negotiationContext.negotiationRequest = negotiatePacket;
        return (SMB2NegotiateResponse)this.connection.sendAndReceive(negotiatePacket);
    }

    private SMB2NegotiateResponse multiProtocolNegotiate() throws TransportException {
        SMB1ComNegotiateRequest negotiatePacket = new SMB1ComNegotiateRequest(this.config.getSupportedDialects());
        long l = this.connection.sequenceWindow.get();
        if (l != 0L) {
            throw new IllegalStateException("The SMBv1 SMB_COM_NEGOTIATE packet needs to be the first packet sent.");
        }
        Request request = new Request(negotiatePacket, l, UUID.randomUUID());
        this.connection.outstandingRequests.registerOutstanding(request);
        this.negotiationContext.negotiationRequest = negotiatePacket;
        this.connection.transport.write(negotiatePacket);
        AFuture future = request.getFuture(null);
        SMB2Packet packet = (SMB2Packet)Futures.get(future, this.config.getTransactTimeout(), TimeUnit.MILLISECONDS, TransportException.Wrapper);
        if (!(packet instanceof SMB2NegotiateResponse)) {
            throw new IllegalStateException("Expected a SMB2 NEGOTIATE Response to our SMB_COM_NEGOTIATE, but got: " + packet);
        }
        SMB2NegotiateResponse negotiateResponse = (SMB2NegotiateResponse)packet;
        if (negotiateResponse.getDialect() == SMB2Dialect.SMB_2XX) {
            return this.smb2OnlyNegotiate();
        }
        return negotiateResponse;
    }

    private void initializeOrValidateServerDetails() throws TransportException {
        Server temp = this.connectionContext.getServer();
        SMB2NegotiateResponse response = this.negotiationContext.negotiationResponse;
        temp.init(response.getServerGuid(), response.getDialect(), response.getSecurityMode(), response.getCapabilities());
        Server cachedServer = this.connection.serverList.lookup(temp.getServerName());
        if (cachedServer == null) {
            this.connection.serverList.registerServer(temp);
            this.negotiationContext.server = temp;
        } else if (temp.validate(cachedServer)) {
            this.negotiationContext.server = cachedServer;
        } else {
            throw new TransportException(String.format("Different server found for same hostname '%s', disconnecting...", temp.getServerName()));
        }
    }

    public static class NegotiationContext {
        private SMBPacket<?, ?> negotiationRequest;
        private SMB2NegotiateResponse negotiationResponse;
        private SMB3EncryptionCipher cipher;
        private SMB3HashAlgorithm preauthIntegrityHashId;
        private Set<SMB3CompressionAlgorithm> compressionIds = EnumSet.noneOf(SMB3CompressionAlgorithm.class);
        private byte[] preauthIntegrityHashValue;
        private Server server;

        public SMBPacket<?, ?> getNegotiationRequest() {
            return this.negotiationRequest;
        }

        public SMB2NegotiateResponse getNegotiationResponse() {
            return this.negotiationResponse;
        }

        public SMB3EncryptionCipher getCipher() {
            return this.cipher;
        }

        public SMB3HashAlgorithm getPreauthIntegrityHashId() {
            return this.preauthIntegrityHashId;
        }

        public Set<SMB3CompressionAlgorithm> getCompressionIds() {
            return this.compressionIds;
        }

        public Server getServer() {
            return this.server;
        }

        public byte[] getPreauthIntegrityHashValue() {
            return this.preauthIntegrityHashValue;
        }

        static /* synthetic */ byte[] access$402(NegotiationContext x0, byte[] x1) {
            x0.preauthIntegrityHashValue = x1;
            return x1;
        }
    }
}

